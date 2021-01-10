package cn.kduck.module.role.query;

import cn.kduck.module.role.service.RoleService;
import cn.kduck.core.dao.definition.BeanEntityDef;
import org.springframework.stereotype.Component;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;

import java.util.Map;

/**
 * LiuHG
 */
@Component
public class RoleQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(RoleService.CODE_ROLE);
        SelectBuilder queryBuilder = new SelectBuilder(entityDef,paramMap);
        queryBuilder.where("role_name", ConditionType.CONTAINS,"roleName");
        return queryBuilder.build();
    }
}
