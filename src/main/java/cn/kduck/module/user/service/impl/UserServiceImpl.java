package cn.kduck.module.user.service.impl;

import cn.kduck.module.user.query.UserAccountQuery;
import cn.kduck.module.user.query.UserQuery;
import cn.kduck.module.user.service.User;
import cn.kduck.module.user.service.UserAccount;
import cn.kduck.module.user.service.UserService;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.event.Event;
import cn.kduck.core.event.Event.EventType;
import cn.kduck.core.event.EventPublisher;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * LiuHG
 */
@Service
public class UserServiceImpl extends DefaultService implements UserService{

    @Autowired
    private EventPublisher eventPublisher;

    @Override
    public void addUser(User user) {
        super.add(CODE_USER,user);
    }

    @Override
    public void deleteUser(String[] ids) {
        super.delete(CODE_USER,ids);
        eventPublisher.publish(new Event(CODE_USER, EventType.DELETE,ids));
    }

    @Override
    public void updateUser(User user) {
        super.update(CODE_USER,user);
    }

    @Override
    public User getUser(String id) {
        return getForBean(CODE_USER ,id ,User::new);
    }

    @Override
    public List<User> listUser(Map<String, Object> paramMap, Page page) {
        QuerySupport query = super.getQuery(UserQuery.class, paramMap);
        return super.listForBean(query,page,User::new);
    }

    @Override
    public List<UserAccount> listUserAccount(Map<String, Object> paramMap, Page page) {
        QuerySupport query = super.getQuery(UserAccountQuery.class, paramMap);
        return super.listForBean(query,page,UserAccount::new);
    }
}
