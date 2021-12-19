package cn.kduck.module.organization.service.impl;

import cn.kduck.module.organization.dao.query.OrgUserQuery;
import cn.kduck.module.organization.dao.query.OrganizationByUserId;
import cn.kduck.module.organization.dao.query.OrganizationQuery;
import cn.kduck.module.organization.dao.query.UserForOrgUserQuery;
import cn.kduck.module.organization.service.OrgUser;
import cn.kduck.module.user.service.User;
import cn.kduck.module.organization.service.Organization;
import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.core.dao.NameFieldFilter;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.DeleteBuilder;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.event.Event;
import cn.kduck.core.event.Event.EventType;
import cn.kduck.core.event.EventPublisher;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueBean;
import cn.kduck.core.service.ValueMapList;
import cn.kduck.core.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
@Service
public class OrganizationServiceImpl extends DefaultService implements OrganizationService {

    @Autowired
    @Lazy
    private EventPublisher eventPublisher;

    @Override
    public void addOrganization(Organization organization) {
        ValueBean valueBean = super.createValueBean(CODE_ORGANIZATION, organization);

        //实现得到id值用于拼接dataPath
        Serializable idValue = super.generateIdValue(valueBean);

        //查询上级ID的dataPath作为基础拼接本机构的dataPath
        String parentId = organization.getParentId();
        Organization parentOrg = super.getForBean(CODE_ORGANIZATION, parentId, new NameFieldFilter("dataPath"),Organization::new);
        String parentDataPath = null;
        if(parentOrg != null ){
            parentDataPath = PathUtils.appendPath(parentOrg.getDataPath(),parentId,true);
        }else{
            parentDataPath = "/";
        }

//        valueBean.setValue("dataPath", PathUtils.appendPath(parentDataPath,String.valueOf(idValue)));'
        valueBean.setValue("dataPath", parentDataPath);
        super.add(valueBean,false);
    }

    @Override
    public void updateOrganization(Organization organization) {
        ValueBean valueBean = super.createValueBean(CODE_ORGANIZATION, organization);
        valueBean.removeField("dataPath");
        valueBean.removeField("parentId");
        super.update(valueBean);
    }

    @Override
    @Transactional
    public void deleteOrganization(String[] ids) {
        NameFieldFilter nameFieldFilter = new NameFieldFilter("dataPath");
        for (String id : ids) {
            Organization organization = super.getForBean(CODE_ORGANIZATION,id,nameFieldFilter,Organization::new);
            if(organization == null || organization.isEmpty()) continue;
            organization.formatValue("dataPath",(Object value, Map<String,Object> valueMap)->PathUtils.appendPath(""+value,id,true)+"%");
            DeleteBuilder delBuilder = new DeleteBuilder(super.getEntityDef(CODE_ORGANIZATION),organization);
            delBuilder.where().and("DATA_PATH", ConditionType.CONTAINS,Organization.DATA_PATH);
            super.executeUpdate(delBuilder.build());
        }

        super.delete(CODE_ORGANIZATION,ids);
        eventPublisher.publish(new Event(CODE_ORGANIZATION, EventType.DELETE,ids));
    }

    @Override
    public Organization getOrganization(String id) {
        return super.getForBean(CODE_ORGANIZATION,id,Organization::new);
    }

    @Override
    public List<Organization> listOrganizationByParentId(String orgId,Map<String, Object> paramMap, Page page) {
        if(paramMap.containsKey("deep")){
            Organization organization = super.getForBean(CODE_ORGANIZATION, orgId, new NameFieldFilter("dataPath"),Organization::new);
            paramMap.put("dataPath",PathUtils.appendPath(String.valueOf(organization.getDataPath()),orgId,true));
        }

        paramMap.put("incRoot",false);

        QuerySupport listOrganization = super.getQuery(OrganizationQuery.class, paramMap);
        return super.listForBean(listOrganization,page,Organization::new);
    }

    @Override
    public List<Organization> listOrganizationByIds(String[] orgIds) {
        Map<String, Object> paramMap = ParamMap.create("orgIds", orgIds).toMap();
        SelectBuilder sqlBuilder = new SelectBuilder(super.getEntityDef(CODE_ORGANIZATION),paramMap);
        sqlBuilder.where().and("org_id",ConditionType.IN,"orgIds",true);
        return super.listForBean(sqlBuilder.build(),Organization::new);
    }

    @Override
    public List<Organization> listAllOrganization(boolean incRoot) {
        Map<String, Object> paramMap = ParamMap.create("incRoot", incRoot).toMap();
        QuerySupport listOrganization = super.getQuery(OrganizationQuery.class, paramMap);
        return super.listForBean(listOrganization,Organization::new);
    }

    @Override
    public void addOrgUser(String orgId, String[] userIds) {
        OrgUser[] orgUsers = new OrgUser[userIds.length];
        int index = 0;
        for (String userId : userIds){
            Map<String, Object> valueMap = ParamMap.create(OrgUser.USER_ID, userId).set(OrgUser.ORG_ID, orgId).set(OrgUser.USER_TYPE,1).toMap();
            orgUsers[index] = new OrgUser(valueMap);
            index++;
        }
        super.batchAdd(CODE_ORGANIZATION_USER,orgUsers);
    }

    @Override
    public void deleteOrgUser(String[] userIds) {
        super.delete(CODE_ORGANIZATION_USER,userIds);
    }

    @Override
    public void deleteOrgUser(String orgId, String[] userIds) {
        Map<String, Object> paramMap = ParamMap.create("userIds", userIds).set("orgId", orgId).toMap();
        DeleteBuilder delBuilder = new DeleteBuilder(super.getEntityDef(CODE_ORGANIZATION_USER),paramMap);
        delBuilder.where().and("user_id", ConditionType.IN,"userIds").and("org_id",ConditionType.EQUALS,"orgId");

        super.executeUpdate(delBuilder.build());
    }

    @Override
    public void deleteOrgUserByUser(String[] userIds) {
        super.delete(CODE_ORGANIZATION_USER,"userId",userIds);
    }

    @Override
    public ValueMapList listOrgUser(String orgId, Page page) {
        QuerySupport query = super.getQuery(OrgUserQuery.class, ParamMap.create("orgId", orgId).toMap());
        return super.list(query,page);
    }

    @Override
    public List<User> listUserForOrgUser(String orgId, Page page) {
        QuerySupport query = super.getQuery(UserForOrgUserQuery.class, ParamMap.create("orgId", orgId).toMap());
        return super.listForBean(query,page, User::new);
    }

    @Override
    public ValueMapList listOrganizationByUserId(String userId) {
        QuerySupport query = super.getQuery(OrganizationByUserId.class, ParamMap.create("userId", userId).toMap());
        return super.list(query);
    }
}
