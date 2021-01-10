package cn.kduck.module.authorize.query;

import cn.kduck.module.authorize.service.AuthorizeOperate;
import cn.kduck.module.authorize.service.AuthorizeService;
import cn.kduck.module.resource.service.ResourceService;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.dao.sqlbuilder.SelectConditionBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;


@Deprecated
@Component
public class AuthorizeResourceQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        Object authorizeType = paramMap.get("authorizeType");
        Assert.notNull(authorizeType,"授权类型不能为null");

        BeanEntityDef authorizeDef = depository.getEntityDef(AuthorizeService.CODE_AUTHORIZE);
        BeanEntityDef resourceOperateDef = depository.getEntityDef(ResourceService.CODE_RESOURCE_OPERATE);

        SelectBuilder sqlBuilder = new SelectBuilder(paramMap);
        sqlBuilder.bindFields("ro",resourceOperateDef.getFieldList());
        SelectConditionBuilder whereBuilder = sqlBuilder.from("a", authorizeDef)
                .innerJoinOn("ro", resourceOperateDef, "operateObject:operateId").andOn("a.operate_type", ConditionType.EQUALS, "${" + AuthorizeOperate.OPERATE_TYPE_ID + "}")
                .where();

        if(AuthorizeOperate.AUTHORIZE_TYPE_USER == Integer.parseInt(authorizeType.toString())){
            whereBuilder.and("a.authorize_object",ConditionType.EQUALS,"userId",true)
                    .and("a.authorize_type",ConditionType.EQUALS,"${" + AuthorizeOperate.AUTHORIZE_TYPE_USER + "}");
        }else if(AuthorizeOperate.AUTHORIZE_TYPE_ROLE == Integer.parseInt(authorizeType.toString())){
            whereBuilder.and("a.authorize_object",ConditionType.IN,"roleId",true)
                    .and("a.authorize_type",ConditionType.EQUALS,"${" + AuthorizeOperate.AUTHORIZE_TYPE_ROLE + "}");
        }else{
            throw new RuntimeException("不支持的授权类型：" + authorizeType);
        }
        return sqlBuilder.build();
    }
}
