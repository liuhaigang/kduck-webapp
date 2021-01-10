package cn.kduck.module.definition.service;

import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueMapList;

import java.util.Map;

/**
 * @author LiuHG
 */
public interface DefinitionService {

    String CODE_ENTITY_DEF_INFO = "K_ENTITY_DEF_INFO";
    String CODE_ENTITY_FIELD_DEF_INFO = "K_ENTITY_FIELD_DEF_INFO";
    String CODE_ENTITY_RELATION = "K_ENTITY_RELATION";

    void addBeanDef(EntityDef valueMap);
    EntityDef importBeanDef(String tableName);
    void deleteBeanDef(String[] ids);
    void updateBeanDef(EntityDef valueMap);
    EntityDef getBeanDef(String id);
    EntityDef getBeanDefByTableName(String tableName);
    ValueMapList listAllBeanDef();
    ValueMapList listRelationEntity(String entityDefId);

    void addFieldDef(String beanDefId, EntityFieldDef valueMap);
    void addFieldDef(String beanDefId, EntityFieldDef[] valueMap);
    void deleteFieldDef(String[] ids);
    void updateFieldDef(EntityFieldDef valueMap);
    EntityFieldDef getFieldDef(String id);
    ValueMapList listFieldDef(String beanDefId, Page page);

    Map<String, String> listTableName();

    /**
     * 判断是否启用了实体定义模块的功能来缓存定义数据
     * @return
     */
    boolean isDefinitionEnabled();
}
