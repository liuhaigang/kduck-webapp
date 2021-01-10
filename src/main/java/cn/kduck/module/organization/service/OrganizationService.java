package cn.kduck.module.organization.service;

import cn.kduck.module.user.service.User;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueMapList;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
public interface OrganizationService {

    String CODE_ORGANIZATION = "k_organization";
    String CODE_ORGANIZATION_USER = "k_organization_user";


    void addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganization(String[] ids);

    Organization getOrganization(String id);

    List<Organization> listOrganizationByParentId(String orgId, Map<String,Object> paramMap, Page page);

    List<Organization> listOrganizationByIds(String[] orgIds);

    void addOrgUser(String orgId,String[] userIds);

    void deleteOrgUser(String[] ids);

    void deleteOrgUser(String orgId,String[] userIds);

    void deleteOrgUserByUser(String[] userIds);

    ValueMapList listOrgUser(String orgId, Page page);

    List<User> listUserForOrgUser(String orgId, Page page);

    ValueMapList listOrganizationByUserId(String userId);

    List<Organization> listAllOrganization(boolean incRoot);

}
