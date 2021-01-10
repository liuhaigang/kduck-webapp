package cn.kduck.module.hierarchical.event;

import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.module.user.service.UserService;
import cn.kduck.core.event.Event.EventType;
import cn.kduck.core.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteAuthorizeUserEventListener implements EventListener<String[]> {

    @Autowired
    private HierarchicalAuthorizeService hierarchicalAuthorizeService;

    @Override
    public String eventCode() {
        return UserService.CODE_USER;
    }

    @Override
    public EventType eventType() {
        return EventType.DELETE;
    }

    @Override
    public void onEvent(String[] userIds) {
        hierarchicalAuthorizeService.deleteAuthorizeByUser(userIds);
    }
}
