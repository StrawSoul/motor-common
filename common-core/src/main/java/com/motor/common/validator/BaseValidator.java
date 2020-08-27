package com.motor.common.validator;

import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.exception.ErrorCode;
import com.motor.common.exception.ParameterEmptyException;
import com.motor.common.utils.MotorUtils;

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
public class BaseValidator {
    private final static ErrorCode UNKNOWN_ERROR = new ErrorCode("BASE00000","UNKNOWN_ERROR", "未知异常,可能原因:%s");
    private final static ErrorCode PARAM_EMPTY_ERROR = new ErrorCode("BASE00001","PARAM_EMPTY_ERROR", "参数[%s]为空");

    private static volatile BaseValidator baseValidator;

    private BaseValidator() {
    }

    public void unknownError(String msg){
        throw new BusinessRuntimeException(UNKNOWN_ERROR, msg);
    }
    public void isEmpty(String key,Object value){
        if(MotorUtils.isNull(value)){
            throw new ParameterEmptyException(PARAM_EMPTY_ERROR, key, key);
        }
    }

    public static synchronized BaseValidator getInstance(){
        if(baseValidator == null){
            synchronized (BaseValidator.class){
                baseValidator = new BaseValidator();
            }
        }
        return baseValidator;
    }

}
