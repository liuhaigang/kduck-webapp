package cn.kduck.module.configstore.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class ConfigObjectBean extends ValueMap {
    /***/
    public static final String CONFIG_ID = "configId";
    /***/
    public static final String CONFIG_CODE = "configCode";
    /***/
    public static final String CONFIG_EXPLAIN = "configExplain";
    /***/
    public static final String IS_MAJOR = "isMajor";
    /***/
    public static final String IS_VALID = "isValid";

    public ConfigObjectBean() {
    }

    public ConfigObjectBean(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置
     *
     * @param configId
     */
    public void setConfigId(String configId) {
        super.setValue(CONFIG_ID, configId);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getConfigId() {
        return super.getValueAsString(CONFIG_ID);
    }

    /**
     * 设置
     *
     * @param configCode
     */
    public void setConfigCode(String configCode) {
        super.setValue(CONFIG_CODE, configCode);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getConfigCode() {
        return super.getValueAsString(CONFIG_CODE);
    }

    /**
     * 设置
     *
     * @param configExplain
     */
    public void setConfigExplain(String configExplain) {
        super.setValue(CONFIG_EXPLAIN, configExplain);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getConfigExplain() {
        return super.getValueAsString(CONFIG_EXPLAIN);
    }

    /**
     * 设置
     *
     * @param isMajor
     */
    public void setIsMajor(Integer isMajor) {
        super.setValue(IS_MAJOR, isMajor);
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getIsMajor() {
        return super.getValueAsInteger(IS_MAJOR);
    }

    /**
     * 设置
     *
     * @param isValid
     */
    public void setIsValid(Boolean isValid) {
        super.setValue(IS_VALID, isValid);
    }

    /**
     * 获取
     *
     * @return
     */
    public Boolean getIsValid() {
        return super.getValueAsBoolean(IS_VALID);
    }
}
