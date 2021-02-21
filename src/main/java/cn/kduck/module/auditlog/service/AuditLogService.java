package cn.kduck.module.auditlog.service;

import cn.kduck.core.service.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AuditLogService {

    String CODE_AUDIT_LOG = "K_AUDIT_LOG";

    void addAuditLog(AuditLog log);

    void deleteAuditLog(String[] ids);

    void deleteAuditLogByCondition(String[] method, Date beforeDate);

    AuditLog getAuditLog(String logId);

    List<AuditLog> listAuditLog(Map<String, Object> paramMap, Page page);
}
