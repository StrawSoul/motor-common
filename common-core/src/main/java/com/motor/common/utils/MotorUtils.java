package com.motor.common.utils;

import com.motor.common.paging.PageList;

import java.util.Collection;
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
 * version: 0.0.0  2020/8/26 11:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class MotorUtils {

    public static boolean isEmpty(Object data){
        if(data == null){
            return true;
        }else {
            if(data instanceof Collection){
                Collection coll = (Collection) data;
                return coll.isEmpty();
            }else if(data instanceof Map){
                return ((Map) data).size() ==0;
            }else if(data instanceof PageList){
                return ((PageList) data).size() ==0;
            }else if(data instanceof CharSequence){
                String str = data.toString();
                return str.trim().isEmpty();
            }else{
                return false;
            }
        }
    }
}
