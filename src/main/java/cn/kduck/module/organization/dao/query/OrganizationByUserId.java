package cn.kduck.module.organization.dao.query;

import org.springframework.stereotype.Component;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.module.organization.service.impl.OrganizationServiceImpl;

import java.util.Map;

/**
 * LiuHG
 */
@Component
public class OrganizationByUserId implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        SelectBuilder queryBuidler = new SelectBuilder("SELECT {*} FROM K_ORGANIZATION o,K_ORGANIZATION_USER ou WHERE o.ORG_ID=ou.ORG_ID",paramMap);
        queryBuidler.where(false).and("ou.USER_ID", ConditionType.EQUALS,"userId").orderBy().asc("DATA_PATH").asc("ORDER_NUM");

        queryBuidler.bindFields("o",depository.getFieldDefList(OrganizationServiceImpl.CODE_ORGANIZATION));
        return queryBuidler.build();
    }
}
