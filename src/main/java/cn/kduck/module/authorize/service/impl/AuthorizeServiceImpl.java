package cn.kduck.module.authorize.service.impl;

import cn.kduck.core.service.ValueMapList;
import cn.kduck.module.authorize.query.AuthenticatedOperateQuery;
import cn.kduck.module.authorize.service.AuthorizeOperate;
import cn.kduck.module.role.service.Role;
import cn.kduck.module.role.service.RoleService;
import cn.kduck.module.authorize.query.AuthorizeOperateByAuthorizeQuery;
import cn.kduck.module.authorize.query.AuthorizeOperateByOperateQuery;
import cn.kduck.module.authorize.query.AuthorizeOperateQuery;
import cn.kduck.module.authorize.service.AuthorizeService;
import cn.kduck.module.organization.service.Organization;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.DeleteBuilder;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.utils.PathUtils;
import cn.kduck.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.kduck.module.authorize.query.AuthenticatedOperateQuery.AUTH_TYPE_ORG;
import static cn.kduck.module.authorize.query.AuthenticatedOperateQuery.AUTH_TYPE_ROLE;
import static cn.kduck.module.authorize.query.AuthenticatedOperateQuery.AUTH_TYPE_USER;
import static cn.kduck.module.organization.service.OrganizationService.CODE_ORGANIZATION;

@Service
public class AuthorizeServiceImpl extends DefaultService implements AuthorizeService {

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional
    public void saveAuthorizeOperate(Integer authorizeType, String authorizeObject, Integer operateType, String[] operateObjects) {
        List<AuthorizeOperate> authorizeOperateList = new ArrayList<>();
        List<String> deletedResource = new ArrayList<>();
        if(operateType.intValue() == AuthorizeOperate.OPERATE_TYPE_GROUP){
//            deleteAuthorizeOperate(authorizeType,authorizeObject);

            //FIXME 下面两处for的重复代码
            for (String operateObject : operateObjects) {
                AuthorizeOperate authorizeOperate = new AuthorizeOperate();
                authorizeOperate.setAuthorizeType(authorizeType);
                authorizeOperate.setAuthorizeObject(authorizeObject);
                authorizeOperate.setOperateType(operateType);

                String[] split = operateObject.split("#");
                Assert.isTrue(split.length == 2 || (split.length == 1 && operateObject.endsWith("#")),"授权操作对象格式不正确");

                if(!deletedResource.contains(split[0])){
                    deletedResource.add(split[0]);
                    deleteAuthorizeOperate(authorizeType,authorizeObject,operateType,split[0]);
//                    super.delete(CODE_AUTHORIZE,"resourceId",new String[]{split[0]});
                }

                if(split.length == 2){
                    authorizeOperate.setResourceId(split[0]);
                    authorizeOperate.setOperateObject(split[1]);
                    authorizeOperateList.add(authorizeOperate);
                }

            }

        }else if(operateType.intValue() == AuthorizeOperate.OPERATE_TYPE_ID){

            deleteAuthorizeOperate(authorizeType,authorizeObject,operateType,null);

            for (String operateObject : operateObjects) {
                AuthorizeOperate authorizeOperate = new AuthorizeOperate();
                authorizeOperate.setAuthorizeType(authorizeType);
                authorizeOperate.setAuthorizeObject(authorizeObject);
                authorizeOperate.setOperateType(operateType);

                String[] split = operateObject.split("#");
                Assert.isTrue(split.length == 2 || (split.length == 1 && operateObject.endsWith("#")),"授权操作对象格式不正确");

//                if(!deletedResource.contains(split[0])){
//                    deletedResource.add(split[0]);
//                    deleteAuthorizeOperate(authorizeType,authorizeObject,split[0]);
//                }

                if(split.length == 2){
                    authorizeOperate.setResourceId(split[0]);
                    authorizeOperate.setOperateObject(split[1]);
                    authorizeOperateList.add(authorizeOperate);
                }

            }
        }
        if(!authorizeOperateList.isEmpty()){
            super.batchAdd(CODE_AUTHORIZE, authorizeOperateList);
        }
    }

    @Override
    public void deleteAuthorizeOperate(Integer authorizeType, String authorizeObject,Integer operateType,String resourceId) {
        Map<String, Object> paramMap = ParamMap.create("authorizeType", authorizeType)
                .set("authorizeObject", authorizeObject)
                .set("operateType", operateType)
                .set("resourceId", resourceId).toMap();
        DeleteBuilder deleteBuilder = new DeleteBuilder(super.getEntityDef(CODE_AUTHORIZE),paramMap);
        deleteBuilder.where()
                .and("authorize_type", ConditionType.EQUALS,"authorizeType",true)
                .and("authorize_object", ConditionType.EQUALS,"authorizeObject",true)
                .and("operate_type", ConditionType.EQUALS,"operateType")
                .and("resource_id", ConditionType.EQUALS,"resourceId");
        super.executeUpdate(deleteBuilder.build());
    }

    @Override
    public void deleteAuthorizeOperate(Integer authorizeType, String[] authorizeObject) {
        Map<String, Object> paramMap = ParamMap.create("authorizeType", authorizeType).set("authorizeObject", authorizeObject).toMap();
        DeleteBuilder deleteBuilder = new DeleteBuilder(super.getEntityDef(CODE_AUTHORIZE),paramMap);
        deleteBuilder.where()
                .and("AUTHORIZE_TYPE",ConditionType.EQUALS,"authorizeType",true)
                .and("AUTHORIZE_OBJECT",ConditionType.EQUALS,"authorizeObject",true);
        super.executeUpdate(deleteBuilder.build());
    }

