package cn.kduck.module.account;

import cn.kduck.module.account.credential.CredentialGenerator;
import cn.kduck.core.configstore.annotation.ConfigAllowableValues;
import cn.kduck.core.configstore.annotation.ConfigItem;
import cn.kduck.core.configstore.annotation.ConfigObject;

@ConfigObject(name="AccountConfig",explain = "帐号参数配置")
public class AccountConfig {

    @ConfigItem(explain="不允许使用的账号名",defaultValue = "admin,root,administrator")
    private String disallowedName;

    @ConfigObject(name="CredentialConfig",explain = "账号凭证配置")
    private CredentialConfig credentialConfig;

    public String getDisallowedName() {
        return disallowedName;
    }

    public void setDisallowedName(String disallowedName) {
        this.disallowedName = disallowedName;
    }

    public CredentialConfig getCredentialConfig() {
        return credentialConfig;
    }

    public void setCredentialConfig(CredentialConfig credentialConfig) {
        this.credentialConfig = credentialConfig;
    }

    public static class CredentialConfig {
//        @ConfigItem(explain="首次登录强制修改密码",defaultValue = "false")
//        private Boolean forceChangeFirstLogin;

        @ConfigItem(explain="凭证最小长度",defaultValue = "6")
        private Integer minLength;

        @ConfigItem(explain="凭证最大长度",defaultValue = "20")
        private Integer maxLength;

        @ConfigItem(explain="凭证生成长度",defaultValue = "6")
        private Integer length;

        @ConfigItem(explain="密码强度",defaultValue = "1,2,4")
        @ConfigAllowableValues({
                CredentialGenerator.NUMBER+":数字",
                CredentialGenerator.LOWER_LETTER+":小写字母",
                CredentialGenerator.UPPER_LETTER+":大写字母",
                CredentialGenerator.SPECIAL+":特殊字符"})
        private Integer[] strength;

        public Integer getMinLength() {
            return minLength;
        }

        public void setMinLength(Integer minLength) {
            this.minLength = minLength;
        }

        public Integer getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(Integer maxLength) {
            this.maxLength = maxLength;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public Integer[] getStrength() {
            return strength;
        }

        public void setStrength(Integer[] strength) {
            this.strength = strength;
        }

    }



}
