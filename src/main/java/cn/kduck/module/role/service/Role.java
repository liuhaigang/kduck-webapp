package cn.kduck.module.role.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

/**
 * @author LiuHG
 */
public class Role extends ValueMap {

    public static final int ROLE_TYPE_USER = 1;
    public static final int ROLE_TYPE_ORG = 2;

    /**角色ID*/
    public static final String ROLE_ID = "roleId";
    /**角色名城*/
    public static final String ROLE_NAME = "roleName";
    /**角色编码*/
    public static final String ROLE_CODE = "roleCode";
    /**角色类型*/
    public static final String ROLE_TYPE = "roleType";
    /**角色备注*/
    public static final String REMARK = "remark";


    public Role() {
    }

    public Role(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(String roleId) {
        super.setValue(ROLE_ID, roleId);
    }

    /**
     * 获取 角色ID
     *
     * @return 角色ID
     */
    public String getRoleId() {
        return super.getValueAsString(ROLE_ID);
    }

    /**
     * 设置 角色名城
     *
     * @param roleName 角色名城
     */
    public void setRoleName(String roleName) {
        super.setValue(ROLE_NAME, roleName);
    }

    /**
     * 获取 角色名城
     *
     * @return 角色名城
     */
    public String getRoleName() {
        return super.getValueAsString(ROLE_NAME);
    }

    /**
     * 设置 角色编码
     *
     * @param roleCode 角色编码
     */
    public void setRoleCode(String roleCode) {
        super.setValue(ROLE_CODE, roleCode);
    }

    /**
     * 获取 角色编码
     *
     * @return 角色编码
     */
    public String getRoleCode() {
        return super.getValueAsString(ROLE_CODE);
    }

    /**
     * 设置 角色类型
     *
     * @param roleType 角色类型
     */
    public void setRoleType(Integer roleType) {
        super.setValue(ROLE_TYPE, roleType);
    }

    /**
     * 获取 角色类型
     *
     * @return 角色类型
     */
    public Integer getRoleType() {
        return super.getValueAsInteger(ROLE_TYPE);
    }

    /**
     * 设置 角色备注
     *
     * @param remark 角色备注
     */
    public void setRemark(String remark) {
        super.setValue(REMARK, remark);
    }

    /**
     * 获取 角色备注
     *
     * @return 角色备注
     */
    public String getRemark() {
        return super.getValueAsString(REMARK);
    }
}
