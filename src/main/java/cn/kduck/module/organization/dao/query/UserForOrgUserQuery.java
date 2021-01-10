package cn.kduck.module.organization.dao.query;

import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import static cn.kduck.module.organization.service.OrganizationService.CODE_ORGANIZATION_USER;
import static cn.kduck.module.user.service.UserService.CODE_USER;

/**
 * 根据机构ID查询不在当前机构的用户，用于选择机构用户，提供备选用户列表
 * @author LiuHG
 */
@Component
public class UserForOrgUserQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef userDef = depository.getEntityDef(CODE_USER);
        BeanEntityDef orgUserDef = depository.getEntityDef(CODE_ORGANIZATION_USER);

        SelectBuilder subSqlBuilder = new SelectBuilder(orgUserDef,paramMap);
        subSqlBuilder.bindFields("", BeanDefUtils.includeField(orgUserDef.getFieldList(),"userId"));
        subSqlBuilder.where().and("org_id", ConditionType.EQUALS,"orgId");

        SelectBuilder sqlBuilder = new SelectBuilder(userDef,paramMap);
        sqlBuilder.where().and("user_id",ConditionType.NOT_IN,subSqlBuilder.build());
        return sqlBuilder.build();
    }
}
