package cn.kduck.module.workday.query;

import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import cn.kduck.module.workday.service.WorkCalendarService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WorkCalendarQuery implements QueryCreator {
    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef entityDef = depository.getEntityDef(WorkCalendarService.CODE_WORK_CALENDAR);
        SelectBuilder sqlBuilder = new SelectBuilder(entityDef,paramMap);
        sqlBuilder.where().and("CALENDAR_CODE", ConditionType.EQUALS,"calendarCode").and("CALENDAR_YEAR", ConditionType.EQUALS,"calendarYear")
        .orderBy().asc("CALENDAR_CODE").desc("CALENDAR_YEAR");
        return sqlBuilder.build();
    }
}
