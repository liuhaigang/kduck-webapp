package cn.kduck.webapp.message;

import cn.kduck.module.privatemessage.MessageGroupProvider;
import org.springframework.stereotype.Component;

@Component
public class AllGroupProvider implements MessageGroupProvider {

    @Override
    public String groupType() {
        return "ALL";
    }

    @Override
    public boolean isReceiveUser(String relationId, String userId) {
        return true;
    }
}
