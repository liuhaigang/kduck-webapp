package cn.kduck.module.hierarchical.service;

import cn.kduck.module.user.service.User;

import java.util.Map;

public class AuthorizeUser extends User {

    public static final String AUTHORIZE_ID = "authorizeId";

    public AuthorizeUser() {}

    public AuthorizeUser(Map<String,Object> map){
        super(map);
    }

    public void setAuthorizeId(String authorizeId) {
        super.setValue(AUTHORIZE_ID, authorizeId);
    }

    public String getAuthorizeId() {
        return super.getValueAsString(AUTHORIZE_ID);
    }
}
