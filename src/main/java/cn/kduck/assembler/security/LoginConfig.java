package cn.kduck.assembler.security;

import cn.kduck.core.configstore.annotation.ConfigItem;
import cn.kduck.core.configstore.annotation.ConfigObject;

@ConfigObject(name="LoginConfig",explain = "登录参数配置")
public class LoginConfig {

    @ConfigItem(explain = "启用验证码",defaultValue="true")
    private Boolean captchaEnabled;

    @ConfigItem(explain = "出现验证码失败次数",defaultValue = "3")
    private Integer captchaThreshold;

    @ConfigItem(explain = "启用锁定账号",defaultValue="true")
    private Boolean lockEnabled;

    @ConfigItem(explain = "锁定帐号失败次数",defaultValue = "5")
    private Integer lockThreshold;

    @ConfigItem(explain="首次登录强制修改密码",defaultValue = "false")
    private Boolean forceChangeFirstLogin;

    public Integer getCaptchaThreshold() {
        return captchaThreshold;
    }

    public void setCaptchaThreshold(Integer captchaThreshold) {
        this.captchaThreshold = captchaThreshold;
    }

    public Integer getLockThreshold() {
        return lockThreshold;
    }

    public void setLockThreshold(Integer lockThreshold) {
        this.lockThreshold = lockThreshold;
    }

    public Boolean getCaptchaEnabled() {
        return captchaEnabled;
    }

    public void setCaptchaEnabled(Boolean captchaEnabled) {
        this.captchaEnabled = captchaEnabled;
    }

    public Boolean getLockEnabled() {
        return lockEnabled;
    }

    public void setLockEnabled(Boolean lockEnabled) {
        this.lockEnabled = lockEnabled;
    }

    public Boolean getForceChangeFirstLogin() {
        return forceChangeFirstLogin;
    }

    public void setForceChangeFirstLogin(Boolean forceChangeFirstLogin) {
        this.forceChangeFirstLogin = forceChangeFirstLogin;
    }
}
