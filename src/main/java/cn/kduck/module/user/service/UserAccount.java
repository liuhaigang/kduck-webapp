package cn.kduck.module.user.service;

import cn.kduck.module.account.service.Account;

import java.util.Map;

/**
 * @author LiuHG
 */
public class UserAccount extends User{

    public UserAccount(){}

    public UserAccount(Map<String,Object> map){
        super(map);
    }

    public void setAccountId(String accountId) {
        super.setValue(Account.ACCOUNT_ID, accountId);
    }

    public String getAccountId() {
        return super.getValueAsString(Account.ACCOUNT_ID);
    }
}
