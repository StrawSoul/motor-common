package com.motor.common.message;

import com.motor.common.message.command.Command;
import com.motor.common.message.data.DataMessage;
import com.motor.common.message.event.Event;

import java.util.concurrent.Future;

public interface MessageSender extends AutoCloseable {


    /**
     *  开启消息事务
     */
    default MessageSender begin(){
        doBegin();
        return this;
    }
    void doBegin();

    /**
     * 确认消息事务提交
     */
    default MessageSender commit(){
        doCommit();
        return this;
    }

    void doCommit();

    /**
     * 回滚, 取消消息的发送
     * @return
     */
    default MessageSender rollback(){
        doRollback();
        return this;
    }

    void doRollback();

    /**
     *  关闭发送器
     */
    void close();


    /**
     * 发布事件
     * @param event
     */
    default MessageSender publish(Event event){
        doPublish(event);
        return this;
    }

    void doPublish(Event event);

    /**
     *  推送数据
     * @param event
     */
    default MessageSender push(DataMessage event){
        doPush(event);
        return this;
    }

    void doPush(DataMessage event);


    /**
     * 只通知,notice only, 不关心返回结果
     * @param command
     */
    default MessageSender tell(Command command){
        doTell(command);
        return this;
    }

    void doTell(Command command);

    /**
     * 询问,请求 ,需要有直接回应, 返回请求的结果
     * @param command
     * @return
     */
    Future<?> ask(Command command);

    /**
     * 呼叫,  可能有多次返回
     * @param command
     * @return
     */
    MessageCallbackConsumer<?> call(Command command);


    void convertAndSend(String exchangeName, String routingKey, Message message);

    void sendToQueue(String queue, Message message);

    MessageCallbackConsumer sendAndReceive(String exchangeName, String routingKey, Message message);
}
