package cn.kduck.module.resource.service.impl;

import cn.kduck.module.resource.dao.query.ResourcByGroupQuery;
import cn.kduck.module.resource.dao.query.ResourcQuery;
import cn.kduck.module.resource.dao.query.ResourceOperateByGroup;
import cn.kduck.module.resource.dao.query.ResourceOperateByResourceId;
import cn.kduck.module.resource.service.ResourceOperate;
import cn.kduck.module.resource.service.ResourceQueryFactory;
import cn.kduck.module.resource.service.ResourceService;
import cn.kduck.module.resource.service.Resource;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.core.service.*;
import cn.kduck.core.web.resource.ModelResourceProcessor;
import cn.kduck.core.web.resource.ResourceValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
@Service
public class ResourceServiceImpl extends DefaultService implements ResourceService, ModelResourceProcessor {

    @Autowired
    private ResourceQueryFactory queryFactory;

    @Override
    @Transactional
    public void saveResource(Resource resource) {
        String code = resource.getResourceCode();
        String md5 = resource.getMd5();

        /*
            根据编码查询资源数据，如果该资源没有发生任何改变（根据md5），则不作任何处理。
            如果资源存在，更新资源信息（包括MD5值），然后查询当前资源下的操作，和新的操作进行比较，存在则更新，不存在则添加。
            如果资源不存在，则直接保存新资源
         */
        Resource res = super.getForBean(CODE_RESOURCE,Resource.RESOURCE_CODE,code,null,Resource::new);

        //资源是否已经存在
        if(res != null){
            String resMd5 = res.getMd5();
            if(resMd5.equals(md5)){
                return;
            }

            String resourceId = res.getResourceId();

            resource.setResourceId(resourceId);
            super.update(CODE_RESOURCE,resource);

            //先禁用所有资源，后对当前存在的资源进行状态启用，剩余的就是被删除的资源，不会被启用。
            ResourceOperate resourceOperate = new ResourceOperate();
            resourceOperate.setResourceId(resourceId);
            resourceOperate.setIsEnable(0);
            super.update(CODE_RESOURCE_OPERATE,ResourceOperate.RESOURCE_ID,resourceOperate);

            QuerySupport query = super.getQuery(ResourceOperateByResourceId.class, ParamMap.create("resourceId", resourceId).toMap());
            List<ResourceOperate> optList = super.listForBean(query,ResourceOperate::new);
            List<ResourceOperate> operateList = resource.getOperateList();
            for (ResourceOperate opt : operateList) {
                ResourceOperate newOpt = new ResourceOperate(opt);
                newOpt.setIsEnable(1);

                //比较操作，判断操作是否已经存在
                ResourceOperate resOpt = operateExist(opt, optList);
                if(resOpt != null){
                    String operateId = resOpt.getOperateId();
                    newOpt.setOperateId(operateId);
                    super.update(CODE_RESOURCE_OPERATE,newOpt);
                }else{
                    newOpt.setResourceId(resource.getResourceId());
                    super.add(CODE_RESOURCE_OPERATE,newOpt);
                }
            }
        }else{
            //是新的资源，则新增资源信息然后新增操作信息
            super.add(CODE_RESOURCE,resource);
            List<ResourceOperate> operateList = resource.getOperateList();
            if(!operateList.isEmpty()){
                ResourceOperate[] resOpts = new ResourceOperate[operateList.size()];
                for (int i = 0; i < resOpts.length; i++) {
                    resOpts[i] = new ResourceOperate(operateList.get(i));
                    resOpts[i].setResourceId(resource.getResourceId());
                    resOpts[i].setIsEnable(1);
                }
                super.batchAdd(CODE_RESOURCE_OPERATE,resOpts);
            }
        }
    }

    private ResourceOperate operateExist(ResourceOperate opt,List<ResourceOperate> optMapList){
        for (ResourceOperate oldOptMap : optMapList) {
            String oldOptCode = oldOptMap.getOperateCode();
            String optCode = opt.getOperateCode();
            if(oldOptCode.equals(optCode)){
                return oldOptMap;
            }

        }
        return null;
    }

