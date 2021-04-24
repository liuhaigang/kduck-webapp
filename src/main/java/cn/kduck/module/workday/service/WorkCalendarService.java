package cn.kduck.module.workday.service;

import cn.kduck.module.workday.exception.WorkCalendarExistException;
import cn.kduck.module.workday.service.orchestrator.HolidayDayOrchestrator;

import java.util.Date;
import java.util.List;

/**
 * 注意：月份从1开始，日从1开始
 * @author LiuHG
 */
public interface WorkCalendarService {

    String CODE_WORK_CALENDAR = "K_WORK_CALENDAR";
    String CODE_HOLIDAY_DAY = "K_HOLIDAY_DAY";

    /**
     * 添加工作日历
     * @param calendar 工作日历对象
     */
    void addWorkCalendar(WorkCalendar calendar) throws WorkCalendarExistException;

    /**
     * 工作日历修改，只能修改名称和描述。且不会影响已经生成的休息日信息
     * @param calendar
     */
    void updateWorkCalendar(WorkCalendar calendar);

    void deleteWorkCalendar(String[] ids);

    WorkCalendar getWorkCalendar(String calendarId);

    List<WorkCalendar> listWorkCalendar();

    WorkCalendar getWorkCalendar(String code,int year);

    boolean existWorkCalendar(String code,int year);


    /**
     * 获取指定日历下的工作日信息，即全年
     * @param calendarId 日历Id
     * @return 全年每个月的工作日信息，数组顺序为月份顺序
     */
    CalendarMonth[] getCalendarMonths(String calendarId);

    CalendarMonth[] getCalendarMonths(String calendarCode, int year);

    /**
     * 获取指定日历下的某个月的工作日信息
     * @param calendarId 日历Id
     * @param month 月份
     * @return 指定月份的工作日信息
     */
    CalendarDay[] getCalendarMonth(String calendarId,int month);

    /**
     * 根据日历编码和年份查询指定月份下的工作日信息
     * @param calendarCode
     * @param year
     * @param month
     * @return
     */
    CalendarDay[] getCalendarMonth(String calendarCode,int year,int month);

    /**
     * 指定工作日的月、日是否为工作日，因为对于同一年可能存在多组工作日配置，因此这里不能依靠年份来决定工作日。
     * @param calendarId 日历Id
     * @param month 月份
     * @param day 日
     * @return true为工作日，false为休息日
     */
    boolean isWorkDay(String calendarId,int month, int day);

    /**
     * 设置指定日期为工作日
     * @param calendarId 日历Id
     * @param date 休息日Id
     */
    void setWorkDay(String calendarId,Date[] date);

    /**
     * 根据休息日Id设置为工作日
     * @param holidayIds 休息日Id
     */
    void setWorkDay(String[] holidayIds);

    /**
     * 设置休息日
     * @param calendarId
     * @param holidayDays
     */
    void setHolidayDay(String calendarId,HolidayDay[] holidayDays);

    /**
     * 初始化指定日历的工作日信息，此方法会覆盖目前所有工作日的配置，恢复为初始状态
     * @param calendarId 日历Id
     * @param orchestrator 休息日编排器
     */
    void initWorkCalendar(String calendarId, HolidayDayOrchestrator orchestrator);

    /**
     * 日历复制，被复制的日历与新的日历年份会保持一致，即使在targetCalendar中设置年份也会无效
     * @param sourceCalendarId
     * @param targetCalendar
     */
    void cloneWorkCalendar(String sourceCalendarId,WorkCalendar targetCalendar);


    /**
     * 获取指定日期后days天之后的工作日，包含当前天，例如有以下日历：
     * <hr>
     * <table width=300>
     *     <tr>
     *       <td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td>
     *     </tr>
     *     <tr>
     *       <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>1</td><td>2</td><td>3</td>
     *     </tr>
     *     <tr>
     *       <td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td><td>10</td>
     *     </tr>
     *     <tr>
     *       <td>11</td><td>12</td><td>13</td><td>14</td><td>15</td><td>16</td><td>17</td>
     *     </tr>
     *     <tr>
     *       <td>18</td><td>19</td><td>20</td><td>21</td><td>22</td><td>23</td><td>24</td>
     *     </tr>
     *     <tr>
     *       <td>25</td><td>26</td><td>27</td><td>28</td><td>29</td><td>30</td><td>&nbsp;</td>
     *     </tr>
     * </table>
     * <hr>
     * <p>
     * 示例：
     * <p>
     * 起始日期：4日，跨天数：2 ---- 结果为7日<p>
     * 起始日期：7日，跨天数：2 ---- 结果为9日<p>
     * 起始日期：9日，跨天数：2 ---- 结果为13日<p>
     * 起始日期：10日，跨天数：2 ---- 结果为14日<p>
     * @param calendarCode
     * @param date 起始日期
     * @param days 跨天数
     * @return
     */
    Date getAfterWorkDay(String calendarCode,Date date,int days);

    /**
     * 
     * @param calendarCode
     * @param date 起始日期
     * @param days 跨天数
     * @return
     * @see #getAfterWorkDay(String, java.util.Date, int)
     */
    Date getBeforeWorkDay(String calendarCode,Date date,int days);

    /**
     *
     * @param calendarCode
     * @param date
     * @return
     */
    boolean isWorkDay(String calendarCode,Date date);

    HolidayDay getHolidayDay(String holidayId);

    void updateHolidayDay(HolidayDay holidayDay);

    void addHolidayDay(HolidayDay holidayDay);
}
