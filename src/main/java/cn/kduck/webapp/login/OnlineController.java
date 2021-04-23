package cn.kduck.webapp.login;

import cn.kduck.core.web.json.JsonObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class OnlineController {

    private List<String> onlineUserList = new ArrayList<>();

    @MessageMapping("/addOnline")
    @SendTo("/topic/onlineTotal")
    public JsonObject addOnlineNum(Principal principal){
        synchronized (onlineUserList){
            String loginName = principal.getName();
            if(!onlineUserList.contains(loginName)){
                onlineUserList.add(loginName);
            }
        }
        return new JsonObject(onlineUserList.size());
    }
}
