package cn.kduck.module.privatemessage.service;

import cn.kduck.core.service.ValueMap;

import java.util.Date;
import java.util.Map;

public class PrivateMessage extends ValueMap {

    /**消息ID*/
    public static final String MESSAGE_ID = "messageId";
    /**消息标题*/
    public static final String MESSATE_TITLE = "messateTitle";
    /**消息内容*/
    public static final String MESSAGE_CONTENT = "messageContent";
    /**发送日期*/
    public static final String SEND_DATE = "sendDate";
    /**发送人ID*/
    public static final String SENDER_ID = "senderId";
    /**发送人姓名*/
    public static final String SENDER_NAME = "senderName";


    public PrivateMessage() {
    }

    public PrivateMessage(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 消息ID
     *
     * @param messageId 消息ID
     */
    public void setMessageId(String messageId) {
        super.setValue(MESSAGE_ID, messageId);
    }

    /**
     * 获取 消息ID
     *
     * @return 消息ID
     */
    public String getMessageId() {
        return super.getValueAsString(MESSAGE_ID);
    }

    /**
     * 设置 消息标题
     *
     * @param messateTitle 消息标题
     */
    public void setMessateTitle(String messateTitle) {
        super.setValue(MESSATE_TITLE, messateTitle);
    }

    /**
     * 获取 消息标题
     *
     * @return 消息标题
     */
    public String getMessateTitle() {
        return super.getValueAsString(MESSATE_TITLE);
    }

    /**
     * 设置 消息内容
     *
     * @param messageContent 消息内容
     */
    public void setMessageContent(String messageContent) {
        super.setValue(MESSAGE_CONTENT, messageContent);
    }

    /**
     * 获取 消息内容
     *
     * @return 消息内容
     */
    public String getMessageContent() {
        return super.getValueAsString(MESSAGE_CONTENT);
    }

    /**
     * 设置 发送日期
     *
     * @param sendDate 发送日期
     */
    public void setSendDate(Date sendDate) {
        super.setValue(SEND_DATE, sendDate);
    }

    /**
     * 获取 发送日期
     *
     * @return 发送日期
     */
    public Date getSendDate() {
        return super.getValueAsDate(SEND_DATE);
    }

    /**
     * 设置 发送人ID
     *
     * @param senderId 发送人ID
     */
    public void setSenderId(String senderId) {
        super.setValue(SENDER_ID, senderId);
    }

    /**
     * 获取 发送人ID
     *
     * @return 发送人ID
     */
    public String getSenderId() {
        return super.getValueAsString(SENDER_ID);
    }

    /**
     * 设置 发送人姓名
     *
     * @param senderName 发送人姓名
     */
    public void setSenderName(String senderName) {
        super.setValue(SENDER_NAME, senderName);
    }

    /**
     * 获取 发送人姓名
     *
     * @return 发送人姓名
     */
    public String getSenderName() {
        return super.getValueAsString(SENDER_NAME);
    }
}
