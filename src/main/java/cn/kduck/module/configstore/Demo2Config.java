package cn.kduck.module.configstore;


import cn.kduck.core.configstore.annotation.ConfigAllowableValues;
import cn.kduck.core.configstore.annotation.ConfigItem;
import cn.kduck.core.configstore.annotation.ConfigObject;

//@ConfigObject(explain = "账号规则配置")
public class Demo2Config {

    @ConfigItem(explain = "登录类型")
    @ConfigAllowableValues({"account:帐号","phone:手机","email:邮箱"})
    private String[] loginType;

    @ConfigItem(explain = "密码长度")
    private Integer length;

    @ConfigItem(explain = "登录失败次数")
    private Integer failNum;

    @ConfigItem(explain = "达到失败次数策略")
    private String failStrategy;

    @ConfigItem(explain = "密码复杂度")
    private String[] rules;

    public String[] getLoginType() {
        return loginType;
    }

    public void setLoginType(String[] loginType) {
        this.loginType = loginType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getFailNum() {
        return failNum;
    }

    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    public String getFailStrategy() {
        return failStrategy;
    }

    public void setFailStrategy(String failStrategy) {
        this.failStrategy = failStrategy;
    }

    public String[] getRules() {
        return rules;
    }

    public void setRules(String[] rules) {
        this.rules = rules;
    }
}
