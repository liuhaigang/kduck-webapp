package cn.kduck.module.configstore;


import cn.kduck.core.configstore.annotation.ConfigAllowableValues;
import cn.kduck.core.configstore.annotation.ConfigItem;
import cn.kduck.core.configstore.annotation.ConfigObject;

import java.util.Date;

//@ConfigObject(name="lhg",explain = "Demo主配置对象")
public class DemoConfig {
    @ConfigItem(explain = "姓名",defaultValue = "保密")
    private String name;
    @ConfigItem(explain = "出生日期")
    private Date birthday;
    @ConfigItem(explain = "年龄")
    private Integer age;

    @ConfigItem(explain = "地址")
    @ConfigAllowableValues({"beijing:北京","shanghai:上海","tianjin:天津","hebei:河北","guangzhou:广州"})
    private String[] address;

    @ConfigObject(explain = "工作信息")
    private SubDemoConfig workConfig;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public SubDemoConfig getWorkConfig() {
        return workConfig;
    }

    public void setWorkConfig(SubDemoConfig workConfig) {
        this.workConfig = workConfig;
    }
}
