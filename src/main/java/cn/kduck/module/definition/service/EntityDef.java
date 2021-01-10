package cn.kduck.module.definition.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

/**
 * @author LiuHG
 */
public class EntityDef extends ValueMap {

    /**主键*/
    private static final String ENTITY_DEF_ID = "entityDefId";
    /**命名空间*/
    private static final String NAMESPACE = "namespace";
    /**实体编码*/
    private static final String ENTITY_CODE = "entityCode";
    /**表名*/
    private static final String TABLE_NAME = "tableName";
    /**表备注*/
    private static final String ENTITY_NAME = "entityName";


    public EntityDef() {
    }

    public EntityDef(Map valueMap) {
        super(valueMap);
    }

    /**
     * 设置 命名空间
     *
     * @param entityCode 命名空间
     */
    public void setEntityCode(String entityCode) {
        this.put(ENTITY_CODE, entityCode);
    }

    /**
     * 获取 命名空间
     *
     * @return 命名空间
     */
    public String getEntityCode() {
        return this.getValueAsString(ENTITY_CODE);
    }

    /**
     * 设置 实体编码
     *
     * @param tableName 实体编码
     */
    public void setTableName(String tableName) {
        this.put(TABLE_NAME, tableName);
    }

    /**
     * 获取 实体编码
     *
     * @return 实体编码
     */
    public String getTableName() {
        return this.getValueAsString(TABLE_NAME);
    }

    /**
     * 设置 主键
     *
     * @param namespace 主键
     */
    public void setNamespace(String namespace) {
        this.put(NAMESPACE, namespace);
    }

    /**
     * 获取 主键
     *
     * @return 主键
     */
    public String getNamespace() {
        return this.getValueAsString(NAMESPACE);
    }

    /**
     * 设置 主键
     *
     * @param entityDefId 主键
     */
    public void setEntityDefId(String entityDefId) {
        this.put(ENTITY_DEF_ID, entityDefId);
    }

    /**
     * 获取 主键
     *
     * @return 主键
     */
    public String getEntityDefId() {
        return this.getValueAsString(ENTITY_DEF_ID);
    }

    /**
     * 设置 表备注
     *
     * @param entityName 表备注
     */
    public void setEntityName(String entityName) {
        this.put(ENTITY_NAME, entityName);
    }

    /**
     * 获取 表备注
     *
     * @return 表备注
     */
    public String getEntityName() {
        return this.getValueAsString(ENTITY_NAME);
    }
}
