package cn.kduck.module.menu.web;

import cn.kduck.module.menu.service.Menu;
import cn.kduck.module.menu.service.MenuAuthorize;
import cn.kduck.module.menu.service.MenuService;
import cn.kduck.core.service.Page;
import cn.kduck.core.utils.TreeNodeUtils;
import cn.kduck.core.utils.TreeNodeUtils.Node;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiParamRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@Api(tags = "菜单管理")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/add")
    @ApiOperation("新增菜单")
    @ApiParamRequest({
            @ApiField(name="parentId",value = "上级菜单Id",paramType = "query"),
            @ApiField(name="menuName",value = "菜单名称",paramType = "query"),
            @ApiField(name="menuCode",value = "菜单编码",paramType = "query"),
            @ApiField(name="menuType",value = "菜单类型 1事件 2文件夹",paramType = "query"),
            @ApiField(name="icon",value = "菜单图标",paramType = "query"),
            @ApiField(name="orderNum",value = "排序",paramType = "query")
    })
    public JsonObject addMenu(@ApiIgnore Menu menu){
        menuService.addMenu(menu);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除菜单")
    @ApiParamRequest({
            @ApiField(name="ids",value = "菜单主键Id",paramType = "query",allowMultiple = true)
    })
    public JsonObject  deleteMenu(@RequestParam("ids") String[] ids){
        menuService.deleteMenu(ids);
        return JsonObject.SUCCESS;
    }

    @PutMapping("/update")
    @ApiOperation("更新菜单")
    @ApiParamRequest({
            @ApiField(name="menuId",value = "菜单Id",paramType = "query"),
            @ApiField(name="menuName",value = "菜单名称",paramType = "query"),
            @ApiField(name="menuCode",value = "菜单编码",paramType = "query"),
            @ApiField(name="menuType",value = "菜单类型 1事件 2文件夹",paramType = "query"),
            @ApiField(name="icon",value = "菜单图标",paramType = "query"),
            @ApiField(name="orderNum",value = "排序",paramType = "query")
    })
    public JsonObject  updateMenu(@ApiIgnore Menu menu){
        menuService.updateMenu(menu);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/get")
    @ApiOperation("查看菜单详情")
    @ApiParamRequest({
            @ApiField(name="menuId",value = "菜单Id",paramType = "query")
    })
    public JsonObject  getMenu(@RequestParam("menuId") String menuId){
        Menu menu = menuService.getMenu(menuId);
        return new JsonObject(menu);
    }

    @GetMapping("/tree")
    @ApiOperation("查询菜单树")
    public JsonObject  treeMenu(){
        List<Menu> menus = menuService.listAllMenu();
        List<Node<Menu>> nodes = TreeNodeUtils.formatTreeNode(menus, Menu::getMenuId, Menu::getMenuName, Menu::getParentId);
        return new JsonObject(nodes);
    }

    @GetMapping("/list")
    @ApiOperation("查询菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "上级菜单ID", paramType = "query",required = true),
            @ApiImplicitParam(name = "deep", value = "是否深度查询", paramType = "query", dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "incRoot", value = "是否包含根节点", paramType = "query", dataTypeClass = Boolean.class),
    })
    public JsonObject  listMenu(@RequestParam("parentId") String parentId, @ApiIgnore @RequestParam Map<String, Object> paramMap, @ApiIgnore Page page){
        List<Menu> menus = menuService.listMenuByParentId(parentId,paramMap,page);
        return new JsonPageObject(page,menus);
    }

    @PostMapping("/authorize/save")
    @ApiOperation("新增菜单授权")
    public JsonObject addMenuAuthorize(@RequestParam("menuId") String menuId,@RequestParam("operateType")Integer operateType,@RequestParam("operateObject") String[] operateObject){
        menuService.saveMenuAuthorize(menuId,operateType,operateObject);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/authorize/list")
    @ApiOperation("查询菜单授权")
    @ApiParamRequest({
            @ApiField(name="menuId",value = "菜单Id",paramType = "query")
    })
    public JsonObject listMenuAuthorize(@RequestParam("menuId") String menuId,@RequestParam("operateType") Integer operateType){
        List<MenuAuthorize> menuAuthorizeList = menuService.listMenuAuthorize(menuId,operateType);
        return new JsonObject(menuAuthorizeList);
    }

    @GetMapping("/user/list")
    @ApiOperation("查询当前用户的菜单")
    public JsonObject listUserMenu(){
        List<Menu> userMenuList = menuService.listMenuByCurrentUser();
        List<Node<Menu>> nodes = TreeNodeUtils.formatTreeNode(userMenuList, Menu::getMenuId, Menu::getMenuName, Menu::getParentId);
        return new JsonObject(nodes);
    }
}
