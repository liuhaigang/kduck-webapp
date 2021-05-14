package cn.kduck.webapp.websocket.global;

import cn.kduck.core.web.json.JsonObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public  class GlobalEventController{

    @MessageMapping("/globalEvent")
//    @SendTo("/topic/globalEvent")
    public JsonObject globalEvent(GlobalEvent event) throws Exception {
        return event;
    }
}
