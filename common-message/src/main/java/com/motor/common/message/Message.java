package com.motor.common.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message<T > implements Serializable,Cloneable {
    private static final long serialVersionUID = -5570281589301607584L;

    private Map<String,String> header;
    private T data;
    public String name() {
        return header("name");
    }
    public Message() {
        header = new HashMap<>();
    }

    public Message(T data) {
        header = new HashMap<>();
        this.data = data;
    }

    public Message(Map<String, String> header, T data) {
        this.header = header;
        this.data = data;
    }

    public String type(){
        return header("type");
    }
    public void type(String type){
        header("type", type);
    }
    public String source(){
        return header("source");
    }
    public void source(String source){
        header("source", source);
    }
    public String replyTo(){
        return header("replyTo");
    }
    public void replyTo(String replyTo){
        header("replyTo", replyTo);
    }

    public String subject(){
        return header("subject");
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public T data() {
        return data;
    }

    public void data(T data) {
        this.data = data;
    }

    public void header(String name, String value){
        header.put(name, value);
    }
    public String header(String name){
        return header.get(name);
    }

    public String timeout(){
        return header("timeout");
    }
    public void timeout(String timeout){
        header("timeout", timeout);
    }
    public Message toMessage(){
        Message msg = new Message();
        msg.setHeader(this.getHeader());
        msg.data(this.data());
        return msg;
    }
    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
                "header=" + header.entrySet().stream().map(e-> ""+ e.getKey()+":"+e.getValue()).reduce((a,b)-> a+"&" +b).orElse(" ") +
                ", content=" + data +
                '}';

    }
}
