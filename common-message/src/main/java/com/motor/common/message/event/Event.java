package com.motor.common.message.event;



import com.motor.common.message.Message;

import java.util.Map;

/**
 *  事件   描述自己发生了怎样的变化
 * @param <T>
 */

public class Event<T> extends Message<T> implements Cloneable{
    private static final long serialVersionUID = -5570281589301607584L;
    public Event() {
    }

    public Event(T content) {
        super(content);
    }

    public Event(Map<String, String> header, T content) {
        super(header, content);
    }

    public String eventType(){
        return header("eventType");
    }
    public void eventType(String eventType){
        header("eventType", eventType);
    }
    public Event<T> cloneObj(){
        Object clone = null;
        try {
            clone = this.clone();
            return (Event<T>) clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
