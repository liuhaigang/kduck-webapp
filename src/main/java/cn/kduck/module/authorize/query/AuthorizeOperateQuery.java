package cn.kduck.module.authorize.query;

import cn.kduck.module.authorize.service.AuthorizeService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthorizeOperateQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(AuthorizeService.CODE_AUTHORIZE);
        SelectBuilder sqlBuilder = new SelectBuilder(entityDef,paramMap);
        sqlBuilder.where()
                .and("authorize_type", ConditionType.EQUALS,"authorizeType",true)
                .and("authorize_object", ConditionType.EQUALS,"authorizeObject",true);
        return sqlBuilder.build();
    }
}
