package cn.kduck.module.workday.service;

import cn.kduck.core.service.Page;
import cn.kduck.module.workday.service.orchestrator.HolidayDayOrchestrator;

import java.util.Date;
import java.util.List;

/**
 * 注意：月份从0开始，日从1开始
 * @author LiuHG
 */
public interface WorkCalendarService {

    String CODE_WORK_CALENDAR = "K_WORK_CALENDAR";
    String CODE_HOLIDAY_DAY = "K_HOLIDAY_DAY";

    /**
     * 添加工作日历
     * @param calendar 工作日历对象
     */
    void addWorkCalendar(WorkCalendar calendar);

    /**
     * 工作日历修改，只能修改名称和描述。且不会影响已经生成的休息日信息
     * @param calendar
     */
    void updateWorkCalendar(WorkCalendar calendar);

    void deleteWorkCalendar(String[] ids);

    WorkCalendar getWorkCalendar(String calendarId);

    List<WorkCalendar> listWorkCalendar(Page page);

    boolean existWorkCalendar(String code,int year);

    /**
     * 获取指定日历下的工作日信息，即全年
     * @param calendarId 日历Id
     * @return 全年每个月的工作日信息，数组顺序为月份顺序
     */
    CalendarMonth[] getCalendarMonth(String calendarId);

    /**
     * 获取指定日历下的某个月的工作日信息
     * @param calendarId 日历Id
     * @param month 月份
     * @return 指定月份的工作日信息
     */
    CalendarDay[] getCalendarMonth(String calendarId,int month);

    /**
     * 指定工作日的月、日是否为工作日，因为对于同一年可能存在多组工作日配置，因此这里不能依靠年份来决定工作日。
     * @param calendarId 日历Id
     * @param month 月份
     * @param day 日
     * @return true为工作日，false为休息日
     */
    boolean isWorkDay(String calendarId,int month, int day);

    /**
     * 设置指定日期为工作日
     * @param calendarId 日历Id
     * @param date 休息日Id
     */
    void setWorkDay(String calendarId,Date[] date);

    /**
     * 根据休息日Id设置为工作日
     * @param holidayIds 休息日Id
     */
    void setWorkDay(String[] holidayIds);

    /**
     * 设置休息日
     * @param calendarId
     * @param holidayDays
     */
    void setHolidayDay(String calendarId,HolidayDay[] holidayDays);

    /**
     * 初始化指定日历的工作日信息，此方法会覆盖目前所有工作日的配置，恢复为初始状态
     * @param calendarId 日历Id
     * @param orchestrator 休息日编排器
     */
    void initWorkCalendar(String calendarId, HolidayDayOrchestrator orchestrator);

    /**
     * 日历复制，被复制的日历与新的日历年份会保持一致，即使在targetCalendar中设置年份也会无效
     * @param sourceCalendarId
     * @param targetCalendar
     */
    void cloneWorkCalendar(String sourceCalendarId,WorkCalendar targetCalendar);


    /**
     * 获取指定日期后days天之后的工作日
     * @param calendarCode
     * @param date
     * @param days
     * @return
     */
    Date getAfterWorkDay(String calendarCode,Date date,int days);

    Date getBeforeWorkDay(String calendarCode,Date date,int days);

    /**
     *
     * @param calendarCode
     * @param date
     * @return
     */
    boolean isWorkDay(String calendarCode,Date date);

}
