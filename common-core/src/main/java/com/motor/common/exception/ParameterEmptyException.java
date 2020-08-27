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
 * version: 0.0.0  2020/8/26 11:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class ParameterEmptyException extends BusinessRuntimeException {
    private String paramLabel;

    public ParameterEmptyException(ErrorCode code, String paramLabel, String... args) {
        super(code, args);
        this.paramLabel = paramLabel;
    }

    public ParameterEmptyException(Throwable cause, ErrorCode code, String paramLabel, String... args) {
        super(cause, code, args);
        this.paramLabel = paramLabel;
    }
}
