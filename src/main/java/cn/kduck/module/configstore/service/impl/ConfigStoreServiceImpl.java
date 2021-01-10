package cn.kduck.module.configstore.service.impl;


import cn.kduck.module.configstore.query.ConfigItemQuery;
import cn.kduck.module.configstore.query.ConfigStoreQuery;
import cn.kduck.module.configstore.service.ConfigItemBean;
import cn.kduck.module.configstore.service.ConfigObjectBean;
import cn.kduck.module.configstore.service.ConfigStoreService;
import cn.kduck.core.configstore.ConfigStoreReloader;
import cn.kduck.core.configstore.annotation.ConfigAllowableValues;
import cn.kduck.core.configstore.annotation.ConfigItem;
import cn.kduck.core.configstore.annotation.ConfigObject;
import cn.kduck.core.configstore.scan.ConfigStoreProxy;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.utils.SpringBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ConfigStoreServiceImpl extends DefaultService implements ConfigStoreService, BeanPostProcessor {

    @Autowired
    private ConfigStoreReloader configStoreReloader;

    @Override
    public List<ConfigObjectBean> listConfigObject() {
        QuerySupport query = super.getQuery(ConfigStoreQuery.class, null);
        List<ConfigObjectBean> configObjectBeans = super.listForBean(query, ConfigObjectBean::new);
        for (ConfigObjectBean configObjectBean : configObjectBeans) {
            try{
                //检测对应的Bean是否存在
                SpringBeanUtils.getBean(configObjectBean.getConfigCode());
                configObjectBean.setIsValid(true);
            }catch (NoSuchBeanDefinitionException e){
                configObjectBean.setIsValid(false);
            }

        }
        return configObjectBeans;
    }

    @Override
    public ConfigWrapper listConfigItem(String configCode) {
        //从Spring中获取指定编码的配置对象（如果不存在会抛NoSuchBeanDefinitionException）
        Object configObject = SpringBeanUtils.getBean(configCode);
        ConfigObject configObjectAnno = AnnotationUtils.findAnnotation(configObject.getClass(), ConfigObject.class);
        return listConfigItem(configCode,configObjectAnno.explain(),configObject);
    }
    private ConfigWrapper listConfigItem(String configCode,String configExplain,Object configObject) {
        //根据编码查询当前已经存在数据库中的配置对象，如果不存在，则提前添加，确保后续动作有配置对象的主键用于配置项是用。
        ConfigObjectBean config = addConfigObject(configCode,configExplain);

        //根据配置编码查询其下的所有配置项
        QuerySupport query = super.getQuery(ConfigItemQuery.class, ParamMap.create("configCode", configCode).toMap());
        List<ConfigItemBean> savedItemList = super.listForBean(query, ConfigItemBean::new);

        ConfigWrapper configWrapper = new ConfigWrapper(configExplain);

        //以配置对象中的属性为准，循环封装返回给前端展现用的信息。如果数据库中已经存在，则使用数据库中的数据封装，不存在的属性就以
        //配置项的注解来封装，如果没有属性注解，则用属性名代替
        Class<?> configClass = configObject.getClass();
        Field[] declaredFields = configClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {

            ConfigItemBean item = new ConfigItemBean();

            ConfigObject subConfigStore = declaredField.getAnnotation(ConfigObject.class);
            //判断对象被标注了@ConfigStore，并且当前的配置对象Code不是subConfigStore.name()结尾，避免循环相同对象发生死循环
            if(subConfigStore != null && !configCode.endsWith("."+subConfigStore.name())){
                Object subConfigObject;
                try {
                    subConfigObject = declaredField.getType().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("实例化子配置对象出错：" + declaredField.getType(),e);
                }
                String subCode = "".equals(subConfigStore.name()) ? declaredField.getName() : subConfigStore.name();
                ConfigWrapper subConfig = listConfigItem(configCode + "." + subCode, subConfigStore.explain(), subConfigObject);
                configWrapper.setSubConfig(subConfig);
//                configItemList.add(item);
                continue;
            }

            item.setConfigId(config.getConfigId());

            ConfigItem itemAnno = declaredField.getAnnotation(ConfigItem.class);

            String itemName = itemAnno.name();
            if("".equals(itemName)){
                itemName = declaredField.getName();
            }

            item.setItemName(itemName);
            item.setItemType(declaredField.getType().getSimpleName());
            if(itemAnno != null) {
                item.setItemExplain(itemAnno.explain());
                item.setDefaultValue(itemAnno.defaultValue());
                if(!"".equals(itemAnno.hint()))item.setHint(itemAnno.hint());
                item.setGroup(itemAnno.group());
                item.setOrder(itemAnno.order());
            } else {
                item.setItemExplain(declaredField.getName());
                item.setGroup("");
                item.setOrder(0);
            }

            ConfigAllowableValues configAllowableValuesAnno = declaredField.getAnnotation(ConfigAllowableValues.class);
            if(configAllowableValuesAnno != null){
                item.setAllowValues(StringUtils.arrayToDelimitedString(configAllowableValuesAnno.value(),","));
            }

            //用数据库中已保存的属性覆盖一遍
            for (ConfigItemBean configItem : savedItemList) {
                if(item.getItemName().equals(configItem.getItemName())){
                    item.putAll(configItem);
                    break;
                }
            }
            configWrapper.addConfigItemBean(item);
        }

        Collections.sort(configWrapper.getConfigItems());

        return configWrapper;
    }

    private ConfigObjectBean addConfigObject(String configCode, String configExplain) {
        ConfigObjectBean config = super.getForBean(CODE_CONFIG_STORE, "configCode", configCode, ConfigObjectBean::new);
        if(config == null){
            config = new ConfigObjectBean();
            config.setConfigCode(configCode);
            config.setConfigExplain(configExplain);
            config.setIsMajor(configCode.indexOf(".") >= 0 ? 0 : 1 );

            super.add(CODE_CONFIG_STORE,config);
            config.setConfigId(config.getConfigId());
        }

        return config;
    }

    @Override
    @Transactional
    public void saveConfigItem(List<ConfigItemBean> configItemList) {

        for (ConfigItemBean configItem : configItemList) {

            //判断参数对象是否在数据库中存在，如果不存在新增，存在就更新
            String configItemId = configItem.getConfigItemId();

            // 如果configItemId不存在，还不能判定是一个新增的配置项，
            // 然后需要检查configId是否存在，并根据配置项名，查询是否有配置项存在，如果存在就将configItemId赋值
            // 如果仍然查询不到，说名是一个新增的配置项
            if(!StringUtils.hasText(configItemId) && StringUtils.hasText(configItem.getConfigId())){
                Map<String, Object> valueMap = ParamMap.create("configId",configItem.getConfigId())
                        .set("itemName",configItem.getItemName())
                        .set("hasSubConfig",true).toMap();
                QuerySupport query = super.getQuery(ConfigItemQuery.class, valueMap);
                ConfigItemBean configItemBean = super.getForBean(query, ConfigItemBean::new);
                if(configItemBean != null){
                    configItemId = configItemBean.getConfigItemId();
                }
            }

            //如果configItemId有值，则执行配置项值的更新
            if(StringUtils.hasText(configItemId)){

                String itemValue = configItem.getItemValue();
                Map<String, Object> valueMap = ParamMap.create("configItemId",configItemId)
                        .set("itemValue",itemValue).toMap();
                //如果没设置值或设置为空字符串，将值更新为NULL
                if(!StringUtils.hasText(itemValue)){
                    valueMap.put("itemValue",null);
                }
                super.update(CODE_CONFIG_STORE_ITEM, valueMap);

            } else {
                //根据configId查询配置对象，以下大部分逻辑都在检测提交的参数是否与配置对象能够对应上
                ConfigObjectBean config = super.getForBean(CODE_CONFIG_STORE, configItem.getValueAsString("configId"), ConfigObjectBean::new);

                if(config == null){
                    throw new RuntimeException("指定配置数据不存在：" + configItem.getValueAsString("configId"));
                }

                Object configObject;
                String configCode = config.getConfigCode();
                String[] subObjectAttrs = configCode.split("[.]");
                try{
                    configObject = SpringBeanUtils.getBean(subObjectAttrs[0]);
                }catch(NoSuchBeanDefinitionException e){
                    throw new RuntimeException("指定的配置对象不存在："
                            + subObjectAttrs[0]
                            +"，configId=" + configItem.getConfigId(),e);
                }

                Class subConfigClass = configObject.getClass();
                for (int i = 1; i < subObjectAttrs.length; i++) {
                    PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(subConfigClass, subObjectAttrs[i]);
                    subConfigClass = propertyDescriptor.getPropertyType();
                }

                //FIXME 属性存在性检测被注释，无法检测对象属性的存在性，因为可能通过注解对属性进行重命名（避免主子对象属性同名）
//                PropertyDescriptor itemName = BeanUtils.getPropertyDescriptor(subConfigClass,
//                        configItem.getItemName());
//                if(itemName == null){
//                    throw new RuntimeException("配置对象" + config.getConfigCode()+ "中不存在配置属性："
//                            + configItem.getValueAsString("itemName"));
//                }

                super.add(CODE_CONFIG_STORE_ITEM,configItem);
            }

        }
//        super.delete(CODE_CONFIG_STORE_ITEM,"configId",configIdList.toArray(new String[0]));
//        super.batchAdd(CODE_CONFIG_STORE_ITEM,configItemList);

        //收集本次保存的配置ID，用于后续刷新配置对象
        List<String> configIdList = new ArrayList<>();
        for (ConfigItemBean configItem : configItemList) {
            String configId = configItem.getConfigId();
            //提示：configId在对象属性的时候是为null的。
            if(StringUtils.hasText(configId) && !configIdList.contains(configId)){
                configIdList.add(configId);
            }
        }
        for (String id : configIdList) {
            reloadValue(id);
        }
    }

    @Override
    public Object reloadValue(String configId) {
        ValueMap valueMap = super.get(CODE_CONFIG_STORE, configId);
        String configCode = valueMap.getValueAsString("configCode");
        //FIXME 判断是否是子配置对象。子配置对象的属性更新必须通过主配置对象
        int subConfigIndex = configCode.indexOf(".");
        if(subConfigIndex >= 0){
            configCode = configCode.substring(0,subConfigIndex);
        }
        Object configObject = SpringBeanUtils.getBean(configCode);
        return configStoreReloader.reloadValue(configCode,configObject);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if(ConfigStoreProxy.class.isAssignableFrom(bean.getClass())){
            Class configClass = ((ConfigStoreProxy) bean).getConfigClass();
            ConfigObject configObjectAnno = (ConfigObject) configClass.getAnnotation(ConfigObject.class);
            String configCode = "".equals(configObjectAnno.name()) ? configClass.getSimpleName() : configObjectAnno.name();
            addConfigObject(configCode,configObjectAnno.explain());
        }

        return bean;
    }

    public static class ConfigWrapper {
        private String configName;
        private List<ConfigItemBean> configItems = new ArrayList<>();

        private ConfigWrapper subConfig;

        public ConfigWrapper(String configName){
            this.configName = configName;
        }

        private void addConfigItemBean(ConfigItemBean configItemBean){
            configItems.add(configItemBean);
        }

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }

        public List<ConfigItemBean> getConfigItems() {
            return configItems;
        }

        public void setConfigItems(List<ConfigItemBean> configItems) {
            this.configItems = configItems;
        }

        public ConfigWrapper getSubConfig() {
            return subConfig;
        }

        public void setSubConfig(ConfigWrapper subConfig) {
            this.subConfig = subConfig;
        }
    }
