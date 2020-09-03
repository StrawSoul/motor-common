package com.motor.common.message.command;


public class CommandBuilder<T> {

    private Command<T> command;

    public CommandBuilder() {
        command = new Command();
    }

    public CommandBuilder<T> id(String id){
        command.header("id",id);
        return this;
    }
    public CommandBuilder<T> name(String name){
        command.header("name",name);
        return this;
    }

    public CommandBuilder<T> source(String source){
        command.header("source", source);
        return this;
    }
    public CommandBuilder<T> target(String target){
        command.header("target", target);
        return this;
    }
    public CommandBuilder<T> header(String name, String value){
        command.header(name, value);
        return this;
    }
    public CommandBuilder<T> token(String token){
        command.header("token", token);
        return this;
    }
    public CommandBuilder<T> appId(String appId){
        command.header("appId", appId);
        return this;
    }
    public CommandBuilder<T> channel(String channel){
        command.header("channel", channel);
        return this;
    }
    public CommandBuilder<T> traceId(String traceId){
        command.header("traceId", traceId);
        return this;
    }
    public CommandBuilder<T> clientId(String clientId){
        command.header("clientId", clientId);
        return this;
    }
    public CommandBuilder<T> time(String time){
        command.header("time", time);
        return this;
    }
    public CommandBuilder<T> userId(String userId){
        command.header("userId", userId);
        return this;
    }

    public CommandBuilder<T> data(T data){
        command.data(data);
        return this;
    }

    public Command<T> build(T data){
        Command command = this.command.cloneObj();
        command.data(data);
        return command;
    }
    public Command<T> build(){
        Command command = this.command.cloneObj();
        return command;
    }

    public static CommandBuilder getInstance(){
        CommandBuilder commandBuilder = new CommandBuilder();
        commandBuilder.header("type","command");
        return commandBuilder;
    }
    public static<T> CommandBuilder<T> getInstance(Class<T> clazz){
        CommandBuilder<T> commandBuilder = new CommandBuilder();
        commandBuilder.header("type","command");
        return commandBuilder;
    }
}
