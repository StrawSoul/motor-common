package com.motor.common.dsl.sql.condition;


import com.motor.common.dsl.bean.Condition;
import com.motor.common.dsl.handler.ConditionSpecificationTranslator;

/**
 * @author zlj
 */
public class SqlConditionInTranslator extends AbstractSqlTranslator  implements ConditionSpecificationTranslator {
    private String type = " in ";
    @Override
    public String translate(Condition condition) {
        return formatColumn(condition.getColumn()) + type +" ("+ formatListString(condition.getColumn(), condition.getValue())+") ";
    }
}
