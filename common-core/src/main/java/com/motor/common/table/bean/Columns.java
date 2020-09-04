package com.motor.common.table.bean;

import com.motor.common.utils.M;

import java.util.*;
import java.util.function.Consumer;

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
public class Columns {

    private Collection<Column> columns;
    private Map<Integer,Column> indexMap;
    private Map<String,Column> nameMap;
    private Map<String,Column> aliasMap;
    private Map<String,Column> fullNameMap;
    private List<Column> primaryColumns;

    public Columns(Collection<? extends Column> columns) {
        this.columns = new ArrayList<>(columns);
        if(M.isEmpty(columns)){
            return;
        }
        int size = columns.size();
        indexMap = new HashMap<>(size);
        nameMap = new HashMap<>(size);
        aliasMap = new HashMap<>(size);
        fullNameMap = new HashMap<>(size);
        primaryColumns = new ArrayList<>(1);

        for (Column column : columns) {
            if(column.isPrimary()|| (column.getName()!= null && Objects.equals(column.getName().toLowerCase() , "id"))){
                primaryColumns.add(column);
            }
            indexMap.put(column.getIndex(), column);
            nameMap.put(column.getName(), column);
            aliasMap.put(column.getAlias(), column);
            fullNameMap.put(column.fullName(), column);
        }
    }
    public Column findColumn(String name){
        Column column = nameMap.getOrDefault(name, aliasMap.getOrDefault(name, fullNameMap.get(name)));
        return column;
    }

    public List<Column> primaryColumns(){
        return primaryColumns;
    }

    public Column primaryColumn() {
        return primaryColumns.stream().findFirst().orElse(null);
    }

    public Collection<Column> getList() {
        return columns;
    }

    public void forEach(Consumer<Column> visitor) {
        this.columns.forEach(visitor);
    }
}
