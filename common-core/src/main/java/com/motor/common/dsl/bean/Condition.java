package com.motor.common.dsl.bean;



import com.motor.common.table.bean.Column;
import com.motor.common.table.constants.ConditionType;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author zlj
 */
public class Condition implements Serializable{

    private static final long serialVersionUID = 7243532602369957599L;
    private Column column;

    private ConditionType type;

    private Object value;

    private Collection<Condition> andList;
    private Collection<Condition> orList;

    /**
     *   json 反序列化,必须提供无参构造函数, 所以不能禁用此构造器
     *   如果程序中直接使用此构造器而不赋值的话,会造成空指针异常
     */
    @Deprecated
    public Condition() {
        this.andList = new ArrayList<>();
        this.orList = new ArrayList<>();
    }

    public Condition(Column column, ConditionType type, Object value) {
        this.column = column;
        this.type = type;
        this.value = value;
        this.andList = new ArrayList<>();
        this.orList = new ArrayList<>();
    }

    public static Condition instance(Column column, ConditionType type, Object value) {
        return new Condition(column, type, value);
    }

    public static Condition is(Column column, Object value) {
        return instance(column, ConditionType.is, value);
    }

    public static Condition not(Column column, Object value) {
        return instance(column, ConditionType.not, value);
    }

    public static Condition like(Column column, String value) {
        return instance(column, ConditionType.like, value);
    }
    public static Condition gte(Column column, Object value) {
        return instance(column, ConditionType.gte, value);
    }

    public static Condition gt(Column column, Object value) {
        return instance(column, ConditionType.gt, value);
    }
    public static Condition in(Column column, Object value) {
        return instance(column, ConditionType.in, value);
    }
    public static Condition notin(Column column, Object value) {
        return instance(column, ConditionType.notin, value);
    }
    public static Condition lt(Column column, Object value) {
        return instance(column, ConditionType.lt, value);
    }
    public static Condition lte(Column column, Object value) {
        return instance(column, ConditionType.lte, value);
    }
    public static Condition notnull(Column column) {
        return instance(column, ConditionType.notnull, 1);
    }
    public static Condition exists(Column column, Object value) {
        return instance(column, ConditionType.exists, value);
    }
    public static Condition notExists(Column column, Object value) {
        return instance(column, ConditionType.notexists, value);
    }



    public Column getColumn() {
        return column;
    }

    public ConditionType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public Condition and(Column column, ConditionType type, Object value) {
        return and(new Condition(column, type, value));
    }

    public Condition notEmptyAnd(Column column, ConditionType type, Object value) {
        if(value == null){
            return this;
        }
        if(value instanceof String && StringUtils.isEmpty((String) value)){
            return this;
        }
        this.and(column, type, value);
        return this;
    }
    public Condition notEmptyOr(Column column, ConditionType type, Object value) {
        if(value == null){
            return this;
        }
        if(value instanceof String && StringUtils.isEmpty((String) value)){
            return this;
        }
        this.or(column, type, value);
        return this;
    }
    public Condition and(Condition condition) {
        Objects.requireNonNull(condition);
        if (isEmpty()) {
            throw new RuntimeException("无效的条件,无法在其基础上追加and条件");
        }
        if (condition.isEmpty()) {
            return this;
        }
        andList.add(condition);
        return this;
    }

    public Condition or(Column column, ConditionType type, Object value) {
        return or(new Condition(column, type, value));
    }

    public Condition or(Condition condition) {
        Objects.requireNonNull(condition);
        if (isEmpty()) {
            throw new NullPointerException();
        }
        if (condition.isEmpty()) {
            throw new NullPointerException();
        }
        orList.add(condition);
        return this;
    }

    public Collection<Condition> getAndList() {
        return andList;
    }

    public Collection<Condition> getOrList() {
        return orList;
    }

    public void setAndList(Collection<Condition> andList) {
        this.andList = andList;
    }

    public void setOrList(Collection<Condition> orList) {
        this.orList = orList;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public void setType(ConditionType type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isEmpty(){
        return column == null || type == null || value == null;
    }


}
