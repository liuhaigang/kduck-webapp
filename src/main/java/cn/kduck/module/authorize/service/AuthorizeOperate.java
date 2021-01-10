package cn.kduck.module.authorize.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class AuthorizeOperate extends ValueMap {

    public static final int AUTHORIZE_TYPE_USER = 1;
    public static final int AUTHORIZE_TYPE_ROLE = 2;
    public static final int AUTHORIZE_TYPE_HIERARCHICAL = 3;

    public static final int OPERATE_TYPE_ID = 1;
    public static final int OPERATE_TYPE_GROUP = 2;

    /**授权信息Id*/
    public static final String AUTHORIZE_ID = "authorizeId";
    /**授权对象*/
    public static final String AUTHORIZE_OBJECT = "authorizeObject";
    /**授权类型 1 用户，2 角色*/
    public static final String AUTHORIZE_TYPE = "authorizeType";
    /**授权操作对象*/
    public static final String OPERATE_OBJECT = "operateObject";
    /**授权操作类型 1 资源操作，2 资源组*/
    public static final String OPERATE_TYPE = "operateType";
    /**资源Id*/
    public static final String RESOURCE_ID = "resourceId";

    public AuthorizeOperate() {
    }

    public AuthorizeOperate(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 授权信息Id
     *
     * @param authorizeId 授权信息Id
     */
    public void setAuthorizeId(String authorizeId) {
        super.setValue(AUTHORIZE_ID, authorizeId);
    }

    /**
     * 获取 授权信息Id
     *
     * @return 授权信息Id
     */
    public String getAuthorizeId() {
        return super.getValueAsString(AUTHORIZE_ID);
    }

    /**
     * 设置 授权对象
     *
     * @param authorizeObject 授权对象
     */
    public void setAuthorizeObject(String authorizeObject) {
        super.setValue(AUTHORIZE_OBJECT, authorizeObject);
    }

    /**
     * 获取 授权对象
     *
     * @return 授权对象
     */
    public String getAuthorizeObject() {
        return super.getValueAsString(AUTHORIZE_OBJECT);
    }

    /**
     * 设置 授权类型 1 用户，2 角色
     *
     * @param authorizeType 授权类型 1 用户，2 角色
     */
    public void setAuthorizeType(Integer authorizeType) {
        super.setValue(AUTHORIZE_TYPE, authorizeType);
    }

    /**
     * 获取 授权类型 1 用户，2 角色
     *
     * @return 授权类型 1 用户，2 角色
     */
    public Integer getAuthorizeType() {
        return super.getValueAsInteger(AUTHORIZE_TYPE);
    }

    /**
     * 设置 授权操作对象
     *
     * @param operateObject 授权操作对象
     */
    public void setOperateObject(String operateObject) {
        super.setValue(OPERATE_OBJECT, operateObject);
    }

    /**
     * 获取 授权操作对象
     *
     * @return 授权操作对象
     */
    public String getOperateObject() {
        return super.getValueAsString(OPERATE_OBJECT);
    }

    /**
     * 设置 授权操作类型 1 资源操作，2 资源组
     *
     * @param operateType 授权操作类型 1 资源操作，2 资源组
     */
    public void setOperateType(Integer operateType) {
        super.setValue(OPERATE_TYPE, operateType);
    }

    /**
     * 获取 授权操作类型 1 资源操作，2 资源组
     *
     * @return 授权操作类型 1 资源操作，2 资源组
     */
    public Integer getOperateType() {
        return super.getValueAsInteger(OPERATE_TYPE);
    }

    /**
     * 设置 资源Id
     *
     * @param resourceId 资源Id
     */
    public void setResourceId(String resourceId) {
        super.setValue(RESOURCE_ID, resourceId);
    }

    /**
     * 获取 资源Id
     *
     * @return 资源Id
     */
    public String getResourceId() {
        return super.getValueAsString(RESOURCE_ID);
    }
}
