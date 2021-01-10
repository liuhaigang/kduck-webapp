package cn.kduck.module.organization.web;

import cn.kduck.module.organization.service.Organization;
import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.module.user.service.User;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueMapList;
import cn.kduck.core.utils.TreeNodeUtils;
import cn.kduck.core.utils.TreeNodeUtils.Node;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/organization")
@Api(tags = "组织机构管理")
public class OrganizationController {

    @Autowired
    private OrganizationService orgService;

    /**
     * 添加组织机构
     *
     * @param organization
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加组织机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "上级主键", paramType = "query",required = true),
            @ApiImplicitParam(name = "orgName", value = "机构名称", paramType = "query",required = true),
            @ApiImplicitParam(name = "orgCode", value = "机构编码", paramType = "query",required = true),
            @ApiImplicitParam(name = "shortName", value = "简称", paramType = "query"),
            @ApiImplicitParam(name = "orgType", value = "机构类型", paramType = "query"),
            @ApiImplicitParam(name = "orgNature", value = "机构性质", paramType = "query")
    })
    public JsonObject addOrganization(@ApiIgnore Organization organization) {
        orgService.addOrganization(organization);
        return JsonObject.SUCCESS;
    }

    /**
     * 更新组织机构
     *
     * @param organization
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新组织机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "机构主键", paramType = "query",required = true),
            @ApiImplicitParam(name = "parentId", value = "上级主键", paramType = "query",required = true),
            @ApiImplicitParam(name = "orgName", value = "机构名称", paramType = "query"),
            @ApiImplicitParam(name = "orgCode", value = "机构编码", paramType = "query"),
            @ApiImplicitParam(name = "shortName", value = "简称", paramType = "query"),
            @ApiImplicitParam(name = "orgType", value = "机构类型", paramType = "query"),
            @ApiImplicitParam(name = "orgNature", value = "机构性质", paramType = "query")
    })
    public JsonObject updateOrganization(@ApiIgnore Organization organization) {
        orgService.updateOrganization(organization);
        return JsonObject.SUCCESS;
    }

    /**
     * 删除组织机构
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除组织机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "机构主键", paramType = "query", allowMultiple = true),
    })
    public JsonObject deleteOrganization(@RequestParam("ids") String[] ids) {
        orgService.deleteOrganization(ids);
        return JsonObject.SUCCESS;
    }

    /**
     * 根据组织机构主键查询组织机构信息
     *
     * @param orgId
     * @return
     */
    @GetMapping("/get")
    @ApiOperation("根据主键查询组织机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "机构主键", paramType = "query"),
    })
    public JsonObject getOrganization(@RequestParam("orgId") String orgId) {
        Organization organization = orgService.getOrganization(orgId);
        return new JsonObject(organization);
    }

    /**
     * 分页查询组织机构列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分页查询组织机构列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "上级机构ID", paramType = "query",required = true),
            @ApiImplicitParam(name = "deep", value = "是否深度查询", paramType = "query", dataTypeClass = Boolean.class),
    })
    public JsonObject listOrganization(@RequestParam("parentId") String parentId,@ApiIgnore @RequestParam Map<String, Object> paramMap,@ApiIgnore Page page) {
        List<Organization> organizations = orgService.listOrganizationByParentId(parentId,paramMap,page);
        return new JsonObject(organizations);
    }

    @GetMapping("/tree")
    @ApiOperation(value = "查询机构详情(树结构封装)")
    public JsonObject listOrganizationTree(){
        List<Organization> valueMaps = orgService.listAllOrganization(true);
        List<Node<Organization>> nodes = TreeNodeUtils.formatTreeNode(valueMaps, Organization::getOrgId, Organization::getOrgName, Organization::getParentId);
        return new JsonObject(nodes);
    }



    @PostMapping("/user/add")
    @ApiOperation(value = "新增机构用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "机构ID", paramType = "query"),
            @ApiImplicitParam(name = "userIds", value = "用户ID", paramType = "query",allowMultiple = true),
    })
    public JsonObject addOrgUser(@RequestParam("orgId") String orgId,@RequestParam("userIds") String[] userIds){
        orgService.addOrgUser(orgId,userIds);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/user/delete")
    @ApiOperation(value = "删除机构用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "机构用户ID", paramType = "query",allowMultiple = true),
    })
    public JsonObject deleteOrgUser(@RequestParam("ids") String[] orgUserIds){
        orgService.deleteOrgUser(orgUserIds);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/user/list")
    @ApiOperation(value = "根据机构ID查询机构用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "机构ID", paramType = "query"),
    })
    public JsonObject listOrgUser(@RequestParam("orgId") String orgId,@ApiIgnore Page page){
        ValueMapList valueMaps = orgService.listOrgUser(orgId,page);
        return new JsonPageObject(page,valueMaps);
    }

    @GetMapping("/user/list/forSelect")
    @ApiOperation(value = "根据机构ID查询可选用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "机构ID", paramType = "query"),
    })
    public JsonObject listOrgUserForSelect(@RequestParam("orgId") String orgId,@ApiIgnore Page page){
        List<User> organizations = orgService.listUserForOrgUser(orgId, page);
        return new JsonPageObject(page,organizations);
    }

}
