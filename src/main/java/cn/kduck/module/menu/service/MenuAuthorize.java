package cn.kduck.module.menu.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class MenuAuthorize extends ValueMap {

    public static final int IS_PUBLIC_YES = 1;
    public static final int IS_PUBLIC_NO = 0;

    /**菜单授权ID*/
    public static final String MENU_AUTHORIZE_ID = "menuAuthorizeId";
    /**菜单ID*/
    public static final String MENU_ID = "menuId";
    /**资源Id*/
    public static final String RESOURCE_ID = "resourceId";
    /**授权资源操作类型*/
    public static final String OPERATE_TYPE = "operateType";
    /**授权资源对象*/
    public static final String OPERATE_OBJECT = "operateObject";
    /**是否公共授权*/
    public static final String IS_PUBLIC = "isPublic";

    public MenuAuthorize() {
    }

    public MenuAuthorize(Map<String, Object> map) {
        super(map);
    }

    public MenuAuthorize(String menuId,String resourceId,Integer operateType,String operateObject) {
        setMenuId(menuId);
        setResourceId(resourceId);
        setOperateType(operateType);
        setOperateObject(operateObject);
        setIsPublic(IS_PUBLIC_NO);
    }

    /**
     * 设置 菜单授权ID
     *
     * @param menuAuthorizeId 菜单授权ID
     */
    public void setMenuAuthorizeId(String menuAuthorizeId) {
        super.setValue(MENU_AUTHORIZE_ID, menuAuthorizeId);
    }

    /**
     * 获取 菜单授权ID
     *
     * @return 菜单授权ID
     */
    public String getMenuAuthorizeId() {
        return super.getValueAsString(MENU_AUTHORIZE_ID);
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
     * 设置 资源
     *
     * @param resourceId 资源
     */
    public void setResourceId(String resourceId) {
        super.setValue(RESOURCE_ID, resourceId);
    }

    /**
     * 获取 资源
     *
     * @return 资源
     */
    public String getResourceId() {
        return super.getValueAsString(RESOURCE_ID);
    }

    /**
     * 设置 授权资源操作类型
     *
     * @param operateType 授权资源操作类型
     */
    public void setOperateType(Integer operateType) {
        super.setValue(OPERATE_TYPE, operateType);
    }

    /**
     * 获取 授权资源操作类型
     *
     * @return 授权资源操作类型
     */
    public Integer getOperateType() {
        return super.getValueAsInteger(OPERATE_TYPE);
    }

    /**
     * 设置 授权资源对象
     *
     * @param operateObject 授权资源对象
     */
    public void setOperateObject(String operateObject) {
        super.setValue(OPERATE_OBJECT, operateObject);
    }

    /**
     * 获取 授权资源对象
     *
     * @return 授权资源对象
     */
    public String getOperateObject() {
        return super.getValueAsString(OPERATE_OBJECT);
    }

    /**
     * 设置
     *
     * @param isPublic
     */
    public void setIsPublic(Integer isPublic) {
        super.setValue(IS_PUBLIC, isPublic);
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getIsPublic() {
        return super.getValueAsInteger(IS_PUBLIC);
    }
}
