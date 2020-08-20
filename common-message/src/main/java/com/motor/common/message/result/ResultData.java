package com.motor.common.message.result;

import com.motor.common.message.Message;
import com.motor.common.message.event.Event;
import com.motor.common.paging.PageList;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ResultData<T> extends Event<T> {

    private static final long serialVersionUID = -6245318111180660159L;
    private Exception exception;
    public ResultData() {
    }

    public ResultData(Map<String, String> header, T content) {
        super(header, content);
    }
    public boolean isSuccess(){
        return Objects.equals(header("code"), "0");
    }
    public boolean isEmpty(){
        Object data = data();
        if(data == null){
            return true;
        }else {
            if(data instanceof Collection){
                Collection coll = (Collection) data;
                return coll.isEmpty();
            }else if(data instanceof Map){
                return ((Map) data).size() ==0;
            }else if(data instanceof PageList){
                return ((PageList) data).size() ==0;
            }else if(data instanceof CharSequence){
                String str = data.toString();
                return str.trim().isEmpty();
            }else{
                return false;
            }
        }
    }
    public boolean isSuccessAndNotEmpty(){
        return isSuccess() && !isEmpty();
    }

    public Message toMessage(){
        header("success", Boolean.toString(isSuccess()));
        header("empty", Boolean.toString(isEmpty()));
        header("successAndNotEmpty", Boolean.toString(isSuccessAndNotEmpty()));
        return this;
    }
    public Optional<T> optional(){
        return Optional.ofNullable(data());
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
