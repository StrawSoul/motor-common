package com.motor.common.message;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zlj
 * @param <T>
 *  伪造的future, 当得到MessageForgedFuture 对象的同时, 已经获得了结果,
 *  当没有实现异步的时候,先用这个类顶替,等到有实现的时候在无缝替换
 */
public class MessageForgedFuture<T> implements Future<T> {

    private T result;

    public MessageForgedFuture(T result) {
        this.result = result;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return result;
    }
}
