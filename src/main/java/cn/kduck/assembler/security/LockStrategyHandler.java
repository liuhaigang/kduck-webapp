package cn.kduck.assembler.security;

import cn.kduck.module.account.service.Account;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.security.exception.AuthenticationFailureException;
import cn.kduck.security.filter.AuthenticationFailureStrategyFilter.AuthenticationFailureStrategyHandler;
import cn.kduck.security.filter.AuthenticationFailureStrategyFilter.PreAuthenticationToken;
import cn.kduck.security.listener.AuthenticationFailListener.AuthenticationFailRecord;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class LockStrategyHandler implements AuthenticationFailureStrategyHandler {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoginConfig loginConfig;

    @Override
    public boolean supports(PreAuthenticationToken authentication, HttpServletRequest httpRequest) {
        if(!loginConfig.getLockEnabled()) return false;
        AuthenticationFailRecord failRecord = authentication.getFailRecord();
        return failRecord.getFailTotalNum() >= loginConfig.getLockThreshold();
    }

    @Override
    public boolean authenticate(PreAuthenticationToken authentication, HttpServletRequest httpRequest) throws AuthenticationFailureException {
        System.out.println("TODO 锁定账户"+authentication.getName());
        //逻辑优化，需要先查询，判断是否已经被锁定或者用户是否存在，再决定是否执行锁定账户的操作。
        Account existAccount = accountService.getAccountByName(authentication.getName());
        if(existAccount != null){
            Account account = new Account();
            account.setAccountName(authentication.getName());
            account.setAccountState(Account.ACCOUNT_STATE_LOCKED);
            accountService.updateAccountByAccountName(account);
            return true;
        }
        return false;
    }
}