    @Override
    public List<AuthorizeOperate> listAuthorizeOperate(Integer authorizeType, String authorizeObject) {
        Map<String, Object> paramMap = ParamMap.create("authorizeType", authorizeType).set("authorizeObject", authorizeObject).toMap();
        QuerySupport query = super.getQuery(AuthorizeOperateQuery.class, paramMap);
        return super.listForBean(query,AuthorizeOperate::new);
    }

    @Override
    public List<AuthorizeOperate> listAuthorizeOperate(String resourceId, String groupCode, String operateId) {
        Map<String, Object> paramMap = ParamMap.create("resourceId", resourceId).set("groupCode", groupCode).set("operateId", operateId).toMap();
        QuerySupport query = super.getQuery(AuthorizeOperateByOperateQuery.class, paramMap);
        return super.listForBean(query,AuthorizeOperate::new);
    }

    @Override
    public List<AuthorizeOperate> listAuthorizeResourceByUserId(String userId, String orgId, int operateType,boolean assigned) {

        List<Role> roles = roleService.listRole(userId, Role.ROLE_TYPE_USER);
        List<String> roleCodeList = roles.stream().map(Role::getRoleCode).collect(Collectors.toList());
        Map<String, Object> paramMap = ParamMap.create("userId", userId)
                .set("roleCode", roleCodeList)
                .set("operateType",operateType).toMap();

        //如果机构ID不为null，说明要进行分级授权的查询
        if(orgId != null){
            if(assigned){
                Organization org = super.getForBean(CODE_ORGANIZATION,"orgId",orgId, Organization::new);
                String dataPath = org.getDataPath();
                String[] orgIds = StringUtils.split(dataPath,PathUtils.PATH_SEPARATOR);

                /*
                NOTE:
                如果期望只能拥有被分配机构的下级机构权限（即没有被分配机构本身的权限），下面一行代码只需要将ordIds换成org.getParentId。
                且AuthorizeOperateByAuthorizeQuery查询器条件建议修改为ConditionType.EQUALS，目前是IN
                 */

                paramMap.put("orgIds",orgIds);
            }else{
                paramMap.put("orgIds",orgId);
            }

        }
        QuerySupport query = super.getQuery(AuthorizeOperateByAuthorizeQuery.class, paramMap);
        List<AuthorizeOperate> authorizeOperateList = super.listForBean(query, AuthorizeOperate::new);

        return authorizeOperateList;
    }

    @Override
    public List<AuthorizeOperate> listAuthenticatedOperate(String userId) {

        List<AuthorizeOperate> allAuthList = new ArrayList<>();

        Map<String, Object> paramMap = ParamMap.create("authType",AUTH_TYPE_USER).set("userId", userId).toMap();
        QuerySupport query = super.getQuery(AuthenticatedOperateQuery.class, paramMap);
        allAuthList.addAll(super.listForBean(query, AuthorizeOperate::new));

        paramMap = ParamMap.create("authType",AUTH_TYPE_ORG).set("userId", userId).toMap();
        query = super.getQuery(AuthenticatedOperateQuery.class, paramMap);
        allAuthList.addAll(super.listForBean(query, AuthorizeOperate::new));

        paramMap = ParamMap.create("authType",AUTH_TYPE_ROLE).set("userId", userId).toMap();
        query = super.getQuery(AuthenticatedOperateQuery.class, paramMap);
        allAuthList.addAll(super.listForBean(query, AuthorizeOperate::new));

        return allAuthList;
    }

//    @Deprecated
//    @Override
//    public List<AuthorizeResource> listAuthorizeResourceByUserId(String userId) {
//        Map<String, Object> paramMap = ParamMap.create("userId", userId).set("authorizeType", AUTHORIZE_TYPE_USER).toMap();
//        QuerySupport query = super.getQuery(AuthorizeResourceQuery.class, paramMap);
//
//        List<ResourceOperate> userOperateList = super.listForBean(query, ResourceOperate::new);
//
//        List<Role> roles = roleService.listRole(userId, Role.ROLE_TYPE_USER);
//        List<String> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
//        paramMap = ParamMap.create("roleId", roleIds).set("authorizeType", AUTHORIZE_TYPE_ROLE).toMap();
//        query = super.getQuery(AuthorizeResourceQuery.class, paramMap);
//        List<ResourceOperate> roleOperateList = super.listForBean(query, ResourceOperate::new);
//
//        List<AuthorizeResource> authorizeResourceList = new ArrayList<>(userOperateList.size()+roleOperateList.size());
//        for (ResourceOperate resourceOperate : userOperateList) {
//            authorizeResourceList.add(new AuthorizeResource(resourceOperate.getOperatePath(),resourceOperate.getMethod()));
//        }
//        for (ResourceOperate resourceOperate : roleOperateList) {
//            if(!authorizeResourceList.contains(resourceOperate)){
//                authorizeResourceList.add(new AuthorizeResource(resourceOperate.getOperatePath(),resourceOperate.getMethod()));
//            }
//        }
//        return authorizeResourceList;
//    }
}
