package com.motor.common.message;

import com.motor.common.exception.BusinessRuntimeException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.motor.common.message.constants.MessageErrorCode.FAILED_RESULT;

/**
 * 回调消息处理, 可以选择以同步或异步的方式处理回调消息
 * @param <T>
 */
public final class MessageCallbackConsumer<T> implements Consumer<Message<T>>{

    private MessageReceiver messageReceiver;
    /**
     * 回调消息消费者标识
     */
    private String consumerTag;
    private CompletableFuture<Message<T>> future;
    private String futureOn;

    /**
     *  不同类型消息的消费者
     */
    private Map<String,Consumer<Message>> consumers;
    /**
     *  执行任意消息的消费者
     */
    private Consumer<Message> alwaysConsumer;
    /**
     * 返回success的结果
     */
    private Message<T> result;
    /**
     *  接收到某些类型的消息就直接关闭关闭回调监听
     */
    private List<String> closeOn;

    public MessageCallbackConsumer(MessageReceiver messageReceiver, String consumerTag) {
        this.messageReceiver = messageReceiver;
        this.consumerTag = consumerTag;
    }

    public MessageCallbackConsumer when(List<String> eventTypes, Consumer<Message> consumer){
        eventTypes.forEach(e->when(e, consumer));
        return this;
    }
    public MessageCallbackConsumer when(String eventType, Consumer<Message> consumer){
        if(consumers == null){
            consumers = new HashMap<>();
        }
        Consumer<Message> current = consumers.get(eventType);
        if(current!= null){
            consumers.put(eventType, current.andThen(consumer));
        }else {
            this.consumers.put(eventType, consumer);
        }
        return this;
    }
    public MessageCallbackConsumer closeOn(List<String> eventTypes){
        closeOn = eventTypes;
        return this;
    }
    public MessageCallbackConsumer closeOn(String... eventTypes){
        return closeOn(Arrays.asList(eventTypes));
    }
    public void close(){
        if(consumerTag == null){
            return;
        }
        messageReceiver.cancelConsumer(consumerTag);
        consumerTag = null;
    }

    /**
     * 尝试关闭,是否能执行关闭要看具体的情况
     * @param message
     */
    private void tryClose(Message<T> message){
        if(message == null){
            close();
            return;
        }
        String eventType = message.header("eventType");
        if(closeOn == null){
            close();
        } else if (closeOn.contains(eventType)) {
            close();
        }
    }

    public void accept( Message<T> message){
        if(message != null && consumers != null){
            String k = null;
            Consumer<Message> c = null;
            String replyType = message.header("eventType");
            if(consumers.containsKey(replyType)){
                Consumer<Message> consumer = consumers.get(replyType);
                if("success".equals(replyType)){
                    result = message;
                }
                if(consumer != null){
                    consumer.accept(message);
                }
            }
        }
        if(futureOn != null && future != null && message != null){
            String eventType = message.header("eventType");
            if (futureOn.equals(eventType)) {
                future.complete(message);
            } else {
                if("failed".equals(eventType)){
                    future.completeExceptionally(new BusinessRuntimeException(FAILED_RESULT, message.toString()));
                }
            }
        }
        if(alwaysConsumer!= null){
            alwaysConsumer.accept(message);
        }
        tryClose(message);
    }
    public MessageCallbackConsumer always(Consumer<Message> consumer){
        if(alwaysConsumer == null){
            alwaysConsumer = consumer;
        } else {
            alwaysConsumer = alwaysConsumer.andThen(consumer);
        }
        return this;
    }

    /**
     * 以什么事件为同步返回结果
     * @param eventType
     * @return
     */
    public CompletableFuture<Message<T>> futureOn(String eventType){
        this.futureOn = eventType;
        if(future == null){
            this.future =  new CompletableFuture<>();
        }
        return future;
    }

}
