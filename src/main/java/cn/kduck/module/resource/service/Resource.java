package cn.kduck.module.resource.service;

import cn.kduck.core.service.ValueMap;

import java.util.List;
import java.util.Map;

/**
 * @author LiuHG
 */
public class Resource extends ValueMap {

    /**资源ID*/
    public static final String RESOURCE_ID = "resourceId";
    /**资源名*/
    public static final String RESOURCE_NAME = "resourceName";
    /**资源编码*/
    public static final String RESOURCE_CODE = "resourceCode";
    /**资源路径*/
    public static final String RESOURCE_PATH = "resourcePath";
    /**校验码*/
    public static final String MD5 = "md5";
    /**资源操作*/
    private static final String OPERATE_LIST = "operateList";

    public Resource() {
    }

    public Resource(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置
     *
     * @param resourceId
     */
    public void setResourceId(String resourceId) {
        super.setValue(RESOURCE_ID, resourceId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getResourceId() {
        return super.getValueAsString(RESOURCE_ID);
    }

    /**
     * 设置
     *
     * @param resourceName
     */
    public void setResourceName(String resourceName) {
        super.setValue(RESOURCE_NAME, resourceName);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getResourceName() {
        return super.getValueAsString(RESOURCE_NAME);
    }

    /**
     * 设置
     *
     * @param resourceCode
     */
    public void setResourceCode(String resourceCode) {
        super.setValue(RESOURCE_CODE, resourceCode);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getResourceCode() {
        return super.getValueAsString(RESOURCE_CODE);
    }

    /**
     * 设置
     *
     * @param resourcePath
     */
    public void setResourcePath(String resourcePath) {
        super.setValue(RESOURCE_PATH, resourcePath);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getResourcePath() {
        return super.getValueAsString(RESOURCE_PATH);
    }

    /**
     * 设置
     *
     * @param md5
     */
    public void setMd5(String md5) {
        super.setValue(MD5, md5);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getMd5() {
        return super.getValueAsString(MD5);
    }

    public void setOperateList(List<ResourceOperate> operateList) {
        super.setValue(OPERATE_LIST, operateList);
    }

    /**
     * 获取 资源操作
     *
     * @return 资源操作
     */
    public List<ResourceOperate> getOperateList() {
        return super.getValueAsValueMapList(OPERATE_LIST).convertList(ResourceOperate::new);
    }
}
