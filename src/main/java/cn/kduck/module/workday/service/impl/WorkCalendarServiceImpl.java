package cn.kduck.module.workday.service.impl;

import cn.kduck.core.dao.query.QuerySupport;
import cn.kduck.core.dao.sqlbuilder.ConditionBuilder.ConditionType;
import cn.kduck.core.dao.sqlbuilder.DeleteBuilder;
import cn.kduck.core.service.DefaultService;
import cn.kduck.core.service.Page;
import cn.kduck.core.service.ParamMap;
import cn.kduck.core.utils.ConversionUtils;
import cn.kduck.module.workday.query.HolidayDayQuery;
import cn.kduck.module.workday.query.WorkCalendarQuery;
import cn.kduck.module.workday.service.*;
import cn.kduck.module.workday.service.orchestrator.HolidayDayOrchestrator;
import cn.kduck.module.workday.service.orchestrator.impl.DefaultHolidayDayOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WorkCalendarServiceImpl extends DefaultService implements WorkCalendarService {


//    @Autowired
//    private EventPublisher eventPublisher;

    @Autowired(required = false)
    private HolidayDayOrchestrator holidayDayOrchestrator;

    @Override
    public void addWorkCalendar(WorkCalendar calendar) {
        Assert.notNull(calendar,"工作日历对象不能为null");
        Assert.notNull(calendar.getCalendarCode(),"工作日历编码不能为null");

        if(calendar.getCalendarYear() == null){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            calendar.setCalendarYear(year);
        }

        if(existWorkCalendar(calendar.getCalendarCode(),calendar.getCalendarYear())){
            throw new RuntimeException("日历编码已经存在：code=" + calendar.getCalendarCode()+",year=" + calendar.getCalendarYear());
        }
        super.add(CODE_WORK_CALENDAR,calendar);

        initWorkCalendar(calendar.getCalendarId(),getHolidayDayOrchestrator());
    }

    @Override
    public void updateWorkCalendar(WorkCalendar calendar) {
        calendar.remove(WorkCalendar.CALENDAR_CODE);
        calendar.remove(WorkCalendar.CALENDAR_YEAR);
        super.update(CODE_WORK_CALENDAR,calendar);
    }

    @Override
    public void deleteWorkCalendar(String[] ids) {
        //TODO 判断不允许删除默认日历
        super.delete(CODE_WORK_CALENDAR,ids);
    }

    @Override
    public WorkCalendar getWorkCalendar(String calendarId) {
        return super.getForBean(CODE_WORK_CALENDAR,calendarId,WorkCalendar::new);
    }

    @Override
    public List<WorkCalendar> listWorkCalendar(Page page) {
        QuerySupport query = super.getQuery(WorkCalendarQuery.class, ParamMap.create().toMap());
        return super.listForBean(query,WorkCalendar::new);
    }

    @Override
    public boolean existWorkCalendar(String code,int year) {
        Map<String, Object> paramMap = ParamMap.create().set("calendarCode",code).set("calendarYear",year).toMap();
        QuerySupport query = super.getQuery(WorkCalendarQuery.class, paramMap);
        return super.get(query) != null;
    }

    @Override
    public CalendarMonth[] getCalendarMonth(String calendarId) {
        WorkCalendar workCalendar = getWorkCalendar(calendarId);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,workCalendar.getCalendarYear());
        int maxMonth = calendar.getActualMaximum(Calendar.MONTH);
        CalendarMonth[] calendarMonths = new CalendarMonth[maxMonth+1];
        for (int i = 0; i < maxMonth + 1; i++) {
            calendarMonths[i] = getCalendarMonth(workCalendar,i);
        }
        return calendarMonths;
    }

    @Override
    public CalendarDay[] getCalendarMonth(String calendarId,int month) {
        WorkCalendar workCalendar = getWorkCalendar(calendarId);
        CalendarMonth calendarMonth = getCalendarMonth(workCalendar, month);
        return calendarMonth.getCalendarDays();
    }

    private CalendarMonth getCalendarMonth(WorkCalendar workCalendar,int month) {
        int year = workCalendar.getCalendarYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);

        CalendarMonth calendarMonth = new CalendarMonth(year,month);
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        QuerySupport query = super.getQuery(HolidayDayQuery.class, ParamMap.create("calendarId", workCalendar.getCalendarId()).set("holidayMonth", month).toMap());
        List<HolidayDay> holidayDays = super.listForBean(query, HolidayDay::new);

        for (int i = 1; i <= maxDayOfMonth; i++) {

            boolean isHolidayDay = false;
            for (HolidayDay holidayDay : holidayDays) {
                if(holidayDay.getHolidayDay() == i && holidayDay.getHolidayMonth() == month) {
                    CalendarDay calendarDay = new CalendarDay(true, year, month, i);
                    calendarDay.setName(holidayDay.getHolidayName());
                    calendarMonth.addCalendarDay(calendarDay);
                    isHolidayDay = true;
                    break;
                }
            }

            if(!isHolidayDay){
                //TODO 在for外层获取日历的公休日，然后判断是否为公休日变更为的工作日。
                calendarMonth.addCalendarDay(new CalendarDay(false,year,month,i));
            }
        }
        return calendarMonth;
    }

    @Override
    public boolean isWorkDay(String calendarId, int month, int day) {
        QuerySupport query = super.getQuery(HolidayDayQuery.class, ParamMap.create("calendarId", calendarId)
                .set("holidayMonth", month)
                .set("holidayDay", day).toMap());
        return !super.exist(query);
    }

    @Override
    public void setWorkDay(String calendarId,Date[] date) {
        for (Date d : date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            Map<String, Object> paramMap = ParamMap.create("calendarId", calendarId)
                    .set("holidayMonth", calendar.get(Calendar.MONTH))
                    .set("holidayDay", calendar.get(Calendar.DAY_OF_MONTH)).toMap();
            DeleteBuilder delBuilder = new DeleteBuilder(super.getEntityDef(CODE_HOLIDAY_DAY),paramMap);
            delBuilder.where().and("holiday_month", ConditionType.EQUALS,"holidayMonth",true)
                    .and("holiday_day", ConditionType.EQUALS,"holidayDay",true);
            super.executeUpdate(delBuilder.build());
//            eventPublisher.publish(new Event());
        }
    }

    @Override
    public void setWorkDay(String[] holidayIds) {
        super.delete(CODE_HOLIDAY_DAY,holidayIds);
    }

    @Override
    public void setHolidayDay(String calendarId,HolidayDay[] holidayDays) {
        WorkCalendar workCalendar = getWorkCalendar(calendarId);
        for (HolidayDay holidayDay : holidayDays) {
            Assert.notNull(holidayDay.getHolidayMonth(),"休息日的月份不能为null");
            Assert.notNull(holidayDay.getHolidayDay(),"休息日的日不能为null");
            String dateStr = workCalendar.getCalendarYear() + "-" + (holidayDay.getHolidayMonth()+1) + "-" + holidayDay.getHolidayDay();
            Date holidayDate = ConversionUtils.convert(dateStr, Date.class);
            holidayDay.setCalendarId(calendarId);
            holidayDay.setHolidayDate(holidayDate);
        }
        super.batchAdd(CODE_HOLIDAY_DAY,holidayDays);
    }

    @Override
    @Transactional
    public void initWorkCalendar(String calendarId, HolidayDayOrchestrator orchestrator) {
        super.delete(CODE_HOLIDAY_DAY,"calendarId",new String[]{calendarId});
        WorkCalendar workCalendar = getWorkCalendar(calendarId);
        Integer calendarYear = workCalendar.getCalendarYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,calendarYear);
        int maxMonth = calendar.getActualMaximum(Calendar.MONTH);
        for (int monthIndex = 0; monthIndex < maxMonth + 1; monthIndex++) {
            calendar.set(Calendar.MONTH,monthIndex);
            int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int dayIndex = 0; dayIndex < maxDayOfMonth; dayIndex++) {
                CalendarDay calendarDay = orchestrator.arrange(calendarYear, monthIndex+1, dayIndex+1);
                if(calendarDay.isHolidayDay()){
                    HolidayDay holidayDay = new HolidayDay(calendarYear,monthIndex+1,dayIndex+1) ;
                    holidayDay.setHolidayName(calendarDay.getName());
                    holidayDay.setCalendarId(calendarId);
                    super.add(CODE_HOLIDAY_DAY, holidayDay);
                }
            }
        }
    }

    @Override
    @Transactional
    public void cloneWorkCalendar(String sourceCalendarId, WorkCalendar targetCalendar) {
        Assert.notNull(targetCalendar.getCalendarName(),"复制目标的工作日名称不能为null");
        Assert.notNull(targetCalendar.getCalendarCode(),"复制目标的工作日编码不能为null");
        WorkCalendar workCalendar = getWorkCalendar(sourceCalendarId);
        if(workCalendar == null){
            throw new RuntimeException("复制的日历不存在：" + sourceCalendarId);
        }

        if(existWorkCalendar(targetCalendar.getCalendarCode(),targetCalendar.getCalendarYear())){
            throw new RuntimeException("日历编码已经存在：" + targetCalendar.getCalendarCode());
        }

        if(workCalendar.getCalendarYear() != targetCalendar.getCalendarYear()){
            throw new RuntimeException("复制的日历年份必须一致：" + workCalendar.getCalendarYear() + " != " + targetCalendar.getCalendarYear());
        }

        targetCalendar.setCalendarYear(workCalendar.getCalendarYear());
        if(!StringUtils.hasText(targetCalendar.getDescription())){
            targetCalendar.setDescription(workCalendar.getDescription());
        }

        super.add(CODE_WORK_CALENDAR,targetCalendar);

        QuerySupport query = super.getQuery(HolidayDayQuery.class, ParamMap.create("calendarId", sourceCalendarId).toMap());
        List<HolidayDay> holidayDays = super.listForBean(query, HolidayDay::new);
        for (HolidayDay holidayDay : holidayDays) {
            holidayDay.setCalendarId(targetCalendar.getCalendarId());
        }
        super.batchAdd(CODE_HOLIDAY_DAY,holidayDays);
    }

    @Override
    public Date getAfterWorkDay(String calendarCode, Date date, int days) {
        return getCrossWorkDay(true,calendarCode,date,days);
    }

    @Override
    public Date getBeforeWorkDay(String calendarCode, Date date, int days) {
        return getCrossWorkDay(false,calendarCode,date,days);
    }

    private Date getCrossWorkDay(boolean after,String calendarCode, Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        List<HolidayDay> holidayDays = listHolidayDays(calendarCode, calendar,after);

        while(days >= 0){
            boolean isHoliday = false;
            for (HolidayDay holidayDay : holidayDays) {
                if(holidayDay.getHolidayMonth() == month && holidayDay.getHolidayDay() == day){
                    isHoliday = true;
                    break;
                }
            }

            //TODO 判断是否为指定的日子，比如星期X

            if(!isHoliday){
                days--;
                /*
                当前日期不是休息日，但是第二天是休息日且days--后为0时，会导致接下来的+1天不会检测休息日，因此需要再次进入循环进行
                休息日判断，再次不是休息日时，才跳出while循环。
                 */
                if(days < 0){
                    break;
                }
            }
            if(after){
                calendar.add(Calendar.DAY_OF_MONTH,1);
            }else{
                calendar.add(Calendar.DAY_OF_MONTH,-1);
            }
            month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.get(Calendar.DAY_OF_MONTH);

            //跨年
            int currentYear = calendar.get(Calendar.YEAR);
            if(currentYear != year){
                year = currentYear;
                holidayDays = listHolidayDays(calendarCode, calendar,after);
            }
        }

        return calendar.getTime();
    }

    private List<HolidayDay> listHolidayDays(String calendarCode, Calendar calendar,boolean after) {

        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        int year = calendar.get(Calendar.YEAR);
        Map<String, Object> paramMap = ParamMap.create("calendarCode", calendarCode).set("calendarYear", year).toMap();
        QuerySupport workCalendarQuery = getQuery(WorkCalendarQuery.class, paramMap);
        WorkCalendar workCalendar = super.getForBean(workCalendarQuery, WorkCalendar::new);
        if(workCalendar == null){
            throw new RuntimeException("当前日期没有日历配置，请先为"+calendarCode+"编码，" + year + "年创建工作日历");
        }

        String dateConditionName = after ? "greaterDate":"lessDate";
        paramMap = ParamMap.create("calendarId", workCalendar.getCalendarId()).set(dateConditionName, calendar.getTime()).toMap();
        QuerySupport holidayDayQuery = super.getQuery(HolidayDayQuery.class, paramMap);
        return super.listForBean(holidayDayQuery, HolidayDay::new);
    }

    @Override
    public boolean isWorkDay(String calendarCode, Date date) {
        return false;
    }

    public HolidayDayOrchestrator getHolidayDayOrchestrator() {
        if(holidayDayOrchestrator == null){
            holidayDayOrchestrator = new DefaultHolidayDayOrchestrator();
        }
        return holidayDayOrchestrator;
    }
}
