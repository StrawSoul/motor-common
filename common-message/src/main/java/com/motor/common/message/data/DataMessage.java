package com.motor.common.message.data;

import com.motor.common.message.Message;

public class DataMessage<T> extends Message<T> {
    private static final long serialVersionUID = -5570281589301607584L;
    public DataMessage() {
        type("data");
    }

    public void routingKey(String key){
        header("routingKey", key);
    }
    public String routingKey(){
        return header("routingKey");
    }
}
