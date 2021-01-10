package cn.kduck.module.definition.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

/**
 * @author LiuHG
 */
public class EntityFieldDef extends ValueMap {

    /**主键*/
    private static final String ENTITY_FIELD_DEF_ID = "entityFieldDefId";
    /**字段名*/
    private static final String FIELD_NAME = "fieldName";
    /**属性名*/
    private static final String ATTRIBUTE_NAME = "attributeName";
    /**java类型*/
    private static final String JAVA_TYPE = "javaType";
    /**jdbc类型*/
    private static final String JDBC_TYPE = "jdbcType";
    /**是否主键*/
    private static final String IS_PK = "isPk";
    /**字段备注*/
    private static final String REMARKS = "remarks";
    /**实体Id*/
    private static final String ENTITY_DEF_ID = "entityDefId";
    /**是否外键*/
    private static final String IS_FK = "isFk";


    public EntityFieldDef() {
    }

    public EntityFieldDef(ValueMap valueMap) {
        this.putAll(valueMap);
    }

    public EntityFieldDef(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 主键
     *
     * @param entityFieldDefId 主键
     */
    public void setEntityFieldDefId(String entityFieldDefId) {
        this.put(ENTITY_FIELD_DEF_ID, entityFieldDefId);
    }

    /**
     * 获取 主键
     *
     * @return 主键
     */
    public String getEntityFieldDefId() {
        return this.getValueAsString(ENTITY_FIELD_DEF_ID);
    }

    /**
     * 设置 字段名
     *
     * @param fieldName 字段名
     */
    public void setFieldName(String fieldName) {
        this.put(FIELD_NAME, fieldName);
    }

    /**
     * 获取 字段名
     *
     * @return 字段名
     */
    public String getFieldName() {
        return this.getValueAsString(FIELD_NAME);
    }

    /**
     * 设置 属性名
     *
     * @param attributeName 属性名
     */
    public void setAttributeName(String attributeName) {
        this.put(ATTRIBUTE_NAME, attributeName);
    }

    /**
     * 获取 属性名
     *
     * @return 属性名
     */
    public String getAttributeName() {
        return this.getValueAsString(ATTRIBUTE_NAME);
    }

    /**
     * 设置 java类型
     *
     * @param javaType java类型
     */
    public void setJavaType(String javaType) {
        this.put(JAVA_TYPE, javaType);
    }

    /**
     * 获取 java类型
     *
     * @return java类型
     */
    public String getJavaType() {
        return this.getValueAsString(JAVA_TYPE);
    }

    /**
     * 设置 jdbc类型
     *
     * @param jdbcType jdbc类型
     */
    public void setJdbcType(Integer jdbcType) {
        this.put(JDBC_TYPE, jdbcType);
    }

    /**
     * 获取 jdbc类型
     *
     * @return jdbc类型
     */
    public Integer getJdbcType() {
        return this.getValueAsInteger(JDBC_TYPE);
    }

    /**
     * 设置 是否主键
     *
     * @param isPk 是否主键
     */
    public void setIsPk(Integer isPk) {
        this.put(IS_PK, isPk);
    }

    /**
     * 获取 是否主键
     *
     * @return 是否主键
     */
    public Integer getIsPk() {
        return this.getValueAsInteger(IS_PK);
    }

    /**
     * 设置 字段备注
     *
     * @param remarks 字段备注
     */
    public void setRemarks(String remarks) {
        this.put(REMARKS, remarks);
    }

    /**
     * 获取 字段备注
     *
     * @return 字段备注
     */
    public String getRemarks() {
        return this.getValueAsString(REMARKS);
    }

    /**
     * 设置 实体Id
     *
     * @param entityDefId 实体Id
     */
    public void setEntityDefId(String entityDefId) {
        this.put(ENTITY_DEF_ID, entityDefId);
    }

    /**
     * 获取 实体Id
     *
     * @return 实体Id
     */
    public String getEntityDefId() {
        return this.getValueAsString(ENTITY_DEF_ID);
    }

    /**
     * 设置 是否外键
     *
     * @param isFk 是否外键
     */
    public void setIsFk(Integer isFk) {
        super.setValue(IS_FK, isFk);
    }

    /**
     * 获取 是否外键
     *
     * @return 是否外键
     */
    public Integer getIsFk() {
        return super.getValueAsInteger(IS_FK);
    }
}