    @Override
    public void deleteResource(String[] ids) {

    }

    @Override
    public Resource getResource(String id) {
        return null;
    }

    @Override
    public List<Resource> listAllResource() {
        QuerySupport listResource = super.getQuery(ResourcQuery.class, null);
        List<Resource> resourceList = super.listForBean(listResource, Resource::new);
        return resourceList;
    }

    @Override
    public List<Resource> listAllResourceByGroup() {
        QuerySupport listResource = super.getQuery(ResourcByGroupQuery.class, null);
        List<Resource> resourceList = super.listForBean(listResource, Resource::new);
        return resourceList;
    }

    @Override
    public List<Resource> listResource(Map<String, Object> condition, Page page) {
        return null;
    }

    @Override
//    @Transactional
    //FIXME 处理事务死锁
    public void saveOperateByRole(String roleId, String[] operateIds) {
        super.delete(CODE_ROLE_OPERATE,"roleId",new String[]{roleId});

        if(operateIds.length == 0) return;
        ValueBean[] valueBeans = new ValueBean[operateIds.length];
        for (int i = 0; i < valueBeans.length; i++) {
            ValueMap valueMap = new ValueMap();
            valueMap.put("roleId",roleId);
            valueMap.put("operateId",operateIds[i]);
            valueBeans[i] = super.createValueBean(CODE_ROLE_OPERATE,valueMap);
        }

        super.batchAdd(valueBeans);
    }

    @Override
    public List<ResourceOperate> listOperateByRole(String roleId) {
        QuerySupport querySupport = queryFactory.listOperateByRole(roleId);
        return super.listForBean(querySupport,ResourceOperate::new);
    }

    @Override
    public ResourceOperate getResourceOperate(String path, String method) {
        Map<String, Object> paramMap = ParamMap.create("path", path).set("method", method).toMap();
        SelectBuilder sqlBuilder = new SelectBuilder(super.getEntityDef(CODE_RESOURCE_OPERATE),paramMap);
        sqlBuilder.where()
                .and("operate_path", ConditionType.EQUALS,"path",true)
                .and("method", ConditionType.EQUALS,"method",true);

        ResourceOperate resourceOperate = super.getForBean(sqlBuilder.build(), ResourceOperate::new);
        return resourceOperate;
    }

    @Override
    public List<ResourceOperate> listOperateByGroup(String resourceId, String[] groupCode) {
        Map<String, Object> paramMap = ParamMap.create("resourceId", resourceId).set("groupCode", groupCode).toMap();
        QuerySupport query = super.getQuery(ResourceOperateByGroup.class, paramMap);
        return super.listForBean(query,ResourceOperate::new);
    }

//    @Override
//    public ValueMapList listResourceAndOperateByRoleCode(String[] roleCode) {
//        if(roleCode.length == 0){return ValueMapList.EMPTY_LIST;
//        }
//        Map<String,Object> condition = new HashMap<>();
//        condition.put("roleCode",roleCode);
//        QuerySupport listResourceAndOperate = super.getQuery("listResourceAndOperateByRole", condition);
//        return super.list(listResourceAndOperate,null, new NameFieldFilter("resourcePath","operatePath","method"));
//    }
//
//    @Override
//    public List<String> listResourceUri() {
//        QuerySupport resourceUriQuery = super.getQuery("listResourceUri", null);
//        ValueMapList valueMapList = super.list(resourceUriQuery, new NameFieldFilter("resourcePath", "operatePath", "method"));
//        List<String> uriList = new LinkedList<>();
//        for (ValueMap valueMap : valueMapList){
//            String uri = PathUtils.appendPath(valueMap.getValueAsString("resourcePath"),valueMap.getValueAsString("operatePath"));
//            uriList.add(formatUri(uri,valueMap.getValueAsString("method")));
//        }
//        return uriList;
//    }
//
//    protected String formatUri(String uri,String method){
//        return method + ":" + uri;
//    }

    @Override
    public void doProcess(ResourceValueMap resource,Class clazz) {
        saveResource(new Resource(resource));
    }
}
