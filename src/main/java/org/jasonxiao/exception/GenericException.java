package org.jasonxiao.exception;

/**
 * @author Jason Xiao
 */
public class GenericException extends Exception {

    protected int code = ErrorType.UNKNOWN_ERROR.getCode();
    protected String message = ErrorType.UNKNOWN_ERROR.getMessage();

    public GenericException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
