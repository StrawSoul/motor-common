package com.motor.common.dsl.handler;

import com.motor.common.dsl.bean.Condition;

/**
 * @author zlj
 */
public interface ConditionTranslatorHandler {

    /**
     * match for translator
     * @param condition
     * @return
     */
    ConditionSpecificationTranslator match(Condition condition);
}
