package com.motor.common.dsl.mysql;

import com.motor.common.dsl.bean.*;
import com.motor.common.dsl.handler.AbstractSqlDialect;
import com.motor.common.paging.Paging;
import com.motor.common.table.utils.ColumnUtils;
import com.motor.common.utils.MotorUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.motor.common.table.utils.ColumnUtils.convertHumpToUnderline;

public class MysqlDialect extends AbstractSqlDialect implements DSLDialect {

    public String dialect(){
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String tableName(TableWrapper template) {
        return template.getName();
    }

    @Override
    public String columnName(ColumnWrapper column) {
        TableWrapper template = column.getTemplate();
        return template.getAlias() != null ? (StringUtils.isNotEmpty(template.getName())?ColumnUtils.convertHumpToUnderline(template.getName()) + "." : "") + column.toColumnStr():column.toColumnStr();
    }

    @Override
    public String viewName(ColumnWrapper column) {
        return columnName(column)+" as `"+ alias(column)+"`";
    }

    @Override
    public String views(List<ColumnWrapper> columns) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (ColumnWrapper column : columns) {
            if(i != 0){
                sb.append(",");
            }
            sb.append(viewName(column)).append(" ");
            i++;
        }
        return sb.toString();
    }

    @Override
    public String count(ColumnWrapper column) {
        return "count("+columnName(column)+") as data ";
    }

    @Override
    public String limit(Paging paging) {
        return "limit " + paging.offset() + ","+ paging.getPageSize();
    }

    @Override
    public String orderBy(TableWrapper template, OrderBy orderBY) {
        if(orderBY == null || orderBY.getOrderList() == null){
            return  null;
        }
        List<KeyValueWeight> orderList = orderBY.getOrderList();
        orderList.sort(KeyValueWeight::compareTo);
        StringBuffer sb = new StringBuffer();
        for (KeyValue keyValue : orderList) {
            if (sb.length()> 0) {
                sb.append(",");
            }
            sb.append(columnName((ColumnWrapper)template.findColumn(keyValue.getName()))).append(" ").append(keyValue.getValue());
        }
        return " order by " + sb.toString();
    }
    @Override
    public String alias(ColumnWrapper column) {
        String alias = column.getAlias();
        if(MotorUtils.isEmpty(alias)){
            return  ColumnUtils.convertHump(column.getName());
        } else {
            return alias;
        }
    }
    @Override
    public String aggregate(ColumnWrapper column, String type) {
        return type+"("+ columnName(column)+") as "+ alias(column);
    }

    @Override
    public String insertInto() {
        return "insert into ";
    }

    @Override
    public String update() {
        return "update ";
    }

    @Override
    public String replaceAliasFlag(String content) {
        return content;
    }

    @Override
    public String fullName(ColumnWrapper ColumnWrapper) {
        TableWrapper template = ColumnWrapper.getTemplate();
        return ColumnUtils.convertHumpToUnderline(template.getName() + "." + ColumnWrapper.toColumnStr());
    }
    public String toColumnStr(ColumnWrapper column){
        String columnStr = null;
        if (column.getIndex() == null) {
            columnStr = ("`" + column.getName() + "`").replace(".", "`.`");
        } else {
            columnStr = column.getIndex() >= 0 ? ("`" + column.getName() + "`").replace(".", "`.`") : "'" + column.getName() + "'";
        }
        return ColumnUtils.convertHumpToUnderline(columnStr);
    }
}
