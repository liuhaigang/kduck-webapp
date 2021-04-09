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
import static cn.kduck.module.workday.service.WorkCalendarService.CODE_WORK_CALENDAR;

@Component
public class HolidayDayCheckQuery implements QueryCreator {

    @Override
    public QuerySupport createQuery(Map<String, Object> paramMap, BeanDefDepository depository) {
        BeanEntityDef holidayDayDef = depository.getEntityDef(CODE_HOLIDAY_DAY);
        BeanEntityDef workCalendarDef = depository.getEntityDef(CODE_WORK_CALENDAR);

        if(!paramMap.containsKey("calendarYear")){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            paramMap.put("calendarYear",year);
        }

        SelectBuilder sqlBuilder = new SelectBuilder(holidayDayDef,paramMap);
        sqlBuilder.from("w",workCalendarDef).innerJoinOn("h",holidayDayDef,"calendarId")
                .where()
                .and("w.calendar_year", ConditionType.EQUALS,"year",true)
                .and("h.holiday_month",ConditionType.EQUALS,"month",true)
                .and("h.holiday_day",ConditionType.EQUALS,"day",true);
        return sqlBuilder.build();
    }
}