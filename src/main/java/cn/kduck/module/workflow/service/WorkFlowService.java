package cn.kduck.module.workflow.service;

import cn.kduck.core.service.Page;

import java.util.List;

public interface WorkFlowService {

    List<DeploymentInfo> listDeployment(Page page);

    List<ProcessDefinitionInfo> listProcessDefinition();

    List<ProcessInstanceInfo> listProcessInstance(String deploymentId, Page page);

    List<ActivityInstanceInfo> listFinishedActivity(String processInstanceId);


}
