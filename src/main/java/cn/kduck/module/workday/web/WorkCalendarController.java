package cn.kduck.module.workday.web;


import cn.kduck.core.service.Page;
import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiParamRequest;
import cn.kduck.module.workday.exception.WorkCalendarExistException;
import cn.kduck.module.workday.service.CalendarDay;
import cn.kduck.module.workday.service.CalendarMonth;
import cn.kduck.module.workday.service.WorkCalendar;
import cn.kduck.module.workday.service.WorkCalendarService;
import cn.kduck.module.workday.web.model.WorkCalendarModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workDay")
@Api(tags="工作日历管理")
public class WorkCalendarController {

    @Autowired
    private WorkCalendarService workCalendarService;

    @PostMapping("/calendar/add")
    @ApiOperation("新增工作日历")
    @ApiParamRequest({
            @ApiField(name="calendarName",value="日历名称", paramType = "query"),
            @ApiField(name="calendarCode",value="日历编码", paramType = "query"),
            @ApiField(name="calendarYear",value="日历年份", paramType = "query"),
            @ApiField(name="description",value="日历描述", paramType = "query"),
    })
    public JsonObject addWorkCalendar(WorkCalendar workCalendar){
        try {
            workCalendarService.addWorkCalendar(workCalendar);
        } catch (WorkCalendarExistException e) {
            return new JsonObject(null,-1,"添加失败，工作日历已经存在");
        }
        return JsonObject.SUCCESS;
    }


    @GetMapping("/calendar/list")
    @ApiOperation("查询所有工作日历")
    public JsonObject listWorkCalendar(){
        List<WorkCalendar> workCalendarList = workCalendarService.listWorkCalendar();

        List<WorkCalendarModel> resultList = new ArrayList();
        String code = null;
        WorkCalendarModel calendarModel = null;
        for (WorkCalendar workCalendar : workCalendarList) {
           if(!workCalendar.getCalendarCode().equals(code)){
               code = workCalendar.getCalendarCode();
               if(calendarModel != null){
                   resultList.add(calendarModel);
               }
               calendarModel = new WorkCalendarModel();
               calendarModel.setCalendarCode(code);
           }
            calendarModel.addWorkCalendar(workCalendar);
        }

        if(calendarModel != null){
            resultList.add(calendarModel);
        }

        return new JsonObject(resultList);
    }


    @PutMapping("/calendar/update")
    @ApiOperation("更新工作日历")
    @ApiParamRequest({
            @ApiField(name="calendarId",value="日历ID", paramType = "query"),
            @ApiField(name="calendarName",value="日历名称", paramType = "query"),
            @ApiField(name="description",value="日历描述", paramType = "query")
    })
    public JsonObject updateWorkCalendar(WorkCalendar workCalendar){
        workCalendarService.updateWorkCalendar(workCalendar);
        return JsonObject.SUCCESS;
    }

    @GetMapping("/calendar/get")
    @ApiOperation("查看工作日历信息（不含具体工作日信息）")
    @ApiParamRequest({
            @ApiField(name="calendarId",value="日历ID", paramType = "query"),
    })
    public JsonObject getWorkCalendar(String  calendarId){
        WorkCalendar workCalendar = workCalendarService.getWorkCalendar(calendarId);
        return new JsonObject(workCalendar);
    }

    @GetMapping("/get/byMonth")
    @ApiOperation("查看指定月份工作日历")
    @ApiParamRequest({
            @ApiField(name="calendarId",value="日历ID", paramType = "query"),
            @ApiField(name="month",value="月份（1-12）", paramType = "query"),
    })
    public JsonObject getCalendarMonthByMonth(String  calendarId,Integer month){
        CalendarDay[] calendarDays = workCalendarService.getCalendarMonth(calendarId, month);
        return new JsonObject(calendarDays);
    }

    @GetMapping("/get/byYear")
    @ApiOperation("查看指定日历ID的工作日信息")
    @ApiParamRequest({
            @ApiField(name="calendarId",value="日历ID", paramType = "query"),
    })
    public JsonObject getCalendarMonth(String  calendarId){
        CalendarMonth[] calendarMonths = workCalendarService.getCalendarMonths(calendarId);
        return new JsonObject(calendarMonths);
    }

    @GetMapping("/get/afterWorkDay")
    @ApiOperation("查看指定日期后days天的工作日")
    @ApiParamRequest({
            @ApiField(name="calendarCode",value="日历编码"),
            @ApiField(name="date",value="起始日期"),
            @ApiField(name="days",value="跨天数"),
    })
    public JsonObject getAfterWorkDay(String calendarCode, Date date, int days){
        Date afterDate = workCalendarService.getAfterWorkDay(calendarCode,date,days);
        return new JsonObject(afterDate);
    }

    @GetMapping("/get/beforeWorkDay")
    @ApiOperation("查看指定日期前days天的工作日")
    @ApiParamRequest({
            @ApiField(name="calendarCode",value="日历编码"),
            @ApiField(name="date",value="起始日期"),
            @ApiField(name="days",value="跨天数"),
    })
    public JsonObject getBeforeWorkDay(String calendarCode, Date date, int days){
        Date afterDate = workCalendarService.getBeforeWorkDay(calendarCode,date,days);
        return new JsonObject(afterDate);
    }
}