//    @Override
//    public Object reloadValue(String configCode,Object configObject) {
//        Class<?> configClass = configObject.getClass();
//
//        QuerySupport query = super.getQuery(ConfigItemQuery.class, ParamMap.create("configCode", configCode).toMap());
//        ValueMapList configList = super.list(query);
//        if(configList.isEmpty()){
//            return configObject;
//        }
//        Field[] declaredFields = configClass.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            String name = declaredField.getName();
//            if(name.equals("class")) continue;
//
//            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(configClass, name);
//
//            ConfigStore configStore = declaredField.getAnnotation(ConfigStore.class);
//
//            if(configStore != null){
//                if(configCode.endsWith("." + configStore.name())){
//                    continue;
//                }
//                Object subConfigObject;
//                try {
//                    subConfigObject = declaredField.getType().newInstance();
//                } catch (Exception e) {
//                    throw new RuntimeException("实例化子配置对象出错：" + declaredField.getType(),e);
//                }
//                Object subObject = reloadValue(configCode + "." + configStore.name(), subConfigObject);
//                setValue(configObject,propertyDescriptor,subObject);
//            }else{
//                boolean hasValue = false;
//                for (ValueMap valueMap : configList) {
//                    String itemName = valueMap.getValueAsString("itemName");
//                    String itemValue = valueMap.getValueAsString("itemValue");
//                    if(name.equals(itemName)){
//                        setValue(configObject,propertyDescriptor, itemValue);
//                        hasValue = true;
//                        break;
//                    }
//                }
//                if(!hasValue){
//                    cn.kduck.core.configstore.annotation.ConfigItem item = declaredField.getAnnotation(cn.kduck.core.configstore.annotation.ConfigItem.class);
//                    if(item != null && StringUtils.hasText(item.defaultValue())){
//                        setValue(configObject,propertyDescriptor, item.defaultValue());
//                    }
//                }
//
//            }
//        }
//        return configObject;
//    }
//
//    private void setValue(Object obj,PropertyDescriptor pd, Object value) {
//        Class<?> propertyType = pd.getPropertyType();
//        Method writeMethod = pd.getWriteMethod();
//        if(writeMethod != null) {
//            try{
//                Object convertValue = ConversionUtils.convert(value, propertyType);
//                writeMethod.invoke(obj,convertValue);
//            }catch (Exception e){
//                throw new RuntimeException("设置配置对象属性时发生错误，config=" + obj.getClass().getName() +
//                        "，field=" + pd.getName() +
//                        "，value=" + value,e);
//            }
//        }
//    }
}
