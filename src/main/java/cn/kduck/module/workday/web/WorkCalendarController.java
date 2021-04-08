package cn.kduck.module.workday.web;


import cn.kduck.core.web.json.JsonObject;
import cn.kduck.core.web.swagger.ApiField;
import cn.kduck.core.web.swagger.ApiParamRequest;
import cn.kduck.module.workday.service.CalendarDay;
import cn.kduck.module.workday.service.CalendarMonth;
import cn.kduck.module.workday.service.WorkCalendar;
import cn.kduck.module.workday.service.WorkCalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workDay")
@Api(tags="工作日历管理")
public class WorkCalendarController {

    @Autowired
    private WorkCalendarService workCalendarService;

    @PostMapping("/add")
    @ApiOperation("新增工作日历")
    @ApiParamRequest({
            @ApiField(name="calendarName",value="日历名称", paramType = "query"),
            @ApiField(name="calendarCode",value="日历编码", paramType = "query"),
            @ApiField(name="calendarYear",value="日历年份", paramType = "query"),
            @ApiField(name="description",value="日历描述", paramType = "query"),
    })
    public JsonObject addWorkCalendar(WorkCalendar workCalendar){
        workCalendarService.addWorkCalendar(workCalendar);
        return JsonObject.SUCCESS;
    }

    @PutMapping("/update")
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

    @GetMapping("/get")
    @ApiOperation("查看工作日历信息（不含具体工作日信息）")
    @ApiParamRequest({
            @ApiField(name="calendarId",value="日历ID"),
    })
    public JsonObject getWorkCalendar(String  calendarId){
        WorkCalendar workCalendar = workCalendarService.getWorkCalendar(calendarId);
        return new JsonObject(workCalendar);
    }

    @GetMapping("/get/byMonth")
    @ApiOperation("查看指定月份工作日历")
    @ApiParamRequest({
            @ApiField(name="calendarId",value="日历ID"),
            @ApiField(name="month",value="月份（1-12）"),
    })
    public JsonObject getCalendarMonthByMonth(String  calendarId,Integer month){
        CalendarDay[] calendarDays = workCalendarService.getCalendarMonth(calendarId, month);
        return new JsonObject(calendarDays);
    }

    @GetMapping("/get/byYear")
    @ApiOperation("查看指定日历ID的工作日信息")
    @ApiParamRequest({
            @ApiField(name="calendarId",value="日历ID"),
    })
    public JsonObject getCalendarMonth(String  calendarId){
        CalendarMonth[] calendarMonths = workCalendarService.getCalendarMonth(calendarId);
        return new JsonObject(calendarMonths);
    }
}
