package cn.kduck.webapp.websocket;


import cn.kduck.core.web.json.JsonObject;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.security.principal.AuthUserHolder;
import cn.kduck.webapp.security.Kuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SimpleBrokerMessageHandler
 * MessageBuilder
 */
@RestController
public class DemoWebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private SimpUserRegistry userRegistry;

    @PostMapping(path="/sendToUser")
    public void sendToUser(UserMessage message,Principal principal) {
        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken)principal;
        Kuser user = (Kuser)userToken.getPrincipal();
        message.setFromUser(user.getDisplayName());

        this.template.convertAndSendToUser(message.getToUser(),"/topic/privateMessage", new JsonObject(message));
    }

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public JsonObject message(UserMessage message, Principal principal) throws Exception {
        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken)principal;
        Kuser user = (Kuser)userToken.getPrincipal();
        message.setFromUser(user.getDisplayName());
        return new JsonObject(message);
    }

    @MessageMapping("/privateMessage")
    @SendToUser("/topic/privateMessage")
    public JsonObject privateMessage(UserMessage message, Principal principal) throws Exception {
        UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken)principal;
        Kuser user = (Kuser)userToken.getPrincipal();
        message.setFromUser(user.getDisplayName());
        return new JsonObject(message);
    }

    @GetMapping("/onlineUser")
    public JsonObject getOnlineUser(){
        List<OnlineUser> onlineUserList = new ArrayList();
        Set<SimpUser> users = userRegistry.getUsers();
        for (SimpUser simpUser : users) {
            Principal principal = simpUser.getPrincipal();
            UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken)principal;
            Kuser user = (Kuser)userToken.getPrincipal();
            onlineUserList.add(new OnlineUser(principal.getName(), user.getUserId() ,user.getDisplayName()));
        }
        return new JsonObject(onlineUserList);
    }
}
