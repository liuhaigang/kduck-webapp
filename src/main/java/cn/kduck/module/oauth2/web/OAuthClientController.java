package cn.kduck.module.oauth2.web;

import cn.kduck.module.oauth2.service.ClientInfo;
import cn.kduck.module.oauth2.service.OAuthClientService;
import cn.kduck.core.service.Page;
import cn.kduck.core.web.annotation.ModelOperate;
import cn.kduck.core.web.annotation.ModelResource;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oauthClient")
@Api(tags = "OAuth客户端管理")
@ModelResource
public class OAuthClientController {

    @Autowired
    private OAuthClientService clientService;

    @PostMapping("/add")
    @ApiOperation("新增OAuth客户端信息")
    @ModelOperate
    public JsonObject addClientInfo(ClientInfo clientInfo){
        clientService.addClientInfo(clientInfo);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/delete")
    @ApiOperation("根据主键删除OAuth客户端信息")
    @ModelOperate
    public JsonObject deleteClientInfo(String[] ids){
        clientService.deleteClientInfo(ids);
        return JsonObject.SUCCESS;
    }

    @PutMapping("/update")
    @ApiOperation("更新OAuth客户端信息")
    @ModelOperate
    public JsonObject updateClientInfo(ClientInfo clientInfo){
        clientService.updateClientInfo(clientInfo);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/get")
    @ApiOperation("根据ID查询某一OAuth客户端详情信息")
    @ModelOperate
    public JsonObject getClientInfo(String clientId){
        ClientInfo clientInfo = clientService.getClientInfo(clientId);
        return new JsonObject(clientInfo);
    }

    @GetMapping("/list")
    @ApiOperation("分页查询OAuth客户端信息")
    @ModelOperate
    public JsonObject listClientInfo(Map<String,Object> paramMap, Page page){
        List<ClientInfo> clientInfos = clientService.listClientInfo(paramMap, page);
        return new JsonPageObject(page,clientInfos);
    }
}
