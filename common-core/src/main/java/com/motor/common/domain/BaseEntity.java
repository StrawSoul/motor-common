package com.motor.common.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

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
 * version: 0.0.0  2020/8/21 10:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class BaseEntity<T> implements Entity<T>,Storable{

    private T id;
    private int version;
    private int oldVersion;
    @Override
    public void setId(T id) {
        this.id = id;
    }

    @Override
    public T getId() {
        return id;
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity entity = (BaseEntity) o;
        return new EqualsBuilder()
                .append(id, entity.getId())
                .isEquals();

    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
        this.oldVersion = version;
    }

    @Override
    public boolean changed() {
        return Objects.equals(this.version, this.oldVersion);
    }

    @Override
    public void commit() {
        synchronized (this){
            this.version = this.oldVersion+1;
        }
    }
}
