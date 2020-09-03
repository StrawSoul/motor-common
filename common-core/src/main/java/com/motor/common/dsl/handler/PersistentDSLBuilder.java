package com.motor.common.dsl.handler;

import com.motor.common.dsl.bean.*;
import com.motor.common.paging.Paging;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface PersistentDSLBuilder {

    DSLDialect getDialect();
    PersistentCommand toQueryAndSort(TableWrapper template, List<ColumnWrapper> columns, Condition condition, OrderBy orderBy);
    PersistentCommand toAggregate(TableWrapper template, ColumnWrapper column, String calculateType, Condition condition);
    PersistentCommand toQuery(TableWrapper template, List<ColumnWrapper> columns, Condition condition);
    PersistentCommand toQuery(TableWrapper template, List<ColumnWrapper> views, Map<String, Object> params);
    PersistentCommand toCount(TableWrapper template, ColumnWrapper columnWrap, Condition condition);
    PersistentCommand toCount(TableWrapper template, Condition condition);
    PersistentCommand toInsert(TableWrapper template, Map<String, Object> params);
    PersistentCommand toUpsert(TableWrapper template, Map<String, Object> params, Condition condition);
    PersistentCommand toUpdate(TableWrapper template, Map<String, Object> params, Condition condition);
    PersistentCommand toUpdate(TableWrapper template, Map<String, Object> params, Map<String, Object> condition);
    PersistentCommand toRemove(TableWrapper template, Condition condition);

    QueryPageCommand toQueryPage(TableWrapper template, List<ColumnWrapper> columns, Map<String, Object> condition, Paging paging);
    QueryPageCommand toQueryPage(TableWrapper template, List<ColumnWrapper> columns, Condition condition, Paging paging);
    QueryPageCommand toQueryPageAndSort(TableWrapper template, List<ColumnWrapper> columns, Condition condition, Paging paging, OrderBy orderBY);
    QueryPageCommand toQueryPageAndSort(TableWrapper template, List<ColumnWrapper> columns, Map<String, Object> condition, Paging paging, OrderBy orderBY);

}
