package cn.kduck.module.workday.service.orchestrator;

import cn.kduck.module.workday.service.CalendarDay;

/**
 * 休息日编排器，用于初始化每年的休息日
 */
public interface HolidayDayOrchestrator {

    CalendarDay arrange(int year, int month, int day);
}
