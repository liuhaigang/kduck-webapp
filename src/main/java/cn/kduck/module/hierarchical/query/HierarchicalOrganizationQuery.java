package cn.kduck.module.hierarchical.query;

import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static cn.kduck.module.organization.service.OrganizationService.CODE_ORGANIZATION;

@Component
public class HierarchicalOrganizationQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef orgDef = depository.getEntityDef(CODE_ORGANIZATION);
        BeanEntityDef authorizeDef = depository.getEntityDef(HierarchicalAuthorizeService.CODE_HIERARCHICAL_AUTHORIZE);

        SelectBuilder subSqlBuilder = new SelectBuilder(paramMap);
//        subSqlBuilder.bindFields("o",orgDef.getFieldList());
        subSqlBuilder.bindFields("o", BeanDefUtils.includeField(orgDef.getFieldList(),"orgId"));
        subSqlBuilder.from("a",authorizeDef).innerJoinOn("o",orgDef,"orgId").where().
                and("a.user_id", ConditionType.EQUALS,"userId");

        SelectBuilder sqlBuilder = new SelectBuilder(orgDef,paramMap);
        sqlBuilder.where().and("parent_id",ConditionType.IN,subSqlBuilder.build());
        return sqlBuilder.build();
    }
}
