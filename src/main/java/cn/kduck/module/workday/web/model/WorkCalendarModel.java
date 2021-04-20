package cn.kduck.module.workday.web.model;

import cn.kduck.module.workday.service.WorkCalendar;

import java.util.ArrayList;
import java.util.List;

public class WorkCalendarModel {

    private String calendarCode;
    private List<WorkCalendar> workCalendarList = new ArrayList<>();

    public String getCalendarCode() {
        return calendarCode;
    }

    public void setCalendarCode(String calendarCode) {
        this.calendarCode = calendarCode;
    }

    public List<WorkCalendar> getWorkCalendarList() {
        return workCalendarList;
    }

    public void addWorkCalendar(WorkCalendar year){
        workCalendarList.add(year);
    }

}
