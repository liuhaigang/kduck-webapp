package cn.kduck.module.workflow.service.impl;

import cn.kduck.core.service.Page;
import cn.kduck.core.service.Page.PageUtils;
import cn.kduck.flow.client.BpmServiceFactory;
import cn.kduck.flow.client.commons.dto.Count;
import cn.kduck.flow.client.commons.dto.Sort;
import cn.kduck.flow.client.definition.BpmProcessDefinitionService;
import cn.kduck.flow.client.definition.dto.ProcessDefinition;
import cn.kduck.flow.client.definition.dto.ProcessDefinitionQuery;
import cn.kduck.flow.client.deployment.BpmDeploymentService;
import cn.kduck.flow.client.deployment.dto.Deployment;
import cn.kduck.flow.client.deployment.dto.DeploymentQuery;
import cn.kduck.flow.client.history.BpmHistoryService;
import cn.kduck.flow.client.history.dto.HistoryActivityInstance;
import cn.kduck.flow.client.history.dto.HistoryActivityInstanceQuery;
import cn.kduck.flow.client.history.dto.HistoryActivitySortBy;
import cn.kduck.flow.client.process.BpmProcceeService;
import cn.kduck.flow.client.process.dto.ProcessInstance;
import cn.kduck.flow.client.process.dto.ProcessInstanceQuery;
import cn.kduck.module.workflow.service.ActivityInstanceInfo;
import cn.kduck.module.workflow.service.DeploymentInfo;
import cn.kduck.module.workflow.service.ProcessDefinitionInfo;
import cn.kduck.module.workflow.service.ProcessInstanceInfo;
import cn.kduck.module.workflow.service.WorkFlowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {
    @Autowired
    private BpmServiceFactory bpmServiceFactory;

    public List<DeploymentInfo> listDeployment(Page page){
        BpmDeploymentService deploymentService = bpmServiceFactory.getService(BpmDeploymentService.class);

        DeploymentQuery query = new DeploymentQuery();
        Count count = deploymentService.count(query);
        PageUtils.calculate(page,count.getCount());

        Deployment[] bmpDeploymentList = deploymentService.list(query, page.getFirstResult(), page.getPageSize());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        List<DeploymentInfo> deploymentInfos = new ArrayList(page.getPageSize());
        for (Deployment deployment : bmpDeploymentList) {
            String id = deployment.getId();
            String name = deployment.getName();
            String deploymentTime = deployment.getDeploymentTime();
            String source = deployment.getSource();
            String tenantId = deployment.getTenantId();

            DeploymentInfo deploymentInfo = new DeploymentInfo();
            deploymentInfo.setDeploymentId(id);
            deploymentInfo.setDeploymentName(name);
            deploymentInfo.setSource(source);
            deploymentInfo.setTenantId(tenantId);

            try {
                deploymentInfo.setDeploymentTime(dateFormat.parse(deploymentTime));
            } catch (ParseException e) {
                throw new RuntimeException("格式化部署日期失败：" + deploymentTime,e);
            }
            deploymentInfos.add(deploymentInfo);
        }
        return deploymentInfos;
    }

    @Override
    public List<ProcessDefinitionInfo> listProcessDefinition() {
        BpmProcessDefinitionService processDefinitionService = bpmServiceFactory.getService(BpmProcessDefinitionService.class);

        ProcessDefinitionQuery query = new ProcessDefinitionQuery();

        Count count = processDefinitionService.countProcessDefinition(query);

        ProcessDefinition[] processDefinitions = processDefinitionService.listProcessDefinition(query, 0, count.getCount());
        List<ProcessDefinitionInfo> processInstanceInfoList = new ArrayList<>(count.getCount());
//       TODO 合并相同key的流程定义
        for (ProcessDefinition processDefinition : processDefinitions) {
            ProcessDefinitionInfo processDefinitionInfo = new ProcessDefinitionInfo();
            BeanUtils.copyProperties(processDefinition,processDefinitionInfo);
            processInstanceInfoList.add(processDefinitionInfo);
        }
        return processInstanceInfoList;
    }

    @Override
    public List<ProcessInstanceInfo> listProcessInstance(String deploymentId, Page page) {
        BpmProcceeService processService = bpmServiceFactory.getService(BpmProcceeService.class);
        BpmProcessDefinitionService definitionService = bpmServiceFactory.getService(BpmProcessDefinitionService.class);

        ProcessInstanceQuery query = new ProcessInstanceQuery();
        query.setDeploymentId(deploymentId);

        Count count = processService.count(query);
        PageUtils.calculate(page,count.getCount());
        ProcessInstance[] processInstances = processService.list(query, page.getFirstResult(), page.getPageSize());

        List<ProcessInstanceInfo> processInstanceInfoList = new ArrayList<>(page.getPageSize());
        for (ProcessInstance processInstance : processInstances) {
            String processInstanceId = processInstance.getId();
            ProcessDefinition processDefinition = definitionService.getProcessDefinitionById(processInstance.getDefinitionId());
            String definitionName = processDefinition.getName();
            int version = processDefinition.getVersion();
            boolean suspended = processInstance.isSuspended();
            boolean ended = processInstance.isEnded();
            String businessKey = processInstance.getBusinessKey();

            ProcessInstanceInfo processInstanceInfo = new ProcessInstanceInfo();
            processInstanceInfo.setProcessInstanceId(processInstanceId);
            processInstanceInfo.setBusinessKey(businessKey);
            processInstanceInfo.setDefinitionName(definitionName);
            processInstanceInfo.setVersion(version);
            processInstanceInfo.setEnded(ended);
            processInstanceInfo.setSuspended(suspended);

            processInstanceInfoList.add(processInstanceInfo);
        }
        return processInstanceInfoList;
    }

    @Override
    public List<ActivityInstanceInfo> listFinishedActivity(String processInstanceId) {
        BpmHistoryService historyService = bpmServiceFactory.getService(BpmHistoryService.class);

        HistoryActivityInstanceQuery query = new HistoryActivityInstanceQuery();
        query.setFinished(true);
        query.setProcessInstanceId(processInstanceId);
//        query.setSortOrder(Sort.SORT_ORDER_ASC);
//        query.setSortBy(HistoryActivitySortBy.startTime.toString());
        //FIXME query startIndex and maxResult
        HistoryActivityInstance[] historyActivitys = historyService.listActivity(query, 0, 1000);

        List<ActivityInstanceInfo> activityInstanceInfoList = new ArrayList<>(historyActivitys.length);
        for (HistoryActivityInstance historyActivity : historyActivitys) {
            String id = historyActivity.getId();
            String activityType = historyActivity.getActivityType();
            String activityName = historyActivity.getActivityName();
            String assignee = historyActivity.getAssignee();
            Date startTime = historyActivity.getStartTime();
            Date endTime = historyActivity.getEndTime();
            Long durationInMillis = historyActivity.getDurationInMillis();

            ActivityInstanceInfo activityInstanceInfo = new ActivityInstanceInfo();
            activityInstanceInfo.setId(id);
            activityInstanceInfo.setActivityName(activityName);
            activityInstanceInfo.setActivityType(activityType);
            activityInstanceInfo.setStartTime(startTime);
            activityInstanceInfo.setEndTime(endTime);
            activityInstanceInfo.setAssignee(assignee);
            activityInstanceInfo.setDurationInMillis(durationInMillis);

            activityInstanceInfoList.add(activityInstanceInfo);
        }
        return activityInstanceInfoList;
    }


}
