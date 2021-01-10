package cn.kduck.module.organization.dao.query;

import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.module.user.service.UserService;
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
public class OrgUserQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef orgUserEntity = depository.getEntityDef(OrganizationService.CODE_ORGANIZATION_USER);
        BeanEntityDef userEntity = depository.getEntityDef(UserService.CODE_USER);

        SelectBuilder builder = new SelectBuilder(paramMap);
        builder.bindFields("u",userEntity.getFieldList());
        builder.bindFields("ou", BeanDefUtils.includeField(orgUserEntity.getFieldList(),"orgUserId","userType"));
        builder.from("ou",orgUserEntity).innerJoinOn("u",userEntity,"userId")
                .where().and("ou.ORG_ID",ConditionType.EQUALS,"orgId",true)
                .and("ou.USER_TYPE", ConditionType.EQUALS,"userType")
                .and("ou.USER_ID", ConditionType.IN,"userIds");
        return builder.build();
    }
}
