package com.motor.common.dsl.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motor.common.table.bean.Column;
import com.motor.common.utils.BeanMapperUtil;


/**
 * @author: zlj
 * @date: 2019-09-17 上午11:48
 * @description:
 */
public class ColumnWrapper extends Column implements Cloneable{

    private TableWrapper template;

    private String calculateType;

    public String getCalculateType() {
        return calculateType;
    }

    public void setCalculateType(String calculateType) {
        this.calculateType = calculateType;
    }

    private Object ref;

    public ColumnWrapper() {
    }

    public ColumnWrapper(String name) {
        this.name = name;
    }

    public ColumnWrapper(String name, String type) {
        super(name,type);
    }

    public ColumnWrapper(String name, String type, String alias) {
        super(name, type, alias);
    }

    public ColumnWrapper(String name, String type, String desc, Integer index) {
       super(name,type,desc, index);
    }

    public ColumnWrapper(String name, String type, String alias, Integer index, boolean primary) {
        super(name, type, alias, index, primary);
    }

    public ColumnWrapper withTemplate(TableWrapper template){
        this.setTemplate(template);
        return this;
    }

    public Object getRef() {
        return ref;
    }

    public void setRef(Object ref) {
        this.ref = ref;
    }

    public Column toColumn(){
        return BeanMapperUtil.map(this,Column.class);
    }
    public static ColumnWrapper fromColumn(Column column){
        if(column == null){
            return null;
        }
        if(column instanceof ColumnWrapper){
            return (ColumnWrapper)column;
        }
        return BeanMapperUtil.map(column, ColumnWrapper.class);
    }

    public ColumnWrapper cloneObj(){
        ColumnWrapper clone = null;
        try {
            clone = (ColumnWrapper) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    @JsonIgnore
    public TableWrapper getTemplate() {
        return template;
    }

    public void setTemplate(TableWrapper template) {
        this.template = template;
    }

    @Override
    public String getAlias() {
        return super.getAlias();
    }

    public String alias() {
        return template.getDialect().alias(this);
    }
    @Override
    public String toColumnStr(){
        return template.getDialect().toColumnStr(this);
    }

    @Override
    public String fullName() {
        return template.getDialect().fullName(this);
    }




}

