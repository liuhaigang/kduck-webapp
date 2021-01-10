package cn.kduck.module.oauth2.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class ClientInfo extends ValueMap {

    /**客户端ID*/
    public static final String CLIENT_ID = "clientId";
    /**客户端密码*/
    public static final String CLIENT_SECRET = "clientSecret";
    /**权限范围。多个以","分隔*/
    public static final String SCOPE = "scope";
    /**授权模式。多个以","分隔*/
    public static final String AUTHORIZED_GRANT_TYPES = "authorizedGrantTypes";
    /**客户端重定向uri。多个以","分隔*/
    public static final String WEB_SERVER_REDIRECT_URI = "webServerRedirectUri";
    /**用户的权限范围*/
    public static final String AUTHORITIES = "authorities";
    /**access_token的有效时间(秒)。默认12小时*/
    public static final String ACCESS_TOKEN_VALIDITY = "accessTokenValidity";
    /**refresh_token的有效时间(秒)。默认30天*/
    public static final String REFRESH_TOKEN_VALIDITY = "refreshTokenValidity";
    /**客户端附加信息，需要是一个正确的json数据*/
    public static final String ADDITIONAL_INFORMATION = "additionalInformation";
    /**是否自动授权*/
    public static final String AUTOAPPROVE = "autoapprove";

    public ClientInfo() {
    }

    public ClientInfo(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 客户端ID
     *
     * @param clientId 客户端ID
     */
    public void setClientId(String clientId) {
        super.setValue(CLIENT_ID, clientId);
    }

    /**
     * 获取 客户端ID
     *
     * @return 客户端
     */
    public String getClientId() {
        return super.getValueAsString(CLIENT_ID);
    }

    /**
     * 设置 客户端密码
     *
     * @param clientSecret 客户端密码
     */
    public void setClientSecret(String clientSecret) {
        super.setValue(CLIENT_SECRET, clientSecret);
    }

    /**
     * 获取 客户端密码
     *
     * @return 客户端密码
     */
    public String getClientSecret() {
        return super.getValueAsString(CLIENT_SECRET);
    }

    /**
     * 设置 权限范围。多个以","分隔
     *
     * @param scope 权限范围。多个以","分隔
     */
    public void setScope(String scope) {
        super.setValue(SCOPE, scope);
    }

    /**
     * 获取 权限范围。多个以","分隔
     *
     * @return 权限范围。多个以","分隔
     */
    public String getScope() {
        return super.getValueAsString(SCOPE);
    }

    /**
     * 设置 授权模式。多个以","分隔
     *
     * @param authorizedGrantTypes 授权模式。多个以","分隔
     */
    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        super.setValue(AUTHORIZED_GRANT_TYPES, authorizedGrantTypes);
    }

    /**
     * 获取 授权模式。多个以","分隔
     *
     * @return 授权模式。多个以","分隔
     */
    public String getAuthorizedGrantTypes() {
        return super.getValueAsString(AUTHORIZED_GRANT_TYPES);
    }

    /**
     * 设置 客户端重定向uri。多个以","分隔
     *
     * @param webServerRedirectUri 客户端重定向uri。多个以","分隔
     */
    public void setWebServerRedirectUri(String webServerRedirectUri) {
        super.setValue(WEB_SERVER_REDIRECT_URI, webServerRedirectUri);
    }

    /**
     * 获取 客户端重定向uri。多个以","分隔
     *
     * @return 客户端重定向uri。多个以","分隔
     */
    public String getWebServerRedirectUri() {
        return super.getValueAsString(WEB_SERVER_REDIRECT_URI);
    }

    /**
     * 设置 用户的权限范围
     *
     * @param authorities 用户的权限范围
     */
    public void setAuthorities(String authorities) {
        super.setValue(AUTHORITIES, authorities);
    }

    /**
     * 获取 用户的权限范围
     *
     * @return 用户的权限范围
     */
    public String getAuthorities() {
        return super.getValueAsString(AUTHORITIES);
    }

    /**
     * 设置 access_token的有效时间(秒)。默认12小时
     *
     * @param accessTokenValidity access_token的有效时间(秒)。默认12小时
     */
    public void setAccessTokenValidity(Integer accessTokenValidity) {
        super.setValue(ACCESS_TOKEN_VALIDITY, accessTokenValidity);
    }

    /**
     * 获取 access_token的有效时间(秒)。默认12小时
     *
     * @return access_token的有效时间(秒)。默认12小时
     */
    public Integer getAccessTokenValidity() {
        return super.getValueAsInteger(ACCESS_TOKEN_VALIDITY);
    }

    /**
     * 设置 refresh_token的有效时间(秒)。默认30天
     *
     * @param refreshTokenValidity refresh_token的有效时间(秒)。默认30天
     */
    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        super.setValue(REFRESH_TOKEN_VALIDITY, refreshTokenValidity);
    }

    /**
     * 获取 refresh_token的有效时间(秒)。默认30天
     *
     * @return refresh_token的有效时间(秒)。默认30天
     */
    public Integer getRefreshTokenValidity() {
        return super.getValueAsInteger(REFRESH_TOKEN_VALIDITY);
    }

    /**
     * 设置 客户端附加信息，需要是一个正确的json数据
     *
     * @param additionalInformation 客户端附加信息，需要是一个正确的json数据
     */
    public void setAdditionalInformation(String additionalInformation) {
        super.setValue(ADDITIONAL_INFORMATION, additionalInformation);
    }

    /**
     * 获取 客户端附加信息，需要是一个正确的json数据
     *
     * @return 客户端附加信息，需要是一个正确的json数据
     */
    public String getAdditionalInformation() {
        return super.getValueAsString(ADDITIONAL_INFORMATION);
    }

    /**
     * 设置 是否自动授权
     *
     * @param autoapprove 是否自动授权
     */
    public void setAutoapprove(Boolean autoapprove) {
        super.setValue(AUTOAPPROVE, autoapprove);
    }

    /**
     * 获取 是否自动授权
     *
     * @return 是否自动授权
     */
    public Boolean getAutoapprove() {
        return super.getValueAsBoolean(AUTOAPPROVE);
    }
}
