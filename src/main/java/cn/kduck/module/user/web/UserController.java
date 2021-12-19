package cn.kduck.module.user.web;

import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.web.annotation.ModelOperate;
import cn.kduck.core.web.annotation.ModelResource;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiJsonResponse;
import cn.kduck.core.web.swagger.ApiParamRequest;
import cn.kduck.module.user.service.User;
import cn.kduck.module.user.service.UserAccount;
import cn.kduck.module.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @Value("${photoPath:${user.home}}")
    private String photoPath;

    @PostMapping("/add")
    @ApiOperation(value = "添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别", paramType = "query"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query"),
    })
    @ApiJsonResponse({
            @ApiField(name="userName",value="姓名"),
            @ApiField(name="gender",value="性别(1 男,2 女)"),
            @ApiField(name="birthday",value="出生日期"),
            @ApiField(name="userName",value="手机号"),
            @ApiField(name="email",value="邮箱")
    })
    @ModelOperate(name="添加用户")
    public JsonObject addUser(@Validated User user){
        processPhoto(user);
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
        processPhoto(user);
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

    @PostMapping("/upload/photo")
    @ModelOperate(name="更新用户头像")
    public JsonObject uploadUserPhoto(String userId,MultipartFile photoFile){
        String photoId = UUID.randomUUID().toString().replaceAll("-", "") + ".new";

        //如果userID为null，说明是新增，需要暂时保存成临时文件，等待用户保存时将头像读取走，并删除临时文件
        //如果userID不为null，说明是更新，直接将对应用户ID的用户头像进行更新，不存临时文件
        System.out.println("上传用户("+userId+")头像："+photoFile.getOriginalFilename());

        File photoDir = getPhotoBasePath();

        try {
            photoFile.transferTo(new File(photoDir,photoId));
        } catch (IOException e) {
            throw new RuntimeException("保存用户头像发生IO错误",e);
        }
        return new JsonObject(photoId);
    }

    @GetMapping("/get/photo")
    public void getUserPhoto(String userId, String photoId, HttpServletResponse response){
        if(StringUtils.hasText(photoId)){
            downloadPhoto(photoId, response);
        }else if(StringUtils.hasText(userId)){
            User user = userService.getUser(userId);
            photoId = user.getPhotoId();
            if(StringUtils.hasText(photoId)){
                downloadPhoto(photoId, response);
            }
        }
    }

    private void downloadPhoto(String photoId, HttpServletResponse response) {
        File file = getPhotoPath(photoId);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            FileCopyUtils.copy(new FileInputStream(file), outputStream);
        } catch (IOException e) {
            throw new RuntimeException("获取头像错误：photoId=" + photoId, e);
        }
    }

    @DeleteMapping("/delete/photo")
//    @ModelOperate(name="删除用户头像")
    public JsonObject deleteUserPhoto(String userId){
        User u = userService.getUser(userId);
        String photoId = u.getPhotoId();

        User user = new User();
        user.setUserId(userId);
        user.setPhotoId(null);
        userService.updateUser(user);

        deletePhotoFile(photoId);
        return JsonObject.SUCCESS;
    }

    private void processPhoto(User user) {
        String photoId = user.getPhotoId();
        if(photoId != null && photoId.endsWith(".new")){
            if(StringUtils.hasText(user.getUserId())){
                User u = userService.getUser(user.getUserId());
                if(u != null && StringUtils.hasText(u.getPhotoId())){
                    deletePhotoFile(u.getPhotoId());
                }
            }
            String newPhotoId = photoId.substring(0,photoId.length()-4);
            File photoPath = getPhotoPath(photoId);
            photoPath.renameTo(getPhotoPath(newPhotoId));
            user.setPhotoId(newPhotoId);
        }
    }

    private void deletePhotoFile(String photoId) {
        File photoFile = getPhotoPath(photoId);
        if(photoFile.exists()){
            photoFile.delete();
        }
    }

    private File getPhotoPath(String photoId) {
        return new File(getPhotoBasePath(),photoId);
    }

    private File getPhotoBasePath() {
        File photoDir = new File(photoPath, "photo");
        if(!photoDir.exists()){
            photoDir.mkdirs();
        }
        return photoDir;
    }


}
