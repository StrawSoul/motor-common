package com.motor.common.dsl.sql.condition;


import com.motor.common.dsl.bean.Condition;
import com.motor.common.dsl.handler.ConditionSpecificationTranslator;
import com.motor.common.dsl.handler.ConditionTranslatorHandler;
import com.motor.common.table.constants.ConditionType;

import java.util.HashMap;
import java.util.Map;

import static com.motor.common.table.constants.ConditionType.*;


/**
 * @author zlj
 */
public class SqlConditionTranslatorHandler implements ConditionTranslatorHandler {

    private Map<ConditionType, ConditionSpecificationTranslator> map;
    public SqlConditionTranslatorHandler(){
        map = new HashMap<>();
        map.put(is, new SimpleSqlConditionTranslator("="));
        map.put(not, new SimpleSqlConditionTranslator("!="));
        map.put(notnull, new SqlConditionNotNullTranslator());
        map.put(isnull, new SqlConditionIsNullTranslator());
        map.put(gt, new SimpleSqlConditionTranslator(">"));
        map.put(lt, new SimpleSqlConditionTranslator("<"));
        map.put(lte, new SimpleSqlConditionTranslator("<="));
        map.put(gte, new SimpleSqlConditionTranslator(">="));
        map.put(in, new SqlConditionInTranslator());
        map.put(notin, new SqlConditionNotInTranslator());
        map.put(like, new SqlConditionLikeTranslator());
    }
    public SqlConditionTranslatorHandler(Map<ConditionType, ConditionSpecificationTranslator> map){
        this.map = map;
    }

    @Override
    public ConditionSpecificationTranslator match(Condition condition){
        return map.get(condition.getType());
    }

}
