package cn.kduck.module.definition.query;

import cn.kduck.module.definition.service.DefinitionService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author LiuHG
 */
@Component
public class BeanFieldDefQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef beanFieldDefInfo = depository.getEntityDef(DefinitionService.CODE_ENTITY_FIELD_DEF_INFO);
        BeanEntityDef beanDefInfo = depository.getEntityDef(DefinitionService.CODE_ENTITY_DEF_INFO);

        SelectBuilder builder = new SelectBuilder(paramMap);
        builder.bindFields("b",beanDefInfo.getFieldList())
                .bindFields("f", BeanDefUtils.excludeField(beanFieldDefInfo.getFieldList(),"entityDefId"))
                .from("b",beanDefInfo).innerJoinOn("f",beanFieldDefInfo,"entityDefId")
                .where().and("b.ENTITY_DEF_ID", ConditionType.EQUALS,"entityDefId")
                .and("b.ENTITY_CODE", ConditionType.EQUALS,"entityCode")
                .orderBy().desc("f.IS_PK").desc("f.IS_FK");
        return builder.build();
    }
}
