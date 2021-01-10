package cn.kduck.module.hierarchical.query;

import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static cn.kduck.module.user.service.UserService.CODE_USER;

@Component
public class AuthorizeUserQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef authorizeDef = depository.getEntityDef(HierarchicalAuthorizeService.CODE_HIERARCHICAL_AUTHORIZE);
        BeanEntityDef userDef = depository.getEntityDef(CODE_USER);
        SelectBuilder sqlBuilder = new SelectBuilder(paramMap);
        sqlBuilder.bindFields("u",userDef.getFieldList())
                    .bindFields("a", BeanDefUtils.includeField(authorizeDef.getFieldList(),"authorizeId"));
        sqlBuilder.from("a",authorizeDef).innerJoinOn("u",userDef,"userId")
            .where().and("a.org_id", ConditionType.EQUALS,"orgId",true);
        return sqlBuilder.build();
    }
}
