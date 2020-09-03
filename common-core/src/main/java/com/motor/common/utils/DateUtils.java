package com.motor.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

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
 * version: 0.0.0  2020/8/31 10:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class DateUtils {

    private final static String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static String formatYMDHMS(TemporalAccessor time){
        String timeStr = DateTimeFormatter.ofPattern(YMDHMS).format(time);
        return timeStr;
    }
    public static String format(TemporalAccessor time, String template){
        String timeStr = DateTimeFormatter.ofPattern(template).format(time);
        return timeStr;
    }
}
