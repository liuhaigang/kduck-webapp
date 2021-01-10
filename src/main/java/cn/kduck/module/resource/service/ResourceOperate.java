package cn.kduck.module.resource.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

/**
 * @author LiuHG
 */
public class ResourceOperate extends ValueMap {
    /**资源操作ID*/
    public static final String OPERATE_ID = "operateId";
    /**资源ID*/
    public static final String RESOURCE_ID = "resourceId";
    /**操作名*/
    public static final String OPERATE_NAME = "operateName";
    /**操作编码*/
    public static final String OPERATE_CODE = "operateCode";
    /**分组编码*/
    public static final String GROUP_CODE = "groupCode";
    /**操作路径*/
    public static final String OPERATE_PATH = "operatePath";
    /**操作请求方法*/
    public static final String METHOD = "method";
    /**是否启用*/
    public static final String IS_ENABLE = "isEnable";

    public ResourceOperate() {
    }

    public ResourceOperate(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置
     *
     * @param operateId
     */
    public void setOperateId(String operateId) {
        super.setValue(OPERATE_ID, operateId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getOperateId() {
        return super.getValueAsString(OPERATE_ID);
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
     * @param operateName
     */
    public void setOperateName(String operateName) {
        super.setValue(OPERATE_NAME, operateName);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getOperateName() {
        return super.getValueAsString(OPERATE_NAME);
    }

    /**
     * 设置
     *
     * @param operateCode
     */
    public void setOperateCode(String operateCode) {
        super.setValue(OPERATE_CODE, operateCode);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getOperateCode() {
        return super.getValueAsString(OPERATE_CODE);
    }

    /**
     * 设置
     *
     * @param groupCode
     */
    public void setGroupCode(String groupCode) {
        super.setValue(GROUP_CODE, groupCode);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getGroupCode() {
        return super.getValueAsString(GROUP_CODE);
    }

    /**
     * 设置
     *
     * @param operatePath
     */
    public void setOperatePath(String operatePath) {
        super.setValue(OPERATE_PATH, operatePath);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getOperatePath() {
        return super.getValueAsString(OPERATE_PATH);
    }

    /**
     * 设置
     *
     * @param method
     */
    public void setMethod(String method) {
        super.setValue(METHOD, method);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getMethod() {
        return super.getValueAsString(METHOD);
    }

    /**
     * 设置
     *
     * @param isEnable
     */
    public void setIsEnable(Integer isEnable) {
        super.setValue(IS_ENABLE, isEnable);
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getIsEnable() {
        return super.getValueAsInteger(IS_ENABLE);
    }
}
