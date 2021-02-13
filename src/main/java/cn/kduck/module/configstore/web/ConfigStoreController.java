package cn.kduck.module.configstore.web;

import cn.kduck.core.web.annotation.ModelOperate;
import cn.kduck.core.web.annotation.ModelResource;
import cn.kduck.module.configstore.service.ConfigObjectBean;
import cn.kduck.module.configstore.service.ConfigStoreService;
import cn.kduck.module.configstore.service.impl.ConfigStoreServiceImpl.ConfigWrapper;
import cn.kduck.module.configstore.service.ConfigItemBean;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiJsonRequest;
import cn.kduck.core.web.swagger.ApiJsonResponse;
import cn.kduck.core.web.swagger.ApiParamRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/configstore")
@Api(tags="模块参数配置")
@ModelResource
public class ConfigStoreController {

    @Autowired
    private ConfigStoreService configStoreService;

    @PostMapping("/item/save")
    @ApiOperation("保存配置属性")
    @ApiJsonRequest({
            @ApiField(name = "configItemId", value = "参数项ID",paramType = "query",position=2),
            @ApiField(name = "configId", value = "配置ID",paramType = "query",position=1),
            @ApiField(name = "itemName", value = "参数名",paramType = "query",position=3),
            @ApiField(name = "itemValue", value = "参数值",paramType = "query",position=4),
    })
    @ApiJsonResponse({
            @ApiField(name = "configItemId", value = "参数项ID",paramType = "query",position=2),
            @ApiField(name = "configId", value = "配置ID",paramType = "query",position=1),
            @ApiField(name = "itemName", value = "参数名",paramType = "query",position=3),
            @ApiField(name = "itemValue", value = "参数值",paramType = "query",position=4),
    })
    @ModelOperate
    public JsonObject addConfigItem(@RequestBody List<ConfigItemBean> configItemList){
        configStoreService.saveConfigItem(configItemList);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/item/list")
    @ApiOperation("查询指定模块的配置属性")
    @ApiParamRequest(
            @ApiField(name="configCode",value="主配置编码")
    )
    @ModelOperate
    public JsonObject listConfigItem(@RequestParam("configCode") String configCode){
        ConfigWrapper configWrapper = configStoreService.listConfigItem(configCode);
        return new JsonObject(configWrapper);
    }

    @GetMapping("/list")
    @ApiOperation("查询可配置模块")
    @ModelOperate
    public JsonObject listConfig(){
        List<ConfigObjectBean> configObjectBeanList = configStoreService.listConfigObject();
        return new JsonObject(configObjectBeanList);
    }
}
