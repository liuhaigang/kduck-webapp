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
public class BeanDefQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef beanDefInfo = depository.getEntityDef(DefinitionService.CODE_ENTITY_DEF_INFO);

        SelectBuilder builder = new SelectBuilder(beanDefInfo,paramMap);
        builder.where("ENTITY_CODE", ConditionType.EQUALS,"entityCode");
        return builder.build();
    }
}
