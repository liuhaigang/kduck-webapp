package cn.kduck.module.workday.service.orchestrator.impl;

import cn.kduck.module.workday.service.HolidayDay;
import cn.kduck.module.workday.service.CalendarDay;
import cn.kduck.module.workday.service.orchestrator.HolidayDayOrchestrator;

import java.util.Calendar;

/**
 * 将周六、日设定为休息日
 *
 * ！！线程不安全！！
 * @author LiuHG
 */
public class DefaultHolidayDayOrchestrator implements HolidayDayOrchestrator {

    private Calendar calendar = Calendar.getInstance();

    @Override
    public CalendarDay arrange(int year, int month, int day) {
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY ){
            CalendarDay holidayDay = new CalendarDay(true,year,month,day);
            holidayDay.setHolidayType(HolidayDay.HOLIDAYDAY_TYPE_PUBLIC);
            holidayDay.setHolidayName("公休日");
            return holidayDay;
        }
        return new CalendarDay(false,year,month,day);
    }

}
