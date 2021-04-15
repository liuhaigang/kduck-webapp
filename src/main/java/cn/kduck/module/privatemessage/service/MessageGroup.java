package cn.kduck.module.privatemessage.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class MessageGroup extends ValueMap implements MessageReceiver{

    /***/
    public static final String GROUP_ID = "groupId";
    /***/
    public static final String RELATION_ID = "relationId";
    /***/
    public static final String GROUP_TYPE = "groupType";

    /***/
    public static final String MESSAGE_ID = "messageId";

    public MessageGroup() {
    }

    public MessageGroup(Map<String, Object> map) {
        super(map);
    }

    @Override
    public String receiverId() {
        return getRelationId();
    }

    @Override
    public String receiverType() {
        return this.getClass().getSimpleName();
    }

    /**
     * 设置
     *
     * @param groupId
     */
    public void setGroupId(String groupId) {
        super.setValue(GROUP_ID, groupId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getGroupId() {
        return super.getValueAsString(GROUP_ID);
    }

    /**
     * 设置
     *
     * @param relationId
     */
    public void setRelationId(String relationId) {
        super.setValue(RELATION_ID, relationId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getRelationId() {
        return super.getValueAsString(RELATION_ID);
    }

    /**
     * 设置
     *
     * @param groupType
     */
    public void setGroupType(String groupType) {
        super.setValue(GROUP_TYPE, groupType);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getGroupType() {
        return super.getValueAsString(GROUP_TYPE);
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
}
