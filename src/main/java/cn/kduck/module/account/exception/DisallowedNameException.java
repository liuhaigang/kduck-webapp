package cn.kduck.module.account.exception;

public class DisallowedNameException extends RuntimeException{

    public DisallowedNameException() {
    }

    public DisallowedNameException(String message) {
        super(message);
    }

    public DisallowedNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisallowedNameException(Throwable cause) {
        super(cause);
    }
}
