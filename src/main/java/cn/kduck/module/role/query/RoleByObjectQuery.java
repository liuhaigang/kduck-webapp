package cn.kduck.module.role.query;

import cn.kduck.module.role.service.RoleService;
import cn.kduck.module.role.service.impl.RoleServiceImpl;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * LiuHG
 */
@Component
public class RoleByObjectQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef roleDef = depository.getEntityDef(RoleService.CODE_ROLE);
        BeanEntityDef roleObjectDef = depository.getEntityDef(RoleService.CODE_ROLE_OBJECT);

        SelectBuilder queryBuilder = new SelectBuilder(paramMap,true);
        queryBuilder.bindFields("r", depository.getFieldDefList(RoleServiceImpl.CODE_ROLE));

        queryBuilder.from("r",roleDef).innerJoinOn("ro",roleObjectDef,"roleId").where().
                and("ro.ROLE_OBJECT", ConditionType.EQUALS, "roleObject").
                and("ro.OBJECT_TYPE", ConditionType.EQUALS, "objectType");
        return queryBuilder.build();
    }
}
