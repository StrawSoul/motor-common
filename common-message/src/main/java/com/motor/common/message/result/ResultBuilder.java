package com.motor.common.message.result;

import com.motor.common.exception.BusinessException;
import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.exception.ErrorCode;
import com.motor.common.message.ReplySender;

import java.util.HashMap;
import java.util.Map;

public class ResultBuilder<T> {

    private Map<String,String> header;
    private T data;
    private Throwable exception;

    public static  ResultBuilder getInstance(){
        return getInstance(Object.class);
    }
    public static <T> ResultBuilder getInstance(Class<T> clazz){
        ResultBuilder builder = new ResultBuilder();
        builder.header = new HashMap<>();
        return builder;
    }
    public ResultBuilder<T> success(){
        header.put("code", "0");
        return this;
    }

    public ResultBuilder<T> failed(){
        return failed("2");
    }
    public ResultBuilder<T> failed(String code){
        header.put("code", code);
        return this;
    }
    public ResultBuilder<T> failed(ErrorCode errorCode){
        return failed(errorCode.getCode());
    }
    public ResultBuilder<T> failed(BusinessException e){
        exception = e;
        return failed(e.getErrorCode()).innerMessage(e.getMessage());
    }
    public ResultBuilder<T> failed(BusinessRuntimeException e){
        exception = e;
        return failed(e.getErrorCode()).innerMessage(e.getMessage());
    }
    public ResultBuilder<T> failed(Exception e){
        exception = e;
        return failed("500").innerMessage(e.getMessage());
    }
    public ResultBuilder<T> exception(Throwable e){
        exception = e;
        return innerMessage(e.getMessage());
    }
    public ResultBuilder<T> header(String name, String value){
        header.put(name,value);
        return this;
    }
    public ResultBuilder<T> header(Map<String,String> header){
        header.putAll(header);
        return this;
    }
    public ResultBuilder<T> data(T content){
        this.data = content;
        return this;
    }
    public ResultBuilder<T> code(ErrorCode code){
        header.put("code", code.getCode());
        return this;
    }
    public ResultBuilder<T> code(String code){
        header.put("code", code);
        return this;
    }
    public ResultBuilder<T> innerMessage(String content){
        header.put("innerMessage", content);
        return this;
    }
    public ResultBuilder<T> userMessage(String content){
        header.put("userMessage", content);
        return this;
    }
    public ResultBuilder<T> moreInfo(String content){
        header.put("moreInfo", content);
        return this;
    }
    public ResultBuilder<T> status(int status){
        header.put("moreInfo", Integer.toString(status));
        return this;
    }
    public ResultBuilder<T> token(String token){
        header.put("token", token);
        return this;
    }

    public void reply(ReplySender sender){
        if (sender!=null) {
            ResultData<?> msg = build();
            sender.reply(msg.isSuccess() ? "success":"failed", msg);
        }
    }
    public void reply(String type, ReplySender sender){
        if (sender!=null) {
            ResultData<?> msg = build();
            sender.reply(type,msg);
        }
    }
    public ResultData<?> build(){
        return new ResultData(header, data);
    }


}
