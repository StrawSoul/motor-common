package com.motor.common.message;

import com.motor.common.message.event.Event;
import com.motor.common.message.event.EventBuilder;

/**
 *  应答消息发送器
 */
public class MessageReplySender implements ReplySender {

    /**
     *  应答目标
     */
    private String replyTo;

    private MessageSender messageSender;
    /**
     *  接收到的消息
     */
    private Message sourceMessage;

    /**
     *  回应完毕
     */
    private boolean completed;

    public MessageReplySender(Message sourceMsg,String replyTo, MessageSender messageSender) {
        this.sourceMessage = sourceMsg;
        this.replyTo = replyTo;
        this.messageSender = messageSender;
    }

    @Override
    public void reply(String eventType, Object o) {
        /**
         *  成功或者失败表示已经结束了, 不必再回应消息
         */
        if(replyTo== null|| "".equals(replyTo.trim()) || completed){
            return;
        }
        if("success".equals(eventType) || "failed".equals(eventType)){
            completed = true;
        }
        if(o instanceof Message){
            Message event = (Message) o;
            event.header("eventType", eventType);
            messageSender.sendToQueue(replyTo, event);
        } else {
            Event event = EventBuilder.nameOf(sourceMessage.name())
                    .eventType(eventType)
                    .build(o);
            messageSender.sendToQueue(replyTo, event);

        }
    }

}
