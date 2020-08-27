package com.motor.common.message.event;
public class EventBuilder<T> {

    private Event event;

    private T content;

    public EventBuilder() {
        this.event =  new Event();
        event.header("type", "event");
    }

    public EventBuilder<T> source( String source){
        event.header("source", source);
        return this;
    }
    public EventBuilder<T> name( String name){
        event.header("name", name);
        return this;
    }
    public EventBuilder<T> header(String key, String value){
        event.header(key, value);
        return this;
    }
    public EventBuilder token(String token){
        event.header("token", token);
        return this;
    }
    public EventBuilder<T> eventType(String eventType){
        event.eventType(eventType);
        return this;
    }
    public EventBuilder<T> data(T data){
        content = data;
        return this;
    }
    public Event build(){
        Event event = this.event.cloneObj();
        event.data(content);

        return event;
    }
    public Event build(Object data){
        Event event = this.event.cloneObj();
        event.data(data);
        return event;
    }


    public EventBuilder<T> subject(String value){
        header("subject", value);
        return this;
    }

    public static EventBuilder getInstance(){
        return new EventBuilder();
    }
    public static EventBuilder nameOf(String name){
        return new EventBuilder().name(name);
    }


}
