package org.jasonxiao.exception;

/**
 * @author Jason Xiao
 */
public class EmployeeNotFoundException extends GenericException {

    public EmployeeNotFoundException() {
        super(ErrorType.EMPLOYEE_NOT_FOUND.getCode(), ErrorType.EMPLOYEE_NOT_FOUND.getMessage());
    }

    public EmployeeNotFoundException(String id) {
        super(ErrorType.EMPLOYEE_NOT_FOUND.getCode(), ErrorType.EMPLOYEE_NOT_FOUND.getMessage() + "[" + id + "]");
    }

}
