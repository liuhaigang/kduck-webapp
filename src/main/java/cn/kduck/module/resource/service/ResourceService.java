package cn.kduck.module.resource.service;

import cn.kduck.core.service.Page;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
public interface ResourceService {

    String CODE_RESOURCE = "k_resource";
    String CODE_RESOURCE_OPERATE = "k_resource_operate";
    String CODE_ROLE_OPERATE = "k_role_operate";

    void saveResource(Resource resource);

    void deleteResource(String[] ids);

    Resource getResource(String id);


    List<Resource> listAllResource();

    /**
     * 按资源ID、组编码分组查询，返回资源对象
     * @return
     */
    List<Resource> listAllResourceByGroup();

    List<Resource> listResource(Map<String, Object> paramMap, Page page);

    void saveOperateByRole(String roleId, String[] operateIds);

    List<ResourceOperate> listOperateByRole(String roleId);

    ResourceOperate getResourceOperate(String path,String method);

    /**
     * 根据操作组编码查询操作信息
     * @param resourceId
     * @param groupCode
     * @return
     */
    List<ResourceOperate> listOperateByGroup(String resourceId,String[] groupCode);

//    @Deprecated
//    ValueMapList listResourceAndOperateByRoleCode(String[] roleCode);
//
//    List<String> listResourceUri();


}
