package cn.kduck.module.organization.event;

import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.module.user.service.UserService;
import cn.kduck.core.event.Event.EventType;
import cn.kduck.core.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteOrgUserEventListener implements EventListener<String[]> {

    @Autowired
    private OrganizationService organizationService;

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
        organizationService.deleteOrgUserByUser(userIds);
    }
}
