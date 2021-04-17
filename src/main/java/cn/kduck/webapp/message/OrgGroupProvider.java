package cn.kduck.webapp.message;

import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.module.privatemessage.MessageGroupProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgGroupProvider implements MessageGroupProvider {

    @Autowired
    private OrganizationService organizationService;

    @Override
    public String groupType() {
        return "ORG";
    }

    @Override
    public boolean isReceiveUser(String relationId, String userId) {
        //根据用户ID查询所属机构，然后和relationId进行比对
        return false;
    }
}
