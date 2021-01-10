package cn.kduck.module.resource.dao.query;

import cn.kduck.module.resource.service.impl.ResourceServiceImpl;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;

import java.util.Map;

/**
 * LiuHG
 */
//@Component
@Deprecated
public class ResourceUriQuery implements QueryCreator {

    @Override
    public String queryCode() {
        return "listResourceUri";
    }

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        SelectBuilder queryBuilder = new SelectBuilder("SELECT {*} FROM K_RESOURCE res,K_RESOURCE_OPERATE ro WHERE " +
                "res.RESOURCE_ID=ro.RESOURCE_ID ");
        queryBuilder.bindFields("res",depository.getFieldDefList(ResourceServiceImpl.CODE_RESOURCE));
        queryBuilder.bindFields("ro",depository.getFieldDefList(ResourceServiceImpl.CODE_RESOURCE_OPERATE));
        return queryBuilder.build();
    }
}
