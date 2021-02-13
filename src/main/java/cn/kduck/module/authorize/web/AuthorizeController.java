package cn.kduck.module.authorize.web;

import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiParamRequest;
import cn.kduck.module.authorize.service.AuthorizeOperate;
import cn.kduck.module.authorize.service.AuthorizeService;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.security.principal.AuthUserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authorize")
@Api(tags="资源授权管理")
public class AuthorizeController {

    @Autowired
    private AuthorizeService authorizeService;

    /**
     * operateObjects = "资源ID"+"#"+"分组名"
     */
    @ApiOperation("为用户授权")
    @ApiParamRequest({
            @ApiField(name="userId",value="授权用户Id"),
            @ApiField(name="operateType",value="授权操作类型，1授权资源，2授权分组"),
            @ApiField(name="operateObjects",value="授权操作对象（operateType=1，资源授权ID、operateType=2，资源ID#分组名）"),
    })
    @PostMapping("/save/forUser")
    public JsonObject saveAuthorizeOperateForUser(@RequestParam("userId") String userId, Integer operateType, String[] operateObjects){
        authorizeService.saveAuthorizeOperate(AuthorizeOperate.AUTHORIZE_TYPE_USER,userId,operateType,operateObjects);
        return JsonObject.SUCCESS;
    }

    @ApiOperation("为角色授权")
    @ApiParamRequest({
            @ApiField(name="roleId",value="角色Id"),
            @ApiField(name="operateType",value="授权操作类型，1授权资源，2授权分组"),
            @ApiField(name="operateObjects",value="授权操作对象（operateType=1，资源授权ID、operateType=2，资源ID#分组名）"),
    })
    @PostMapping("/save/forRole")
    public JsonObject saveAuthorizeOperateForRole(@RequestParam("roleCode") String roleCode, Integer operateType, String[] operateObjects){
        authorizeService.saveAuthorizeOperate(AuthorizeOperate.AUTHORIZE_TYPE_ROLE,roleCode,operateType,operateObjects);
        return JsonObject.SUCCESS;
    }

    @ApiOperation("为分级授权用户授权")
    @ApiParamRequest({
            @ApiField(name="userId",value="授权用户Id"),
            @ApiField(name="operateType",value="授权操作类型，1授权资源，2授权分组"),
            @ApiField(name="operateObjects",value="授权操作对象（operateType=1，资源授权ID、operateType=2，资源ID#分组名）"),
    })
    @PostMapping("/save/forHierarchicalUser")
    public JsonObject saveAuthorizeOperateForHierarchicalUser(@RequestParam("userId") String userId, Integer operateType, String[] operateObjects){
        authorizeService.saveAuthorizeOperate(AuthorizeOperate.AUTHORIZE_TYPE_HIERARCHICAL,userId,operateType,operateObjects);
        return JsonObject.SUCCESS;
    }

    @ApiOperation("根据用户ID查询授权的资源操作")
    @ApiParamRequest({
            @ApiField(name="userId",value="用户Id")
    })
    @GetMapping("/list/byUser")
    public JsonObject listAuthorizeOperateByUser(@RequestParam("userId") String userId,String operateType){
        List<AuthorizeOperate> authorizeOperates = authorizeService.listAuthorizeOperate(AuthorizeOperate.AUTHORIZE_TYPE_USER, userId);
        return new JsonObject(authorizeOperates);
    }

    @ApiOperation("根据角色ID查询授权的资源操作")
    @ApiParamRequest({
            @ApiField(name="roleId",value="角色Id")
    })
    @GetMapping("/list/byRole")
    public JsonObject listAuthorizeOperateByRole(@RequestParam("roleCode") String roleCode,String operateType){
        List<AuthorizeOperate> authorizeOperates = authorizeService.listAuthorizeOperate(AuthorizeOperate.AUTHORIZE_TYPE_ROLE, roleCode);
        return new JsonObject(authorizeOperates);
    }

    @ApiOperation("查询当前登录用户被分配的操作类授权")
    @GetMapping("/list/byOperate")
    //参数orgId主要用于分级授权，查询具体某个机构的权限
    public JsonObject listAuthorizeOperateByOperate(String orgId){
        AuthUser authUser = AuthUserHolder.getAuthUser();
        Object userId = authUser.getDetailsItem("userId");
        List<AuthorizeOperate> authorizeOperateList = authorizeService.listAuthorizeResourceByUserId(userId.toString(),orgId, AuthorizeOperate.OPERATE_TYPE_ID,true);
        return new JsonObject(authorizeOperateList);
    }

    @ApiOperation("查询指定用户分配给机构的操作类授权")
    @GetMapping("/list/byUserOperate")
    //参数orgId主要用于分级授权，查询具体某个机构的权限
    public JsonObject listAuthorizeOperateByOperate(String orgId,String userId){
        List<AuthorizeOperate> authorizeOperateList = authorizeService.listAuthorizeResourceByUserId(userId,orgId, AuthorizeOperate.OPERATE_TYPE_ID,false);
        return new JsonObject(authorizeOperateList);
    }

    @ApiOperation("查询当前登录用户被分配的分组类授权")
    @GetMapping("/list/byGroup")
    //参数orgId主要用于分级授权，查询具体某个机构的权限
    public JsonObject listAuthorizeOperateByGroup(String orgId){
        AuthUser authUser = AuthUserHolder.getAuthUser();
        Object userId = authUser.getDetailsItem("userId");
        List<AuthorizeOperate> authorizeOperateList = authorizeService.listAuthorizeResourceByUserId(userId.toString(), orgId,AuthorizeOperate.OPERATE_TYPE_GROUP,true);
        return new JsonObject(authorizeOperateList);
    }

    @ApiOperation("查询指定用户分配给机构的分组类授权")
    @GetMapping("/list/byUserGroup")
    //参数orgId主要用于分级授权，查询具体某个机构的权限
    public JsonObject listAuthorizeOperateByGroup(String orgId,String userId){
        List<AuthorizeOperate> authorizeOperateList = authorizeService.listAuthorizeResourceByUserId(userId, orgId,AuthorizeOperate.OPERATE_TYPE_GROUP,false);
        return new JsonObject(authorizeOperateList);
    }

    @ApiOperation("查询当前用户所有已授权的资源操作")
    @GetMapping("/list/authenticated")
    public JsonObject listAuthenticatedOperate(){
        AuthUser authUser = AuthUserHolder.getAuthUser();
        String userId = (String)authUser.getDetailsItem("userId");
        List<AuthorizeOperate> authorizeOperateList = authorizeService.listAuthenticatedOperate(userId);
        return new JsonObject(authorizeOperateList);
    }

}
