package org.jasonxiao.exception;

/**
 * @author Jason Xiao
 */
public enum ErrorType {

    UNKNOWN_ERROR(9999, "Unknown Error"),

    EMPLOYEE_NOT_FOUND(1002, "Employee not found");

    private int code;
    private String message;

    ErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
