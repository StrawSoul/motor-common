package com.motor.common.dsl.handler;

import com.motor.common.table.bean.Column;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractSqlDialect {
    protected static final List<String> STRING_TYPES = Arrays.asList("CHAR", "VARCHAR", "BINARY", "VARBINARY", "BLOB", "TEXT", "ENUM", "SET");
    protected static final List<String> INT_TYPES = Arrays.asList("TINYINT", "SMALLINT", "MEDIUMINT", "INT", "BIGINT");
    protected static final List<String> DOUBLE_TYPES = Arrays.asList("FLOAT", "DOUBLE");
    protected static final List<String> DATE_TYPES = Arrays.asList("DATETIME", "DATE", "TIMESTAMP", "TIME", "YEAR");
    protected static final List<String> BIT_TYPES = Arrays.asList("BIT");
    protected static final List<String> BOOLEAN_TYPES = Arrays.asList("BOOLEAN");
    public String formatValue(Column column, Object value) {
        if (value == null) {
            return null;
        } else {
            String type = column.getType();
            if(value instanceof Boolean){
                type = "BOOLEAN";
            }
            type = type == null ? "VARCHAR" : type;
            if (isString(type)) {
                return "'" + value.toString() + "'";
            } else if (isInt(type)) {
                return value.toString();
            } else if (isDouble(type)) {
                return value.toString();
            } else if (isDate(type)) {
                return "'" + value.toString() + "'";
            } else if (isBit(type) ){
                return value.toString();
            } else if (isBoolean(type) ){
                return ((Boolean)value)? "1": "0";
            } else {
                return value.toString();
            }
        }
    }
    public boolean isString(String type){
        return STRING_TYPES.indexOf(type.toUpperCase()) >= 0;
    }
    public boolean isInt(String type){
        return INT_TYPES.indexOf(type.toUpperCase()) >= 0;
    }
    public boolean isDouble(String type){
        return DOUBLE_TYPES.indexOf(type.toUpperCase()) >= 0;
    }
    public boolean isDate(String type){
        return DATE_TYPES.indexOf(type.toUpperCase()) >= 0;
    }
    public boolean isBit(String type){
        return BIT_TYPES.indexOf(type.toUpperCase()) >= 0;
    }
    public boolean isBoolean(String type){
        return BOOLEAN_TYPES.indexOf(type.toUpperCase()) >= 0;
    }
    public boolean isNumber(String type){
        return isInt(type)
                || isDouble(type);
    }
}
