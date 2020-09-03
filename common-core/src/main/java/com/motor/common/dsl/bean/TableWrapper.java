package com.motor.common.dsl.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motor.common.table.bean.Column;
import com.motor.common.table.bean.Columns;
import com.motor.common.table.bean.Table;
import com.motor.common.table.utils.ColumnUtils;
import com.motor.common.utils.BeanMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author: zlj
 * @date: 2019-09-17 上午11:48
 * @description:
 */
public class TableWrapper {


    private static final Logger logger = LoggerFactory.getLogger(TableWrapper.class);
    private static final long serialVersionUID = 6219876567228868540L;

    private String name;
    private String alias;
    private String type;
    private String namespace;
    private String repository;
    private String dataSourceId;
    private DSLDialect dialect;
    private String schema;

    private Columns columns;


    public TableWrapper(Table template, DSLDialect dslDialect) {
        this.setRepository(template.getRepository());
        this.schema = template.getSchema();
        this.name = template.getName();
        this.dialect = dslDialect;
        List<ColumnWrapper> list = new ArrayList<>();
        for (Column column : template.getColumns().getList()) {
            ColumnWrapper columnWrap = BeanMapperUtil.map(column, ColumnWrapper.class);
            columnWrap.setTemplate(this);
            list.add(columnWrap);
        }
        this.columns = new Columns(list);

    }

    public TableWrapper() {
    }

    public String getName() {
        return name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Columns getColumns() {
        return columns;
    }
    public List<ColumnWrapper> getColumnWrappers() {
        List<ColumnWrapper> list = columns.getList().stream().map(e -> (ColumnWrapper) e).collect(Collectors.toList());
        return list;
    }

    public void setColumns(Columns columns) {
        this.columns= columns;
    }

    public void each(Consumer<Column> visitor){
        columns.forEach(visitor);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ColumnWrapper> allColumns() {

        List<ColumnWrapper> columnWraps = new ArrayList<>();

        this.each((Column e) ->{

            ColumnWrapper columnWrap = (ColumnWrapper)e;

            columnWraps.add(columnWrap);

            if(columnWrap.getRef() != null){

                if(columnWrap.getRef() instanceof TableWrapper){

                    columnWraps.addAll(((TableWrapper) columnWrap.getRef()).allColumns());
                } else {
                    logger.error("未处理的列类型　{}", e.getClass());
                }
            }

        });
        return columnWraps;
    }

    public ColumnWrapper findPrimaryColumn() {
        return (ColumnWrapper)columns.primaryColumn();
    }

    public Column findColumn(String s) {
        /**
         * 按层级匹配字段
         */
        List<TableWrapper> temp = new ArrayList<>();
        for (Column c : columns.getList()) {
            ColumnWrapper column = (ColumnWrapper)c;
            column.autoAlias(true);
            String alias = column.alias();
            if(Objects.equals(s,column.getCode())
                    || Objects.equals(ColumnUtils.convertHumpToUnderline(s), column.getName())
                    || Objects.equals(s, alias)){
                column.autoAlias(false);
                return column.cloneObj();
            }
            Object ref = column.getRef();
            if (ref != null && ref instanceof TableWrapper) {
                temp.add((TableWrapper)ref);
            }
        }
        for (TableWrapper wrap : temp) {
            Column column = wrap.findColumn(s);
            if(column != null){
                return column;
            }
        }
        return null;
    }
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @JsonIgnore
    public DSLDialect getDialect() {
        return dialect;
    }

    public void setDialect(DSLDialect dialect) {
        this.dialect = dialect;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

