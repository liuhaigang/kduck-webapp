package cn.kduck.webapp.websocket;

import cn.kduck.core.web.websocket.DisconnectEventHandler;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class DemoDisconnectEventHandler implements DisconnectEventHandler {
    @Override
    public void onDisconnect(Principal principal, String sessionId) {
        System.out.println(principal.getName()+"离开了");
    }
}
