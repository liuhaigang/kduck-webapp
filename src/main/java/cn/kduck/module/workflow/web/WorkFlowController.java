package cn.kduck.module.workflow.web;

import cn.kduck.core.service.Page;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import cn.kduck.module.workflow.service.ActivityInstanceInfo;
import cn.kduck.module.workflow.service.DeploymentInfo;
import cn.kduck.module.workflow.service.ProcessInstanceInfo;
import cn.kduck.module.workflow.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
