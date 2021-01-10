package cn.kduck.module.configstore.query;

import cn.kduck.module.configstore.service.ConfigStoreService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.dao.sqlbuilder.SelectConditionBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConfigItemQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef configDef = depository.getEntityDef(ConfigStoreService.CODE_CONFIG_STORE);
        BeanEntityDef configItemDef = depository.getEntityDef(ConfigStoreService.CODE_CONFIG_STORE_ITEM);

        SelectBuilder sqlBuilder = new SelectBuilder(paramMap);
        sqlBuilder.bindFields("i",configItemDef.getFieldList());
        SelectConditionBuilder builder = sqlBuilder.from("c", configDef).innerJoinOn("i", configItemDef, "configId").where();
        if(queryByItemName(paramMap)){
            builder.and("i.config_id", ConditionType.EQUALS, "configId").and("i.item_name", ConditionType.EQUALS, "itemName");
        }else{
            builder.and("c.config_code", ConditionType.EQUALS, "configCode", true);
            if(Boolean.valueOf(""+paramMap.get("hasSubConfig"))){
                builder.or("c.config_code", ConditionType.BEGIN_WITH, "configCode", true);
            }
        }
        return sqlBuilder.build();
    }

    private boolean queryByItemName(Map<String, Object> paramMap){
        return paramMap.containsKey("configId") && paramMap.containsKey("itemName");
    }
}
