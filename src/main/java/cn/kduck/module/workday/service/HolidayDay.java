package cn.kduck.module.workday.service;

import cn.kduck.core.service.ValueMap;
import cn.kduck.core.utils.ConversionUtils;

import java.util.Date;
import java.util.Map;

public class HolidayDay extends ValueMap{

    /**公休日*/
    public static final Integer HOLIDAYDAY_TYPE_PUBLIC=1;
    /**节假日*/
    public static final Integer HOLIDAYDAY_TYPE_FESTIVAL=2;

    /**节假日ID*/
    public static final String HOLIDAY_ID = "holidayId";
    /**日历ID*/
    public static final String CALENDAR_ID = "calendarId";
    /**节假日名称*/
    public static final String HOLIDAY_NAME = "holidayName";
    /**节假日类型*/
    public static final String HOLIDAY_TYPE = "holidayType";
    /**节假日日期*/
    public static final String HOLIDAY_DATE = "holidayDate";
    /**休息日-月*/
    public static final String HOLIDAY_MONTH = "holidayMonth";
    /**休息日-日*/
    public static final String HOLIDAY_DAY = "holidayDay";

    public HolidayDay() {
    }

    public HolidayDay(Map<String, Object> map) {
        super(map);
    }

    public HolidayDay(int year,int month,int day) {
        setHolidayMonth(month);
        setHolidayDay(day);
        setHolidayDate(ConversionUtils.convert(year + "-" + month + "-" + day,Date.class));
    }

    /**
     * 设置 节假日ID
     *
     * @param holidayId 节假日ID
     */
    public void setHolidayId(String holidayId) {
        super.setValue(HOLIDAY_ID, holidayId);
    }

    /**
     * 获取 节假日ID
     *
     * @return 节假日ID
     */
    public String getHolidayId() {
        return super.getValueAsString(HOLIDAY_ID);
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
     * @return 日历ID
     */
    public String getCalendarId() {
        return super.getValueAsString(CALENDAR_ID);
    }

    /**
     * 设置 节假日名称
     *
     * @param holidayName 节假日名称
     */
    public void setHolidayName(String holidayName) {
        super.setValue(HOLIDAY_NAME, holidayName);
    }

    /**
     * 获取 节假日名称
     *
     * @return 节假日名称
     */
    public String getHolidayName() {
        return super.getValueAsString(HOLIDAY_NAME);
    }

    /**
     * 设置 节假日类型
     *
     * @param holidayType 节假日类型
     */
    public void setHolidayType(Integer holidayType) {
        super.setValue(HOLIDAY_TYPE, holidayType);
    }

    /**
     * 获取 节假日类型
     *
     * @return 节假日类型
     */
    public Integer getHolidayType() {
        return super.getValueAsInteger(HOLIDAY_TYPE);
    }

    /**
     * 设置 节假日日期
     *
     * @param holidayDate 节假日日期
     */
    public void setHolidayDate(Date holidayDate) {
        super.setValue(HOLIDAY_DATE, holidayDate);
    }

    /**
     * 获取 节假日日期
     *
     * @return 节假日日期
     */
    public Date getHolidayDate() {
        return super.getValueAsDate(HOLIDAY_DATE);
    }

    /**
     * 设置 休息日-月
     *
     * @param holidayMonth 休息日-月
     */
    public void setHolidayMonth(int holidayMonth) {
        super.setValue(HOLIDAY_MONTH, holidayMonth);
    }

    /**
     * 获取 休息日-月
     *
     * @return 休息日-月
     */
    public int getHolidayMonth() {
        return super.getValueAsInt(HOLIDAY_MONTH);
    }

    /**
     * 设置 休息日-日
     *
     * @param holidayDay 休息日-日
     */
    public void setHolidayDay(int holidayDay) {
        super.setValue(HOLIDAY_DAY, holidayDay);
    }

    /**
     * 获取 休息日-日
     *
     * @return 休息日-日
     */
    public int getHolidayDay() {
        return super.getValueAsInt(HOLIDAY_DAY);
    }
}

