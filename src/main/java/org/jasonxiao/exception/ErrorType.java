package org.jasonxiao.exception;

/**
 * @author Jason Xiao
 */
public enum ErrorType {

    UNKNOWN_ERROR(9999, "Unknown Error"),

    /**
     * Employee related errors (1001 ~ 1999)
     */
    EMPLOYEE_NOT_FOUND(1001, "Employee not found"),

    /**
     * Group related errors (2001 ~ 2999)
     */
    GROUP_NOT_FOUND(2001, "Group not found");

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
