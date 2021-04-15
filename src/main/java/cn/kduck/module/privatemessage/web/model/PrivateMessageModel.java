package cn.kduck.module.privatemessage.web.model;

import java.util.Date;

public class PrivateMessageModel {

    /**消息ID*/
    private String messageId;
    /**消息标题*/
    private String messateTitle;
    /**消息内容*/
    private String messageContent;

    private MessageReceiverModel[] receivers;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessateTitle() {
        return messateTitle;
    }

    public void setMessateTitle(String messateTitle) {
        this.messateTitle = messateTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public MessageReceiverModel[] getReceivers() {
        return receivers;
    }

    public void setReceivers(MessageReceiverModel[] receivers) {
        this.receivers = receivers;
    }
}
