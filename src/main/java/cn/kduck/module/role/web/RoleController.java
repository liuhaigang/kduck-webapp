package cn.kduck.module.role.web;

import cn.kduck.module.role.service.Role;
import cn.kduck.module.role.service.RoleService;
import cn.kduck.module.role.service.RoleObject;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.service.Page;
import cn.kduck.core.utils.RequestUtils;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * LiuHG
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    @ApiOperation(value = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query"),
            @ApiImplicitParam(name = "roleCode", value = "角色编码", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "角色描述", paramType = "query")
    })
    public JsonObject addRole(Role role){
        roleService.addRole(role);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户ID", paramType = "query",allowMultiple = true)
    })
    public JsonObject deleteRole(String[] ids){
        roleService.deleteRole(ids);
        return JsonObject.SUCCESS;
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", paramType = "query",required = true),
            @ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query"),
            @ApiImplicitParam(name = "roleCode", value = "角色编码", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "角色描述", paramType = "query")
    })
    public JsonObject updateRole(Role role){
        roleService.updateRole(role);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/get")
    @ApiOperation(value = "角色详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "用户Id", paramType = "query")
    })
    public JsonObject getUser(@RequestParam("roleId") String roleId){
        Role user = roleService.getRole(roleId);
        return new JsonObject(user);
    }

    @GetMapping("/list")
    @ApiOperation(value = "角色列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页返回最大数据", paramType = "query")
    })
    public JsonObject listUser(@ApiIgnore Page page, HttpServletRequest request){
        List<Role> result = roleService.listRole(RequestUtils.getParameterMap(request), page);
        return new JsonPageObject(page,result);
    }

    @PostMapping("/object/get/{roleId}")
//    @ApiOperation(value = "添加角色对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", paramType = "path"),
            @ApiImplicitParam(name = "objectIds", value = "角色对象Ids", paramType = "query"),
            @ApiImplicitParam(name = "objectType", value = "角色对象类型", paramType = "query")
    })
    public JsonObject addRoleObject(@PathVariable String roleId,
                                    @RequestParam("objectIds") String[] objectIds,
                                    @RequestParam("objectType") Integer objectType){
        roleService.addRoleObject(roleId,objectIds,objectType);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/object/delete")
    @ApiOperation(value = "删除角色对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectIds", value = "角色对象IDs", paramType = "query",allowMultiple = true),
    })
    public JsonObject deleteRoleObject(String[] objectIds){
        roleService.deleteRoleObject(objectIds);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/object/list")
    @ApiOperation(value = "查询角色列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", paramType = "query"),
            @ApiImplicitParam(name = "objectType", value = "角色对象类型。1为用户，2为机构", paramType = "query"),
            @ApiImplicitParam(name = "objectName", value = "角色对象名称", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页返回最大数据", paramType = "query")
    })
    public JsonObject listRoleObject(@RequestParam("roleId") String roleId,
                                     @RequestParam("objectType") Integer objectType,
                                     @RequestParam(name="objectName",required = false) String objectName,
                                     @ApiIgnore Page page){
        List<RoleObject> list = roleService.listRoleObject(roleId,objectType, ParamMap.create("objectName",objectName).toMap(),page);
        return new JsonPageObject(page,list);
    }

    @GetMapping("/object/list/forSelect")
    @ApiOperation(value = "查询角色列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", paramType = "query"),
            @ApiImplicitParam(name = "objectType", value = "角色对象类型。1为用户，2为机构", paramType = "query"),
            @ApiImplicitParam(name = "objectName", value = "角色对象名称", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页返回最大数据", paramType = "query")
    })
    public JsonObject listRoleObjectForSelect(@RequestParam("roleId") String roleId,
                                     @RequestParam("objectType") Integer objectType,
                                     @RequestParam(name="objectName",required = false) String objectName,
                                     @ApiIgnore Page page){
        List<RoleObject> list = roleService.listUserForRoleObject(roleId,objectType, ParamMap.create("objectName",objectName).toMap(),page);
        return new JsonPageObject(page,list);
    }
}
