package cn.kduck.webapp.websocket;

public class OnlineUser {
    private String loginName;
    private String userName;
    private String userId;

    private MessageType messageType = MessageType.SUBSCRIBE;

    public OnlineUser() {
    }

    public OnlineUser(String loginName, String userId, String userName) {
        this.loginName = loginName;
        this.userName = userName;
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
