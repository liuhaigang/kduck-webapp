package cn.kduck.module.privatemessage.service;

import cn.kduck.core.service.Page;

import java.util.List;

/**
 * 站内消息
 */
public interface PrivateMessageService {

    String CODE_PRIVATE_MESSAGE = "K_PRIVATE_MESSAGE";
    String CODE_MESSAGE_GROUP = "K_MESSAGE_GROUP";
    String CODE_MESSAGE_USER = "K_MESSAGE_USER";

    String EVENT_PRIVATE_MESSAGE_ADD = "EVENT_PRIVATE_MESSAGE_ADD";

    void addPrivateMessage(PrivateMessage privateMessage,MessageReceiver[] receiver);

    void addMessageReceiver(String messageId,MessageReceiver[] receiver);

    void deleteMessageReceiver(String messageId,MessageReceiver[] receiver);

    void updatePrivateMessage(PrivateMessage privateMessage);

    void deletePrivateMessage(String[] ids);

    PrivateMessage getPrivateMessage(String id);

    PrivateMessage getPrivateMessageByUser(String messageId,String userId);

    List<PrivateMessage> listPrivateMessage(Page page);

    List<PrivateMessage> listPrivateMessageByUser(String userId,Page page);

    void updateMessageReadState(String messageId,String userId);

    long countMessageUnread(String userId);

    void deleteUserMessage(String messageId,String userId);
}
