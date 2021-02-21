package cn.kduck.module.auditlog.service;

import cn.kduck.core.service.ValueMap;

import java.util.Date;
import java.util.Map;

public class AuditLog extends ValueMap {
    /**日志ID*/
    public static final String LOG_ID = "logId";
    /**操作的请求method*/
    public static final String METHOD = "method";
    /**模块名称*/
    public static final String MODULE_NAME = "moduleName";
    /**操作名称*/
    public static final String OPERATE_NAME = "operateName";
    /**接口url*/
    public static final String URL = "url";
    /**操作用户ID*/
    public static final String USER_ID = "userId";
    /**操作用户名*/
    public static final String USER_NAME = "userName";
    /**操作ip*/
    public static final String REMOTE_HOST = "remoteHost";
    /**操作时间*/
    public static final String CREATE_TIME = "createTime";

    public AuditLog() {
    }

    public AuditLog(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 日志ID
     *
     * @param logId 日志ID
     */
    public void setLogId(String logId) {
        super.setValue(LOG_ID, logId);
    }

    /**
     * 获取 日志ID
     *
     * @return 日志ID
     */
    public String getLogId() {
        return super.getValueAsString(LOG_ID);
    }

    /**
     * 设置 操作的请求method
     *
     * @param method 操作的请求method
     */
    public void setMethod(String method) {
        super.setValue(METHOD, method);
    }

    /**
     * 获取 操作的请求method
     *
     * @return 操作的请求method
     */
    public String getMethod() {
        return super.getValueAsString(METHOD);
    }

    /**
     * 设置 模块名称
     *
     * @param moduleName 模块名称
     */
    public void setModuleName(String moduleName) {
        super.setValue(MODULE_NAME, moduleName);
    }

    /**
     * 获取 模块名称
     *
     * @return 模块名称
     */
    public String getModuleName() {
        return super.getValueAsString(MODULE_NAME);
    }

    /**
     * 设置 操作名称
     *
     * @param operateName 操作名称
     */
    public void setOperateName(String operateName) {
        super.setValue(OPERATE_NAME, operateName);
    }

    /**
     * 获取 操作名称
     *
     * @return 操作名称
     */
    public String getOperateName() {
        return super.getValueAsString(OPERATE_NAME);
    }

    /**
     * 设置 接口url
     *
     * @param url 接口url
     */
    public void setUrl(String url) {
        super.setValue(URL, url);
    }

    /**
     * 获取 接口url
     *
     * @return 接口url
     */
    public String getUrl() {
        return super.getValueAsString(URL);
    }

    /**
     * 设置 操作用户ID
     *
     * @param userId 操作用户ID
     */
    public void setUserId(String userId) {
        super.setValue(USER_ID, userId);
    }

    /**
     * 获取 操作用户ID
     *
     * @return 操作用户ID
     */
    public String getUserId() {
        return super.getValueAsString(USER_ID);
    }

    /**
     * 设置 操作用户名
     *
     * @param userName 操作用户名
     */
    public void setUserName(String userName) {
        super.setValue(USER_NAME, userName);
    }

    /**
     * 获取 操作用户名
     *
     * @return 操作用户名
     */
    public String getUserName() {
        return super.getValueAsString(USER_NAME);
    }

    /**
     * 设置 远程主机
     *
     * @param remoteHost 远程主机
     */
    public void setRemoteHost(String remoteHost) {
        super.setValue(REMOTE_HOST, remoteHost);
    }

    /**
     * 获取 远程主机
     *
     * @return 远程主机
     */
    public String getRemoteHost() {
        return super.getValueAsString(REMOTE_HOST);
    }

    /**
     * 设置 操作时间
     *
     * @param createTime 操作时间
     */
    public void setCreateTime(Date createTime) {
        super.setValue(CREATE_TIME, createTime);
    }

    /**
     * 获取 操作时间
     *
     * @return 操作时间
     */
    public Date getCreateTime() {
        return super.getValueAsDate(CREATE_TIME);
    }
}
