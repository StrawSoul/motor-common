package com.motor.message.http.servlet;

import com.motor.common.message.MessageHeaderGetter;
import com.motor.common.utils.M;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
public class HttpServletRequestMessageHeaderGetter implements MessageHeaderGetter {

    private HttpServletRequest httpServletRequest;
    private Map<String,Cookie> cookies;

    public HttpServletRequestMessageHeaderGetter(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        this.cookies = new HashMap<>();
        Cookie[] cookies = this.httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            this.cookies.put(name,cookie);
        }
    }

    @Override
    public String get(String name) {
        name = "m-"+name;
        String value = getCookieValue(name);
        if(M.isEmpty(value)){
            value = getParameter(name);
        } else if(M.isEmpty(value)){
            value = getSessionValue(name);
        } else if(M.isEmpty(value)){
            value = getSessionValue(name);
        } else if(M.isEmpty(value)){
            value = getContextValue(name);
        }
        return value;
    }

    public String getCookieValue(String name){
        Cookie cookie = getCookie(name);
        if(cookie == null){
            return null;
        }
        return cookie.getValue();
    }
    public Cookie getCookie(String name){
        return this.cookies.get(name);
    }

    public String getParameter(String name){
        return this.httpServletRequest.getParameter(name);
    }
    public String getSessionValue(String name){
        HttpSession session = this.httpServletRequest.getSession();
        if(session == null){
            return null;
        }
        Object value = session.getAttribute(name);
        return getString(value);
    }
    public String getContextValue(String name){
        ServletContext servletContext = this.httpServletRequest.getServletContext();
        Object value = servletContext.getAttribute(name);
        return getString(value);
    }
    private String getString(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return String.valueOf(value);
        }
        if(!(value instanceof String)){
            return null;
        }
        return value.toString();
    }

}
