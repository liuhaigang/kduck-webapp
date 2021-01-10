package cn.kduck.module.role.query;

import cn.kduck.module.role.service.Role;
import cn.kduck.module.role.service.RoleService;
import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.module.role.service.impl.RoleServiceImpl;
import cn.kduck.module.user.service.UserService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * LiuHG
 */
@Component
public class RoleObjectQuery implements QueryCreator {
    @Override
    public String queryCode() {
        return "listRoleObject";
    }

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        int type = Integer.parseInt(paramMap.get("type").toString());

        BeanEntityDef roleObjectDef = depository.getEntityDef(RoleService.CODE_ROLE_OBJECT);

        SelectBuilder selectBuilder = new SelectBuilder(paramMap);

        if(type == Role.ROLE_TYPE_USER){
            BeanEntityDef userDef = depository.getEntityDef(UserService.CODE_USER);

            selectBuilder.bindFields("u",userDef.getFieldList());

            selectBuilder.from("ro",roleObjectDef).innerJoinOn("u",userDef,"roleObject:userId").where()
                    .and("ro.ROLE_ID",ConditionType.EQUALS,"roleId",true)
                    .and("ro.OBJECT_TYPE",ConditionType.EQUALS,"${"+Role.ROLE_TYPE_USER+"}")
                    .and("u.USER_NAME", ConditionType.CONTAINS,"objectName");
        } else if(type == Role.ROLE_TYPE_ORG){

            BeanEntityDef orgDef = depository.getEntityDef(OrganizationService.CODE_ORGANIZATION);

            selectBuilder.bindFields("o",orgDef.getFieldList());

            selectBuilder.from("ro",roleObjectDef).innerJoinOn("o",orgDef,"roleObject:orgId").where()
                    .and("ro.ROLE_ID",ConditionType.EQUALS,"roleId",true)
                    .and("ro.OBJECT_TYPE",ConditionType.EQUALS,"${"+Role.ROLE_TYPE_ORG+"}")
                    .and("o.ORG_NAME", ConditionType.CONTAINS,"objectName");
        }else {
            throw new RuntimeException("查询角色对象错误，不支持的角色类型：" + type);
        }

        selectBuilder.bindFields("ro", BeanDefUtils.includeField(depository.getFieldDefList(RoleServiceImpl.CODE_ROLE_OBJECT),"objectId"));


        return selectBuilder.build();
    }
}
