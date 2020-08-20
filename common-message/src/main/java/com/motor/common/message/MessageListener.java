package com.motor.common.message;

public interface MessageListener<T> {

    /**
     * @param data
     * @param replySender
     */
    void handle(T data, ReplySender replySender);

}
