package cn.kduck.webapp.message;


import cn.kduck.core.event.EventListener;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.module.privatemessage.service.PrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static cn.kduck.module.privatemessage.service.PrivateMessageService.EVENT_PRIVATE_MESSAGE_ADD;

@Component
public class PrivateMessageEventListener implements EventListener<PrivateMessage> {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public String eventCode() {
        return EVENT_PRIVATE_MESSAGE_ADD;
    }

    @Override
    public void onEvent(PrivateMessage eventObject) {
        JsonObject jsonObject = new JsonObject(eventObject);
        jsonObject.setCode(100);
        this.template.convertAndSend("/topic/globalEvent", jsonObject);
    }
}
