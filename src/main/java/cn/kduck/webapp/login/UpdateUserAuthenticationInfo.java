package cn.kduck.webapp.login;

import cn.kduck.module.account.service.Account;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.security.callback.AuthenticationSuccessCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * LiuHG
 */
@Component
public class UpdateUserAuthenticationInfo implements AuthenticationSuccessCallback {

    @Autowired
    private AccountService accountService;

    @Override
    public void doHandle(UserDetails user, HttpServletRequest request) {
        Account account = new Account();
        account.setAccountName(user.getUsername());
        account.setLastLoginDate(new Date());
        account.setLastLoginIp(request.getRemoteHost());
        accountService.updateAccountByAccountName(account);
    }
}
