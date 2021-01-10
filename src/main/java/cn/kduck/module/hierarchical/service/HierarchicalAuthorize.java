package cn.kduck.module.hierarchical.service;

import cn.kduck.core.service.ValueMap;

import java.util.Date;
import java.util.Map;

public class HierarchicalAuthorize extends ValueMap {

    /**授权ID*/
    public static final String AUTHORIZE_ID = "authorizeId";
    /**授权机构ID*/
    public static final String ORG_ID = "orgId";
    /**授权用户ID*/
    public static final String USER_ID = "userId";
    /**创建时间*/
    public static final String CREATE_TIME = "createTime";

    public HierarchicalAuthorize() {
    }

    public HierarchicalAuthorize(Map<String, Object> map) {
        super(map);
    }

    public HierarchicalAuthorize(String orgId,String userId) {
        setOrgId(orgId);
        setUserId(userId);
        setCreateTime(new Date());
    }

    /**
     * 设置
     *
     * @param authorizeId
     */
    public void setAuthorizeId(String authorizeId) {
        super.setValue(AUTHORIZE_ID, authorizeId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getAuthorizeId() {
        return super.getValueAsString(AUTHORIZE_ID);
    }

    /**
     * 设置
     *
     * @param orgId
     */
    public void setOrgId(String orgId) {
        super.setValue(ORG_ID, orgId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getOrgId() {
        return super.getValueAsString(ORG_ID);
    }

    /**
     * 设置
     *
     * @param userId
     */
    public void setUserId(String userId) {
        super.setValue(USER_ID, userId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getUserId() {
        return super.getValueAsString(USER_ID);
    }

    /**
     * 设置
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        super.setValue(CREATE_TIME, createTime);
    }

    /**
     * 获取
     *
     * @return
     */
    public Date getCreateTime() {
        return super.getValueAsDate(CREATE_TIME);
    }
}
