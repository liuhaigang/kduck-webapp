package cn.kduck.module.menu.query;

import cn.kduck.module.menu.service.Menu;
import cn.kduck.module.menu.service.MenuService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MenuQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef menuDef = depository.getEntityDef(MenuService.CODE_MENU);
        SelectBuilder sqlBuilder = new SelectBuilder(menuDef,paramMap);
        if(paramMap != null && paramMap.containsKey("dataPath")){
            sqlBuilder.where("data_path",ConditionType.BEGIN_WITH,"dataPath").and("menu_id",ConditionType.NOT_EQUALS,"parentId");
        }else{
            sqlBuilder.where("parent_id", ConditionType.EQUALS,"parentId");
        }
        sqlBuilder.get().and("menu_id", ConditionType.IN,"ids").orderBy().asc("data_path").asc("order_num");

        if(!paramMap.containsKey("incRoot") || !Boolean.valueOf("" + paramMap.get("incRoot"))){
            sqlBuilder.get().and("menu_id",ConditionType.NOT_EQUALS,"${" + Menu.ROOT_ID + "}");
        }

        return sqlBuilder.build();
    }
}
