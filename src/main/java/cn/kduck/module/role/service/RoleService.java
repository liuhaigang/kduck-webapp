package cn.kduck.module.role.service;

import cn.kduck.core.service.Page;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
public interface RoleService {

    String CODE_ROLE = "k_role";
    String CODE_ROLE_OBJECT = "k_role_object";

    void addRole(Role role);

    void deleteRole(String[] ids);

    void updateRole(Role role);

    Role getRole(String id);

    List<Role> listRole(Map<String, Object> paramMap, Page page);

    void addRoleObject(String roleId, String[] objectIds, Integer type);

    void deleteRoleObject(String[] objectIds);

    void deleteRoleObject(String[] objectIds, Integer type);

    List<RoleObject> listRoleObject(String roleId, int type, Map<String, Object> paramMap, Page page);

    List<RoleObject> listUserForRoleObject(String roleId,int type,Map<String, Object> paramMap, Page page);

    /**
     * 根据授权对象类型查询角色
     * @param roleObject
     * @param objectType 授权对象类型。取值参考：RoleService.ROLE_TYPE_USER,RoleService.ROLE_TYPE_ORG,RoleService.ROLE_TYPE_GROUP
     * @return
     */
    List<Role> listRole(String roleObject, Integer objectType);

}
