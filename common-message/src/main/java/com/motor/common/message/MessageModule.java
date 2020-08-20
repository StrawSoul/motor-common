package com.motor.common.message;

import com.motor.common.message.command.Command;
import com.motor.common.message.data.DataMessage;
import com.motor.common.message.event.Event;

import java.util.concurrent.Future;

/**
 * @author zlj
 * 消息模块, 每个业务模块拥有自己的消息模块
 */
public interface MessageModule {

    /**
     * 关注  source模块 的  eventName 事件
     * @param source  事件来源模块名称
     * @param eventName  事件名称
     * @param listener  消息监听器
     */
    <T> String focusEvent(String source, String eventName, MessageListener<Message<T>> listener);
    /**
     * 关注  source模块 的  eventName 事件
     * @param source  事件来源模块名称
     * @param routingKey  路由
     * @param listener  消息监听器
     *
     */
    <T> String focusData(String source, String routingKey, MessageListener<Message<T>> listener);

    /**
     * 关注 本模块的 命令, 由其他模块调用本模块的命令
     * @param command
     * @param listener
     */
    <T> String focusCommand(String command, MessageListener<Message<T>> listener);


    /**
     * 注销活跃的监听者
     * @param consumerTag
     */
    void cancelFocus(String consumerTag);

    /**
     * 发布事件
     * @param event
     */
    void publish(Event event);

    /**
     *  推送数据
     * @param event
     */
    void push(DataMessage event);

    /**
     *  通知其他模块执行命令
     * @param command
     */
    void tell(Command command);

    /**
     * 询问,请求 ,需要有直接回应
     * @param command
     * @return
     */
    Future<?> ask(Command command);

    /**
     * 呼叫,  可以有多次回应
     * @param command
     * @return
     */
    MessageCallbackConsumer call(Command command);

    /**
     *  获取发送器
     * @return
     */
    MessageSender sender();

    /**
     *  获取接收器器
     * @return
     */
    MessageReceiver receiver();

    /**
     * 获取提供事务的发送器
     * 该发送器 需要手动关闭,否则会产生大量的 channel
     * @return
     */
    MessageSender transactionSender();
}
