package cn.kduck.module.authorize.query;

import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

import static cn.kduck.module.authorize.service.AuthorizeService.CODE_AUTHORIZE;
import static cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService.CODE_HIERARCHICAL_AUTHORIZE;
import static cn.kduck.module.organization.service.OrganizationService.CODE_ORGANIZATION;
import static cn.kduck.module.resource.service.ResourceService.CODE_RESOURCE_OPERATE;
import static cn.kduck.module.role.service.RoleService.CODE_ROLE;
import static cn.kduck.module.role.service.RoleService.CODE_ROLE_OBJECT;
import static cn.kduck.module.user.service.UserService.CODE_USER;

@Component
public class AuthenticatedOperateQuery implements QueryCreator {

    public static final String AUTH_TYPE_USER = "AUTH_TYPE_USER";
    public static final String AUTH_TYPE_ORG = "AUTH_TYPE_ORG";
    public static final String AUTH_TYPE_ROLE = "AUTH_TYPE_ROLE";

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        Object authType = paramMap.get("authType");
        Assert.notNull(authType,"必须指定查询的授权类型参数：authType");

        if(authType.equals(AUTH_TYPE_USER)){
            return getAuthUserQuery(paramMap,depository);
        }else if(authType.equals(AUTH_TYPE_ORG)){
            return getAuthOrgQuery(paramMap,depository);
        }else if(authType.equals(AUTH_TYPE_ROLE)){
            return getAuthRoleQuery(paramMap,depository);
        }
        throw new RuntimeException("错误的授权类型：authType=" + authType);
    }

    private QuerySupport getAuthUserQuery(Map<String, Object> paramMap, BeanDefDepository depository){
        BeanEntityDef authorizeDef = depository.getEntityDef(CODE_AUTHORIZE);
        BeanEntityDef resourceOperateDef = depository.getEntityDef(CODE_RESOURCE_OPERATE);
        BeanEntityDef userDef = depository.getEntityDef(CODE_USER);
        SelectBuilder sqlBuiler = new SelectBuilder(paramMap);
        sqlBuiler.bindFields("ro",BeanDefUtils.includeField(resourceOperateDef.getFieldList(),"operateId","operateName"));
        sqlBuiler.from("a",authorizeDef)
                .innerJoinOn("ro",resourceOperateDef,"operateObject:operateId")
                .innerJoinOn("u",userDef,"authorizeObject:userId",authorizeDef)
                .where().and("a.authorize_type", ConditionType.EQUALS,"${1}")
                .and("a.operate_type", ConditionType.EQUALS,"${1}")
                .and("u.user_id",ConditionType.EQUALS,"userId");
        return sqlBuiler.build();
    }

    private QuerySupport getAuthOrgQuery(Map<String, Object> paramMap, BeanDefDepository depository){
        BeanEntityDef authorizeDef = depository.getEntityDef(CODE_AUTHORIZE);
        BeanEntityDef resourceOperateDef = depository.getEntityDef(CODE_RESOURCE_OPERATE);
        BeanEntityDef orgDef = depository.getEntityDef(CODE_ORGANIZATION);
        BeanEntityDef orgAuthorizeDef = depository.getEntityDef(CODE_HIERARCHICAL_AUTHORIZE);

        SelectBuilder sqlBuiler = new SelectBuilder(paramMap);
        sqlBuiler.bindFields("ro",BeanDefUtils.includeField(resourceOperateDef.getFieldList(),"operateId","operateName"));
        sqlBuiler.from("ro",resourceOperateDef)
                .innerJoinOn("a",authorizeDef,"operateId:operateObject")
                .innerJoinOn("ha",orgAuthorizeDef,"authorizeObject:authorizeId")
                .innerJoinOn("o",orgDef,"orgId")
                .where().and("a.authorize_type", ConditionType.EQUALS,"${3}")
                .and("a.operate_type", ConditionType.EQUALS,"${1}")
                .and("ha.user_id",ConditionType.EQUALS,"userId");
        return sqlBuiler.build();
    }

    private QuerySupport getAuthRoleQuery(Map<String, Object> paramMap, BeanDefDepository depository){
        BeanEntityDef authorizeDef = depository.getEntityDef(CODE_AUTHORIZE);
        BeanEntityDef resourceOperateDef = depository.getEntityDef(CODE_RESOURCE_OPERATE);
        BeanEntityDef roleDef = depository.getEntityDef(CODE_ROLE);
        BeanEntityDef roleObjDef = depository.getEntityDef(CODE_ROLE_OBJECT);

        SelectBuilder sqlBuiler = new SelectBuilder(paramMap);
        sqlBuiler.bindFields("ro",BeanDefUtils.includeField(resourceOperateDef.getFieldList(),"operateId","operateName"));
        sqlBuiler.from("ro",resourceOperateDef)
                .innerJoinOn("a",authorizeDef,"operateId:operateObject")
                .innerJoinOn("r",roleDef,"authorizeObject:roleCode")
                .innerJoinOn("roo",roleObjDef,"roleId")
                .where().and("a.authorize_type", ConditionType.EQUALS,"${2}")
                .and("a.operate_type", ConditionType.EQUALS,"${1}")
                .and("roo.role_object",ConditionType.EQUALS,"userId");
        return sqlBuiler.build();
    }
}
