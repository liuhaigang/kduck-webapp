package cn.kduck.module.configstore.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class ConfigItemBean extends ValueMap implements Comparable<ConfigItemBean>{
    /**参数项ID*/
    public static final String CONFIG_ITEM_ID = "configItemId";
    /**配置ID*/
    public static final String CONFIG_ID = "configId";
    /**参数名*/
    public static final String ITEM_NAME = "itemName";
    /**参数值*/
    public static final String ITEM_VALUE = "itemValue";
    /**参数描述*/
    public static final String ITEM_EXPLAIN = "itemExplain";


    /**默认值，只用于查询返回，保存不存储该属性*/
    public static final String DEFAULT_VALUE = "defaultValue";
    /**允许的值，只用于查询返回，保存不存储该属性*/
    public static final String ALLOW_VALUES = "allowValues";
    /**属性类型，只用于查询返回，保存不存储该属性*/
    public static final String ITEM_TYPE = "itemType";
    /**属性提示，只用于查询返回，保存不存储该属性*/
    public static final String HINT = "hint";
    /**属性分组，只用于查询返回，保存不存储该属性*/
    public static final String GROUP = "group";
    /**属性序号，只用于查询返回，保存不存储该属性*/
    public static final String ORDER = "order";

    public ConfigItemBean() {
    }

    public ConfigItemBean(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 参数项ID
     *
     * @param configItemId 参数项ID
     */
    public void setConfigItemId(String configItemId) {
        super.setValue(CONFIG_ITEM_ID, configItemId);
    }

    /**
     * 获取 参数项ID
     *
     * @return 参数项ID
     */
    public String getConfigItemId() {
        return super.getValueAsString(CONFIG_ITEM_ID);
    }

    /**
     * 设置 配置ID
     *
     * @param configId 配置ID
     */
    public void setConfigId(String configId) {
        super.setValue(CONFIG_ID, configId);
    }

    /**
     * 获取 配置ID
     *
     * @return 配置ID
     */
    public String getConfigId() {
        return super.getValueAsString(CONFIG_ID);
    }

    /**
     * 设置 参数名
     *
     * @param itemName 参数名
     */
    public void setItemName(String itemName) {
        super.setValue(ITEM_NAME, itemName);
    }

    /**
     * 获取 参数名
     *
     * @return 参数名
     */
    public String getItemName() {
        return super.getValueAsString(ITEM_NAME);
    }

    /**
     * 设置 参数值
     *
     * @param itemValue 参数值
     */
    public void setItemValue(String itemValue) {
        super.setValue(ITEM_VALUE, itemValue);
    }

    /**
     * 获取 参数值
     *
     * @return 参数值
     */
    public String getItemValue() {
        return super.getValueAsString(ITEM_VALUE);
    }

    /**
     * 设置
     *
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        super.setValue(DEFAULT_VALUE, defaultValue);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getDefaultValue() {
        return super.getValueAsString(DEFAULT_VALUE);
    }

    /**
     * 设置 允许的值，只用于查询返回，保存不存储该属性
     *
     * @param allowValues 允许的值，只用于查询返回，保存不存储该属性
     */
    public void setAllowValues(String allowValues) {
        super.setValue(ALLOW_VALUES, allowValues);
    }

    /**
     * 获取 允许的值，只用于查询返回，保存不存储该属性
     *
     * @return 允许的值，只用于查询返回，保存不存储该属性
     */
    public String getAllowValues() {
        return super.getValueAsString(ALLOW_VALUES);
    }

    /**
     * 设置
     *
     * @param itemExplain
     */
    public void setItemExplain(String itemExplain) {
        super.setValue(ITEM_EXPLAIN, itemExplain);
    }

    /**
     * 获取
     *
     * @return
     */
    public String getItemExplain() {
        return super.getValueAsString(ITEM_EXPLAIN);
    }

    /**
     * 设置 属性类型，只用于查询返回，保存不存储该属性
     *
     * @param itemType 属性类型，只用于查询返回，保存不存储该属性
     */
    public void setItemType(String itemType) {
        super.setValue(ITEM_TYPE, itemType);
    }

    /**
     * 获取 属性类型，只用于查询返回，保存不存储该属性
     *
     * @return 属性类型，只用于查询返回，保存不存储该属性
     */
    public String getItemType() {
        return super.getValueAsString(ITEM_TYPE);
    }

    /**
     * 设置 属性提示，只用于查询返回，保存不存储该属性
     *
     * @param hint 属性提示，只用于查询返回，保存不存储该属性
     */
    public void setHint(String hint) {
        super.setValue(HINT, hint);
    }

    /**
     * 获取 属性提示，只用于查询返回，保存不存储该属性
     *
     * @return 属性提示，只用于查询返回，保存不存储该属性
     */
    public String getHint() {
        return super.getValueAsString(HINT);
    }

    /**
     * 设置 属性分组，只用于查询返回，保存不存储该属性
     *
     * @param group 属性分组，只用于查询返回，保存不存储该属性
     */
    public void setGroup(String group) {
        super.setValue(GROUP, group);
    }

    /**
     * 获取 属性分组，只用于查询返回，保存不存储该属性
     *
     * @return 属性分组，只用于查询返回，保存不存储该属性
     */
    public String getGroup() {
        return super.getValueAsString(GROUP);
    }

    /**
     * 设置 属性序号，只用于查询返回，保存不存储该属性
     *
     * @param order 属性序号，只用于查询返回，保存不存储该属性
     */
    public void setOrder(Integer order) {
        super.setValue(ORDER, order);
    }

    /**
     * 获取 属性序号，只用于查询返回，保存不存储该属性
     *
     * @return 属性序号，只用于查询返回，保存不存储该属性
     */
    public Integer getOrder() {
        return super.getValueAsInteger(ORDER);
    }

    @Override
    public int compareTo(ConfigItemBean itemBean) {
        int groupCompare = getGroup().compareTo(itemBean.getGroup());
        if(groupCompare == 0){
            return getOrder().compareTo(itemBean.getOrder());
        }else{
            return groupCompare;
        }
    }
}
