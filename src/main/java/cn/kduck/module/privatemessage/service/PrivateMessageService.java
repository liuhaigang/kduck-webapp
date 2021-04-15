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

    void addPrivateMessage(PrivateMessage privateMessage,MessageReceiver[] receiver);

    void addMessageReceiver(String messageId,MessageReceiver[] receiver);

    void deleteMessageReceiver(String messageId,MessageReceiver[] receiver);

    void updatePrivateMessage(PrivateMessage privateMessage);

    void deletePrivateMessage(String[] ids);

    PrivateMessage getPrivateMessage(String id);

    List<PrivateMessage> listPrivateMessage(Page page);

    List<PrivateMessage> listPrivateMessageByUser(String userId,Page page);
}
