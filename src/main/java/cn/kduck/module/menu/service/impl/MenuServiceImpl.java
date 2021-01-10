package cn.kduck.module.menu.service.impl;

import cn.kduck.module.authorize.service.AuthorizeOperate;
import cn.kduck.module.hierarchical.query.HierarchicalAuthorizeQuery;
import cn.kduck.module.hierarchical.service.HierarchicalAuthorize;
import cn.kduck.module.menu.query.MenuAuthorizeQuery;
import cn.kduck.module.menu.query.MenuByUserQuery;
import cn.kduck.module.menu.query.MenuQuery;
import cn.kduck.module.menu.service.Menu;
import cn.kduck.module.menu.service.MenuAuthorize;
import cn.kduck.module.menu.service.MenuService;
import cn.kduck.core.dao.NameFieldFilter;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.DeleteBuilder;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.security.principal.AuthUserHolder;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ValueMapList;
import cn.kduck.core.utils.PathUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends DefaultService implements MenuService {

    @Override
    public void addMenu(Menu menu) {
        //实现得到id值用于拼接dataPath
        Serializable idValue = super.generateIdValue();
        menu.setMenuId(idValue.toString());

        //查询上级ID的dataPath作为基础，拼接菜单的dataPath
        String parentId = menu.getParentId();
        Menu parentMenu = super.getForBean(CODE_MENU, parentId, new NameFieldFilter(Menu.DATA_PATH),Menu::new);
        String parentDataPath;
        if(parentMenu != null ){
            parentDataPath = PathUtils.appendPath(parentMenu.getDataPath(),parentId,true);
        }else{
            parentDataPath = String.valueOf(PathUtils.PATH_SEPARATOR);
        }
        menu.setDataPath(parentDataPath);

        super.add(CODE_MENU,menu,false);
    }

    @Override
    public void deleteMenu(String[] ids) {
        super.delete(CODE_MENU_AUTHORIZE,MenuAuthorize.MENU_ID,ids);
        super.delete(CODE_MENU,ids);
    }

    @Override
    public void updateMenu(Menu menu) {
        //上级ID和路径参数不能进行修改，因此移除上级ID和路径参数，避免页面传递过来的属性覆盖
        menu.remove(Menu.DATA_PATH);
        menu.remove(Menu.PARENT_ID);
        super.update(CODE_MENU,menu);
    }

    @Override
    public Menu getMenu(String menuId) {
        return super.getForBean(CODE_MENU,menuId,Menu::new);
    }

    @Override
    public List<Menu> listAllMenu() {
        QuerySupport query = super.getQuery(MenuQuery.class, ParamMap.create("incRoot",true).toMap());
        return super.listForBean(query,Menu::new);
    }

    @Override
    public List<Menu> listMenuByParentId(String parentId, Map<String, Object> paramMap, Page page) {
        if(Boolean.valueOf(""+paramMap.containsKey("deep"))){
            Menu menu = super.getForBean(CODE_MENU, parentId, new NameFieldFilter("dataPath"),Menu::new);
            paramMap.put("dataPath",PathUtils.appendPath(menu.getDataPath(),parentId,true));
        }

        paramMap.put("parentId", parentId);

        Object incRoot = paramMap.get("incRoot");
        paramMap.put("incRoot", incRoot != null ? Boolean.valueOf(incRoot.toString()) : parentId.equals(Menu.ROOT_ID));
        QuerySupport query = super.getQuery(MenuQuery.class, paramMap);
        return super.listForBean(query,page,Menu::new);
    }

    @Override
    @Transactional
    public void saveMenuAuthorize(String menuId, Integer operateType,String[] operateObject) {

        List<MenuAuthorize> menuAuthorizeList = new ArrayList<>();

        if(operateType.intValue() == AuthorizeOperate.OPERATE_TYPE_ID){
            deleteMenuAuthorize(menuId,operateType,null);
        }else if(operateType.intValue() == AuthorizeOperate.OPERATE_TYPE_GROUP){
            List<String> deletedResource = new ArrayList<>();
            for (String ro : operateObject) {
                String[] splitInfo = obtainResourceOperate(ro);
                if(!deletedResource.contains(splitInfo[0])){
                    deletedResource.add(splitInfo[0]);
                    deleteMenuAuthorize(menuId,operateType,splitInfo[0]);
                }
            }
        }

        for (String ro : operateObject) {
            String[] splitInfo = obtainResourceOperate(ro);
            if (splitInfo.length > 1) {
                menuAuthorizeList.add(new MenuAuthorize(menuId,splitInfo[0],operateType,splitInfo[1]));
            }
        }

        if(!menuAuthorizeList.isEmpty()){
            super.batchAdd(CODE_MENU_AUTHORIZE,menuAuthorizeList);
        }

    }

    private String[] obtainResourceOperate(String ro) {
        String[] splitInfo = ro.split("#");
        return splitInfo;
    }

    private void deleteMenuAuthorize(String menuId,Integer operateType,String resourceId) {
        Map<String, Object> paramMap = ParamMap.create("menuId", menuId)
                .set("operateType", operateType)
                .set("resourceId",resourceId).toMap();
        DeleteBuilder deleteBuilder = new DeleteBuilder(super.getEntityDef(CODE_MENU_AUTHORIZE),paramMap);
        deleteBuilder.where()
                .and("menu_id", ConditionType.EQUALS,"menuId",true)
                .and("operate_type", ConditionType.EQUALS,"operateType")
                .and("resource_id", ConditionType.EQUALS,"resourceId");
        super.executeUpdate(deleteBuilder.build());
    }

    @Override
    public List<MenuAuthorize> listMenuAuthorize(String menuId,Integer operateType) {
        QuerySupport query = super.getQuery(MenuAuthorizeQuery.class, ParamMap.create("menuId", menuId)
                .set("operateType",operateType).toMap());
        return super.listForBean(query,MenuAuthorize::new);
    }

    @Override
    public List<Menu> listMenuByIds(String[] ids) {
        Map<String, Object> paramMap = ParamMap.create("ids", ids).toMap();
        QuerySupport query = super.getQuery(MenuQuery.class, paramMap);
        return super.listForBean(query,Menu::new);
    }

    @Override
    public List<Menu> listMenuByCurrentUser() {
        AuthUser authUser = AuthUserHolder.getAuthUser();

        Collection<String> authorities = authUser.getAuthorities();
        List<String> roleCodeList = new ArrayList<>();
        for (String authority : authorities) {
            roleCodeList.add(authority);
        }

        Map<String, Object> paramMap = ParamMap.create("userId", authUser.getUserId()).set("orgId", authUser.getAuthOrgId()).toMap();
        QuerySupport haQuery = super.getQuery(HierarchicalAuthorizeQuery.class, paramMap);
        ValueMapList authList = super.list(haQuery);

        paramMap = ParamMap.create("userId", authUser.getUserId())
                .set("hierarchicalIds",authList.getValueArray(HierarchicalAuthorize.AUTHORIZE_ID,String.class))
                .set("roleCodes",roleCodeList)
                .toMap();
        QuerySupport query = super.getQuery(MenuByUserQuery.class, paramMap);
        List<Menu> menuList = super.listForBean(query, Menu::new);

        //填补缺失的附菜单分组
        List<String> parentIdList = new ArrayList<>();
        for (Menu menu : menuList) {
//            menu.setHasEvent(true);
            String dataPath = menu.getDataPath();
            String[] parentIds = dataPath.split("[/]");
            for (String parentId : parentIds) {
                if("".equals(parentId)) continue;
                if(!parentIdList.contains(parentId)){
                    parentIdList.add(parentId);
                }
            }
        }
        if(!parentIdList.isEmpty()){
            List<Menu> parentMenuList = listMenuByIds(parentIdList.toArray(new String[0]));
            for (Menu menu : parentMenuList) {
                boolean isExist = false;
                for (Menu authorizeOrg : menuList) {
                    if(menu.getMenuId().equals(authorizeOrg.getMenuId())){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
//                    menu.setHasEvent(false);
                    menuList.add(menu);
                }
            }
        }

        return menuList;
    }
}
