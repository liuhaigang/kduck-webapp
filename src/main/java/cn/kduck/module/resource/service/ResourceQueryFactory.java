package cn.kduck.module.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.query.CustomQueryBean;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.module.resource.service.impl.ResourceServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * LiuHG
 */
@Component
@Deprecated
public class ResourceQueryFactory {

    @Autowired
    private BeanDefDepository defDepo;

    public QuerySupport getResourceByMd5(String md5) {
        Map<String,Object> condition = new HashMap<>();
        condition.put("md5",md5);
        return new CustomQueryBean("SELECT {*} FROM K_RESOURCE WHERE MD5=#{md5}",condition, defDepo.getFieldDefList(ResourceServiceImpl.CODE_RESOURCE));
    }

    public QuerySupport getResourceByCode(String code) {
        Map<String,Object> condition = new HashMap<>();
        condition.put("code",code);
        return new CustomQueryBean("SELECT {*} FROM K_RESOURCE WHERE RESOURCE_CODE=#{code}",condition, defDepo.getFieldDefList(ResourceServiceImpl.CODE_RESOURCE));
    }

    public QuerySupport listResourceOperate(String resourceId) {
        Map<String,Object> condition = new HashMap<>();
        condition.put("resourceId",resourceId);
        return new CustomQueryBean("SELECT {*} FROM K_RESOURCE_OPERATE WHERE RESOURCE_ID=#{resourceId}",condition, defDepo.getFieldDefList(ResourceServiceImpl.CODE_RESOURCE));
    }

    public QuerySupport listOperateByRole(String roleId) {
        Map<String,Object> condition = new HashMap<>();
        condition.put("roleId",roleId);
        return new CustomQueryBean("SELECT {*} FROM K_ROLE_OPERATE WHERE ROLE_ID=#{roleId}",condition, defDepo.getFieldDefList(ResourceServiceImpl.CODE_ROLE_OPERATE));
    }


}
