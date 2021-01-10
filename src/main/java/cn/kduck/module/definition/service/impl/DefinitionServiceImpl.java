package cn.kduck.module.definition.service.impl;

import cn.kduck.module.definition.query.BeanDefQuery;
import cn.kduck.module.definition.query.BeanFieldDefQuery;
import cn.kduck.module.definition.query.RelationBeanQuery;
import cn.kduck.module.definition.service.DefinitionService;
import cn.kduck.module.definition.service.EntityDef;
import cn.kduck.module.definition.service.EntityFieldDef;
import cn.kduck.module.definition.source.DatabaseBeanDefSource;
import cn.kduck.module.definition.utils.TableEntityUtils;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.definition.BeanFieldDef;
import cn.kduck.core.dao.definition.TableAliasGenerator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.service.ValueMapList;
import cn.kduck.core.utils.SpringBeanUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author LiuHG
 */
@Service
public class DefinitionServiceImpl extends DefaultService implements DefinitionService {

    @Autowired
    private BeanDefDepository beanDefDepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TableAliasGenerator tableAliasGenerator;

    @Override
    public void addBeanDef(EntityDef valueMap) {
        String tableName = valueMap.getTableName();
        if(existTable(tableName)){
            throw new RuntimeException("指定表名的实体信息已经存在：" + tableName);
        }
        super.add(CODE_ENTITY_DEF_INFO,valueMap);
    }

    @Override
    @Transactional
    public EntityDef importBeanDef(String tableName) {
        if(existTable(tableName)){
            throw new RuntimeException("指定表名的实体信息已经存在：" + tableName);
        }

        BeanEntityDef beanEntityDef = null;
        try {
            beanEntityDef = TableEntityUtils.analyzeTable(tableName, dataSource);
        } catch (SQLException e) {
            throw new RuntimeException("获取数据表名错误",e);
        }

        EntityDef entityDef = new EntityDef();
        entityDef.setEntityCode(tableAliasGenerator.genAlias(tableName));
        entityDef.setEntityName(beanEntityDef.getEntityName());
        entityDef.setNamespace(beanEntityDef.getNamespace());
        entityDef.setTableName(tableName);
        addBeanDef(entityDef);

        List<BeanFieldDef> fieldList = beanEntityDef.getFieldList();
        BeanEntityDef[] fkBeanEntityDefList = beanEntityDef.getFkBeanEntityDef();

        EntityFieldDef[] entityFieldDefs = new EntityFieldDef[fieldList.size()];
        for (int i = 0; i < fieldList.size(); i++) {
            BeanFieldDef beanFieldDef = fieldList.get(i);
            entityFieldDefs[i] = new EntityFieldDef();
            entityFieldDefs[i].setAttributeName(beanFieldDef.getAttrName());
            entityFieldDefs[i].setFieldName(beanFieldDef.getFieldName());
            entityFieldDefs[i].setIsPk(beanFieldDef.isPk()? 1:0);
            entityFieldDefs[i].setJavaType(beanFieldDef.getJavaType().getName());
            entityFieldDefs[i].setJdbcType(beanFieldDef.getJdbcType());
            entityFieldDefs[i].setRemarks(beanFieldDef.getRemarks());
            entityFieldDefs[i].setIsFk(0);

            if(fkBeanEntityDefList != null){

                for (BeanEntityDef def : fkBeanEntityDefList) {
                    String pkFieldName = def.getPkFieldDef().getFieldName();
                    if(beanFieldDef.getFieldName().equals(pkFieldName)){
                        entityFieldDefs[i].setIsFk(1);
                        break;
                    }
                }
            }

        }

        addFieldDef(entityDef.getEntityDefId(),entityFieldDefs);

        entityDef.setValue("fieldList",fieldList);
        return entityDef;
    }

    @Override
    public void deleteBeanDef(String[] ids) {
        for (String id : ids) {
            ValueMap valueMap = super.get(CODE_ENTITY_DEF_INFO, id);
            String entityCode = valueMap.getValueAsString("entityCode");
            beanDefDepository.deleteEntity(entityCode);
        }
        super.delete(CODE_ENTITY_FIELD_DEF_INFO,"entityDefId",ids);
        super.delete(CODE_ENTITY_DEF_INFO,ids);
    }

    @Override
    public void updateBeanDef(EntityDef valueMap) {
        super.update(CODE_ENTITY_DEF_INFO,valueMap);
    }

    @Override
    public EntityDef getBeanDef(String id) {
        ValueMap valueMap = super.get(CODE_ENTITY_DEF_INFO, id);
        if(valueMap == null){
            return null;
        }
        return new EntityDef(valueMap);
    }

    @Override
    public EntityDef getBeanDefByTableName(String tableName) {
        EntityDef valueMap = super.getForBean(CODE_ENTITY_DEF_INFO,"tableName",tableName,null,EntityDef::new);
        return valueMap;
    }

    @Override
    public ValueMapList listAllBeanDef() {
        QuerySupport beanDefQuery = super.getQuery(BeanDefQuery.class, null);
        return super.list(beanDefQuery);
    }

    @Override
    public ValueMapList listRelationEntity(String entityDefId) {
        Map valueMap = ParamMap.create("entityDefId", entityDefId).toMap();
        QuerySupport query = super.getQuery(RelationBeanQuery.class, valueMap);
        return super.list(query);
    }

    @Override
    public void addFieldDef(String beanDefId, EntityFieldDef valueMap) {
        valueMap.setValue("entityDefId",beanDefId);
        super.add(CODE_ENTITY_FIELD_DEF_INFO,valueMap);
    }

    @Override
    public void addFieldDef(String beanDefId, EntityFieldDef[] valueMap) {
        for (EntityFieldDef fieldDef : valueMap) {
            fieldDef.setEntityDefId(beanDefId);
        }
        super.batchAdd(CODE_ENTITY_FIELD_DEF_INFO,valueMap);
    }

    @Override
    public void deleteFieldDef(String[] ids) {
        super.delete(CODE_ENTITY_FIELD_DEF_INFO,ids);
    }

    @Override
    public void updateFieldDef(EntityFieldDef valueMap) {
        super.update(CODE_ENTITY_FIELD_DEF_INFO,valueMap);
    }

    @Override
    public EntityFieldDef getFieldDef(String id) {
        ValueMap valueMap = super.get(CODE_ENTITY_FIELD_DEF_INFO, id);
        if(valueMap == null){
            return null;
        }
        return new EntityFieldDef(valueMap);
    }

    @Override
    public ValueMapList listFieldDef(String entityDefId, Page page) {
        Map valueMap = ParamMap.create("entityDefId", entityDefId).toMap();
        QuerySupport beanDefQuery = super.getQuery(BeanFieldDefQuery.class, valueMap);
        return super.list(beanDefQuery,page);
    }

    @Override
    public Map<String, String> listTableName() {
        try {
            return TableEntityUtils.getTableNames(dataSource);
        } catch (SQLException e) {
            throw new RuntimeException("获取数据表名错误",e);
        }
    }

    @Override
    public boolean isDefinitionEnabled() {
        try{
            //仅用于检测DatabaseBeanDefSource是否存在
            SpringBeanUtils.getBean(DatabaseBeanDefSource.class);
            return true;
        }catch (NoSuchBeanDefinitionException e){
            return false;
        }
    }

    private boolean existTable(String tableName){
        ValueMap tableMap = super.get(CODE_ENTITY_DEF_INFO, "tableName", tableName, null);
        return tableMap != null;
    }


}
