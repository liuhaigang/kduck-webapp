package cn.kduck.module.definition.web;

import cn.kduck.module.definition.service.DefinitionService;
import cn.kduck.module.definition.service.EntityDef;
import cn.kduck.module.definition.service.EntityFieldDef;
import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.service.ValueMapList;
import cn.kduck.core.web.json.JsonMapObject;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiParamRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author LiuHG
 */
@RestController
@RequestMapping("/definition")
@Api(tags = "数据实体定义")
public class DefinitionController {

    @Autowired
    private DefinitionService definitionService;

    @Autowired
    private BeanDefDepository beanDefDepository;

    @PostMapping("/entity/add")
    @ApiOperation("新增实体信息，不包含字段信息")
    @ApiParamRequest({
            @ApiField(name = "entityCode", value = "实体编码",paramType = "query"),
            @ApiField(name = "tableName", value = "数据表名",paramType = "query"),
            @ApiField(name = "entityName", value = "实体名称（描述）",paramType = "query"),
    })
    public JsonObject addBeanDefinition(@ApiIgnore EntityDef entityDef){
        definitionService.addBeanDef(entityDef);
        return JsonObject.SUCCESS;
    }

    @PutMapping("/entity/update")
    @ApiOperation("更新实体信息，不包含字段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityDefId", value = "实体ID", paramType = "query"),
            @ApiImplicitParam(name = "entityCode", value = "实体编码", paramType = "query"),
            @ApiImplicitParam(name = "tableName", value = "数据表名", paramType = "query"),
            @ApiImplicitParam(name = "entityName", value = "实体名称（描述）", paramType = "query"),
    })
    public JsonObject updateBeanDefinition(EntityDef valueMap){
        definitionService.updateBeanDef(valueMap);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/entity/delete")
    @ApiOperation("删除实体信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "预删除的实体信息ID", paramType = "query",allowMultiple = true),
    })
    public JsonObject deleteBeanDefinition(String[] ids){
        definitionService.deleteBeanDef(ids);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/entity/get")
    public JsonObject getBeanDefinition(@RequestParam("entityDefId") String defId){
        EntityDef beanDef = definitionService.getBeanDef(defId);
        return new JsonObject(beanDef);
    }

    @GetMapping("/entity/all")
    public JsonObject listAllBeanDefinition(){
        ValueMapList valueMaps = definitionService.listAllBeanDef();
        return new JsonObject(valueMaps);
    }


    //*****************************************************************

    @PostMapping("/field/add")
    public JsonObject addFieldDefinition(@RequestParam("entityDefId") String beanDefId, EntityFieldDef valueMap){
        if(!valueMap.hasValue("isPk")){
            valueMap.put("isPk",0);
        }
        definitionService.addFieldDef(beanDefId,valueMap);
        return JsonObject.SUCCESS;
    }

    @PutMapping("/field/update")
    public JsonObject updateFieldDefinition(EntityFieldDef valueMap){
        definitionService.updateFieldDef(valueMap);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/field/delete")
    public JsonObject deleteFieldDefinition(String[] ids){
        definitionService.deleteFieldDef(ids);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/field/get")
    public JsonObject getFieldDefinition(@RequestParam("entityFieldDefId") String defId){
        ValueMap beanDef = definitionService.getFieldDef(defId);
        return new JsonObject(beanDef);
    }

    @GetMapping("/field/list")
    public JsonObject listFieldDefinition(@RequestParam("entityDefId") String entityDefId,Page page){
        ValueMapList valueMaps = definitionService.listFieldDef(entityDefId,page);
        return new JsonPageObject(page,valueMaps);
    }

    //*****************************************************************

    @GetMapping("/reload/entity")
    @ApiOperation("刷新指定实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "实体编码", paramType = "query"),
    })
    public JsonObject reloadEntity(@RequestParam("entityCode") String entityCode){
        beanDefDepository.reloadEntity(entityCode);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/entity/tableName/list")
    @ApiOperation("获取所有未配置的数据表")
    public JsonObject listTableName(){
        Map<String, String> tableNameMap = definitionService.listTableName();
        return new JsonObject(tableNameMap);
    }

    @PostMapping("/entity/add/formTable")
    @ApiOperation("根据数据表添加实体信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "数据表名", paramType = "query"),
    })
    public JsonObject addBeanDefinitionFromTable(String tableName){
        EntityDef entityDef = definitionService.importBeanDef(tableName);
        JsonMapObject jsonMapObject = new JsonMapObject();
        jsonMapObject.put("tableName",entityDef.getTableName());
        jsonMapObject.put("fieldNum",entityDef.getValueAsList("fieldList").size());
        return jsonMapObject;
    }

    @GetMapping("/enabled")
    @ApiOperation("当前是否开启着数据库方式的实体定义功能")
    public JsonObject isDefinitionEnabled(){
        boolean enabled = definitionService.isDefinitionEnabled();
        return new JsonObject(enabled);
    }

}
