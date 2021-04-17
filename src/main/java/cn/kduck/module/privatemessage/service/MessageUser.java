package cn.kduck.module.privatemessage.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class MessageUser extends ValueMap implements MessageReceiver{

    public static final int IS_READ_NO = 0;
    public static final int IS_READ_YES = 1;

    public static final int IS_DELETE_NO = 0;
    public static final int IS_DELETE_YES = 1;

    /***/
    public static final String MESSAGE_USER_ID = "messageUserId";
    /***/
    public static final String MESSAGE_ID = "messageId";
    /***/
    public static final String USER_ID = "userId";
    /**是否已读*/
    public static final String IS_READ = "isRead";

    public static final String IS_DELETE = "isDelete";

    public MessageUser() {
    }

    public MessageUser(String messageId,String userId) {
        setMessageId(messageId);
        setUserId(userId);
        setIsRead(IS_READ_NO);
        setIsDelete(IS_DELETE_NO);
    }

    public MessageUser(Map<String, Object> map) {
        super(map);
    }

    @Override
    public String receiverId() {
        return getUserId();
    }

    @Override
    public String receiverType() {
        return this.getClass().getSimpleName();
    }


    /**
     * 设置
     *
     * @param messageUserId
     */
    public void setMessageUserId(String messageUserId) {
        super.setValue(MESSAGE_USER_ID, messageUserId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getMessageUserId() {
        return super.getValueAsString(MESSAGE_USER_ID);
    }

    /**
     * 设置
     *
     * @param messageId
     */
    public void setMessageId(String messageId) {
        super.setValue(MESSAGE_ID, messageId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getMessageId() {
        return super.getValueAsString(MESSAGE_ID);
    }

    /**
     * 设置
     *
     * @param userId
     */
    public void setUserId(String userId) {
        super.setValue(USER_ID, userId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getUserId() {
        return super.getValueAsString(USER_ID);
    }

    /**
     * 设置 是否已读
     *
     * @param read 是否已读
     */
    public void setIsRead(Integer read) {
        super.setValue(IS_READ, read);
    }

    /**
     * 获取 是否已读
     *
     * @return 是否已读
     */
    public Integer getIsRead() {
        return super.getValueAsInteger(IS_READ);
    }

    public void setIsDelete(Integer read) {
        super.setValue(IS_DELETE, read);
    }

    public Integer getIsDelete() {
        return super.getValueAsInteger(IS_DELETE);
    }
}
