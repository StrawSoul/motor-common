package com.motor.common.message.command;


public class CommandBuilder {

    private Command command;

    public CommandBuilder() {
        command = new Command();
    }

    public CommandBuilder id(String id){
        command.header("id",id);
        return this;
    }
    public CommandBuilder name(String name){
        command.header("name",name);
        return this;
    }

    public CommandBuilder source(String source){
        command.header("source", source);
        return this;
    }
    public CommandBuilder target(String target){
        command.header("target", target);
        return this;
    }
    public CommandBuilder header(String name, String value){
        command.header(name, value);
        return this;
    }
    public CommandBuilder token(String token){
        command.header("token", token);
        return this;
    }
    public CommandBuilder appId(String appId){
        command.header("appId", appId);
        return this;
    }
    public CommandBuilder channel(String channel){
        command.header("channel", channel);
        return this;
    }
    public CommandBuilder traceId(String traceId){
        command.header("traceId", traceId);
        return this;
    }
    public CommandBuilder clientId(String clientId){
        command.header("clientId", clientId);
        return this;
    }
    public CommandBuilder time(String time){
        command.header("time", time);
        return this;
    }
    public CommandBuilder userId(String userId){
        command.header("userId", userId);
        return this;
    }

    public CommandBuilder data(Object data){
        command.data(data);
        return this;
    }

    public Command build(Object data){
        Command command = this.command.cloneObj();
        command.data(data);
        return command;
    }
    public Command build(){
        Command command = this.command.cloneObj();
        return command;
    }

    public static CommandBuilder getInstance(){
        CommandBuilder commandBuilder = new CommandBuilder();
        commandBuilder.header("type","command");
        return commandBuilder;
    }
}
