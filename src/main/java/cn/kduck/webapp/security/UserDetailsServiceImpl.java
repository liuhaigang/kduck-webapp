package cn.kduck.webapp.security;

import cn.kduck.module.account.service.Account;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.module.organization.service.OrganizationService;
import cn.kduck.module.role.service.Role;
import cn.kduck.module.role.service.RoleService;
import cn.kduck.module.user.service.User;
import cn.kduck.module.user.service.UserService;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.ValueMap;
import cn.kduck.core.service.ValueMapList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * LiuHG
 */
public class UserDetailsServiceImpl extends DefaultService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getAccountByName(username);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        String password = account.getPassword();
        Date expiredDate = account.getExpiredDate();
//        Date credentialsExpiredDate = valueMap.getValueAsDate("credentialsExpiredDate");
        int accountState = account.getAccountState();

        boolean enabled = accountState == Account.ACCOUNT_STATE_ENABLED;
        boolean accountNonExpired = expiredDate == null || expiredDate.after(new Date());
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = accountState != Account.ACCOUNT_STATE_LOCKED;

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String userId = account.getUserId();

        //获取用户角色
        List<Role> roleList = roleService.listRole(userId, Role.ROLE_TYPE_USER);

        //获取用户机构角色
//        ValueMapList orgList = organizationService.listOrganizationByUserId(userId);
//        for(ValueMap org : orgList){
//            ValueMapList orgRoleList = roleService.listRole(org.getValueAsString("orgId"), RoleService.ROLE_TYPE_ORG);
//            for (ValueMap roleMap : orgRoleList) {
//                if(!containRole(roleList,roleMap)){
//                    roleList.add(roleMap);
//                }
//            }
//        }


        for(ValueMap roleMap : roleList){
            authorities.add(new SimpleGrantedAuthority(roleMap.getValueAsString("roleCode")));
        }

        User user = userService.getUser(account.getUserId());

//        AuthUser authUser = new AuthUser(userId, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        Kuser authUser = new Kuser(user.getUserId(),user.getUserName(),username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        authUser.setPhotoId(user.getPhotoId());
        return authUser;
    }

    private boolean containRole(ValueMapList roleList,ValueMap role){
        String code = role.getValueAsString("roleCode");
        for (ValueMap role_ : roleList) {
            if(code.equals(role_.getValueAsString("roleCode"))){
                return true;
            }
        }
        return false;
    }


}
