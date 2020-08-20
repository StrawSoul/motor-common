package com.motor.common.message;

public interface MessageConverter {

    byte[] toBytes(Message message);

    Object toObject(byte[] bytes);
}
