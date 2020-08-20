package com.motor.common.message;

/**
 ===========================================================================================
 *  设计说明
 -------------------------------------------------------------------------------------------
 *
 ===========================================================================================
 *  方法简介
 -------------------------------------------------------------------------------------------
 *  queueForCommand     -> 创建用于接收本模块命令的队列名称
 *  queueForEvent       -> 创建本队列用于监听其他模块时间的队列;
 *  queueForData        -> 创建本模块用于接收数据的队列
 *  routeKeyForCommand  -> 定义接收命令的routingKey
 *  routeKeyForEvent    -> 定义接收时间的routingKey
 *  exchangeName        -> 指定交换机名称
 *
 ===========================================================================================
 * 变更记录
 -------------------------------------------------------------------------------------------
 * version: 0.0.0  2020/05/20  zlj
 * 第一次创建
 *------------------------------------------------------------------------------------------
 * version: 0.0.1  2020/06/05  zlj
 * 增加注释模板
 *==========================================================================================
 */
public interface MessageMetadataBuilder {

    public String queueForCommand(String moudle, String msgType, String msgName);
    public String queueForEvent(String moudle, String source, String msgType, String msgName);
    public String queueForData(String moudle, String source, String msgType, String msgName);
    public String routeKeyForCommand(String moudle, String msgType, String msgName);
    public String routeKeyForEvent(String source, String msgType, String msgName);
    public String exchangeName(String moudle, String msgType, String msgName);
}
