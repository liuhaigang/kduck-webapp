package cn.kduck.module.account.service.impl;

import cn.kduck.module.account.exception.DisallowedNameException;
import cn.kduck.module.account.AccountConfig;
import cn.kduck.module.account.exception.ExistNameException;
import cn.kduck.module.account.query.AccountByNameQuery;
import cn.kduck.module.account.service.Account;
import cn.kduck.module.account.service.AccountPwdEncoder;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueBean;
import cn.kduck.core.utils.BeanDefUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
@Service
public class AccountServiceImpl extends DefaultService implements AccountService {


    private AccountPwdEncoder encoder;

    @Autowired
    private AccountConfig accountConfig;

    @Autowired
    private AccountPwdEncoder accountPwdEncoder;

    public AccountServiceImpl(@Autowired(required = false)AccountPwdEncoder encoder){
        this.encoder = encoder;
    }

    @Override
    public void addAccount(Account account) throws DisallowedNameException {

        String disallowedName = accountConfig.getDisallowedName();
        if(disallowedName != null){
            String[] nameArray = disallowedName.split(",");
            for (String name : nameArray) {
                if(name.equalsIgnoreCase(account.getAccountName())){
                    throw new DisallowedNameException("不允许使用的帐户名：" + account.getAccountName());
                }
            }
        }

        Account accountName = getAccountByName(account.getAccountName());

        if(accountName != null){
            throw new ExistNameException("帐户名已经存在："+accountName);
        }

        account.setCreateDate(new Date());

        updateAccountDate(account);
        encodePwd(account);

        super.add(CODE_ACCOUNT,account);
    }

    @Override
    public Account getAccountByUserId(String userId) {
        return super.getForBean(CODE_ACCOUNT,Account.USER_ID,userId,null,Account::new);
    }

    @Override
    public void updateAccount(Account account) {
        //移除用户ID和账户名属性，避免在更新时覆盖
        account.remove(Account.USER_ID);
        account.remove(Account.ACCOUNT_NAME);

        updateAccountDate(account);
        encodePwd(account);

        super.update(CODE_ACCOUNT,account);
    }

    @Override
    public void updateAccountByAccountName(Account account) {
        //移除用户ID，避免在更新时覆盖
        account.remove(Account.USER_ID);

        encodePwd(account);
        ValueBean valueBean = super.createValueBean(CODE_ACCOUNT,account);
        super.update(valueBean,Account.ACCOUNT_NAME);
    }

    @Override
    public Account getAccountByName(String accountName) {
        Map<String, Object> paramMap = ParamMap.create(Account.ACCOUNT_NAME, accountName).toMap();
        QuerySupport query = getQuery(AccountByNameQuery.class, paramMap);
        //查询除了密码之外的所有字段
        return super.getForBean(query,Account::new);
    }

    @Override
    public List<Account> listAccount(Map<String, Object> paramMap, Page page) {
        return null;
    }

    @Override
    public Account getAccount(String accountId) {
        //查询除了密码之外的所有字段
        return super.getForBean(CODE_ACCOUNT,accountId, fieldList-> BeanDefUtils.excludeAliasField(fieldList,Account.PASSWORD),Account::new);
    }

    @Override
    public void deleteAccount(String[] accountIds) {
        super.delete(CODE_ACCOUNT,accountIds);
    }

    @Override
    public void deleteAccountByUserId(String[] userIds) {
        super.delete(CODE_ACCOUNT,"userId",userIds);
    }

    @Override
    public void changePassword(String accountName, String oldPwd, String newPwd) {
        Account acccount = super.getForBean(CODE_ACCOUNT, Account.ACCOUNT_NAME,accountName, Account::new);
        if(acccount != null){
            String password = acccount.getPassword();
            if(accountPwdEncoder.matches(oldPwd,password)){
                acccount.setPassword(accountPwdEncoder.encode(newPwd));
                acccount.setChangePasswordDate(new Date());
                Map<String, Object> valueMap = acccount.createMap(Account.ACCOUNT_ID, Account.PASSWORD,Account.CHANGE_PASSWORD_DATE);
                super.update(CODE_ACCOUNT,valueMap);
            }else{
                throw new RuntimeException("原密码不正确");
            }
        }else{
            throw new RuntimeException("账号不存在：" + accountName);
        }
    }

    /**
     * 根据账号状态更新对应状态日期
     * @param account
     */
    private void updateAccountDate(Account account) {
        if(account.getAccountState() != null){
            int accountState = account.getAccountState();
            if(accountState == Account.ACCOUNT_STATE_ENABLED){
                account.setActiveDate(new Date());
            }else if(accountState == Account.ACCOUNT_STATE_LOCKED){
                account.setLockedDate(new Date());
            }
        }
    }

    /**
     * 密码编码
     * @param account
     */
    private void encodePwd(Account account) {
        String password = account.getPassword();
        if (password != null && encoder != null) {
            account.setPassword(encoder.encode(password));
        }
    }
}
