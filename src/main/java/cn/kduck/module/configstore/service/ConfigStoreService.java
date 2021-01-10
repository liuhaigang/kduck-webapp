package cn.kduck.module.configstore.service;


import cn.kduck.module.configstore.service.impl.ConfigStoreServiceImpl.ConfigWrapper;

import java.util.List;

public interface ConfigStoreService {
    String CODE_CONFIG_STORE = "K_CONFIG_STORE";
    String CODE_CONFIG_STORE_ITEM = "K_CONFIG_STORE_ITEM";

    List<ConfigObjectBean> listConfigObject();

    ConfigWrapper listConfigItem(String configCode);

    void saveConfigItem(List<ConfigItemBean> configItemList);

    Object reloadValue(String configId);

}
