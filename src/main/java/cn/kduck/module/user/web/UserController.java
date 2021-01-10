package cn.kduck.module.user.web;

import cn.kduck.module.user.service.User;
import cn.kduck.module.user.service.UserAccount;
import cn.kduck.module.user.service.UserService;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.web.annotation.ModelOperate;
import cn.kduck.core.web.annotation.ModelResource;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiParamRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
@ModelResource
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private List<HttpMessageConverter<?>> messageConverters;

    @PostMapping("/add")
    @ApiOperation(value = "添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query"),
    })
    @ModelOperate(name="添加用户")
    public JsonObject addUser(@Validated User user){
        userService.addUser(user);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户ID", paramType = "query",allowMultiple = true)
    })
    @ModelOperate(name="删除用户")
    public JsonObject deleteUser(String[] ids){
        userService.deleteUser(ids);
        return JsonObject.SUCCESS;
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "query",required = true),
            @ApiImplicitParam(name = "userName", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query"),
    })
    @ModelOperate(name="修改用户")
    public JsonObject updateUser(User user){
        userService.updateUser(user);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/get")
    @ApiOperation(value = "查看用户详情")
    @ApiParamRequest({
            @ApiField(name="userId",value="用户Id",paramType = "query")
    })
    @ModelOperate(name="查看用户详情")
    public JsonObject getUser(@RequestParam("userId") String userId){
        ValueMap user = userService.getUser(userId);
        return new JsonObject(user);
    }

    @GetMapping("/list")
    @ApiOperation(value = "用户列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页返回最大数据", paramType = "query")
    })
    @ModelOperate(name="用户列表查询")
//    @ApiJsonResponse(code="K_USER")
    public JsonPageObject listUser(@ApiIgnore Page page, @ApiIgnore @RequestParam Map<String,Object> paramMap){
//        AuthUser authUser = AuthUserHolder.getAuthUser();
//        System.out.println(authUser.getUsername());
        List<UserAccount> users = userService.listUserAccount(paramMap, page);
        return new JsonPageObject(page,users);
    }

}
