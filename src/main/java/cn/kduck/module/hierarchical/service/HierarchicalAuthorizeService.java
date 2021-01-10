package cn.kduck.module.hierarchical.service;

import java.util.List;

public interface HierarchicalAuthorizeService {

    String CODE_HIERARCHICAL_AUTHORIZE = "K_HIERARCHICAL_AUTHORIZE";

    void addAuthorize(String orgId ,String[] userIds);

    void deleteAuthorize(String[] ids);

    void deleteAuthorizeByUser(String[] userIds);

    void deleteAuthorizeByOrgId(String[] orgIds);

    List<AuthorizeOrganization> listAuthorizeOrganizationByUserId(String userId);

    List<AuthorizeOrganization> listAuthorizeOrganizationByOrgId(String orgId);

    List<AuthorizeUser> listAuthorizeUser(String orgId);

    /**
     * 判断指定用户所在的机构，是否拥有对应的分级授权
     * @param userId
     * @param orgId
     * @param authorizeUserIds
     * @return
     */
    boolean existAuthorizeUser(String userId,String orgId,String[] authorizeUserIds);
}
