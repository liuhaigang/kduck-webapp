package cn.kduck.module.definition.source;

import cn.kduck.module.definition.service.DefinitionService;
import cn.kduck.module.definition.service.EntityDef;
import cn.kduck.module.definition.service.EntityFieldDef;
import cn.kduck.module.definition.query.BeanDefQuery;
import cn.kduck.module.definition.query.BeanFieldDefQuery;
import cn.kduck.module.definition.query.RelationBeanQuery;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.definition.BeanDefSource;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.definition.BeanFieldDef;
import cn.kduck.core.dao.definition.impl.JdbcBeanDefSource;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.UpdateBuilder;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.service.ValueMapList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuHG
 */
//@Component
@Order(Ordered.HIGHEST_PRECEDENCE+5)
public class DatabaseBeanDefSource extends JdbcBeanDefSource implements BeanDefSource {

    @Autowired
    private DefinitionService definitionService;

    @Autowired
    private DefaultService defaultService;

    private List<BeanEntityDef> resultList;

    @Override
    public String getNamespace() {
        return null;
    }

    @Override
    public List<BeanEntityDef> listEntityDef() {
        List<EntityDef> allBeanDefList = definitionService.listAllBeanDef().convertList(EntityDef.class);

//        List<BeanEntityDef> resultList;

        //如果当前数据表中没有任何实体定义数据，则将扫描后的Bean初始化到数据表中
        if(allBeanDefList.isEmpty()){
            resultList = getAllEntityDefs();
        } else {
            resultList = new ArrayList<>();

            Map<String,BeanEntityDef> defIdMap = new HashMap();
            for (EntityDef entityDef : allBeanDefList) {
                ValueMapList beanDefList = definitionService.listFieldDef(entityDef.getEntityDefId(), null);
                BeanEntityDef beanEntityDef = convertBeanEntity(entityDef, beanDefList);

                defIdMap.put(entityDef.getEntityDefId(),beanEntityDef);
                resultList.add(beanEntityDef);
            }

            //处理外键实体关系
            for (EntityDef entityDef : allBeanDefList) {
                String mainEntityDefId = entityDef.getEntityDefId();
                Map paramMap = ParamMap.create("entityDefId", mainEntityDefId).toMap();
                QuerySupport query = defaultService.getQuery(RelationBeanQuery.class, paramMap);
                ValueMapList relationIdList = defaultService.list(query);

                if(relationIdList.isEmpty())continue;

                BeanEntityDef beanEntityDef = defIdMap.get(mainEntityDefId);
                BeanEntityDef[] fkBeanEntityDef = new BeanEntityDef[relationIdList.size()];
                for (int i = 0; i < relationIdList.size(); i++) {
                    EntityDef valueMap = new EntityDef(relationIdList.get(i));
                    fkBeanEntityDef[i] = defIdMap.get(valueMap.getValueAsString("relationDefId"));;
                }
                beanEntityDef.setFkBeanEntityDef(fkBeanEntityDef);
            }
        }
        return resultList;
    }


