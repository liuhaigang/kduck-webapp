package cn.kduck.module.authorize.event;

import cn.kduck.module.authorize.service.AuthorizeService;
import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.core.event.Event.EventType;
import cn.kduck.core.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteAuthorizeEventListener implements EventListener<String[]> {

    @Autowired
    private AuthorizeService authorizeService;

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
//        根据机构ID查询出分级授权ID
//        hierarchicalAuthorizeService.lis
//        authorizeService.deleteAuthorizeOperate(AuthorizeOperate.AUTHORIZE_TYPE_HIERARCHICAL,orgAuthIds);
    }
}
