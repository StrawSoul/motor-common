package com.motor.common.message;

public interface MessageRpcHandler<T> {
    Object handle(T data, ReplySender replySender);
}
