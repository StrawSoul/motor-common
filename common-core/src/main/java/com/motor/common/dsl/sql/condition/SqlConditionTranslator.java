package com.motor.common.dsl.sql.condition;


import com.motor.common.dsl.bean.Condition;
import com.motor.common.dsl.handler.ConditionSpecificationTranslator;
import com.motor.common.dsl.handler.ConditionTranslatorHandler;

import java.util.Collection;

/**
 * @author zlj
 */
public class SqlConditionTranslator implements ConditionSpecificationTranslator {

    private ConditionTranslatorHandler handler;

    public SqlConditionTranslator(ConditionTranslatorHandler handler) {
        this.handler = handler;
    }

    public SqlConditionTranslator() {
        this.handler = new SqlConditionTranslatorHandler();
    }

    private final static String AND = " and ";
    private final static String OR = " or ";
    private final static String PREV = " ( ";
    private final static String END = " ) ";
    private final static String TYPE = " ";
    @Override
    public String translate(Condition condition) {
        if(condition == null){
            return null;
        }
        return TYPE + createCondition(condition).toString();
    }

    private StringBuffer createCondition(Condition condition){
        StringBuffer cond = new StringBuffer();
        /**
         *  orList 不为空, 则以or解析开始, 这里的判断是以什么开始,不是只解析orList或andList,
         *  or()方法里会解析andList,
         *  and()方法里也会解析其元素的orList
         */
        if(translateOrListFirst(condition)){
            cond.append(or(condition));
            cond = pack(cond);
        }else if(translateAndListFirst(condition)){
            cond.append(and(condition));
            cond = pack(cond);
        }else{
            cond.append(doTranslate(condition)) ;
        }
        return cond;
    }

    private boolean translateOrListFirst(Condition condition){
        return condition.getOrList().size()>0;
    }
    private boolean translateAndListFirst(Condition condition){
        return condition.getAndList().size()>0;
    }


    private StringBuffer and(Condition condition) {
        StringBuffer sb = new StringBuffer();
        Collection<Condition> andList = condition.getAndList();

        sb.append(doTranslate(condition));
        for (Condition andCondi : andList) {
            sb.append(AND).append(createCondition(andCondi));
        }
        return sb;
    }
    private StringBuffer or(Condition condition) {
        StringBuffer sb = new StringBuffer();
        Collection<Condition> orList = condition.getOrList();
        if(condition.getAndList().size()>0){
            sb.append(PREV);
        }
        sb.append(and(condition));
        if(condition.getAndList().size()>0){
            sb.append(END);
        }
        for (Condition andCondi : orList) {
            sb.append(OR).append(createCondition(andCondi));
        }

        return sb;
    }

    String doTranslate(Condition condition){
        return handler
                .match(condition)
                .translate(condition);
    }

    private StringBuffer pack(StringBuffer str){
        return new StringBuffer("(").append(str).append(")");
    }
}
