package cn.kduck.module.workday.web.model;

import cn.kduck.module.workday.service.WorkCalendar;

import java.util.ArrayList;
import java.util.List;

public class WorkCalendarModel {

    private String code;
    private List<WorkCalendar> workCalendarList = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<WorkCalendar> getWorkCalendarList() {
        return workCalendarList;
    }

    public void addWorkCalendar(WorkCalendar year){
        workCalendarList.add(year);
    }

}
