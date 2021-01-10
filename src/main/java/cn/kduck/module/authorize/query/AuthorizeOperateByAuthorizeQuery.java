package cn.kduck.module.authorize.query;

import cn.kduck.module.authorize.service.AuthorizeOperate;
import cn.kduck.module.authorize.service.AuthorizeService;
import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AuthorizeOperateByAuthorizeQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {

        BeanEntityDef entityDef = depository.getEntityDef(AuthorizeService.CODE_AUTHORIZE);
        SelectBuilder sqlBuilder = new SelectBuilder(entityDef,paramMap);
        ConditionBuilder groupBuilder = sqlBuilder.where()
                .and("operate_type", ConditionType.EQUALS, "operateType", true)
                .groupBegin("authorize_type", ConditionType.EQUALS, "${" + AuthorizeOperate.AUTHORIZE_TYPE_USER + "}")
                .and("authorize_object", ConditionType.EQUALS, "userId");

        Object roleCodeList = paramMap.get("roleCode");
        if(roleCodeList != null && !((List)roleCodeList).isEmpty()) {
            groupBuilder.or("authorize_type", ConditionType.EQUALS, "${" + AuthorizeOperate.AUTHORIZE_TYPE_ROLE + "}")
                    .and("authorize_object", ConditionType.IN, "roleCode");
        }
        if(paramMap.containsKey("orgIds")){
            BeanEntityDef hierAuthorizeDef = depository.getEntityDef(HierarchicalAuthorizeService.CODE_HIERARCHICAL_AUTHORIZE);

            SelectBuilder subSqlBuilder = new SelectBuilder(hierAuthorizeDef,paramMap);
            subSqlBuilder.bindFields("", BeanDefUtils.includeField(hierAuthorizeDef.getFieldList(),"authorizeId"));
            subSqlBuilder.where().and("org_id",ConditionType.IN,"orgIds",true).and("user_id",ConditionType.EQUALS,"userId",true);

            groupBuilder.or("authorize_type", ConditionType.EQUALS,"${"+ AuthorizeOperate.AUTHORIZE_TYPE_HIERARCHICAL+"}")
                    .and("authorize_object", ConditionType.IN,subSqlBuilder.build());
        }
        groupBuilder.groupEnd();

        return sqlBuilder.build();
    }
}