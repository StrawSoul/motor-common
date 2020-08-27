package com.motor.message.http.servlet;

import com.motor.common.message.MessageHeaderGetter;
import com.motor.common.message.command.CommandBuilder;

import javax.servlet.http.HttpServletRequest;

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
 * version: 0.0.0  2020/8/27 10:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class HttpServletCommandBuilder {

    private static final ThreadLocal<CommandBuilder> threadLocal = new ThreadLocal<>();


    public static void create(HttpServletRequest request){
        threadLocal.set(fromRequest(request));
    }
    public static CommandBuilder get(){
        return threadLocal.get();
    }
    public static void remove(){
        threadLocal.remove();
    }

    public static CommandBuilder fromRequest(HttpServletRequest request){
        return commandBuilder(new HttpServletRequestMessageHeaderGetter(request));
    }
    public static CommandBuilder commandBuilder(MessageHeaderGetter headerGetter){
        return CommandBuilder.getInstance()
                .appId(headerGetter.get("appId"))
                .channel(headerGetter.get("channel"))
                .traceId(headerGetter.get("traceId"))
                .clientId(headerGetter.get("clientId"))
                .time(headerGetter.get("time"))
                .source(headerGetter.get("source"))
                .target(headerGetter.get("target"))
                .token(headerGetter.get("token"));
    }
}
