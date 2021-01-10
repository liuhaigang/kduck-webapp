package cn.kduck.module.organization.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

/**
 * @author LiuHG
 */
public class OrgUser extends ValueMap {
    /**机构用户ID*/
    public static final String ORG_USER_ID = "orgUserId";
    /**机构ID*/
    public static final String ORG_ID = "orgId";
    /**用户ID*/
    public static final String USER_ID = "userId";
    /**用户类型*/
    public static final String USER_TYPE = "userType";

    public OrgUser() {
    }

    public OrgUser(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 机构用户ID
     *
     * @param orgUserId 机构用户ID
     */
    public void setOrgUserId(String orgUserId) {
        super.setValue(ORG_USER_ID, orgUserId);
    }

    /**
     * 获取 机构用户ID
     *
     * @return 机构用户ID
     */
    public String getOrgUserId() {
        return super.getValueAsString(ORG_USER_ID);
    }

    /**
     * 设置 机构ID
     *
     * @param orgId 机构ID
     */
    public void setOrgId(String orgId) {
        super.setValue(ORG_ID, orgId);
    }

    /**
     * 获取 机构ID
     *
     * @return 机构ID
     */
    public String getOrgId() {
        return super.getValueAsString(ORG_ID);
    }

    /**
     * 设置 用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        super.setValue(USER_ID, userId);
    }

    /**
     * 获取 用户ID
     *
     * @return 用户ID
     */
    public String getUserId() {
        return super.getValueAsString(USER_ID);
    }

    /**
     * 设置 用户类型
     *
     * @param userType 用户类型
     */
    public void setUserType(String userType) {
        super.setValue(USER_TYPE, userType);
    }

    /**
     * 获取 用户类型
     *
     * @return 用户类型
     */
    public String getUserType() {
        return super.getValueAsString(USER_TYPE);
    }
}
