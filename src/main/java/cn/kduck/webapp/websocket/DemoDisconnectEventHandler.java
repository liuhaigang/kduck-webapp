package cn.kduck.webapp.websocket;

import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.websocket.DisconnectEventHandler;
import cn.kduck.webapp.security.Kuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class DemoDisconnectEventHandler implements DisconnectEventHandler {

    @Autowired
    @Lazy
    private SimpMessagingTemplate template;

    @Override
    public void onDisconnect(Principal principal, String sessionId) {
        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken)principal;
        Kuser user = (Kuser)userToken.getPrincipal();

        System.out.println(user.getDisplayName()+"离开了");

        UserMessage userMessage = new UserMessage();
        userMessage.setMessageType(MessageType.DISCONNECT);
        userMessage.setFromUser(user.getDisplayName());
        template.convertAndSend("/topic/broadcast",new JsonObject(userMessage));
    }
}
