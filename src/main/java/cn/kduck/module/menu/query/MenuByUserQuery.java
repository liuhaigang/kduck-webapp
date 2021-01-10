package cn.kduck.module.menu.query;

import cn.kduck.module.menu.service.Menu;
import cn.kduck.module.menu.service.MenuAuthorize;
import cn.kduck.module.menu.service.MenuService;
import cn.kduck.module.authorize.service.AuthorizeOperate;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static cn.kduck.module.authorize.service.AuthorizeService.CODE_AUTHORIZE;


@Component
public class MenuByUserQuery implements QueryCreator {

    /*
    select distinct m.* from k_menu m
        inner join k_menu_authorize ma on m.menu_id =ma.menu_id and m.menu_type=1 or ma.is_publish = 1
        inner join k_authorize a on a.operate_object = ma.operate_object and a.operate_type = ma.operate_type and a.resource_id = ma.resource_id
        where
        a.authorize_object='1' and a.authorize_type='1' or
        a.authorize_object in ('admin') and a.authorize_type='2' or
        a.authorize_object in ('12531198391872389121') and a.authorize_type='3';
     */
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef authorizeDef = depository.getEntityDef(CODE_AUTHORIZE);
        BeanEntityDef menuDef = depository.getEntityDef(MenuService.CODE_MENU);
        BeanEntityDef menuAuthorizeDef = depository.getEntityDef(MenuService.CODE_MENU_AUTHORIZE);

        SelectBuilder sqlBuilder = new SelectBuilder(paramMap,true);
        sqlBuilder.bindFields("m",menuDef.getFieldList());
        sqlBuilder.from("m",menuDef).innerJoinOn("ma",menuAuthorizeDef,"menuId")
                .andOn("m.menu_type", ConditionType.EQUALS,"${" + Menu.MENU_TYPE_MENU_ITEM + "}")
                .innerJoinOn("a",authorizeDef,"operateObject")
                .andOn("a.resource_id",ConditionType.EQUALS,"${ma.resource_id}")
                .andOn("a.operate_type",ConditionType.EQUALS,"${ma.operate_type}")
                .orOn("ma.is_public",ConditionType.EQUALS,"${" + MenuAuthorize.IS_PUBLIC_YES + "}")
                .where()
                .and("a.authorize_object",ConditionType.EQUALS,"userId").and("a.authorize_type",ConditionType.EQUALS,"${" + AuthorizeOperate.AUTHORIZE_TYPE_USER + "}");
            List roleCodes = (List)paramMap.get("roleCodes");
            if(roleCodes != null && !roleCodes.isEmpty()){
                sqlBuilder.get().or("a.authorize_object",ConditionType.IN,"roleCodes").and("a.authorize_type",ConditionType.EQUALS,"${" + AuthorizeOperate.AUTHORIZE_TYPE_ROLE + "}");
            }
            Object[] hierarchicalIds = (Object[])paramMap.get("hierarchicalIds");
            if(hierarchicalIds != null && hierarchicalIds.length > 0){
                sqlBuilder.get().or("a.authorize_object",ConditionType.IN,"hierarchicalIds").and("a.authorize_type",ConditionType.EQUALS,"${" + AuthorizeOperate.AUTHORIZE_TYPE_HIERARCHICAL + "}");
            }
        sqlBuilder.get().orderBy().asc("m.data_path").asc("m.order_num");
        return sqlBuilder.build();
    }
}
