package cn.kduck.module.resource.dao.query;

import cn.kduck.module.resource.service.ResourceService;
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
public class ResourceOperateByGroup implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(ResourceService.CODE_RESOURCE_OPERATE);
        SelectBuilder queryBuilder = new SelectBuilder(entityDef, paramMap);
        queryBuilder.where()
                .and("resource_id", ConditionType.EQUALS,"resourceId",true)
                .and("group_code", ConditionType.IN,"groupCode",true);
        return queryBuilder.build();
    }
}
