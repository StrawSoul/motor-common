package com.motor.common.dsl.handler;


import com.motor.common.dsl.bean.Condition;

public interface ConditionSpecificationTranslator extends SpecificationTranslator<Condition>{

    String translate(Condition condition);

}
