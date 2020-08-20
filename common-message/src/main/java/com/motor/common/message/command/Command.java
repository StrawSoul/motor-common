package com.motor.common.message.command;

import com.motor.common.message.Message;

/**
 * 通知别人执行某个操作
 * @param <T>
 */
public class Command<T> extends Message<T> {


    public String target(){
        return header("target");
    }
    public void target(String target){
        header("target", target);
    }

    public String callback() {
        return header("callback");
    }
    public void callback(String callback){
        header("callback", callback);
    }

    public Command cloneObj() {
        Object clone = null;
        try {
            clone = this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (Command) clone;
    }
}
