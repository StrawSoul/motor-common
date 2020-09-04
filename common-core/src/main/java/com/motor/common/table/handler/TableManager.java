package com.motor.common.table.handler;

import com.motor.common.table.bean.Column;
import com.motor.common.table.bean.Columns;
import com.motor.common.table.bean.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ===========================================================================================
 * 设计说明
 * -------------------------------------------------------------------------------------------
 * <p>
 * ===========================================================================================
 * 方法简介
 * -------------------------------------------------------------------------------------------
 * {methodName}     ->  {description}
 * ===========================================================================================
 * 变更记录
 * -------------------------------------------------------------------------------------------
 * version: 0.0.0  2020/9/3 10:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class TableManager {
    private final static Logger logger = LoggerFactory.getLogger(TableManager.class);
    private DataSource dataSource;
    private String repository;
    private Map<String,Table> tableCache;

    public TableManager(String repository, DataSource dataSource) {
        this.dataSource = dataSource;
        this.repository = repository;
        this.tableCache = new ConcurrentHashMap<>();
    }

    public Table findTableByName(String tableName) {
        return tableCache.computeIfAbsent(tableName, e-> {
            try {
                return findTableByNameFromDB(e);
            } catch (SQLException ex) {
                logger.error(String.format("获取表%s元数据失败", tableName),ex);
            }
            return null;
        });
    }
    public Table findTableByNameFromDB(String tableName) throws SQLException {
        ResultSet rs = null;
        String columnName = null;
        String columnType = null;
        String tableSchem = null;
        List<Column> columns = new ArrayList<>();
        try (Connection connection =  dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getColumns(null, "%", tableName, "%");
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, "%", tableName);
            List<String> keyNames = new ArrayList<>();

            while (primaryKeys.next()) {
                keyNames.add(primaryKeys.getString("COLUMN_NAME"));
            }
            int i = 0;

            String database = null;
            while (rs.next()) {
                columnName = rs.getString("COLUMN_NAME");
                columnType = rs.getString("TYPE_NAME");
                tableSchem = rs.getString("TABLE_SCHEM");
                if (database == null) {
                    database = tableSchem;
                }
                Column column = new Column(tableSchem, tableName, columnName, columnType, "", i++,
                        (keyNames.contains(columnName) || Objects.equals("ID", columnName.toUpperCase())));
                column.setCode(columnName);
                column.setResourceType("NORMAL");
                columns.add(column);
            }
        }
        Table table = new Table();
        table.setRepository(repository);
        table.setName(tableName);
        table.setSchema(tableSchem);
        table.setColumns(new Columns(columns));
        return table;
    }

}
