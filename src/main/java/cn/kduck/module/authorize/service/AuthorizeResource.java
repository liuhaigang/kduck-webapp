package cn.kduck.module.authorize.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class AuthorizeResource extends ValueMap {

    /**资源接口路径*/
    public static final String PATH = "path";
    /**接口method*/
    public static final String METHOD = "method";

    public AuthorizeResource() {
    }

    public AuthorizeResource(Map<String, Object> map) {
        super(map);
    }

    public AuthorizeResource(String path,String method) {
        setPath(path);
        setMethod(method);
    }

    /**
     * 设置 资源接口路径
     *
     * @param path 资源接口路径
     */
    public void setPath(String path) {
        super.setValue(PATH, path);
    }

    /**
     * 获取 资源接口路径
     *
     * @return 资源接口路径
     */
    public String getPath() {
        return super.getValueAsString(PATH);
    }

    /**
     * 设置 接口method
     *
     * @param method 接口method
     */
    public void setMethod(String method) {
        super.setValue(METHOD, method);
    }

    /**
     * 获取 接口method
     *
     * @return 接口method
     */
    public String getMethod() {
        return super.getValueAsString(METHOD);
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() != AuthorizeResource.class){
            return false;
        }
        AuthorizeResource ar = (AuthorizeResource) o;
        return ar.getPath().equals(getPath()) && ar.getMethod().equals(getMethod());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (getPath() == null ? 0 : getPath().hashCode());
        result = 31 * result + (getMethod() == null ? 0 : getMethod().hashCode());
        return result;
    }
}
