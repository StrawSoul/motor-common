package com.motor.common.domain;

import java.util.Map;
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
 * version: 0.0.0  2020/9/4 13:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class DefaultNameMappingPrimaryKeyProducer  extends DefaultPrimaryKeyProducer implements PrimaryKeyProducer{
    private Map<String,String> map;

    public DefaultNameMappingPrimaryKeyProducer(Map<String, String> map) {
        this.map = new ConcurrentHashMap<>(map);
    }

    @Override
    public String produce(String businessCode) {
        return super.produce(prefix(businessCode));
    }

    @Override
    public String[] produce(String businessCode, int n) {
        return super.produce(prefix(businessCode), n);
    }

    private String prefix(String businessCode){
        return map.getOrDefault(businessCode, businessCode);
    }
}
