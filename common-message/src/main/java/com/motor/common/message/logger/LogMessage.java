package com.motor.common.message.logger;

import com.motor.common.message.event.Event;

public class LogMessage extends Event {
    private static final long serialVersionUID = -5570281589301607584L;

    public LogMessage() {
        this.type("log");
    }
}
