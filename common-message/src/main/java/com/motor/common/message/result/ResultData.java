package com.motor.common.message.result;

import com.motor.common.message.Message;
import com.motor.common.message.event.Event;
import com.motor.common.utils.M;

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
        return M.isEmpty(data());
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
