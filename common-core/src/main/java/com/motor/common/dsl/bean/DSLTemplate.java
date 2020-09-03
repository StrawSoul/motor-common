package com.motor.common.dsl.bean;

import java.util.Map;

/**
 * @author: zlj
 * @date: 2019-05-17 上午11:49
 * @description:  领域特定语言模板 ， 如： SQL, JS (mongodb 操作)
 */
public class DSLTemplate {

    private String template;
    private Map<String,Object> params;

    public DSLTemplate(String template, Map<String, Object> params) {
        this.template = template;
        this.params = params;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
