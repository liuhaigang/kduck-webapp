package cn.kduck.module.definition.query;

import cn.kduck.module.definition.service.DefinitionService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author LiuHG
 */
@Component
public class RelationBeanQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
//        BeanEntityDef relationEntity = depository.getEntityDef(TableCode.ENTITY_RELATION);
//        BeanEntityDef beanDefInfo = depository.getEntityDef(TableCode.ENTITY_DEF_INFO);
//
//        SelectBuilder builder = new SelectBuilder(paramMap);
//        builder.bindFields("b",beanDefInfo.getFieldList())
//                .from("b",beanDefInfo).innerJoin("r",relationEntity)
//                .where().and("r.RELATION_DEF_ID", ConditionType.EQUALS,"entityDefId");
//        return builder.build();

        BeanEntityDef relationEntity = depository.getEntityDef(DefinitionService.CODE_ENTITY_RELATION);

        SelectBuilder builder = new SelectBuilder(relationEntity,paramMap);
        builder.where("ENTITY_DEF_ID", ConditionType.EQUALS,"entityDefId");
        return builder.build();
    }
}