    /**
     * 通过jdbc连接获取数据表的实体信息
     */
    private List<BeanEntityDef> getAllEntityDefs() {
        List<BeanEntityDef> jdbcEntityDefList = super.listEntityDef();
        Map<String,Object> entityIdMap = new HashMap<>();
        for (BeanEntityDef beanEntityDef : jdbcEntityDefList) {
            EntityDef entityDef = new EntityDef();
            entityDef.setEntityName(beanEntityDef.getEntityName());
            entityDef.setTableName(beanEntityDef.getTableName());
            entityDef.setEntityCode(beanEntityDef.getEntityCode());
            entityDef.setNamespace(beanEntityDef.getNamespace());
            definitionService.addBeanDef(entityDef);

            entityIdMap.put(entityDef.getEntityCode(),entityDef.getEntityDefId());

            List<BeanFieldDef> fieldList = beanEntityDef.getFieldList();
            EntityFieldDef[] fieldDefList = new EntityFieldDef[fieldList.size()];
            for (int i = 0; i < fieldList.size(); i++) {
                BeanFieldDef fieldDef = fieldList.get(i);
                fieldDefList[i] = new EntityFieldDef();
                fieldDefList[i].setAttributeName(fieldDef.getAttrName());
                fieldDefList[i].setFieldName(fieldDef.getFieldName());
                fieldDefList[i].setRemarks(fieldDef.getRemarks());
                fieldDefList[i].setIsPk(fieldDef.isPk() ? 1 : 0);
                fieldDefList[i].setJavaType(fieldDef.getJavaType().getName());
                fieldDefList[i].setJdbcType(fieldDef.getJdbcType());
                fieldDefList[i].setIsFk(0);
            }
            definitionService.addFieldDef(entityDef.getEntityDefId(),fieldDefList);

        }

        //处理外键实体关系
        for (BeanEntityDef beanEntityDef : jdbcEntityDefList) {
            BeanEntityDef[] fkBeanEntityDef = beanEntityDef.getFkBeanEntityDef();
            if(fkBeanEntityDef != null){
                Object idValue = entityIdMap.get(beanEntityDef.getEntityCode());
                for (BeanEntityDef fkBeanDef : fkBeanEntityDef) {
                    Object relationIdValue = entityIdMap.get(fkBeanDef.getEntityCode());
                    Map valueMap = ParamMap.create("relationDefId",relationIdValue).set("entityDefId", idValue).toMap();
                    defaultService.add(DefinitionService.CODE_ENTITY_RELATION,valueMap);

                    BeanEntityDef fkEntityFieldDef = defaultService.getEntityDef(DefinitionService.CODE_ENTITY_FIELD_DEF_INFO);
                    valueMap.clear();
                    valueMap.put("pkFieldName",fkBeanDef.getPkFieldDef().getFieldName());
                    valueMap.put("pkTableId",idValue);
                    valueMap.put("isFk",1);
                    UpdateBuilder updateBuilder = new UpdateBuilder(fkEntityFieldDef,valueMap);
                    updateBuilder.where("entity_def_id", ConditionType.EQUALS,"pkTableId")
                    .and("field_name", ConditionType.EQUALS,"pkFieldName");

                    defaultService.executeUpdate(updateBuilder.build());
                }
            }
        }
        return jdbcEntityDefList;
    }

    @Override
    public BeanEntityDef reloadEntity(String entityCode) {
//        Entity
        Map paramMap = ParamMap.create("entityCode", entityCode).toMap();
        QuerySupport query = defaultService.getQuery(BeanDefQuery.class, paramMap);
        ValueMap valueMap = defaultService.get(query);

//        Entity Fields
        EntityDef entityDef = new EntityDef(valueMap);
        query = defaultService.getQuery(BeanFieldDefQuery.class, paramMap);
        ValueMapList beanDefList = defaultService.list(query);

////        替换缓存中的对象
//        for (int i = 0; i < resultList.size(); i++) {
//            String entityCode1 = beanEntityDef.getEntityCode();
//            BeanEntityDef tempEntityDef = resultList.get(i);
//            if(tempEntityDef.getEntityCode().equals(entityCode1)){
//                resultList.set(i,beanEntityDef);
//            }
//
//            BeanEntityDef[] fkBeanEntityDef = tempEntityDef.getFkBeanEntityDef();
//            if(fkBeanEntityDef != null){
//                for (int j = 0; j < fkBeanEntityDef.length; j++) {
//                    if(fkBeanEntityDef[j].getEntityCode().equals(entityCode1)){
//                        fkBeanEntityDef[j] = beanEntityDef;
//                        break;
//                    }
//                }
//            }
//        }

        return convertBeanEntity(entityDef, beanDefList);
    }

    private BeanEntityDef convertBeanEntity(EntityDef entityDef, ValueMapList beanDefList) {
        List<EntityFieldDef> entityFieldDefs = beanDefList.convertList(EntityFieldDef.class);

        List<BeanFieldDef> fieldList = new ArrayList(entityFieldDefs.size());
        entityFieldDefs.forEach(item -> {
            Class<?> javaClass;
            try {
                javaClass = Class.forName(item.getJavaType());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("无法识别java数据类型：" + item.getJavaType(), e);
            }
            BeanFieldDef beanFieldDef = new BeanFieldDef(item.getAttributeName(), item.getFieldName(), javaClass, item.getIsPk() == 1);
            beanFieldDef.setRemarks(item.getRemarks());

            fieldList.add(beanFieldDef);
        });

        return new BeanEntityDef(entityDef.getNamespace(), entityDef.getEntityCode(), entityDef.getEntityName(), entityDef.getTableName(), fieldList);
    }
}
