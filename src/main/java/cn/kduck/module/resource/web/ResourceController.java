package cn.kduck.module.resource.web;

import cn.kduck.module.resource.service.ResourceOperate;
import cn.kduck.module.resource.service.ResourceService;
import cn.kduck.module.resource.service.Resource;
import cn.kduck.core.utils.TreeNodeUtils.Node;
import cn.kduck.core.web.json.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
@RestController
@RequestMapping("/resource")
@Api(tags = "资源管理")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/all")
    @ApiOperation("查询所有资源")
    public JsonObject listAllResource(){
        List<Resource> resources = resourceService.listAllResource();
        List<Node<Resource>> nodes = formatNodeTree(resources);
        return new JsonObject(nodes);
    }

    @GetMapping("/all/byGroup")
    @ApiOperation("查询所有资源")
    public JsonObject listAllResourceByGroup(){
        List<Resource> resources = resourceService.listAllResourceByGroup();

        //FIXME
        for (Resource resource : resources) {
            String groupCode = resource.getValueAsString("groupCode");
            resource.setValue("operateId",groupCode);
            if("add".equals(groupCode)){
                resource.setValue("operateName","添加");
            }else if("del".equals(groupCode)){
                resource.setValue("operateName","删除");
            }else if("edit".equals(groupCode)){
                resource.setValue("operateName","编辑");
            }else if("get".equals(groupCode)){
                resource.setValue("operateName","详情");
            }else if("list".equals(groupCode)){
                resource.setValue("operateName","查询");
            }
        }

        List<Node<Resource>> nodes = formatNodeTree(resources);
        return new JsonObject(nodes);
    }

//    @GetMapping("/all")
//    @ApiOperation("查询所有资源")
//    public JsonObject listTreeAllResource(){
//        ValueMapList valueMaps = resourceService.listAllResource();
//        List<Node<ValueMap>> nodes = formatNodeTree(valueMaps);
//        return new JsonObject(nodes);
//    }

    @GetMapping("/operate/list")
    @ApiOperation("根据角色ID查询资源操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", paramType = "query")
    })
    public JsonObject listOperateByRole(@RequestParam("roleId") String roleId){
        List<ResourceOperate> resourceOperateList = resourceService.listOperateByRole(roleId);
        return new JsonObject(resourceOperateList);
    }

    @PostMapping("/operate/save")
    @ApiOperation("根据角色ID保存资源操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", paramType = "query"),
            @ApiImplicitParam(name = "operateIds", value = "操作Id", paramType = "query",allowMultiple = true)
    })
    public JsonObject saveOperateByRole(@RequestParam("roleId") String roleId,@RequestParam("operateIds") String[] operateIds){
        resourceService.saveOperateByRole(roleId,operateIds);
        return JsonObject.SUCCESS;
    }

    private List<Node<Resource>> formatNodeList(List<Resource> resources){
        List<Node<Resource>> nodeList = new ArrayList<>();
        Map<String,Node> parentNodeMap = new HashMap<>();
        for (Resource valueMap : resources) {
            String resourceId = valueMap.getValueAsString("resourceId");
            String resourceName = valueMap.getValueAsString("resourceName");
            String operateName = valueMap.getValueAsString("operateName");
            String optId = valueMap.getValueAsString("operateId");

            Node parentNode = parentNodeMap.get(resourceId);
            if(parentNode == null){
                parentNode = new Node();
                parentNode.setId(resourceId);
                parentNode.setParent(null);
                parentNode.setTitle(resourceName);
                nodeList.add(parentNode);

                parentNodeMap.put(resourceId,parentNode);
            }


            Node optNode = new Node(valueMap);
            optNode.setId(optId);
            optNode.setParent(parentNode);
            optNode.setTitle(operateName);

            nodeList.add(optNode);
        }

        return nodeList;
    }

    private List<Node<Resource>> formatNodeTree(List<Resource> resourceList){
        List<Node<Resource>> nodes = formatNodeList(resourceList);

        List<Node<Resource>> nodeTreeList = new ArrayList<>();
        for (Node<Resource> node : nodes) {
            Node<Resource> parent = node.getParent();
            if(parent != null){
                parent.getChildren().add(node);
            }else{
                if(!nodeTreeList.contains(parent)){
                    nodeTreeList.add(node);
                }
            }
        }
        return nodeTreeList;
    }

//    private List<Node<ValueMap>> formatNodeList(ValueMapList valueMaps){
//        List<Node<ValueMap>> nodeList = new ArrayList<>();
//        Map<String,Node> parentNodeMap = new HashMap<>();
//        for (ValueMap valueMap : valueMaps) {
//            String resourceId = valueMap.getValueAsString("resourceId");
//            String resourceName = valueMap.getValueAsString("resourceName");
//            String operateName = valueMap.getValueAsString("operateName");
//            String optId = valueMap.getValueAsString("operateId");
//
//            Node parentNode = parentNodeMap.get(resourceId);
//            if(parentNode == null){
//                parentNode = new Node();
//                parentNode.setId(resourceId);
//                parentNode.setParent(null);
//                parentNode.setTitle(resourceName);
//                nodeList.add(parentNode);
//
//                parentNodeMap.put(resourceId,parentNode);
//            }
//
//
//            Node optNode = new Node(valueMap);
//            optNode.setId(optId);
//            optNode.setParent(parentNode);
//            optNode.setTitle(operateName);
//
//            nodeList.add(optNode);
//        }
//
//        return nodeList;
//    }
}
