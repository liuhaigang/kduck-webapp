package cn.kduck.module.privatemessage.web;

import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiJsonRequest;
import cn.kduck.module.privatemessage.MessageGroupProvider;
import cn.kduck.module.privatemessage.service.MessageGroup;
import cn.kduck.module.privatemessage.service.MessageReceiver;
import cn.kduck.module.privatemessage.service.MessageUser;
import cn.kduck.module.privatemessage.service.PrivateMessage;
import cn.kduck.module.privatemessage.service.PrivateMessageService;
import cn.kduck.module.privatemessage.web.model.MessageReceiverModel;
import cn.kduck.module.privatemessage.web.model.PrivateMessageModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiJsonRequest({
//            @ApiField(name="messageId",value="消息ID", paramType = "query"),
            @ApiField(name="messateTitle",value="消息标题", paramType = "query"),
            @ApiField(name="messageContent",value="消息内容", paramType = "query"),
            @ApiField(name="receivers[].receiverId",value="接收对象Id", paramType = "query"),
            @ApiField(name="receivers[].groupType",value="接收对象类型", paramType = "query"),

    })
    public JsonObject addPrivateMessage(@RequestBody PrivateMessageModel messageModel){

        PrivateMessage message = new PrivateMessage();
        message.setMessateTitle(messageModel.getMessateTitle());
        message.setMessageContent(messageModel.getMessageContent());
        message.setSendDate(new Date());

        MessageReceiverModel[] receivers = messageModel.getReceivers();
        MessageReceiver[] messageReceivers = new MessageReceiver[receivers.length];

        int index = 0;
        for (MessageReceiverModel receiver : receivers) {
            String receiverType = receiver.getReceiverType();
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
}
