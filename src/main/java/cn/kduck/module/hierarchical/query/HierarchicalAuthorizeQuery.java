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
public class HierarchicalAuthorizeQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef authorizeDef = depository.getEntityDef(HierarchicalAuthorizeService.CODE_HIERARCHICAL_AUTHORIZE);

        SelectBuilder sqlBuilder = new SelectBuilder(authorizeDef,paramMap);
        sqlBuilder.where()
                .and("org_id",ConditionType.EQUALS,"orgId")
                .and("user_id", ConditionType.EQUALS,"userId");
        return sqlBuilder.build();
    }
}
