package cn.kduck.webapp.login;

import cn.kduck.module.account.service.Account;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.module.user.service.User;
import cn.kduck.module.user.service.UserService;
import cn.kduck.security.UserExtInfo;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.service.ValueMapList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * LiuHG
 */
public class UserExtInfoImpl implements UserExtInfo {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public ValueMap getUserExtInfo(String accountName) {
        Account account = accountService.getAccountByName(accountName);
        User user = userService.getUser(account.getUserId());
        ValueMapList orgList = organizationService.listOrganizationByUserId(account.getUserId());
        if(!orgList.isEmpty()){
            //FIXME
            user.setValue("orgId",orgList.get(0).get("orgId"));
        }
        return user;
    }
}
