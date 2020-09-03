package com.motor.common.dsl.bean;


import com.motor.common.paging.Paging;
import com.motor.common.table.bean.Column;

import java.io.Serializable;
import java.util.List;


public interface DSLDialect extends Cloneable, Serializable {
    String dialect();
    String tableName(TableWrapper template);
    String columnName(ColumnWrapper column);
    String viewName(ColumnWrapper column);
    String views(List<ColumnWrapper> columns);
    String count(ColumnWrapper column);
    String limit(Paging paging);
    String orderBy(TableWrapper template, OrderBy orderBY);
    String alias(ColumnWrapper column);
    String aggregate(ColumnWrapper columnWrap, String aggregate);
    String formatValue(Column column, Object value);
    String insertInto();
    String update();
    String replaceAliasFlag(String content);
    String fullName(ColumnWrapper columnWrapper);
    String toColumnStr(ColumnWrapper column);

    boolean isString(String type);
    boolean isInt(String type);
    boolean isDouble(String type);
    boolean isDate(String type);
    boolean isBit(String type);
    boolean isBoolean(String type);
    boolean isNumber(String type);
}
