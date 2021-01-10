package cn.kduck.module.hierarchical.service.impl;

import cn.kduck.module.hierarchical.query.AuthorizeUserQuery;
import cn.kduck.module.hierarchical.query.ExistAuthorizeUserQuery;
import cn.kduck.module.hierarchical.query.HierarchicalOrganizationQuery;
import cn.kduck.module.hierarchical.service.AuthorizeOrganization;
import cn.kduck.module.hierarchical.service.AuthorizeUser;
import cn.kduck.module.hierarchical.service.HierarchicalAuthorize;
import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.module.organization.service.Organization;
import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HierarchicalAuthorizeServiceImpl extends DefaultService implements HierarchicalAuthorizeService {

    @Autowired
    private OrganizationService organizationService;

    @Override
    public void addAuthorize(String orgId, String[] userIds) {
        List<HierarchicalAuthorize> authorizeList = new ArrayList<>(userIds.length);

        for (String userId : userIds) {
            authorizeList.add(new HierarchicalAuthorize(orgId,userId));
        }
        super.batchAdd(CODE_HIERARCHICAL_AUTHORIZE,authorizeList);
    }

    @Override
    public void deleteAuthorize(String[] ids) {
        super.delete(CODE_HIERARCHICAL_AUTHORIZE,ids);
    }

    @Override
    public void deleteAuthorizeByUser(String[] userId) {
        super.delete(CODE_HIERARCHICAL_AUTHORIZE,"userId",userId);
    }

    @Override
    public void deleteAuthorizeByOrgId(String[] orgIds) {
        super.delete(CODE_HIERARCHICAL_AUTHORIZE,"orgId",orgIds);
    }

    @Override
    public List<AuthorizeOrganization> listAuthorizeOrganizationByUserId(String userId) {
        Map<String, Object> paramMap = ParamMap.create("userId", userId).toMap();
        QuerySupport query = super.getQuery(HierarchicalOrganizationQuery.class, paramMap);
        List<AuthorizeOrganization> authorizeOrgList = super.listForBean(query, AuthorizeOrganization::new);

        //TODO 如果可分配下级所有子机构，需要进行多OR的LIKE查询。目前只是查询下1级的机构进行权限分配

        // STEP1：
        // 将用户拥有权限的机构的所有父级机构ID从DATA_PATH中提取出来，
        // 便于后面查询用于构造完整树的父机构查询条件
        List<String> parentIdList = new ArrayList<>();
        for (AuthorizeOrganization authorizeOrg : authorizeOrgList) {
            authorizeOrg.setIsAuthorized(true);
            String dataPath = authorizeOrg.getDataPath();
            String[] parentIds = dataPath.split("[/]");
            for (String parentId : parentIds) {
                if("".equals(parentId)) continue;
                if(!parentIdList.contains(parentId)){
                    parentIdList.add(parentId);
                }
            }
        }

        // STEP2：
        // 查询所有授权机构下的所有子节点，适用于可以跨级为子机构用户授权。
        // NOTE：如果仅需要查询下面一级机构，则不需要这步骤的所有代码。
        // FIXME 此处为实现方便，将所有机构查询出来进行子机构判断。如果机构过多会有性能隐患。
        List<Organization> allOrgList = organizationService.listAllOrganization(false);
        for (Organization org : allOrgList) {
            for (AuthorizeOrganization authorizeOrg : authorizeOrgList) {
                if(org.getDataPath().startsWith(PathUtils.appendPath(authorizeOrg.getDataPath(),authorizeOrg.getOrgId(),true))){
                    authorizeOrgList.add(new AuthorizeOrganization(org,true));
                    break;
                }
            }
        }

        // STEP3：
        // 根据"STEP1"汇总的所有父机构ID，查询所有父机构节点，便于返回前端组装完整树。
        if(!parentIdList.isEmpty()){
            List<Organization> parentOrgList = organizationService.listOrganizationByIds(parentIdList.toArray(new String[0]));
            for (Organization parentOrg : parentOrgList) {
                boolean isExist = false;
                for (AuthorizeOrganization authorizeOrg : authorizeOrgList) {
                    if(parentOrg.getOrgId().equals(authorizeOrg.getOrgId())){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    authorizeOrgList.add(new AuthorizeOrganization(parentOrg,false));
                }
            }
        }

        return authorizeOrgList;
    }

    @Override
    public List<AuthorizeOrganization> listAuthorizeOrganizationByOrgId(String orgId) {
        return null;
    }

    @Override
    public List<AuthorizeUser> listAuthorizeUser(String orgId) {
        QuerySupport query = super.getQuery(AuthorizeUserQuery.class, ParamMap.create("orgId", orgId).toMap());
        return super.listForBean(query,AuthorizeUser::new);
    }

    @Override
    public boolean existAuthorizeUser(String userId, String orgId, String[] authorizeUserIds) {
        Map<String, Object> paramMap = ParamMap.create("userId", userId).set("orgId", orgId).set("authorizeUserIds", authorizeUserIds).toMap();
        QuerySupport query = super.getQuery(ExistAuthorizeUserQuery.class, paramMap);
        return super.exist(query);
    }

    /**
     * 用于检测指定机构下是否存在指定的授权用户，用于做添加时判重复
     * @param orgId
     * @param userIds
     * @return
     */
    private List<HierarchicalAuthorize> existsHierarchicalAuthorize(String orgId, String[] userIds){
        return null;
    }
}
