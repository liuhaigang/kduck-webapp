package cn.kduck.module.configstore.service.impl;

import cn.kduck.core.configstore.annotation.ConfigObject;
import cn.kduck.core.configstore.scan.ConfigStoreProxy;
import cn.kduck.module.configstore.service.ConfigStoreService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ConfigStoreProcessor implements BeanPostProcessor {

    private ConfigStoreService configStoreService;

    @Lazy
    public ConfigStoreProcessor(ConfigStoreService configStoreService){
        this.configStoreService = configStoreService;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if(ConfigStoreProxy.class.isAssignableFrom(bean.getClass())){
            Class configClass = ((ConfigStoreProxy) bean).getConfigClass();
            ConfigObject configObjectAnno = (ConfigObject) configClass.getAnnotation(ConfigObject.class);
            String configCode = "".equals(configObjectAnno.name()) ? configClass.getSimpleName() : configObjectAnno.name();
            configStoreService.addConfigObject(configCode,configObjectAnno.explain());
        }

        return bean;
    }
}
