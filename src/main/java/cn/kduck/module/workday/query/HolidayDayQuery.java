package cn.kduck.module.workday.query;

import cn.kduck.core.dao.definition.BeanDefDepository;
import cn.kduck.core.dao.definition.BeanEntityDef;
import cn.kduck.core.dao.query.QueryCreator;
import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.SelectBuilder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

import static cn.kduck.module.workday.service.WorkCalendarService.CODE_HOLIDAY_DAY;

@Component
public class HolidayDayQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef holidayDayDef = depository.getEntityDef(CODE_HOLIDAY_DAY);
//        BeanEntityDef workCalendarDef = depository.getEntityDef(CODE_WORK_CALENDAR);

        if(!paramMap.containsKey("calendarYear")){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            paramMap.put("calendarYear",year);
        }

        SelectBuilder sqlBuilder = new SelectBuilder(holidayDayDef,paramMap);
        sqlBuilder.where().and("calendar_id", ConditionType.EQUALS,"calendarId",true)
                .and("holiday_month",ConditionType.EQUALS,"holidayMonth")
                .and("holiday_day",ConditionType.EQUALS,"holidayDay")
                .and("holiday_day",ConditionType.GREATER_OR_EQUALS,"greaterDay")
                .and("holiday_day",ConditionType.LESS_OR_EQUALS,"lessDay").orderBy().asc("holiday_date");
//        sqlBuilder.from("w",workCalendarDef).innerJoinOn("h",holidayDayDef, "calendarId");
        return sqlBuilder.build();
    }
}
