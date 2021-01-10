package cn.kduck.module.menu.service;

import cn.kduck.core.service.Page;

import java.util.List;
import java.util.Map;

public interface MenuService {

    String CODE_MENU = "K_MENU";
    String CODE_MENU_AUTHORIZE = "K_MENU_AUTHORIZE";

    void addMenu(Menu menu);

    void deleteMenu(String[] ids);

    void updateMenu(Menu menu);

    Menu getMenu(String menuId);

    List<Menu> listAllMenu();

    List<Menu> listMenuByParentId(String parentId, Map<String, Object> paramMap, Page page);

    void saveMenuAuthorize(String menuId,Integer operateType,String[] operateObject);

    List<MenuAuthorize> listMenuAuthorize(String menuId,Integer operateType);

    List<Menu> listMenuByIds(String[] ids);

    List<Menu> listMenuByCurrentUser();
}
