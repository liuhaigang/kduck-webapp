package cn.kduck.module.account.service;

import cn.kduck.core.service.ValueMap;

import java.util.Date;
import java.util.Map;

/**
 * @author LiuHG
 */
public class Account extends ValueMap {

    public static final int ACCOUNT_STATE_ENABLED = 1;
    public static final int ACCOUNT_STATE_LOCKED = 2;
    public static final int ACCOUNT_STATE_EXPIRED = 3;

    /**账户ID*/
    public static final String ACCOUNT_ID = "accountId";
    /**账户名*/
    public static final String ACCOUNT_NAME = "accountName";
    /**账户密码*/
    public static final String PASSWORD = "password";
    /**账户状态*/
    public static final String ACCOUNT_STATE = "accountState";
    /**激活时间*/
    public static final String ACTIVE_DATE = "activeDate";
    /**锁定时间*/
    public static final String LOCKED_DATE = "lockedDate";
    /**创建时间*/
    public static final String CREATE_DATE = "createDate";
    /**过期时间*/
    public static final String EXPIRED_DATE = "expiredDate";
    /**密码过期时间*/
    public static final String CREDENTIALS_EXPIRED_DATE = "credentialsExpiredDate";
    /**最后登录时间*/
    public static final String LAST_LOGIN_DATE = "lastLoginDate";
    /**用户ID*/
    public static final String USER_ID = "userId";
    /**最后登录IP*/
    private static final String LAST_LOGIN_IP = "lastLoginIp";
    /**最后一次修改密码的日期*/
    public static final String CHANGE_PASSWORD_DATE = "changePasswordDate";

    public Account() {
    }

    public Account(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 账户ID
     *
     * @param accountId 账户ID
     */
    public void setAccountId(String accountId) {
        super.setValue(ACCOUNT_ID, accountId);
    }

    /**
     * 获取 账户ID
     *
     * @return 账户ID
     */
    public String getAccountId() {
        return super.getValueAsString(ACCOUNT_ID);
    }

    /**
     * 设置 账户名
     *
     * @param accountName 账户名
     */
    public void setAccountName(String accountName) {
        super.setValue(ACCOUNT_NAME, accountName);
    }

    /**
     * 获取 账户名
     *
     * @return 账户名
     */
    public String getAccountName() {
        return super.getValueAsString(ACCOUNT_NAME);
    }

    /**
     * 设置 账户密码
     *
     * @param password 账户密码
     */
    public void setPassword(String password) {
        super.setValue(PASSWORD, password);
    }

    /**
     * 获取 账户密码
     *
     * @return 账户密码
     */
    public String getPassword() {
        return super.getValueAsString(PASSWORD);
    }

    /**
     * 设置 账户状态
     *
     * @param accountState 账户状态
     */
    public void setAccountState(Integer accountState) {
        super.setValue(ACCOUNT_STATE, accountState);
    }

    /**
     * 获取 账户状态
     *
     * @return 账户状态
     */
    public Integer getAccountState() {
        return super.getValueAsInteger(ACCOUNT_STATE);
    }

    /**
     * 设置 激活时间
     *
     * @param activeDate 激活时间
     */
    public void setActiveDate(Date activeDate) {
        super.setValue(ACTIVE_DATE, activeDate);
    }

    /**
     * 获取 激活时间
     *
     * @return 激活时间
     */
    public Date getActiveDate() {
        return super.getValueAsDate(ACTIVE_DATE);
    }

    /**
     * 设置 锁定时间
     *
     * @param lockedDate 锁定时间
     */
    public void setLockedDate(Date lockedDate) {
        super.setValue(LOCKED_DATE, lockedDate);
    }

    /**
     * 获取 锁定时间
     *
     * @return 锁定时间
     */
    public Date getLockedDate() {
        return super.getValueAsDate(LOCKED_DATE);
    }

    /**
     * 设置 创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        super.setValue(CREATE_DATE, createDate);
    }

    /**
     * 获取 创建时间
     *
     * @return 创建时间
     */
    public Date getCreateDate() {
        return super.getValueAsDate(CREATE_DATE);
    }

    /**
     * 设置 过期时间
     *
     * @param expiredDate 过期时间
     */
    public void setExpiredDate(Date expiredDate) {
        super.setValue(EXPIRED_DATE, expiredDate);
    }

    /**
     * 获取 过期时间
     *
     * @return 过期时间
     */
    public Date getExpiredDate() {
        return super.getValueAsDate(EXPIRED_DATE);
    }

    /**
     * 设置 密码过期时间
     *
     * @param credentialsExpiredDate 密码过期时间
     */
    public void setCredentialsExpiredDate(Date credentialsExpiredDate) {
        super.setValue(CREDENTIALS_EXPIRED_DATE, credentialsExpiredDate);
    }

    /**
     * 获取 密码过期时间
     *
     * @return 密码过期时间
     */
    public Date getCredentialsExpiredDate() {
        return super.getValueAsDate(CREDENTIALS_EXPIRED_DATE);
    }

    /**
     * 设置 最后登录时间
     *
     * @param lastLoginDate 最后登录时间
     */
    public void setLastLoginDate(Date lastLoginDate) {
        super.setValue(LAST_LOGIN_DATE, lastLoginDate);
    }

    /**
     * 获取 最后登录时间
     *
     * @return 最后登录时间
     */
    public Date getLastLoginDate() {
        return super.getValueAsDate(LAST_LOGIN_DATE);
    }

    /**
     * 设置 用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        super.setValue(USER_ID, userId);
    }

    /**
     * 获取 用户ID
     *
     * @return 用户ID
     */
    public String getUserId() {
        return super.getValueAsString(USER_ID);
    }

    /**
     * 设置
     *
     * @param lastLoginIp
     */
    public void setLastLoginIp(String lastLoginIp) {
        super.setValue(LAST_LOGIN_IP, lastLoginIp);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getLastLoginIp() {
        return super.getValueAsString(LAST_LOGIN_IP);
    }

    /**
     * 设置 最后一次修改密码的日期
     *
     * @param changePasswordDate 最后一次修改密码的日期
     */
    public void setChangePasswordDate(Date changePasswordDate) {
        super.setValue(CHANGE_PASSWORD_DATE, changePasswordDate);
    }

    /**
     * 获取 最后一次修改密码的日期
     *
     * @return 最后一次修改密码的日期
     */
    public Date getChangePasswordDate() {
        return super.getValueAsDate(CHANGE_PASSWORD_DATE);
    }
}
