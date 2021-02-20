package cn.kduck.webapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class Kuser extends User {

    private String displayName;
    private String userId;
    private String photoId;

    /**
     *
     * @param userId 用户ID
     * @param displayName 用户显示名
     * @param username 登录名
     * @param password 密码
     * @param enabled 帐号是否有效、启用
     * @param accountNonExpired 帐号是否未过期
     * @param credentialsNonExpired 密码是否未过期
     * @param accountNonLocked 帐号是否未锁定
     * @param authorities 授权的角色信息
     */
    public Kuser(String userId,String displayName,String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhotoId() {
        return photoId;
    }

    void setPhotoId(String photoId) {
        this.photoId = photoId;
    }
}
