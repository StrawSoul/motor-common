package com.motor.common.message;

/**
 * @author zlj
 *
 */
public interface MessageContainer {

    /**
      * 获取消息模块
      * @param name
      * @return
      */
    MessageModule module(String name);

    /**
     *  消息接收器
     * @param module
     * @return
     */
    MessageReceiver getReceiver(String module);

    /**
     *  创建交换机
     * @param exchangeName
     */
    void createExchange(String exchangeName);


    void createQueue(String name);

    void createQueueBind(String queueName, String exchangeName, String routingKey);

    /**
     * 消息发送器
     * @param module
     * @return
     */
    MessageSender getSender(String module);
}
