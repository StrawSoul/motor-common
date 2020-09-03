package com.motor.common.dsl.sql.condition;

import com.motor.common.dsl.bean.Condition;
import com.motor.common.dsl.handler.ConditionSpecificationTranslator;

/**
 * @author zlj
 */
public class SqlConditionIsNullTranslator extends AbstractSqlTranslator implements ConditionSpecificationTranslator {
    private String type = " IS NULL ";


    @Override
    public String translate(Condition condition) {
        return formatColumn(condition.getColumn()) + type ;
    }
}
