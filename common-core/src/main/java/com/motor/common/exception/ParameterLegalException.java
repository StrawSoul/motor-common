package com.motor.common.exception;

/**
 * ===========================================================================================
 * 设计说明
 * -------------------------------------------------------------------------------------------
 * <p>
 * ===========================================================================================
 * 方法简介
 * -------------------------------------------------------------------------------------------
 * {methodName}     ->  {description}
 * ===========================================================================================
 * 变更记录
 * -------------------------------------------------------------------------------------------
 * version: 0.0.0  2020/8/26 16:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class ParameterLegalException extends BusinessRuntimeException {

    private String paramLabel;
    private String legalMessage;

    public ParameterLegalException(ErrorCode code, String paramLabel, String legalMessage, String... args) {
        super(code, args);
        this.paramLabel = paramLabel;
        this.legalMessage = legalMessage;
    }

    public ParameterLegalException(Throwable cause, ErrorCode code, String paramLabel, String legalMessage, String... args) {
        super(cause, code, args);
        this.paramLabel = paramLabel;
        this.legalMessage = legalMessage;
    }

    public String getParamLabel() {
        return paramLabel;
    }

    public String getLegalMessage() {
        return legalMessage;
    }
}
