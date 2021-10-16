package cn.kduck.module.workflow.service;

import cn.kduck.core.service.Page;

import java.util.List;
import java.util.Map;

public interface WorkFlowService {

    List<DeploymentInfo> listDeployment(Page page);

    Map<String,List<ProcessDefinitionInfo>> listProcessDefinition();

    List<ProcessInstanceInfo> listProcessInstance(String deploymentId, Page page);

    List<ActivityInstanceInfo> listFinishedActivity(String processInstanceId);


}
