package cn.kduck.module.role.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

/**
 * @author LiuHG
 */
public class RoleObject extends ValueMap {

    /**角色对象ID*/
    public static final String OBJECT_ID = "objectId";
    /**角色ID*/
    public static final String ROLE_ID = "roleId";
    /**角色对象ID值*/
    public static final String ROLE_OBJECT = "roleObject";
    /**角色对象类型*/
    public static final String OBJECT_TYPE = "objectType";

    public RoleObject() {
    }

    public RoleObject(Map<String, Object> map) {
        super(map);
    }

    public RoleObject(String roleId,String roleObject,Integer objectType) {
        setRoleId(roleId);
        setRoleObject(roleObject);
        setObjectType(objectType);
    }

    /**
     * 设置
     *
     * @param objectId
     */
    public void setObjectId(String objectId) {
        super.setValue(OBJECT_ID, objectId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getObjectId() {
        return super.getValueAsString(OBJECT_ID);
    }

    /**
     * 设置
     *
     * @param roleId
     */
    public void setRoleId(String roleId) {
        super.setValue(ROLE_ID, roleId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getRoleId() {
        return super.getValueAsString(ROLE_ID);
    }

    /**
     * 设置
     *
     * @param roleObject
     */
    public void setRoleObject(String roleObject) {
        super.setValue(ROLE_OBJECT, roleObject);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getRoleObject() {
        return super.getValueAsString(ROLE_OBJECT);
    }

    /**
     * 设置
     *
     * @param objectType
     */
    public void setObjectType(Integer objectType) {
        super.setValue(OBJECT_TYPE, objectType);
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getObjectType() {
        return super.getValueAsInteger(OBJECT_TYPE);
    }
}
