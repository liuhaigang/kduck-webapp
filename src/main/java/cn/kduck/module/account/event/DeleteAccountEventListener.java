package cn.kduck.module.account.event;

import cn.kduck.core.event.Event.EventType;
import cn.kduck.core.event.EventListener;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.module.role.service.Role;
import cn.kduck.module.role.service.RoleService;
import cn.kduck.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteAccountEventListener implements EventListener<String[]> {

    @Autowired
    private AccountService accountService;

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
        accountService.deleteAccountByUserId(eventObject);
    }
}
