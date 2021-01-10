package cn.kduck.module.hierarchical.query;

import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExistAuthorizeUserQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(HierarchicalAuthorizeService.CODE_HIERARCHICAL_AUTHORIZE);
        SelectBuilder sqlBuilder = new SelectBuilder(entityDef,paramMap);
        sqlBuilder.where()
                .and("user_id", ConditionType.EQUALS,"userId",true)
                .and("org_id",ConditionType.EQUALS,"orgId",true)
                .and("authorize_id",ConditionType.IN,"authorizeUserIds",true);
        return sqlBuilder.build();
    }
}
