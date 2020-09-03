package com.motor.common.dsl.bean;

/**
 * @author: zlj
 * @date: 2019-05-15 下午4:14
 * @description:
 */
public class KeyValue {

    private String name;

    private String value;

    public KeyValue() {
    }

    public KeyValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
