package cn.kduck.module.workflow.web.model;

import cn.kduck.module.workflow.service.ProcessDefinitionInfo;

import java.util.ArrayList;
import java.util.List;

public class ProcessDefinitionModel {

    private final ProcessDefinitionInfo processDefinitionInfo;

    public ProcessDefinitionModel(ProcessDefinitionInfo processDefinitionInfo){
        this.processDefinitionInfo = processDefinitionInfo;
    }

    private List<Integer> versionList = new ArrayList<>();

    public String getProcessDefinitionId() {
        return processDefinitionInfo.getProcessDefinitionId();
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        processDefinitionInfo.setProcessDefinitionId(processDefinitionId);
    }

    public String getKey() {
        return processDefinitionInfo.getKey();
    }

    public void setKey(String key) {
        processDefinitionInfo.setKey(key);
    }

    public String getCategory() {
        return processDefinitionInfo.getCategory();
    }

    public void setCategory(String category) {
        processDefinitionInfo.setCategory(category);
    }

    public String getDescription() {
        return processDefinitionInfo.getDescription();
    }

    public void setDescription(String description) {
        processDefinitionInfo.setDescription(description);
    }

    public String getName() {
        return processDefinitionInfo.getName();
    }

    public void setName(String name) {
        processDefinitionInfo.setName(name);
    }

    public int getVersion() {
        return processDefinitionInfo.getVersion();
    }

    public void setVersion(int version) {
        processDefinitionInfo.setVersion(version);
    }

    public String getResource() {
        return processDefinitionInfo.getResource();
    }

    public void setResource(String resource) {
        processDefinitionInfo.setResource(resource);
    }

    public String getDeploymentId() {
        return processDefinitionInfo.getDeploymentId();
    }

    public void setDeploymentId(String deploymentId) {
        processDefinitionInfo.setDeploymentId(deploymentId);
    }

    public String getDiagram() {
        return processDefinitionInfo.getDiagram();
    }

    public void setDiagram(String diagram) {
        processDefinitionInfo.setDiagram(diagram);
    }

    public boolean isSuspended() {
        return processDefinitionInfo.isSuspended();
    }

    public void setSuspended(boolean suspended) {
        processDefinitionInfo.setSuspended(suspended);
    }

    public String getTenantId() {
        return processDefinitionInfo.getTenantId();
    }

    public void setTenantId(String tenantId) {
        processDefinitionInfo.setTenantId(tenantId);
    }

    public String getVersionTag() {
        return processDefinitionInfo.getVersionTag();
    }

    public void setVersionTag(String versionTag) {
        processDefinitionInfo.setVersionTag(versionTag);
    }

    public Integer getHistoryTimeToLive() {
        return processDefinitionInfo.getHistoryTimeToLive();
    }

    public void setHistoryTimeToLive(Integer historyTimeToLive) {
        processDefinitionInfo.setHistoryTimeToLive(historyTimeToLive);
    }

    public boolean isStartableInTasklist() {
        return processDefinitionInfo.isStartableInTasklist();
    }

    public void setStartableInTasklist(boolean startableInTasklist) {
        processDefinitionInfo.setStartableInTasklist(startableInTasklist);
    }

    public void addVersion(Integer version){
        versionList.add(version);
    }

    public List<Integer> getVersionList() {
        return versionList;
    }
}
