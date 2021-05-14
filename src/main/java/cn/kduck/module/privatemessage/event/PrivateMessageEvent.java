package cn.kduck.module.privatemessage.event;

import cn.kduck.core.event.Event;

public class PrivateMessageEvent extends Event {

    public PrivateMessageEvent(String code, Object eventObject) {
        super(code, eventObject);
    }
}
