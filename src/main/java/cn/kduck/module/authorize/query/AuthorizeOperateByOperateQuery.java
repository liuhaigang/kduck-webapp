package cn.kduck.module.authorize.query;

import cn.kduck.module.authorize.service.AuthorizeOperate;
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
public class AuthorizeOperateByOperateQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(AuthorizeService.CODE_AUTHORIZE);
        SelectBuilder sqlBuilder = new SelectBuilder(entityDef,paramMap);
        sqlBuilder.where()
                .and("resource_id", ConditionType.EQUALS, "resourceId",true)
                .groupBegin("operate_type", ConditionType.EQUALS,"${"+ AuthorizeOperate.OPERATE_TYPE_GROUP+"}")
                .and("operate_object", ConditionType.IN,"groupCode")
                .or("operate_type", ConditionType.EQUALS,"${"+ AuthorizeOperate.OPERATE_TYPE_ID+"}")
                .and("operate_object", ConditionType.EQUALS,"operateId")
                .groupEnd();
        return sqlBuilder.build();
    }
}
