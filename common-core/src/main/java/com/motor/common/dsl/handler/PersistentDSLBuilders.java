package com.motor.common.dsl.handler;


import java.util.Map;

public class PersistentDSLBuilders {

    private Map<String, PersistentDSLBuilder> builderMap;

    public PersistentDSLBuilders(Map<String, PersistentDSLBuilder> builderMap) {
        this.builderMap = builderMap;
    }

    public PersistentDSLBuilder get(String dialect){
        return builderMap.get(dialect);
    }

}
