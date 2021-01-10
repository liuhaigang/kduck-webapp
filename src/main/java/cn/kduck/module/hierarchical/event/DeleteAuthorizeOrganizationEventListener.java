package cn.kduck.module.hierarchical.event;

import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.core.event.Event.EventType;
import cn.kduck.core.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteAuthorizeOrganizationEventListener implements EventListener<String[]> {

    @Autowired
    private HierarchicalAuthorizeService hierarchicalAuthorizeService;

    @Override
    public String eventCode() {
        return OrganizationService.CODE_ORGANIZATION;
    }

    @Override
    public EventType eventType() {
        return EventType.DELETE;
    }

    @Override
    public void onEvent(String[] orgIds) {
        hierarchicalAuthorizeService.deleteAuthorizeByOrgId(orgIds);
    }
}
