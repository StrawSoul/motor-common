package com.motor.common.dsl.bean;

public class QueryPageCommand {
    private PersistentCommand queryCommand;
    private PersistentCommand countCommand;

    public QueryPageCommand(PersistentCommand queryCommand, PersistentCommand countCommand) {
        this.queryCommand = queryCommand;
        this.countCommand = countCommand;
    }

    public PersistentCommand getQueryCommand() {
        return queryCommand;
    }

    public void setQueryCommand(PersistentCommand queryCommand) {
        this.queryCommand = queryCommand;
    }

    public PersistentCommand getCountCommand() {
        return countCommand;
    }

    public void setCountCommand(PersistentCommand countCommand) {
        this.countCommand = countCommand;
    }
}
