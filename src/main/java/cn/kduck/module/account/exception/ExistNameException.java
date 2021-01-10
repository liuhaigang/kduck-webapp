package cn.kduck.module.account.exception;

public class ExistNameException extends RuntimeException{

    public ExistNameException() {
    }

    public ExistNameException(String message) {
        super(message);
    }

    public ExistNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistNameException(Throwable cause) {
        super(cause);
    }
}
