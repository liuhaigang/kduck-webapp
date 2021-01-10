package cn.kduck.module.resource.dao.query;

import cn.kduck.module.resource.service.impl.ResourceServiceImpl;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;

import java.util.Map;

/**
 *
 * LiuHG
 */
//@Component
@Deprecated
public class ResourceOperateByRoleQuery implements QueryCreator {

    @Override
    public String queryCode() {
        return "listResourceAndOperateByRole";
    }

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        SelectBuilder queryBuilder = new SelectBuilder("SELECT {*} FROM K_ROLE r,K_RESOURCE res,K_RESOURCE_OPERATE ro,K_ROLE_OPERATE o WHERE " +
                "res.RESOURCE_ID=ro.RESOURCE_ID " +
                "AND ro.OPERATE_ID=o.OPERATE_ID " +
                "AND r.ROLE_ID=o.ROLE_ID", paramMap);
        queryBuilder.where(false).and("r.ROLE_CODE", ConditionType.IN,"roleCode");
        queryBuilder.bindFields("res",depository.getFieldDefList(ResourceServiceImpl.CODE_RESOURCE));
        queryBuilder.bindFields("ro",depository.getFieldDefList(ResourceServiceImpl.CODE_RESOURCE_OPERATE));
        return queryBuilder.build();
    }
}
