package cn.kduck.module.role.query;

import cn.kduck.module.role.service.Role;
import cn.kduck.module.role.service.RoleService;
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
public class RoleObjectForRoleQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {

        int type = Integer.parseInt(paramMap.get("type").toString());

        SelectBuilder sqlBuilder = null;
        if(type == Role.ROLE_TYPE_USER){
            BeanEntityDef roleObjectDef = depository.getEntityDef(RoleService.CODE_ROLE_OBJECT);
            BeanEntityDef userDef = depository.getEntityDef(CODE_USER);

            SelectBuilder subSqlBuilder = new SelectBuilder(roleObjectDef,paramMap);
            subSqlBuilder.bindFields("",BeanDefUtils.includeField(roleObjectDef.getFieldList(),"roleObject"));
            subSqlBuilder.where().and("object_type", ConditionType.EQUALS,"${" + Role.ROLE_TYPE_USER + "}")
            .and("role_id",ConditionType.EQUALS,"roleId",true);

            sqlBuilder = new SelectBuilder(userDef,paramMap);
            sqlBuilder.where().and("user_id",ConditionType.NOT_IN,subSqlBuilder.build())
                    .and("user_name", ConditionType.CONTAINS,"objectName");
        }else if(type == Role.ROLE_TYPE_ORG){
            //TODO for org type
        }

        return sqlBuilder.build();
    }
}
