package org.jasonxiao.exception;

/**
 * @author Jason Xiao
 */
public class GroupNotFoundException extends GenericException {

    public GroupNotFoundException() {
        super(ErrorType.GROUP_NOT_FOUND.getCode(), ErrorType.GROUP_NOT_FOUND.getMessage());
    }

    public GroupNotFoundException(String id) {
        super(ErrorType.GROUP_NOT_FOUND.getCode(), ErrorType.GROUP_NOT_FOUND.getMessage() + "[" + id + "]");
    }

}
