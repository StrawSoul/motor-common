package com.motor.common.exception;

public class BusinessRuntimeException extends RuntimeException {

    private ErrorCode code;

    public BusinessRuntimeException(ErrorCode code, String ...args) {
        super(String.format(code.getDesc(), args));
        this.code = code;
    }

    public BusinessRuntimeException(Throwable cause, ErrorCode code, String ...args) {
        super(String.format(code.getDesc(), args), cause);
        this.code = code;
    }


    public ErrorCode getErrorCode() {
        return code;
    }


}
