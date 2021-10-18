package cn.kduck.module.workflow.web;

import cn.kduck.core.service.Page;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import cn.kduck.module.workflow.service.ActivityInstanceInfo;
import cn.kduck.module.workflow.service.DeploymentInfo;
import cn.kduck.module.workflow.service.ProcessDefinitionInfo;
import cn.kduck.module.workflow.service.ProcessInstanceInfo;
import cn.kduck.module.workflow.service.WorkFlowService;
import cn.kduck.module.workflow.web.model.ProcessDefinitionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@RestController
@RequestMapping("/workflow")
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    @GetMapping("/deployment/list")
    public JsonObject listDeployment(Page page){
        List<DeploymentInfo> deploymentList = workFlowService.listDeployment(page);
        return new JsonPageObject(page,deploymentList);
    }

    @GetMapping("/processDefinition/get")
    public JsonObject getProcessDefinition(@RequestParam("key") String key,@RequestParam("version") Integer version){
        Map<String, List<ProcessDefinitionInfo>> processDefinitionMapList = workFlowService.listProcessDefinition();

        List<ProcessDefinitionInfo> processDefinitionInfoList = processDefinitionMapList.get(key);
        for (ProcessDefinitionInfo processDefinitionInfo : processDefinitionInfoList) {
            if(processDefinitionInfo.getVersion() == version.intValue()){
                return new JsonObject(processDefinitionInfo);
            }
        }
        JsonObject errorJsonObject = new JsonObject();
        errorJsonObject.setMessage("未匹配到对应的流程定义对象");
        errorJsonObject.setCode(-1);
        return errorJsonObject;
    }

    @GetMapping("/processDefinition/list")
    public JsonObject listProcessDefinition(){
        Map<String, List<ProcessDefinitionInfo>> processDefinitionMapList = workFlowService.listProcessDefinition();

        List<ProcessDefinitionModel> modelList = new ArrayList<>();
        for (Entry<String, List<ProcessDefinitionInfo>> processDefinitionEntry : processDefinitionMapList.entrySet()) {
            List<ProcessDefinitionInfo> pdList = processDefinitionEntry.getValue();
            ProcessDefinitionModel processDefinitionModel = null;
            for (ProcessDefinitionInfo processDefinitionInfo : pdList) {
                if(processDefinitionModel == null){
                    processDefinitionModel = new ProcessDefinitionModel(processDefinitionInfo);
                    modelList.add(processDefinitionModel);
                }
                processDefinitionModel.addVersion(processDefinitionInfo.getVersion());

            }
        }
        return new JsonObject(modelList);
    }

    @GetMapping("/processDefinition/bpmnXml/get")
    public JsonObject getProcessDefinitionBpmn20Xml(@RequestParam("processDefinitionId") String processDefinitionId){
        String xml = workFlowService.getProcessDefinitionBpmn20Xml(processDefinitionId);
        return new JsonObject(xml);
    }

    @GetMapping("/processInstance/list")
    public JsonObject listDeployment(@RequestParam("deploymentId") String deploymentId, Page page){
        List<ProcessInstanceInfo> processInstanceList = workFlowService.listProcessInstance(deploymentId,page);
        return new JsonPageObject(page,processInstanceList);
    }

    @GetMapping("/activity/finished/list")
    public JsonObject listFinishedActivity(@RequestParam("processInstanceId") String processInstanceId){
        List<ActivityInstanceInfo> activityInstanceInfo = workFlowService.listFinishedActivity(processInstanceId);
        return new JsonObject(activityInstanceInfo);
    }



}
