package com.motor.common.dsl.bean;

import com.motor.common.table.bean.Attribute;
import com.motor.common.utils.M;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zlj
 */
public class PersistentCommand {

    private String body;
    private List<Attribute> args;

    public PersistentCommand() {
    }

    public PersistentCommand(String body, List args) {
        this.body = body;
        this.args = args;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Attribute> getArgs() {
        return args == null ? Collections.emptyList(): args;
    }

    public void setArgs(List<Attribute> args) {
        this.args = args;
    }

    public Collection<?> argValues(){
        if(M.isEmpty(args)){
            return Collections.EMPTY_LIST;
        }
        List<Object> list = args.stream().map(e -> e.getValue()).collect(Collectors.toList());
        return list;
    }
}
