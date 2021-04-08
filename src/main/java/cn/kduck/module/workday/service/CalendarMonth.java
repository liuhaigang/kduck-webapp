package cn.kduck.module.workday.service;

import java.util.ArrayList;
import java.util.List;

public class CalendarMonth {

    private int year;
    private int month;
    private List<CalendarDay> calendarDays = new ArrayList(31);

    public CalendarMonth() {
    }

    public CalendarMonth(int year, int month) {
        this.year = year;
        this.month = month;
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

    public CalendarDay[] getCalendarDays() {
        return calendarDays.toArray(new CalendarDay[0]);
    }

    public void addCalendarDay(CalendarDay calendarDay){
        calendarDays.add(calendarDay);
    }
}
