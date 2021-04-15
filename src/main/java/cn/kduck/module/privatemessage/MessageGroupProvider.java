package cn.kduck.module.privatemessage;

public interface MessageGroupProvider {

    String groupType();

    boolean isReceiveUser(String relationId,String userId);
}
