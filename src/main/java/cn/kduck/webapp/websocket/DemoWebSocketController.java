package cn.kduck.webapp.websocket;


import cn.kduck.core.web.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@RestController
public class DemoWebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private SimpUserRegistry userRegistry;

    @GetMapping(path="/sendToUser")
    public void sendToUser(Principal principal) {
        String text = "[" + template + "]:服务端主动发送消息！！";
//        SimpUser lhg = userRegistry.getUser(principal.getName());
//        WsUser wsUser = (WsUser) lhg.getPrincipal();
        this.template.convertAndSendToUser(principal.getName(),"/topic/lhg", "主动单独推送给用户的消息");
    }

    @MessageMapping("/broadcast")
    @SendTo("/topic/broadcast")
    public JsonObject broadcast(String message, Principal principal) throws Exception {
        return new JsonObject("【广播】" + message);
    }

    @MessageMapping("/toUser")
    @SendToUser("/topic/toUser")
    public JsonObject toUser(String message, Principal principal) throws Exception {
        return new JsonObject("【来自服务器的回应】收到了你的消息：" + HtmlUtils.htmlEscape(message));
    }
}
