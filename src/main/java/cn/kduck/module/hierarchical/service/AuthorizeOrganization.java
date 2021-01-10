package cn.kduck.module.hierarchical.service;

import cn.kduck.module.organization.service.Organization;

import java.util.Map;

public class AuthorizeOrganization extends Organization {

    /**是否为授权的机构（仅用于显示）*/
    public static final String IS_AUTHORIZED = "isAuthorized";

    public AuthorizeOrganization() {
    }

    public AuthorizeOrganization(Map valueMap) {
        super(valueMap);
    }

    public AuthorizeOrganization(Organization organization) {
        super(organization);
    }

    public AuthorizeOrganization(Organization organization,Boolean isAuthorized) {
        super(organization);
        setIsAuthorized(isAuthorized);
    }


    /**
     * 设置
     *
     * @param isAuthorized
     */
    public void setIsAuthorized(Boolean isAuthorized) {
        super.setValue(IS_AUTHORIZED, isAuthorized);
    }

    /**
     * 获取
     *
     * @return
     */
    public Boolean getIsAuthorized() {
        return super.getValueAsBoolean(IS_AUTHORIZED);
    }
}
