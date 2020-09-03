package com.motor.common.dsl.sql.condition;

import com.motor.common.dsl.bean.Condition;
import com.motor.common.dsl.handler.ConditionSpecificationTranslator;

/**
 * @author zlj
 */
public class SimpleSqlConditionTranslator extends AbstractSqlTranslator implements ConditionSpecificationTranslator {
    private String type;

    public SimpleSqlConditionTranslator(String type) {
        this.type = " "+type+" ";
    }

    @Override
    public String translate(Condition condition) {
        if(condition.getColumn() != null){
            return formatColumn(condition.getColumn()) + type +formatValue(condition.getColumn(), condition.getValue());
        }
        return "";
    }
}
