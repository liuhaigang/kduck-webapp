package cn.kduck.module.privatemessage.service.impl;

import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.DeleteBuilder;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.service.ValueMap;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PrivateMessageServiceImpl extends DefaultService implements PrivateMessageService {

    @Autowired(required = false)
    private List<MessageGroupProvider> providerList;

    @Override
    @Transactional
    public void addPrivateMessage(PrivateMessage privateMessage, MessageReceiver[] receiver) {
        super.add(CODE_PRIVATE_MESSAGE,privateMessage);

        addMessageReceiver(privateMessage.getMessageId(),receiver);
    }

    @Override
    public void addMessageReceiver(String messageId, MessageReceiver[] receiver) {
        List<MessageUser> userList = new ArrayList();
        for (MessageReceiver messageReceiver : receiver) {
            if(messageReceiver.receiverType().equals(MessageUser.class.getSimpleName())) {
                MessageUser messageUser = (MessageUser) messageReceiver;
                messageUser.setMessageId(messageId);
                messageUser.setRead(false);
                userList.add(messageUser);
            }else{
                MessageGroup messageGroup = (MessageGroup) messageReceiver;
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
    public List<PrivateMessage> listPrivateMessage(Page page) {
        Map<String, Object> paramMap = ParamMap.create().toMap();
        QuerySupport query = super.getQuery(PrivateMessageQuery.class, paramMap);
        return super.listForBean(query,page,PrivateMessage::new);
    }

    @Override
    public List<PrivateMessage> listPrivateMessageByUser(String userId, Page page) {

        Map<String, Object> paramMap;
        if(providerList != null){
            paramMap = ParamMap.create("expiredDate",new Date()).toMap();
            QuerySupport query = super.getQuery(MessageGroupQuery.class, paramMap);
            List<MessageGroup> messageGroups = super.listForBean(query, MessageGroup::new);

            List<MessageUser> messageUserList = new ArrayList();

            for (MessageGroup messageGroup : messageGroups) {
                for (MessageGroupProvider groupProvider : providerList) {
                    if(groupProvider.groupType().equals(messageGroup.getGroupType())
                            && groupProvider.isReceiveUser(messageGroup.getRelationId(),userId)) {
                        //判断是否已经存在当前消息，不存在则添加
                        ValueMap messageMap = super.get(CODE_MESSAGE_USER, "messageId", messageGroup.getMessageId(), null);
                        if(messageMap == null){
                            messageUserList.add(new MessageUser(messageGroup.getMessageId(),userId));
                        }
                    }
                }
            }
        }

        paramMap = ParamMap.create("userId",userId).toMap();

        QuerySupport query = super.getQuery(MessageUserQuery.class, paramMap);
        return super.listForBean(query, page, PrivateMessage::new);
    }
}
