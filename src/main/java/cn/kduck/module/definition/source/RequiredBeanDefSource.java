package cn.kduck.module.definition.source;

import cn.kduck.core.dao.definition.BeanDefSource;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.definition.BeanFieldDef;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuHG
 */
//@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequiredBeanDefSource implements BeanDefSource {
    @Override
    public String getNamespace() {
        return null;
    }

    @Override
    public List<BeanEntityDef> listEntityDef() {
        List<BeanFieldDef> beanDefList = new ArrayList<>();
        BeanFieldDef beanDefId = new BeanFieldDef("entityDefId","ENTITY_DEF_ID",Long.class,true);
        BeanFieldDef namespace = new BeanFieldDef("namespace","NAMESPACE",String.class);
        BeanFieldDef entityName = new BeanFieldDef("entityName","ENTITY_NAME",String.class);
        BeanFieldDef tableName = new BeanFieldDef("tableName","TABLE_NAME",String.class);
        BeanFieldDef entityCode = new BeanFieldDef("entityCode","ENTITY_CODE",String.class);
        beanDefList.add(beanDefId);
        beanDefList.add(namespace);
        beanDefList.add(entityName);
        beanDefList.add(tableName);
        beanDefList.add(entityCode);
        BeanEntityDef beanEntityDef = new BeanEntityDef(null,"K_ENTITY_DEF_INFO","实体定义","K_ENTITY_DEF_INFO",beanDefList);

        List<BeanFieldDef> fieldDefList = new ArrayList<>();
        BeanFieldDef beanFieldDefId = new BeanFieldDef("entityFieldDefId","ENTITY_FIELD_DEF_ID",Long.class,true);
        BeanFieldDef fieldName = new BeanFieldDef("fieldName","FIELD_NAME",String.class);
        BeanFieldDef attributeName = new BeanFieldDef("attributeName","ATTRIBUTE_NAME",String.class);
        BeanFieldDef javaType = new BeanFieldDef("javaType","JAVA_TYPE",String.class);
        BeanFieldDef jdbcType = new BeanFieldDef("jdbcType","JDBC_TYPE",Integer.class);
        BeanFieldDef remarks = new BeanFieldDef("remarks","REMARKS",String.class);
        BeanFieldDef isPk = new BeanFieldDef("isPk","IS_PK",Integer.class);
        BeanFieldDef isFk = new BeanFieldDef("isFk","IS_FK",Integer.class);

        BeanFieldDef fkBeanDefId = new BeanFieldDef("entityDefId","ENTITY_DEF_ID",Long.class);
        fieldDefList.add(beanFieldDefId);
        fieldDefList.add(fieldName);
        fieldDefList.add(attributeName);
        fieldDefList.add(javaType);
        fieldDefList.add(jdbcType);
        fieldDefList.add(remarks);
        fieldDefList.add(isPk);
        fieldDefList.add(isFk);
        fieldDefList.add(fkBeanDefId);
        BeanEntityDef beanEntityFieldDef = new BeanEntityDef(null,"K_ENTITY_FIELD_DEF_INFO","实体字段定义","K_ENTITY_FIELD_DEF_INFO",fieldDefList);
        beanEntityFieldDef.setFkBeanEntityDef(new BeanEntityDef[]{beanEntityDef});

        List<BeanFieldDef> entityRelationList = new ArrayList<>();
        BeanFieldDef relationId = new BeanFieldDef("relationId","RELATION_ID",Long.class,true);
        BeanFieldDef relEntityCode = new BeanFieldDef("relationDefId","RELATION_DEF_ID",Long.class);
        entityRelationList.add(fkBeanDefId);
        entityRelationList.add(relEntityCode);
        entityRelationList.add(relationId);
        BeanEntityDef entityRelationDef = new BeanEntityDef(null,"K_ENTITY_RELATION","实体关联定义","K_ENTITY_RELATION",entityRelationList);

        List<BeanEntityDef> entityDefList = new ArrayList<>();
        entityDefList.add(beanEntityDef);
        entityDefList.add(beanEntityFieldDef);
        entityDefList.add(entityRelationDef);
        return entityDefList;
    }

    @Override
    public BeanEntityDef reloadEntity(String entityCode) {
        return null;
    }
}
