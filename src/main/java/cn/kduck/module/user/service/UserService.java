package cn.kduck.module.user.service;

import cn.kduck.core.service.Page;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
public interface UserService {

    String CODE_USER = "k_user";

    void addUser(User user);

    void deleteUser(String[] ids);

    void updateUser(User user);

    User getUser(String id);

    List<User> listUser(Map<String, Object> paramMap, Page page);

    List<UserAccount> listUserAccount(Map<String, Object> paramMap, Page page);

}
