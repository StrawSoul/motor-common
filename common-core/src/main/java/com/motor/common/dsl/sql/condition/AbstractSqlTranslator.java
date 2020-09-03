package com.motor.common.dsl.sql.condition;

import com.motor.common.table.bean.Column;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author zlj
 */
public abstract class AbstractSqlTranslator {

    private final static List<String> STRING_TYPES = Arrays.asList("CHAR","VARCHAR","BINARY","VARBINARY","BLOB","TEXT","ENUM","SET");
    private final static List<String> INT_TYPES = Arrays.asList("TINYINT","SMALLINT","MEDIUMINT","INT", "BIGINT");
    private final static List<String> DOUBLE_TYPES = Arrays.asList("FLOAT","DOUBLE");
    private final static List<String> DATE_TYPES = Arrays.asList("DATETIME","DATE","TIMESTAMP","TIME","YEAR");
    private final static List<String> BOOLEAN_TYPES = Arrays.asList("BOOLEAN");
    public String formatValue(Column column, Object value){
        if(value == null){
            return null;
        }
        String type = column.getType();

        type = type == null ? "VARCHAR": type;

        if (STRING_TYPES.indexOf(type.toUpperCase()) >= 0) {
            return "\'"+value.toString()+"\'";
        } else if (INT_TYPES.indexOf(type.toUpperCase()) >= 0) {
            return value.toString();
        } else if (DOUBLE_TYPES.indexOf(type) >= 0) {
            return value.toString();
        } else if (DATE_TYPES.indexOf(type.toUpperCase()) >= 0) {
            return "\'"+value.toString()+"\'";
        } else if (BOOLEAN_TYPES.indexOf(type.toUpperCase()) >= 0) {
            return value.toString();
        }
        return value.toString();
    }

    public String formatListString(Column column, Object valueObj){
        if(valueObj == null){
            return null;
        }
        Object[] values = null;
        if(valueObj instanceof String){
            String valueStr = valueObj.toString();
            if (StringUtils.isBlank(valueStr)) {
                return null;
            }
            values = valueStr.split(",");
        } else if(valueObj instanceof List){
            List  list = (List)valueObj;
            values = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                values[i] = list.get(i).toString();
            }
        }


        StringBuffer stringBuffer = new StringBuffer();
        if(values.length > 0 ){
            for (Object value : values) {
                if(stringBuffer.length()>0){
                    stringBuffer.append(",");
                }
                stringBuffer.append(formatValue(column, value));
            }
        }
        return stringBuffer.toString();
    }
    public String formatColumn(Column column){
        return column.fullName();
    }

}
