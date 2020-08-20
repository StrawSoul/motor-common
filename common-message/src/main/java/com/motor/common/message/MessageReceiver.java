package com.motor.common.message;

public interface MessageReceiver {

    String listenData(String source, String msgName, MessageListener listener);

    String listenEvent(String source, String eventName, MessageListener listener);

    String listenCommand(String command, MessageListener listener);

    void cancelConsumer(String consumerTag);

    String listen(String queue, MessageListener listener);

    String listen(String queue, MessageRpcHandler handler);
}
