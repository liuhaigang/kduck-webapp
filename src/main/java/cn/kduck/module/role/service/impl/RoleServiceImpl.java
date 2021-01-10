package cn.kduck.module.role.service.impl;

import cn.kduck.module.role.query.RoleByObjectQuery;
import cn.kduck.module.role.query.RoleObjectForRoleQuery;
import cn.kduck.module.role.query.RoleObjectQuery;
import cn.kduck.module.role.query.RoleQuery;
import cn.kduck.module.role.service.Role;
import cn.kduck.module.role.service.RoleService;
import cn.kduck.module.role.service.RoleObject;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.DeleteBuilder;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
@Service
public class RoleServiceImpl extends DefaultService implements RoleService {

    @Override
    public void addRole(Role role) {
        super.add(CODE_ROLE,role);
    }

    @Override
    public void deleteRole(String[] ids) {
        super.delete(CODE_ROLE,ids);
    }

    @Override
    public void updateRole(Role role) {
        super.update(CODE_ROLE,role);
    }

    @Override
    public Role getRole(String id) {
        return super.getForBean(CODE_ROLE,id,Role::new);
    }

    @Override
    public List<Role> listRole(Map<String, Object> paramMap, Page page) {
        QuerySupport queryBean = getQuery(RoleQuery.class,paramMap);
        return super.listForBean(queryBean,page,Role::new);
    }

    @Override
    public void addRoleObject(String roleId, String[] roIds, Integer type) {
        RoleObject[] roleObjects = new RoleObject[roIds.length];
        for (int i = 0; i < roIds.length; i++) {
            roleObjects[i] = new RoleObject(roleId,roIds[i],type);
        }
        super.batchAdd(CODE_ROLE_OBJECT,roleObjects);
    }

    @Override
    public void deleteRoleObject(String[] objectIds) {
        super.delete(CODE_ROLE_OBJECT,objectIds);
    }

    @Override
    public void deleteRoleObject(String[] objectIds, Integer type) {
        Map<String, Object> paramMap = ParamMap.create("objectIds", objectIds).set("type", type).toMap();
        DeleteBuilder delBuilder = new DeleteBuilder(super.getEntityDef(CODE_ROLE_OBJECT),paramMap);
        delBuilder.where().and("role_object", ConditionType.IN,"objectIds").and("object_type",ConditionType.EQUALS,"type");

        super.executeUpdate(delBuilder.build());
    }

    @Override
    public List<RoleObject> listRoleObject(String roleId, int type, Map<String,Object> paramMap, Page page) {
        paramMap.put("roleId",roleId);
        paramMap.put("type",type);

        QuerySupport queryBean = getQuery(RoleObjectQuery.class, paramMap);
        return super.listForBean(queryBean,page,RoleObject::new);
    }

    @Override
    public List<RoleObject> listUserForRoleObject(String roleId,int type, Map<String, Object> paramMap, Page page) {
        paramMap.put("roleId",roleId);
        paramMap.put("type",type);
        QuerySupport query = super.getQuery(RoleObjectForRoleQuery.class, paramMap);
        return super.listForBean(query,page,RoleObject::new);
    }

    @Override
    public List<Role> listRole(String roleObject,Integer objectType) {
        Map<String, Object> paramMap = ParamMap.create(RoleObject.ROLE_OBJECT, roleObject)
                .set(RoleObject.OBJECT_TYPE, objectType).toMap();
        QuerySupport queryBean = getQuery(RoleByObjectQuery.class,paramMap);
        return super.listForBean(queryBean,Role::new);
    }

}
