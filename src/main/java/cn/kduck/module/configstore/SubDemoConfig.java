package cn.kduck.module.configstore;


import cn.kduck.core.configstore.annotation.ConfigItem;

public class SubDemoConfig {

    @ConfigItem(name="compayName",explain = "工作单位")
    private String name;
    @ConfigItem(explain = "工作性质")
    private String jobNature;
    @ConfigItem(explain = "职位")
    private Integer position;
    @ConfigItem(explain = "工资")
    private Double wage;
    @ConfigItem(explain = "在岗",defaultValue = "true(在岗)")
    private Boolean onDuty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobNature() {
        return jobNature;
    }

    public void setJobNature(String jobNature) {
        this.jobNature = jobNature;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public Boolean getOnDuty() {
        return onDuty;
    }

    public void setOnDuty(Boolean onDuty) {
        this.onDuty = onDuty;
    }
}
