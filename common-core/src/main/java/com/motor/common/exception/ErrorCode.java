package com.motor.common.exception;

import com.motor.common.business.BusinessCode;

public class ErrorCode implements BusinessCode {


    private String code;
    private String name;
    private String desc;

    public ErrorCode(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String format(String ...args){
        return String.format(desc, args);
    }
}
