package cn.kduck.module.auditlog;

import cn.kduck.core.configstore.annotation.ConfigAllowableValues;
import cn.kduck.core.configstore.annotation.ConfigItem;
import cn.kduck.core.configstore.annotation.ConfigObject;

@ConfigObject(name="AuditLogConfig",explain = "审计日志配置")
public class AuditLogConfig {

    @ConfigItem(explain="启用审计日志",defaultValue = "POST,DELETE,PUT")
    @ConfigAllowableValues({
            "POST:POST请求",
            "DELETE:DELETE请求",
            "PUT:PUT请求",
            "GET:GET请求"})
    private String[] requestMethod;

    @ConfigItem(explain="自动清理指定N天前",defaultValue = "15",hint="为0时表示不自动清理")
    private Integer deleteLogDays;

    public String[] getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String[] requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Integer getDeleteLogDays() {
        return deleteLogDays;
    }

    public void setDeleteLogDays(Integer deleteLogDays) {
        this.deleteLogDays = deleteLogDays;
    }
}
