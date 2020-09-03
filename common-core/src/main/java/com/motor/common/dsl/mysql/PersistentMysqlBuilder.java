package com.motor.common.dsl.mysql;



import com.motor.common.dsl.ConditionUtils;
import com.motor.common.dsl.bean.*;
import com.motor.common.dsl.handler.PersistentDSLBuilder;
import com.motor.common.dsl.sql.SimpleSqlBuilder;
import com.motor.common.dsl.sql.condition.SqlConditionTranslator;
import com.motor.common.paging.Paging;
import com.motor.common.table.bean.Column;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class PersistentMysqlBuilder implements PersistentDSLBuilder {

    static {
        ConditionUtils.register("mysql", new SqlConditionTranslator());
    }

    private DSLDialect dslDialect = new MysqlDialect();

    public DSLDialect getDialect() {
        return dslDialect;
    }

    public PersistentCommand toQueryAndSort(TableWrapper template, List<ColumnWrapper> columns, Condition condition, OrderBy orderBy){
        PersistentCommand cmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .views(columns)
                .where(condition)
                .sorted(orderBy)
                .build();
        return cmd;
    }
    public PersistentCommand toAggregate(TableWrapper template, ColumnWrapper column, String calculateType, Condition condition){
        Column deleted = template.findColumn("DELETED");
        if(deleted != null){
            Condition delCondition = Condition.is(deleted, 0);
            condition = condition == null ? delCondition : delCondition.and(condition);
        }
        PersistentCommand cmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .views(dslDialect.aggregate(column,calculateType))
                .where(condition)
                .build();
        return cmd;
    }
    public PersistentCommand toQuery(TableWrapper template, List<ColumnWrapper> columns, Condition condition){
        Column deleted = template.findColumn("DELETED");
        if(deleted != null){
            Condition delCondition = Condition.is(deleted, 0);
            condition = condition == null ? delCondition : delCondition.and(condition);
        }

        PersistentCommand cmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .views(dslDialect.views(columns))
                .where(condition)
                .build();
        return cmd;
    }
    public PersistentCommand toQuery(TableWrapper template, List<ColumnWrapper> views, Map<String,Object> params){
        Condition condition = ConditionUtils.fromMap(params, template.getColumns());
        return toQuery(template, views, condition);
    }
    public PersistentCommand toCount(TableWrapper template, ColumnWrapper columnWrap, Condition condition){
        PersistentCommand cmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .views(dslDialect.count(columnWrap))
                .where(condition)
                .build();
        return cmd;
    }
    public PersistentCommand toCount( TableWrapper template, Condition condition){
        ColumnWrapper primaryColumn = template.findPrimaryColumn();
        return toCount(template, primaryColumn, condition);
    }

    public QueryPageCommand toQueryPage(TableWrapper template, List<ColumnWrapper> columns, Condition condition, Paging paging){
        Column deleted = template.findColumn("DELETED");
        if(deleted != null){
            Condition delCondition = Condition.is(deleted, 0);
            condition = condition == null ? delCondition : delCondition.and(condition);
        }
        PersistentCommand countCmd = toCount(template, template.findPrimaryColumn(), condition);
        PersistentCommand queryCmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .views(columns)
                .where(condition)
                .limit(paging)
                .build();
        return new QueryPageCommand(queryCmd, countCmd);
    }

    public QueryPageCommand toQueryPageAndSort(TableWrapper template, List<ColumnWrapper> columns, Condition condition, Paging paging, OrderBy orderBY){
        Column deleted = template.findColumn("DELETED");
        if(deleted != null){
            Condition delCondition = Condition.is(deleted, 0);
            condition = condition == null ? delCondition : delCondition.and(condition);
        }
        PersistentCommand countCmd = toCount(template, template.findPrimaryColumn(), condition);
        PersistentCommand queryCmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .views(columns)
                .where(condition)
                .sorted(orderBY)
                .limit(paging)
                .build();
        return new QueryPageCommand(queryCmd, countCmd);
    }



    public PersistentCommand toInsert(TableWrapper template, Map<String,Object> params){
        PersistentCommand cmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .insert(params)
                .build();
        return cmd;
    }
    public PersistentCommand toUpsert(TableWrapper template, Map<String,Object> params, Condition condition){
        PersistentCommand cmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .update(params)
                .where(condition)
                .build();
        return cmd;
    }
    public PersistentCommand toUpdate(TableWrapper template, Map<String,Object> params, Condition condition){
        PersistentCommand cmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .update(params)
                .where(condition)
                .build();
        return cmd;
    }

    @Override
    public PersistentCommand toUpdate(TableWrapper template, Map<String, Object> params, Map<String, Object> conditionMap) {
        Condition condition = ConditionUtils.fromMap(conditionMap, template.getColumns());
        return toUpdate(template, params, condition);
    }

    public PersistentCommand toRemove(TableWrapper template, Condition condition){
        PersistentCommand cmd = SimpleSqlBuilder.sql(dslDialect)
                .from(template)
                .remove()
                .where(condition)
                .build();
        return cmd;
    }

    @Override
    public QueryPageCommand toQueryPage(TableWrapper template, List<ColumnWrapper> columns, Map<String, Object> condition, Paging paging) {
        return toQueryPage(template, columns, ConditionUtils.fromMap(condition, template.getColumns()), paging);
    }
    @Override
    public QueryPageCommand toQueryPageAndSort(TableWrapper template, List<ColumnWrapper> columns, Map<String, Object> condition, Paging paging, OrderBy orderBY) {
        return toQueryPageAndSort(template, columns, ConditionUtils.fromMap(condition, template.getColumns()), paging, orderBY);
    }

}
