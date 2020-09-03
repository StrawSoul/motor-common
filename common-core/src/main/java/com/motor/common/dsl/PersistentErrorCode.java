package com.motor.common.dsl;

import com.motor.common.exception.ErrorCode;

/**
 * @author: zlj
 * @date: 2019-05-15 下午10:40
 * @description:
 */
public class PersistentErrorCode {

    public final static ErrorCode NOT_SUPPORT_SQL_TYPE = new ErrorCode("PERS00001","NOT_SUPPORT_SQL_TYPE", "不支持的sql类型");
    public final static ErrorCode NOT_SUPPORT_RESOURCE_TYPE = new ErrorCode("PERS00002","NOT_SUPPORT_RESOURCE_TYPE", "不支持的资源类型 [%s]");
    public final static ErrorCode PARAMETER_LOST = new ErrorCode("PERS00003","PARAMETER_LOST", "参数 [%s]不能为空");
    public final static ErrorCode PROJECT_NOT_FOUND = new ErrorCode("PERS00004","PROJECT_NOT_FOUND", "ProjectId => [%s] Not Found !!!");
    public final static ErrorCode SQL_NOT_FOUND_IN_PROJECT = new ErrorCode("PERS00005","SQL_NOT_FOUND_IN_PROJECT", "Sql => [%s] Not Found In Project[%s]!!!");
    public final static ErrorCode DATA_NOT_FOUND = new ErrorCode("PERS00006","DATA_NOT_FOUND", "DataNotFound => [%s] Not Found In ResourceTemplate[%s]!!!");
    public final static ErrorCode COLUMN_NOT_FOUND = new ErrorCode("PERS00007","COLUMN_NOT_FOUND", "ColumnNotFound => [%s] Not Found In ResourceTemplate[%s]!!!");
    public final static ErrorCode PARAM_NAME_IS_BLANK = new ErrorCode("PERS00008","PARAM_NAME_IS_BLANK", "Column[%s] Could Not Be Blank !!!");
    public final static ErrorCode TABLE_NOT_FOUND = new ErrorCode("PERS00009","TABLE_NOT_FOUND", "TableNotFound => Table [%s] Not Found In Project[%s]!!!");
    public final static ErrorCode SQL_EXECUTE_ERROR = new ErrorCode("PERS00010","SQL_EXECUTE_ERROR", "SQL execute error : [%s]");
    public final static ErrorCode PARAMS_VALIDATE_ERROR = new ErrorCode("PERS00011","PARAMS_VALIDATE_ERROR", "%s");

}
