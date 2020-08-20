package com.motor.common.message;

/**
 * 回复消息发送器
 * @param <T>
 */
public interface ReplySender<T> {

    /**
     *  回复 eventType类型的消息
     * @param eventType
     * @param o
     */
    void reply(String eventType, Object o);

    default void replySuccess(T t){
        reply("success", t);
    }
    default void replyFailed(T t){
        reply("failed", t);
    }
}
