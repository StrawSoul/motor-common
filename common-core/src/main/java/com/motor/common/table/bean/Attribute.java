package com.motor.common.table.bean;

/**
 * @author zlj
 */
public class Attribute<T> {

    private Column column;
    private T value;

    public Attribute() {
    }

    public Attribute(Column column, T value) {
        this.column = column;
        this.value = value;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <E> Attribute<E> instance(Column column, E value){
        return new Attribute<>(column, value);
    }
}
