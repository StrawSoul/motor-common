package com.motor.common.exception;

public class BusinessException extends Throwable{
    private ErrorCode errorCode;
    public BusinessException(ErrorCode errorCode,  String ...args) {
        super(errorCode.format(args));
        this.errorCode =errorCode;
    }
    public BusinessException(Throwable cause, ErrorCode errorCode,  String ...args) {
        super(errorCode.format(args), cause);
        this.errorCode =errorCode;

    }
    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
