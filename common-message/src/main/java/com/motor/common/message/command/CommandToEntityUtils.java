package com.motor.common.message.command;

import com.motor.common.domain.BaseEntity;

import java.util.Date;

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
 * version: 0.0.0  2020/9/3 15:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public interface CommandToEntityUtils {

    static<T> void forInsert(Command cmd, BaseEntity<T> entity){
        Date now = new Date();
        entity.setCreateBy(cmd.userId());
        entity.setUpdateBy(cmd.userId());
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
    }
    static<T> void forUpdate(Command cmd, BaseEntity<T> entity){
        Date now = new Date();
        entity.setUpdateBy(cmd.userId());
        entity.setUpdateTime(now);
    }
}
