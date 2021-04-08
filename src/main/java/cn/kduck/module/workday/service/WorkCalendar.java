package cn.kduck.module.workday.service;

import cn.kduck.core.service.ValueMap;

import java.util.Map;

public class WorkCalendar extends ValueMap {

    /**日历ID*/
    public static final String CALENDAR_ID = "calendarId";
    /**日历名称*/
    public static final String CALENDAR_NAME = "calendarName";
    /**日历编码*/
    public static final String CALENDAR_CODE = "calendarCode";
    /**日历描述*/
    public static final String DESCRIPTION = "description";
    /**日历年份*/
    public static final String CALENDAR_YEAR = "calendarYear";


    public WorkCalendar() {}

    public WorkCalendar(Map<String, Object> map) {
        super(map);
    }

    public WorkCalendar(String calendarCode,String calendarName) {
        setCalendarName(calendarName);
        setCalendarCode(calendarCode);
//        TODO 设置默认的日历类型
//        setCalendarType();
    }

    /**
     * 设置 日历ID
     *
     * @param calendarId 日历ID
     */
    public void setCalendarId(String calendarId) {
        super.setValue(CALENDAR_ID, calendarId);
    }

    /**
     * 获取 日历ID
     *
     * @return
     */
    public String getCalendarId() {
        return super.getValueAsString(CALENDAR_ID);
    }

    /**
     * 设置 日历名称
     *
     * @param calendarName 日历名称
     */
    public void setCalendarName(String calendarName) {
        super.setValue(CALENDAR_NAME, calendarName);
    }

    /**
     * 获取 日历名称
     *
     * @return 日历名称
     */
    public String getCalendarName() {
        return super.getValueAsString(CALENDAR_NAME);
    }

    /**
     * 设置 日历编码
     *
     * @param calendarCode 日历编码
     */
    public void setCalendarCode(String calendarCode) {
        super.setValue(CALENDAR_CODE, calendarCode);
    }

    /**
     * 获取 日历编码
     *
     * @return 日历编码
     */
    public String getCalendarCode() {
        return super.getValueAsString(CALENDAR_CODE);
    }

    /**
     * 设置 日历描述
     *
     * @param description 日历描述
     */
    public void setDescription(String description) {
        super.setValue(DESCRIPTION, description);
    }

    /**
     * 获取 日历描述
     *
     * @return 日历描述
     */
    public String getDescription() {
        return super.getValueAsString(DESCRIPTION);
    }

    /**
     * 设置 日历年份
     *
     * @param calendarYear 日历年份
     */
    public void setCalendarYear(Integer calendarYear) {
        super.setValue(CALENDAR_YEAR, calendarYear);
    }

    /**
     * 获取 日历年份
     *
     * @return 日历年份
     */
    public Integer getCalendarYear() {
        return super.getValueAsInteger(CALENDAR_YEAR);
    }
}
