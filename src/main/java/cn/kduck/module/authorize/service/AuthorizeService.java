package cn.kduck.module.authorize.service;

import java.util.List;

public interface AuthorizeService {

    String CODE_AUTHORIZE = "k_authorize";

    void saveAuthorizeOperate(Integer authorizeType,String authorizeObject,Integer operateType,String[] operateObjects);

    void deleteAuthorizeOperate(Integer authorizeType,String authorizeObject,Integer operateType,String resourceId);

    /**
     * 根据授权类型及相关关联值，删除其所有授权操作
     * @param authorizeType
     * @param authorizeObject
     */
    void deleteAuthorizeOperate(Integer authorizeType,String[] authorizeObject);

    List<AuthorizeOperate> listAuthorizeOperate(Integer authorizeType, String authorizeObject);

    /**
     * 查询指定资源在授权中存在的所有信息
     * @param resourceId
     * @param groupCode
     * @param operateId
     * @return
     */
    List<AuthorizeOperate> listAuthorizeOperate(String resourceId,String groupCode,String operateId);

    List<AuthorizeOperate> listAuthorizeResourceByUserId(String userId, String orgId,int operateType,boolean assigned);

//    /**
//     * 根据用户ID查询用户拥有的授权资源操作。仅限资源ID类，不含分组类
//     * @param userId
//     * @return
//     */
//    @Deprecated
//    List<AuthorizeResource> listAuthorizeResourceByUserId(String userId);

    List<AuthorizeOperate> listAuthenticatedOperate(String userId);

}
