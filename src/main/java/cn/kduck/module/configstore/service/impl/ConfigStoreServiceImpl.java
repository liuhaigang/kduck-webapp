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
import org.springframework.context.annotation.Lazy;
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
public class ConfigStoreServiceImpl extends DefaultService implements ConfigStoreService {

    private final ConfigStoreReloader configStoreReloader;

    @Lazy
    public ConfigStoreServiceImpl(ConfigStoreReloader configStoreReloader){
        this.configStoreReloader = configStoreReloader;
    }

    @Override
    public List<ConfigObjectBean> listConfigObject() {
        QuerySupport query = super.getQuery(ConfigStoreQuery.class, null);
        List<ConfigObjectBean> configObjectBeans = super.listForBean(query, ConfigObjectBean::new);
        for (ConfigObjectBean configObjectBean : configObjectBeans) {
            try{
                //???????????????Bean????????????
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
        //???Spring????????????????????????????????????????????????????????????NoSuchBeanDefinitionException???
        Object configObject = SpringBeanUtils.getBean(configCode);
        ConfigObject configObjectAnno = AnnotationUtils.findAnnotation(configObject.getClass(), ConfigObject.class);
        return listConfigItem(configCode,configObjectAnno.explain(),configObject);
    }
    private ConfigWrapper listConfigItem(String configCode,String configExplain,Object configObject) {
        //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        ConfigObjectBean config = addConfigObject(configCode,configExplain);

        //????????????????????????????????????????????????
        QuerySupport query = super.getQuery(ConfigItemQuery.class, ParamMap.create("configCode", configCode).toMap());
        List<ConfigItemBean> savedItemList = super.listForBean(query, ConfigItemBean::new);

        ConfigWrapper configWrapper = new ConfigWrapper(configExplain);

        //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        //??????????????????????????????????????????????????????????????????????????????
        Class<?> configClass = configObject.getClass();
        Field[] declaredFields = configClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {

            ConfigItemBean item = new ConfigItemBean();

            ConfigObject subConfigStore = declaredField.getAnnotation(ConfigObject.class);
            //????????????????????????@ConfigStore??????????????????????????????Code??????subConfigStore.name()????????????????????????????????????????????????
            if(subConfigStore != null && !configCode.endsWith("."+subConfigStore.name())){
                Object subConfigObject;
                try {
                    subConfigObject = declaredField.getType().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("?????????????????????????????????" + declaredField.getType(),e);
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

            //?????????????????????????????????????????????
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

    public ConfigObjectBean addConfigObject(String configCode, String configExplain) {
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

            //???????????????????????????????????????????????????????????????????????????????????????
            String configItemId = configItem.getConfigItemId();

            // ??????configItemId?????????????????????????????????????????????????????????
            // ??????????????????configId??????????????????????????????????????????????????????????????????????????????????????????configItemId??????
            // ????????????????????????????????????????????????????????????
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

            //??????configItemId???????????????????????????????????????
            if(StringUtils.hasText(configItemId)){

                String itemValue = configItem.getItemValue();
                Map<String, Object> valueMap = ParamMap.create("configItemId",configItemId)
                        .set("itemValue",itemValue).toMap();
                //????????????????????????????????????????????????????????????NULL
                if(!StringUtils.hasText(itemValue)){
                    valueMap.put("itemValue",null);
                }
                super.update(CODE_CONFIG_STORE_ITEM, valueMap);

            } else {
                //??????configId?????????????????????????????????????????????????????????????????????????????????????????????????????????
                ConfigObjectBean config = super.getForBean(CODE_CONFIG_STORE, configItem.getValueAsString("configId"), ConfigObjectBean::new);

                if(config == null){
                    throw new RuntimeException("??????????????????????????????" + configItem.getValueAsString("configId"));
                }

                Object configObject;
                String configCode = config.getConfigCode();
                String[] subObjectAttrs = configCode.split("[.]");
                try{
                    configObject = SpringBeanUtils.getBean(subObjectAttrs[0]);
                }catch(NoSuchBeanDefinitionException e){
                    throw new RuntimeException("?????????????????????????????????"
                            + subObjectAttrs[0]
                            +"???configId=" + configItem.getConfigId(),e);
                }

                Class subConfigClass = configObject.getClass();
                for (int i = 1; i < subObjectAttrs.length; i++) {
                    PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(subConfigClass, subObjectAttrs[i]);
                    subConfigClass = propertyDescriptor.getPropertyType();
                }

                //FIXME ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//                PropertyDescriptor itemName = BeanUtils.getPropertyDescriptor(subConfigClass,
//                        configItem.getItemName());
//                if(itemName == null){
//                    throw new RuntimeException("????????????" + config.getConfigCode()+ "???????????????????????????"
//                            + configItem.getValueAsString("itemName"));
//                }

                super.add(CODE_CONFIG_STORE_ITEM,configItem);
            }

        }
//        super.delete(CODE_CONFIG_STORE_ITEM,"configId",configIdList.toArray(new String[0]));
//        super.batchAdd(CODE_CONFIG_STORE_ITEM,configItemList);

        //???????????????????????????ID?????????????????????????????????
        List<String> configIdList = new ArrayList<>();
        for (ConfigItemBean configItem : configItemList) {
            String configId = configItem.getConfigId();
            //?????????configId??????????????????????????????null??????
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
        //FIXME ??????????????????????????????????????????????????????????????????????????????????????????
        int subConfigIndex = configCode.indexOf(".");
        if(subConfigIndex >= 0){
            configCode = configCode.substring(0,subConfigIndex);
        }
        Object configObject = SpringBeanUtils.getBean(configCode);
        return configStoreReloader.reloadValue(configCode,configObject);
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
//                    throw new RuntimeException("?????????????????????????????????" + declaredField.getType(),e);
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
//                throw new RuntimeException("??????????????????????????????????????????config=" + obj.getClass().getName() +
//                        "???field=" + pd.getName() +
//                        "???value=" + value,e);
//            }
//        }
//    }
}
