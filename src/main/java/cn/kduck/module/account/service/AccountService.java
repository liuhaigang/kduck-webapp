package cn.kduck.module.account.service;

import cn.kduck.module.account.exception.DisallowedNameException;
import cn.kduck.core.service.Page;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
public interface AccountService {

    String CODE_ACCOUNT = "k_account";

    void addAccount(Account account) throws DisallowedNameException;

    Account getAccountByUserId(String userId);

    void updateAccount(Account account);

    void updateAccountByAccountName(Account account);

    Account getAccountByName(String accountName);

    List<Account> listAccount(Map<String, Object> paramMap, Page page);

    Account getAccount(String accountId);

    void deleteAccount(String[] accountIds);

    void deleteAccountByUserId(String[] userIds);

    void changePassword(String accountName,String oldPwd,String newPwd);

}
