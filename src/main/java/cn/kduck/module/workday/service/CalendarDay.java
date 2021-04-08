package cn.kduck.module.workday.service;

import cn.kduck.core.utils.ConversionUtils;

import java.util.Date;

public class CalendarDay {

    private boolean holidayDay;
    private int holidayType;
    private String name;
    private Date date;

    private int year;
    private int month;
    private int day;

    public CalendarDay(boolean holidayDay){
        this.holidayDay = holidayDay;
        this.holidayType = holidayDay ? HolidayDay.HOLIDAYDAY_TYPE_PUBLIC : -1;
    }

    public CalendarDay(boolean holidayDay,int year, int month, int day){
        this(holidayDay);
        this.year = year;
        this.month = month;
        this.day = day;

        date = ConversionUtils.convert(year + "-" + month + "-" + day,Date.class);

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isHolidayDay() {
        return holidayDay;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHolidayDay(boolean holidayDay) {
        this.holidayDay = holidayDay;
    }

    public int getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(int holidayType) {
        this.holidayType = holidayType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
