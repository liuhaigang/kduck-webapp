package cn.kduck.module.organization.dao.query;

import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.module.organization.service.Organization;
import cn.kduck.module.organization.service.impl.OrganizationServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * LiuHG
 */
@Component
public class OrganizationQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        SelectBuilder queryBuidler = new SelectBuilder("SELECT {*} FROM K_ORGANIZATION",paramMap,depository.getFieldDefList(OrganizationServiceImpl.CODE_ORGANIZATION));

        if(paramMap != null && paramMap.containsKey("dataPath")){
            queryBuidler.where("DATA_PATH",ConditionType.BEGIN_WITH,"dataPath").and("ORG_ID",ConditionType.NOT_EQUALS,"parentId");
        }else{
            queryBuidler.where("PARENT_ID", ConditionType.EQUALS,"parentId");
        }
        if(!paramMap.containsKey("incRoot") || !Boolean.valueOf("" + paramMap.get("incRoot"))){
            queryBuidler.get().and("ORG_ID",ConditionType.NOT_EQUALS,"${" + Organization.ROOT_ID + "}");
        }
        queryBuidler.get().and("ORG_NAME",ConditionType.CONTAINS,"orgName").orderBy().asc("DATA_PATH").asc("ORDER_NUM");
        return queryBuidler.build();
    }
}
