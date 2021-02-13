package cn.kduck.module.account.web;

import cn.kduck.core.web.annotation.ModelOperate;
import cn.kduck.core.web.annotation.ModelResource;
import cn.kduck.module.account.service.Account;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.core.web.json.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static cn.kduck.security.principal.AuthUserHolder.getAuthUser;

/**
 * LiuHG
 */
@RestController
@RequestMapping("/account")
@Api(tags = "帐号管理")
@ModelResource
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    @ApiOperation(value = "添加帐户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "query"),
            @ApiImplicitParam(name = "accountName", value = "帐号名", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "帐号密码", paramType = "query"),
            @ApiImplicitParam(name = "accountState", value = "帐号状态（1启用，2锁定）", paramType = "query"),
    })
    @ModelOperate
    public JsonObject addAccount(Account account){

        Integer accountState = account.getAccountState();

        if(accountState == null){
            account.setAccountState(Account.ACCOUNT_STATE_LOCKED);
        }

        accountService.addAccount(account);
        return JsonObject.SUCCESS;
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改帐户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountId", value = "帐户Id", paramType = "query"),
            @ApiImplicitParam(name = "accountName", value = "帐号名", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "帐号密码", paramType = "query"),
            @ApiImplicitParam(name = "accountState", value = "帐号状态", paramType = "query"),
    })
    @ModelOperate
    public JsonObject updateAccount(Account account){
        accountService.updateAccount(account);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除帐户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "帐户Id", paramType = "query"),
    })
    @ModelOperate
    public JsonObject deleteAccount(@RequestParam("ids") String[] accountIds){
        accountService.deleteAccount(accountIds);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/get")
    @ApiOperation(value = "根据用户Id查看帐户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountId", value = "帐户Id", paramType = "query"),
    })
    @ModelOperate
    public JsonObject getAccount(@RequestParam("accountId") String accountId){
        Account account = accountService.getAccount(accountId);
        return new JsonObject(account);
    }

    @GetMapping("/get/userId")
    @ApiOperation(value = "根据用户Id查看帐户详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "query"),
    })
    @ModelOperate
    public JsonObject getAccountByUserId(@RequestParam("userId") String userId){
        Account account = accountService.getAccountByUserId(userId);
        return new JsonObject(account);
    }

    @GetMapping("/exist")
    @ApiOperation(value = "根据登录名判断帐户是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountName", value = "用户Id", paramType = "query"),
    })
    public JsonObject accountExist(@RequestParam("accountName") String accountName){
        Account account = accountService.getAccountByName(accountName);
        return new JsonObject(account != null);
    }

    @PostMapping("/changePassword")
    @ApiOperation("修改当前登录人密码")
    @ModelOperate
    public JsonObject changePassword(@RequestParam("oldPwd") String oldPwd,@RequestParam("newPwd") String newPwd){
        if(oldPwd.equals(newPwd)){
            return new JsonObject(null,-1,"新旧密码不能相同");
        }
        AuthUser authUser = getAuthUser();
        accountService.changePassword(authUser.getUsername(),oldPwd,newPwd);
        authUser.setDetailsItem("changePasswordRequired",new Date());
        return JsonObject.SUCCESS;
    }
}
