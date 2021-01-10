package cn.kduck.module.menu.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class Menu extends ValueMap {

    public static final String ROOT_ID = "-1";

    public static final int MENU_TYPE_MENU_ITEM = 1;
    public static final int MENU_TYPE_MENU_GROUP = 2;

    /**菜单ID*/
    public static final String MENU_ID = "menuId";
    /**上级菜单ID*/
    public static final String PARENT_ID = "parentId";
    /**菜单名称*/
    public static final String MENU_NAME = "menuName";
    /**菜单编码*/
    public static final String MENU_CODE = "menuCode";
    /**菜单类型 1事件 2文件夹*/
    public static final String MENU_TYPE = "menuType";
    /**数据路径*/
    public static final String DATA_PATH = "dataPath";
    /**菜单图标*/
    public static final String ICON = "icon";
    /**排序*/
    public static final String ORDER_NUM = "orderNum";

    public Menu() {
    }

    public Menu(Map<String, Object> map) {
        super(map);
    }

    /**
     * 设置 菜单ID
     *
     * @param menuId 菜单ID
     */
    public void setMenuId(String menuId) {
        super.setValue(MENU_ID, menuId);
    }

    /**
     * 获取 菜单ID
     *
     * @return 菜单ID
     */
    public String getMenuId() {
        return super.getValueAsString(MENU_ID);
    }

    /**
     * 设置 上级菜单ID
     *
     * @param parentId 上级菜单ID
     */
    public void setParentId(String parentId) {
        super.setValue(PARENT_ID, parentId);
    }

    /**
     * 获取 上级菜单ID
     *
     * @return 上级菜单ID
     */
    public String getParentId() {
        return super.getValueAsString(PARENT_ID);
    }

    /**
     * 设置 菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        super.setValue(MENU_NAME, menuName);
    }

    /**
     * 获取 菜单名称
     *
     * @return 菜单名称
     */
    public String getMenuName() {
        return super.getValueAsString(MENU_NAME);
    }

    /**
     * 设置 菜单编码
     *
     * @param menuCode 菜单编码
     */
    public void setMenuCode(String menuCode) {
        super.setValue(MENU_CODE, menuCode);
    }

    /**
     * 获取 菜单编码
     *
     * @return 菜单编码
     */
    public String getMenuCode() {
        return super.getValueAsString(MENU_CODE);
    }

    /**
     * 设置 菜单类型 1事件 2文件夹
     *
     * @param menuType 菜单类型 1事件 2文件夹
     */
    public void setMenuType(String menuType) {
        super.setValue(MENU_TYPE, menuType);
    }

    /**
     * 获取 菜单类型 1事件 2文件夹
     *
     * @return 菜单类型 1事件 2文件夹
     */
    public String getMenuType() {
        return super.getValueAsString(MENU_TYPE);
    }

    /**
     * 设置 数据路径
     *
     * @param dataPath 数据路径
     */
    public void setDataPath(String dataPath) {
        super.setValue(DATA_PATH, dataPath);
    }

    /**
     * 获取 数据路径
     *
     * @return 数据路径
     */
    public String getDataPath() {
        return super.getValueAsString(DATA_PATH);
    }

    /**
     * 设置 菜单图标
     *
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        super.setValue(ICON, icon);
    }

    /**
     * 获取 菜单图标
     *
     * @return 菜单图标
     */
    public String getIcon() {
        return super.getValueAsString(ICON);
    }

    /**
     * 设置 排序
     *
     * @param orderNum 排序
     */
    public void setOrderNum(Integer orderNum) {
        super.setValue(ORDER_NUM, orderNum);
    }

    /**
     * 获取 排序
     *
     * @return 排序
     */
    public Integer getOrderNum() {
        return super.getValueAsInteger(ORDER_NUM);
    }
}
