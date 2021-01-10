package cn.kduck.module.configstore.service.impl;

import cn.kduck.module.configstore.query.ConfigItemQuery;
import cn.kduck.core.configstore.ConfigStoreReloader;
import cn.kduck.core.configstore.annotation.ConfigItem;
import cn.kduck.core.configstore.annotation.ConfigObject;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.service.ValueMapList;
import cn.kduck.core.utils.ConversionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Service("configStoreReloader")
public class ConfigStoreReloaderImpl extends DefaultService implements ConfigStoreReloader {

    @Override
    public Object reloadValue(String configCode,Object configObject) {
        Class<?> configClass = configObject.getClass();

        QuerySupport query = super.getQuery(ConfigItemQuery.class, ParamMap.create("configCode", configCode).toMap());
        ValueMapList configList = super.list(query);
//        if(configList.isEmpty()){
//            return configObject;
//        }
        Field[] declaredFields = configClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            if(name.equals("class")) continue;

            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(configClass, name);

            ConfigObject configStore = declaredField.getAnnotation(ConfigObject.class);

            if(configStore != null){

                if(configClass == declaredField.getType()){
                    throw new RuntimeException("不支持主、子配置是同一个对象：" + configClass);
                }

                String subCode = configStore.name();
                if("".equals(configStore.name())){
                    subCode = declaredField.getName();
                }

                if(configCode.endsWith("." + subCode)){
                    continue;
                }
                Object subConfigObject;
                try {
                    subConfigObject = declaredField.getType().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("实例化子配置对象出错：" + declaredField.getType(),e);
                }

                Object subObject = reloadValue(configCode + "." + subCode, subConfigObject);
                setValue(configObject,propertyDescriptor,subObject);
            }else{
                ConfigItem configItemAnno = declaredField.getAnnotation(ConfigItem.class);
                if(configItemAnno != null && !"".equals(configItemAnno.name())){
                    name = configItemAnno.name();
                }
                boolean hasValue = false;
                for (ValueMap valueMap : configList) {
                    String itemName = valueMap.getValueAsString("itemName");
                    String itemValue = valueMap.getValueAsString("itemValue");
                    if(name.equals(itemName)){
                        setValue(configObject,propertyDescriptor, itemValue);
                        hasValue = true;
                        break;
                    }
                }
                if(!hasValue){
                    cn.kduck.core.configstore.annotation.ConfigItem item = declaredField.getAnnotation(cn.kduck.core.configstore.annotation.ConfigItem.class);
                    if(item != null && StringUtils.hasText(item.defaultValue())){
                        setValue(configObject,propertyDescriptor, item.defaultValue());
                    }
                }

            }
        }
        return configObject;
    }

    private void setValue(Object obj,PropertyDescriptor pd, Object value) {
        Class<?> propertyType = pd.getPropertyType();
        Method writeMethod = pd.getWriteMethod();
        if(writeMethod != null) {
            try{
                Object convertValue = ConversionUtils.convert(value, propertyType);
                writeMethod.invoke(obj,convertValue);
            }catch (Exception e){
                throw new RuntimeException("设置配置对象属性时发生错误，config=" + obj.getClass().getName() +
                        "，field=" + pd.getName() +
                        "，value=" + value,e);
            }
        }
    }
}
