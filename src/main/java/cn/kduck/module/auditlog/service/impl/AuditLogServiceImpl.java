package cn.kduck.module.auditlog.service.impl;

import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.DeleteBuilder;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.utils.StringUtils;
import cn.kduck.core.web.interceptor.operateinfo.OperateInfo;
import cn.kduck.core.web.interceptor.operateinfo.OperateInfoHandler;
import cn.kduck.core.web.interceptor.operateinfo.OperateObject;
import cn.kduck.core.web.interceptor.operateinfo.OperateObject.OperateType;
import cn.kduck.module.auditlog.AuditLogConfig;
import cn.kduck.module.auditlog.query.AuditLogQuery;
import cn.kduck.module.auditlog.service.AuditLog;
import cn.kduck.module.auditlog.service.AuditLogService;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.security.principal.AuthUserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuditLogServiceImpl extends DefaultService implements AuditLogService, OperateInfoHandler {

    @Autowired
    private AuditLogConfig auditLogConfig;

    @Override
    public void doHandle(boolean success, OperateInfo operateInfo, List<OperateObject> operateObjects) {
        String[] logMethods = auditLogConfig.getRequestMethod();
        if(StringUtils.contain(logMethods,operateInfo.getMethod())){
            AuditLog log = new AuditLog();
            log.setCreateTime(new Date());
            log.setRemoteHost(operateInfo.getRemoteHost());
            log.setMethod(operateInfo.getMethod());
            log.setModuleName(operateInfo.getModuleName());
            log.setOperateName(operateInfo.getOperateName());
            log.setUrl(operateInfo.getUrl());
            AuthUser authUser = AuthUserHolder.getAuthUser();
            if(authUser != null){
                log.setUserId(authUser.getUserId());
                log.setUserName((String)authUser.getDetailsItem("userName"));
            }

            StringBuilder strBuilder = new StringBuilder();
            for (OperateObject operateObject : operateObjects) {
                String tableName = operateObject.getEntityDef().getTableName();
                OperateType operateType = operateObject.getOperateType();
                strBuilder.append("数据表："+tableName + " 操作：" + operateType + "\r\n");
            }
            System.out.println(strBuilder);

            addAuditLog(log);
        }
    }

    @Override
    public void addAuditLog(AuditLog log) {
        super.add(CODE_AUDIT_LOG,log);
    }

    @Override
    public void deleteAuditLog(String[] ids) {
        super.delete(CODE_AUDIT_LOG,ids);
    }

    @Override
    public void deleteAuditLogByCondition(String[] method, Date beforeDate) {
        Map<String,Object> valueMap = ParamMap.create("beforeDate", beforeDate).set("method",method).toMap();
        DeleteBuilder deleteBuilder = new DeleteBuilder(super.getEntityDef(CODE_AUDIT_LOG),valueMap);
        deleteBuilder.where().and("CREATE_TIME", ConditionType.LESS_OR_EQUALS,"beforeDate")
                .and("METHOD", ConditionType.IN,"method");
        super.executeUpdate(deleteBuilder.build());
    }

    @Override
    public AuditLog getAuditLog(String logId) {
        return super.getForBean(CODE_AUDIT_LOG,logId,AuditLog::new);
    }

    @Override
    public List<AuditLog> listAuditLog(Map<String, Object> paramMap,Page page) {
        QuerySupport query = super.getQuery(AuditLogQuery.class, paramMap);
        return super.listForBean(query,page,AuditLog::new);
    }
}
