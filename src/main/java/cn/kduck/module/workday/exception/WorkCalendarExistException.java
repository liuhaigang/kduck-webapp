package cn.kduck.module.workday.exception;

public class WorkCalendarExistException extends Exception{

    private final int year;
    private final String code;

    public WorkCalendarExistException(String code,int year) {
        this.code = code;
        this.year = year;
    }

    public WorkCalendarExistException(String code,int year,String message) {
        super(message);
        this.code = code;
        this.year = year;
    }

    public WorkCalendarExistException(String code,int year,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.year = year;
    }

    public WorkCalendarExistException(String code,int year,Throwable cause) {
        super(cause);
        this.code = code;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public String getCode() {
        return code;
    }
}
