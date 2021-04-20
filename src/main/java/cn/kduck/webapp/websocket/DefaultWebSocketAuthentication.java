package cn.kduck.webapp.websocket;

import cn.kduck.core.web.websocket.WebSocketAuthentication;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.security.principal.AuthUserHolder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class DefaultWebSocketAuthentication implements WebSocketAuthentication {

    @Override
    public Principal authenticate(Message<?> message, MessageChannel channel) {
        AuthUser authUser = AuthUserHolder.getAuthUser();
        return () -> authUser.getUsername();
    }
}
