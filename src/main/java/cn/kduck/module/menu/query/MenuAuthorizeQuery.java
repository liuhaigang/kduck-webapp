package cn.kduck.module.menu.query;

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
public class MenuAuthorizeQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef menuAuthorizeDef = depository.getEntityDef(MenuService.CODE_MENU_AUTHORIZE);
        SelectBuilder sqlBuilder = new SelectBuilder(menuAuthorizeDef,paramMap);
        sqlBuilder.where().and("menu_id", ConditionType.EQUALS,"menuId",true)
        .and("operate_type",ConditionType.EQUALS,"operateType");
        return sqlBuilder.build();
    }
}
