package com.motor.common.dsl;

import com.motor.common.dsl.bean.ColumnWrapper;
import com.motor.common.dsl.bean.Condition;
import com.motor.common.dsl.bean.DSLTemplate;
import com.motor.common.dsl.handler.ConditionSpecificationTranslator;
import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.table.bean.Column;
import com.motor.common.table.bean.Columns;
import com.motor.common.table.constants.ConditionType;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.motor.common.dsl.bean.Condition.is;
import static com.motor.common.table.utils.ColumnUtils.convertHumpToUnderline;


/**
 * @author: zlj
 * @date: 2019-05-17 上午11:40
 * @description:
 */
public class ConditionUtils {

    private static final String SPLIT_FLAG = "!";

    private static Map<String, ConditionSpecificationTranslator> translatorMap = new HashMap<>(4);

    public static void register(String dialect, ConditionSpecificationTranslator translator){
        translatorMap.put(dialect, translator);
    }
    public static DSLTemplate translate(String dialect, Condition condition){
        ConditionSpecificationTranslator translator = translatorMap.get(dialect);
        if(translator == null){
            return translate(condition);
        }
        return translate(translator, condition);
    }
    public static DSLTemplate translate(Condition condition){
        return translate(translatorMap.get("mysql"), condition);
    }
    public static DSLTemplate translate(ConditionSpecificationTranslator translator, Condition condition){
        condition = condition == null ? notDeleted(): condition;
        String sql = translator.translate(condition);
        DSLTemplate body = new DSLTemplate(sql, Collections.EMPTY_MAP);
        return body;
    }
    public static Condition fromKeyValue(String k, Object v, Columns entity){
        Condition temp = null;
        if (StringUtils.isBlank(k)) {
            throw new BusinessRuntimeException(PersistentErrorCode.PARAM_NAME_IS_BLANK, k);
        }
        if(Objects.equals(k, "$and")){
            return conditionAnd(v,entity);
        }
        if(Objects.equals(k, "$or")){
            return conditionOr(v,entity);
        }
        String[] keyType = k.split(":");
        String key = keyType[0];
        String[] arr = key.split(SPLIT_FLAG);
        String name = convertHumpToUnderline(arr[0]);
        Column column = null;
        try {
            column = entity.findColumn(name);
        } catch (Exception e){
            //do nothing
        }
        if(column == null){
            column = new Column(arr[0],keyType.length<=1? "varchar": keyType[1]);
        }
        if(arr.length == 1){
            // 默认  is  and
            temp = is(column, v);
        } else if(arr.length == 2){
            // 默认  and
            temp = Condition.instance(column, ConditionType.valueOf(arr[1]), v);
        } else if(arr.length >= 3){
            String s = arr[2];
            ColumnWrapper newColumn = ColumnWrapper.fromColumn(column);
            if (keyType.length>1) {
                newColumn.setType(keyType[1]);
            }
            temp = Condition.instance(newColumn, ConditionType.valueOf(arr[1]), v);
        }
        return  temp;

    }
    public static Condition fromMap( Map<String, Object> params, Columns entity){
        Condition condition = null;
        String k = null;
        Object v = null;

        Object orValue = params.get("$or");
        Object andValue = params.get("$and");
        Condition temp = null;
        params = new TreeMap(params);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            k = entry.getKey();
            v = entry.getValue();
            if(Objects.equals(k,"$and") || Objects.equals(k,"$or")){
                continue;
            }
            temp = fromKeyValue(k,v, entity);
            if(condition == null ){
                condition = temp;
            }else{
                condition.and(temp);
            }
        }

        Condition conditionAnd = null;
        Condition conditionOr = null;
        if(orValue != null ){
            conditionOr = conditionOr(orValue, entity);
        }

        if (andValue != null ){
            conditionAnd = conditionAnd(andValue, entity);
        }

        if(condition == null){
            if(conditionAnd != null){
                condition = conditionAnd;
                conditionAnd = null;
            } else if(conditionOr != null){
                condition = conditionOr;
                conditionOr = null;
            }else{
                return null;
            }
        }
        if(conditionAnd != null){
            condition.and(conditionAnd);
        }
        if(conditionOr != null){
            condition.and(conditionOr);
        }
        return condition;
    }

    private static Condition conditionOr(Object orValue, Columns entity){
        Condition conditionOr = null;
        Condition temp = null;
        if(orValue instanceof Map){
            Map<String,Object> map = (Map<String,Object>)orValue;
            if(map.size()>0){
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    temp = fromKeyValue(entry.getKey(), entry.getValue(), entity);
                    conditionOr = conditionOr == null ? temp: conditionOr.or(temp);
                }
            }
        } else if(orValue instanceof List){
            List<Map<String,Object>> list = (List<Map<String,Object>>)orValue;
            for (int i = 0; i < list.size(); i++) {
                temp = fromMap(list.get(i), entity);
                conditionOr = conditionOr == null ? temp: conditionOr.or(temp);
            }
        }
        return conditionOr;
    }
    private static Condition conditionAnd(Object andValue, Columns entity){
        Condition temp = null;
        Condition conditionAnd = null;
        if(andValue instanceof Map){
            Map<String,Object> map = (Map<String,Object>)andValue;
            if(map.size()>0){
                temp = fromMap(map, entity);
                conditionAnd = conditionAnd == null ? temp: conditionAnd.and(temp);
            }
        } else if(andValue instanceof List){
            List<Map<String,Object>> list = (List<Map<String,Object>>)andValue;
            for (int i = 0; i < list.size(); i++) {
                temp = fromMap(list.get(i), entity);
                conditionAnd = conditionAnd == null ? temp: conditionAnd.and(temp);
            }
        }
        return conditionAnd;
    }

    public static Condition notDeleted(){
        return is(new Column("deleted"), 0);
    }
}
