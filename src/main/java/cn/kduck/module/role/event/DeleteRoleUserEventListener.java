package cn.kduck.module.role.event;

import cn.kduck.module.role.service.Role;
import cn.kduck.module.role.service.RoleService;
import cn.kduck.module.user.service.UserService;
import cn.kduck.core.event.Event.EventType;
import cn.kduck.core.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteRoleUserEventListener implements EventListener<String[]> {

    @Autowired
    private RoleService roleService;

    @Override
    public String eventCode() {
        return UserService.CODE_USER;
    }

    @Override
    public EventType eventType() {
        return EventType.DELETE;
    }

    @Override
    public void onEvent(String[] eventObject) {
        roleService.deleteRoleObject(eventObject, Role.ROLE_TYPE_USER);
    }
}
