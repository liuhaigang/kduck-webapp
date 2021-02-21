package cn.kduck.module.auditlog.web;

import cn.kduck.core.service.Page;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.json.JsonPageObject;
import cn.kduck.module.auditlog.service.AuditLog;
import cn.kduck.module.auditlog.service.AuditLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auditlog")
@Api(tags = "审计日志管理")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @DeleteMapping("/delete")
    public JsonObject deleteAuditLog(String[] ids){
        auditLogService.deleteAuditLog(ids);
        return JsonObject.SUCCESS;
    }

    @DeleteMapping("/delete/byCondition")
    public JsonObject deleteAuditLog(String[] method, Date beforeDate){
        auditLogService.deleteAuditLogByCondition(method,beforeDate);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/list")
    public JsonObject listAuditLog(AuditLog auditLog, Page page){
        List<AuditLog> auditLogList = auditLogService.listAuditLog(auditLog, page);
        return new JsonPageObject(page,auditLogList);
    }
}
