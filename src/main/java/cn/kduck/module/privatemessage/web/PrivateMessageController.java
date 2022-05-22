package cn.kduck.module.privatemessage.web;

import cn.kduck.core.service.Page;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import cn.kduck.module.privatemessage.service.MessageGroup;
import cn.kduck.module.privatemessage.service.MessageReceiver;
import cn.kduck.module.privatemessage.service.MessageUser;
import cn.kduck.module.privatemessage.service.PrivateMessage;
import cn.kduck.module.privatemessage.service.PrivateMessageService;
import cn.kduck.module.privatemessage.web.model.MessageReceiverModel;
import cn.kduck.module.privatemessage.web.model.PrivateMessageModel;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.security.principal.AuthUserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/privateMessage")
@Api(tags = "站内信管理")
public class PrivateMessageController {

    @Autowired
    private PrivateMessageService messageService;

    @PostMapping("/add")
    @ApiOperation("添加站内信")
    public JsonObject addPrivateMessage(@RequestBody PrivateMessageModel messageModel){

        PrivateMessage message = new PrivateMessage();
        message.setMessageTitle(messageModel.getMessageTitle());
        message.setMessageContent(messageModel.getMessageContent());
        message.setSendDate(new Date());

        AuthUser authUser = AuthUserHolder.getAuthUser();
        message.setSenderId(authUser.getUserId());
        message.setSenderName(authUser.getUsername());

        MessageReceiverModel[] receivers = messageModel.getReceivers();
        MessageReceiver[] messageReceivers = new MessageReceiver[receivers.length];

        int index = 0;
        for (MessageReceiverModel receiver : receivers) {
            String receiverType = receiver.getReceiverType();
            Assert.notNull(receiverType,"接收类型不能为null");
            if(MessageUser.class.getSimpleName().equals(receiverType)){
                MessageUser messageUser = new MessageUser();
                messageUser.setUserId(receiver.getReceiverId());
                messageReceivers[index] = messageUser;
            }else{
                MessageGroup messageGroup = new MessageGroup();
                messageGroup.setRelationId(receiver.getReceiverId());
                messageGroup.setGroupType(receiver.getGroupType());
                messageReceivers[index] = messageGroup;
            }
            index++;
        }
        messageService.addPrivateMessage(message,messageReceivers);
        return JsonObject.SUCCESS;
    }

    @PostMapping("/user/list")
    @ApiOperation("获取当前用户的站内信")
    public JsonObject listMessageByUser(@ApiIgnore Page page){
        AuthUser authUser = AuthUserHolder.getAuthUser();
        List<PrivateMessage> messageList = messageService.listPrivateMessageByUser(authUser.getUserId(), page);
        return new JsonPageObject(page,messageList);
    }

    @GetMapping("/user/get")
    @ApiOperation("获取当前用户指定messageId的站内信")
    public JsonObject getMessageByUser(@RequestParam("messageId") String messageId){
        AuthUser authUser = AuthUserHolder.getAuthUser();
        PrivateMessage privateMessage = messageService.getPrivateMessageByUser(messageId, authUser.getUserId());
        return new JsonObject(privateMessage);
    }

    @GetMapping("/user/unread/count")
    @ApiOperation("获取当前用户未读消息数量")
    public JsonObject updateMessageReadState(){
        AuthUser authUser = AuthUserHolder.getAuthUser();
        long count = messageService.countMessageUnread(authUser.getUserId());
        return new JsonObject(count);
    }

    @PutMapping("/user/read")
    @ApiOperation("更新当前用户指定消息的已读状态")
    public JsonObject updateMessageReadState(@RequestParam("messageId") String messageId){
        AuthUser authUser = AuthUserHolder.getAuthUser();
        messageService.updateMessageReadState(messageId, authUser.getUserId());
        return JsonObject.SUCCESS;
    }
}
