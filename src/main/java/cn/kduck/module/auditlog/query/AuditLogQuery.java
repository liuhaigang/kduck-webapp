package cn.kduck.module.auditlog.query;

import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static cn.kduck.module.auditlog.service.AuditLogService.CODE_AUDIT_LOG;

@Component
public class AuditLogQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(CODE_AUDIT_LOG);
        SelectBuilder sqlBuilder = new SelectBuilder(entityDef,paramMap);
        sqlBuilder.where().and("MODULE_NAME", ConditionType.CONTAINS,"moduleName")
                .and("OPERATE_NAME", ConditionType.CONTAINS,"operateName")
                .and("USER_NAME", ConditionType.CONTAINS,"userName")
                .and("REMOTE_HOST", ConditionType.CONTAINS,"remoteHost")
        .orderBy().desc("CREATE_TIME");
        return sqlBuilder.build();
    }

}
