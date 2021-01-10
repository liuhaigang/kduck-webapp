package cn.kduck.module.resource.dao.query;

import cn.kduck.module.resource.service.ResourceService;
import cn.kduck.module.resource.service.impl.ResourceServiceImpl;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.definition.BeanFieldDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
@Component
public class ResourcByGroupQuery implements QueryCreator {


    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {

        BeanEntityDef resDef = depository.getEntityDef(ResourceService.CODE_RESOURCE);
        BeanEntityDef resOptDef = depository.getEntityDef(ResourceService.CODE_RESOURCE_OPERATE);

        List<BeanFieldDef> fieldDefList = depository.getFieldDefList(ResourceServiceImpl.CODE_RESOURCE_OPERATE);

        SelectBuilder queryBuilder = new SelectBuilder(paramMap);
        queryBuilder.from("res",resDef).innerJoin("ro",resOptDef).where().and("ro.is_enable", ConditionType.EQUALS,"${1}")
                .groupBy("res.resource_id","ro.group_code").orderBy().asc("res.resource_name");

        queryBuilder.bindFields("res",depository.getFieldDefList(ResourceServiceImpl.CODE_RESOURCE));
        queryBuilder.bindFields("ro", BeanDefUtils.includeField(fieldDefList,"groupCode"));
//        queryBuilder.bindAlias("ro.group_code","operate_id");

        return queryBuilder.build();
    }
}
