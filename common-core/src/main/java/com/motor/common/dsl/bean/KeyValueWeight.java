package com.motor.common.dsl.bean;


public class KeyValueWeight extends KeyValue implements Comparable<KeyValueWeight>{

    private Integer weight;

    public KeyValueWeight() {
    }

    public KeyValueWeight(Integer weight) {
        this.weight = weight;
    }

    public KeyValueWeight(String name, String value) {
        super(name, value);
    }

    public KeyValueWeight(String name, String value, Integer weight) {
        super(name, value);
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight == null ? 0 : weight;
    }

    @Override
    public int compareTo(KeyValueWeight o) {
        return this.getWeight().compareTo(o.getWeight());
    }
}
