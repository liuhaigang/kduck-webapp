package cn.kduck.module.hierarchical.web;

import cn.kduck.module.hierarchical.service.AuthorizeOrganization;
import cn.kduck.module.hierarchical.service.AuthorizeUser;
import cn.kduck.module.hierarchical.service.HierarchicalAuthorizeService;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.security.principal.AuthUserHolder;
import cn.kduck.core.utils.TreeNodeUtils;
import cn.kduck.core.utils.TreeNodeUtils.Node;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiParamRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hierarchical/authorize")
@Api(tags = "分级授权管理")
public class HierarchicalAuthorizeController {

    @Autowired
    private HierarchicalAuthorizeService authorizeService;

    @PostMapping("/user/add")
    @ApiOperation("添加分级授权用户")
    @ApiParamRequest(value = {
            @ApiField(name="orgId",value="授权机构"),
            @ApiField(name="userIds",value = "授权用户",allowMultiple = true),
    })
    public JsonObject addAuthorize(@RequestParam("orgId") String orgId,@RequestParam("userIds") String[] userIds){
        authorizeService.addAuthorize(orgId,userIds);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/user/delete")
    @ApiOperation("删除授权用户")
    @ApiParamRequest(value = {
            @ApiField(name="ids",value = "授权用户ID",allowMultiple = true),
    })
    public JsonObject deleteAuthorize(@RequestParam("ids") String[] authUserIds){
        authorizeService.deleteAuthorize(authUserIds);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/user/list")
    @ApiOperation("根据机构查询其下的授权用户")
    @ApiParamRequest(value = {
            @ApiField(name="orgId",value="授权机构Id")
    })
    public JsonObject listAuthorizeUser(@RequestParam("orgId") String orgId){
        List<AuthorizeUser> userList = authorizeService.listAuthorizeUser(orgId);
        return new JsonObject(userList);
    }

    @GetMapping("/tree")
    @ApiOperation("根据当前登录用户查询授权机构")
//    @ApiParamRequest(value = {
//            @ApiField(name="userId",value="授权用户Id")
//    })
    public JsonObject listAuthorizeOrganization(){
        AuthUser authUser = AuthUserHolder.getAuthUser();
        List<AuthorizeOrganization> organizationList = authorizeService.listAuthorizeOrganizationByUserId(authUser.getDetailsItem("userId").toString());
        List<Node<AuthorizeOrganization>> nodes = TreeNodeUtils.formatTreeNode(organizationList, AuthorizeOrganization::getOrgId, AuthorizeOrganization::getOrgName, AuthorizeOrganization::getParentId);
        return new JsonObject(nodes);
    }
}
