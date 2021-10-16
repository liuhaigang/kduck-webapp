package cn.kduck.module.workflow.service;

public class ProcessDefinitionInfo {

    private String processDefinitionId;
    private String key;
    private String category;
    private String description;
    private String name;
    private int version;
    private String resource;
    private String deploymentId;
    private String diagram;
    private boolean suspended;
    private String tenantId;
    private String versionTag;
    private Integer historyTimeToLive;
    private boolean isStartableInTasklist;

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getVersionTag() {
        return versionTag;
    }

    public void setVersionTag(String versionTag) {
        this.versionTag = versionTag;
    }

    public Integer getHistoryTimeToLive() {
        return historyTimeToLive;
    }

    public void setHistoryTimeToLive(Integer historyTimeToLive) {
        this.historyTimeToLive = historyTimeToLive;
    }

    public boolean isStartableInTasklist() {
        return isStartableInTasklist;
    }

    public void setStartableInTasklist(boolean startableInTasklist) {
        isStartableInTasklist = startableInTasklist;
    }
}
