package cn.kduck.webapp.websocket;

import cn.kduck.core.service.ParamMap;
import cn.kduck.core.web.websocket.ConnectEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Date;
import java.util.Map;

@Component
public class DemoConnectEventHandler implements ConnectEventHandler {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void onConnect(Principal principal, String sessionId) {
        System.out.println(principal.getName()+"上线了");

        Map<String, Object> dataMap = ParamMap.create().set("user", principal.getName()).set("date", new Date()).toMap();
        template.convertAndSend("/topic/broadcast",dataMap);
    }
}
