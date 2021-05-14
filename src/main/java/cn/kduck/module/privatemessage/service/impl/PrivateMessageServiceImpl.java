package cn.kduck.module.privatemessage.service.impl;

import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.event.Event;
import cn.kduck.core.event.EventPublisher;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ParamMap;
import cn.kduck.module.privatemessage.MessageGroupProvider;
import cn.kduck.module.privatemessage.query.MessageGroupQuery;
import cn.kduck.module.privatemessage.query.MessageUserQuery;
import cn.kduck.module.privatemessage.query.PrivateMessageQuery;
import cn.kduck.module.privatemessage.service.MessageGroup;
import cn.kduck.module.privatemessage.service.MessageReceiver;
import cn.kduck.module.privatemessage.service.MessageUser;
import cn.kduck.module.privatemessage.service.PrivateMessage;
import cn.kduck.module.privatemessage.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PrivateMessageServiceImpl extends DefaultService implements PrivateMessageService {

    @Autowired(required = false)
    private List<MessageGroupProvider> providerList;

    @Autowired
    private EventPublisher eventPublisher;

    @Override
    @Transactional
    public void addPrivateMessage(PrivateMessage privateMessage, MessageReceiver[] receiver) {
        super.add(CODE_PRIVATE_MESSAGE,privateMessage);

        addMessageReceiver(privateMessage.getMessageId(),receiver);

        eventPublisher.publish(new Event(EVENT_PRIVATE_MESSAGE_ADD,privateMessage));
    }

    @Override
    public void addMessageReceiver(String messageId, MessageReceiver[] receiver) {
        List<MessageUser> userList = new ArrayList();
        for (MessageReceiver messageReceiver : receiver) {
            if(messageReceiver.receiverType().equals(MessageUser.class.getSimpleName())) {
                MessageUser messageUser = (MessageUser) messageReceiver;
                messageUser.setMessageId(messageId);
                messageUser.setIsRead(MessageUser.IS_READ_NO);
                messageUser.setIsDelete(MessageUser.IS_DELETE_NO);
                userList.add(messageUser);
            }else{
                MessageGroup messageGroup = (MessageGroup) messageReceiver;

                if(providerList == null && providerList.isEmpty()){
                    throw new RuntimeException("当前没有任何消息分组提供者实现，请实现" + MessageGroupProvider.class.getName() + "接口进行支持扩展");
                }

                if(!supportedGroup(messageGroup.getGroupType())){
                    throw new RuntimeException("不支持消息分组类型：" + messageGroup.getGroupType());
                }

                if(messageGroup.getExpiredDate() == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH, 100);//FIXME
                    messageGroup.setExpiredDate(calendar.getTime());
                }

                messageGroup.setMessageId(messageId);
                super.add(CODE_MESSAGE_GROUP,messageGroup);
            }
        }

        if(!userList.isEmpty()){
            super.batchAdd(CODE_MESSAGE_USER,userList);
        }
    }

    @Override
    public void deleteMessageReceiver(String messageId, MessageReceiver[] receiver) {
//        super.delete();
    }

    @Override
    public void updatePrivateMessage(PrivateMessage privateMessage) {
        super.update(CODE_PRIVATE_MESSAGE,privateMessage);
    }

    @Override
    @Transactional
    public void deletePrivateMessage(String[] ids) {
        super.delete(CODE_MESSAGE_USER,PrivateMessage.MESSAGE_ID,ids);
        super.delete(CODE_MESSAGE_GROUP,PrivateMessage.MESSAGE_ID,ids);
        super.delete(CODE_PRIVATE_MESSAGE,ids);
    }

    @Override
    public PrivateMessage getPrivateMessage(String id) {
        return super.getForBean(CODE_PRIVATE_MESSAGE,id,PrivateMessage::new);
    }

    @Override
    public PrivateMessage getPrivateMessageByUser(String messageId,String userId) {
        Map paramMap = ParamMap.create("messageId",messageId).set("userId",userId).toMap();
        QuerySupport query = super.getQuery(PrivateMessageQuery.class, paramMap);
        return super.getForBean(query, PrivateMessage::new);
    }

    @Override
    public List<PrivateMessage> listPrivateMessage(Page page) {
        Map<String, Object> paramMap = ParamMap.create().toMap();
        QuerySupport query = super.getQuery(PrivateMessageQuery.class, paramMap);
        return super.listForBean(query,page,PrivateMessage::new);
    }

    @Override
    public List<PrivateMessage> listPrivateMessageByUser(String userId, Page page) {

        pullUserMessages(userId);

        Map<String, Object> paramMap = ParamMap.create("userId",userId).toMap();

        QuerySupport query = super.getQuery(PrivateMessageQuery.class, paramMap);
        return super.listForBean(query, page, PrivateMessage::new);
    }

    @Override
    public void updateMessageReadState(String messageId, String userId) {
        MessageUser messageUser = getMessageUser(messageId,userId);

        if(messageUser == null) {
            return;
        }

        int isRead = messageUser.getIsRead().intValue() == MessageUser.IS_READ_NO ? MessageUser.IS_READ_YES : MessageUser.IS_READ_NO;
        messageUser.setIsRead(isRead);
        super.update(CODE_MESSAGE_USER,messageUser);
    }

    @Override
    public long countMessageUnread(String userId) {
        pullUserMessages(userId);
        Map<String, Object> paramMap = ParamMap.create("userId", userId).set("isRead", MessageUser.IS_READ_NO).set("isDelete", MessageUser.IS_DELETE_NO).toMap();
        QuerySupport query = super.getQuery(MessageUserQuery.class, paramMap);
        return super.count(query);
    }

    @Override
    public void deleteUserMessage(String messageId, String userId) {
        MessageUser messageUser = getMessageUser(messageId,userId);

        if(messageUser == null) {
            return;
        }
        messageUser.setIsDelete(MessageUser.IS_DELETE_NO);
        super.update(CODE_MESSAGE_USER,messageUser);
    }

    private void pullUserMessages(String userId){
        if(providerList != null){
            Map<String,Object> paramMap = ParamMap.create("expiredDate",new Date()).toMap();
            QuerySupport query = super.getQuery(MessageGroupQuery.class, paramMap);
            List<MessageGroup> messageGroups = super.listForBean(query, MessageGroup::new);

            List<MessageUser> messageUserList = new ArrayList();

            for (MessageGroup messageGroup : messageGroups) {
                for (MessageGroupProvider groupProvider : providerList) {
                    if(groupProvider.groupType().equals(messageGroup.getGroupType())
                            && groupProvider.isReceiveUser(messageGroup.getRelationId(),userId)) {
                        //判断是否已经存在当前消息，不存在则添加
                        MessageUser messageUser = getMessageUser(messageGroup.getMessageId(), userId,true);
                        if(messageUser == null){
                            messageUserList.add(new MessageUser(messageGroup.getMessageId(),userId));
                        }
                    }
                }
            }

            if(!messageUserList.isEmpty()){
                super.batchAdd(CODE_MESSAGE_USER,messageUserList);
            }
        }
    }

    private MessageUser getMessageUser(String messageId, String userId){
        return getMessageUser(messageId,userId,false);
    }

    private MessageUser getMessageUser(String messageId, String userId,boolean incDelete){
        Map paramMap = ParamMap.create("messageId",messageId).set("userId",userId).toMap();
        if(!incDelete){
            paramMap.put("isDelete",MessageUser.IS_DELETE_NO);
        }
        QuerySupport query = super.getQuery(MessageUserQuery.class, paramMap);
        return super.getForBean(query, MessageUser::new);
    }

    private boolean supportedGroup(String groupType){
        for (MessageGroupProvider messageGroupProvider : providerList) {
            if(groupType.equals(messageGroupProvider.groupType())){
                return true;
            }
        }
        return false;
    }
}
