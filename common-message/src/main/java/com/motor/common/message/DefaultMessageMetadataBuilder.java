package com.motor.common.message;

public class DefaultMessageMetadataBuilder implements MessageMetadataBuilder {

    @Override
    public String queueForCommand(String moudle, String msgType, String msgName) {
        return moudle + "."+ msgType +"."+ msgName;
    }

    @Override
    public String queueForEvent(String moudle, String source, String msgType, String msgName) {
        return moudle + "."+ msgType +"."+source+"."
                + msgName.replace("#","x");
    }

    @Override
    public String queueForData(String moudle, String source, String msgType, String msgName) {
        return moudle + "."+ msgType +"."+source+"."
                + msgName.replace("#","x");
    }

    @Override
    public String routeKeyForCommand(String module, String msgType, String msgName) {
        return module+"."+ msgName;
    }

    @Override
    public String routeKeyForEvent(String source, String msgType, String msgName) {
        return source+"."+ msgName;
    }

    @Override
    public String exchangeName(String moudle, String msgType, String msgName) {
        return msgType+ "SW";
    }


}
