package com.motor.common.table.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import static com.motor.common.table.utils.ColumnUtils.convertHumpToUnderline;


/**
 * @author zlj
 */
public class Column implements Serializable {

    private static final long serialVersionUID = 1745157095151366609L;

    private String id;
    private String code;
    protected String name;
    private String label;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String desc;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int status;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private boolean deleted;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private boolean autoAlias;
    private String database;
    private String tablename;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String templateId;
    private String type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String alias;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer index;
    private boolean primary;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String resourceType;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String resource;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String resourceInstance;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String defaultValue;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String family;

    private int computable;


    public Column() {
    }

    public Column(String name) {
        this.name = name;
    }

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public Column(String name, String type, String alias) {
        this.name = name;
        this.type = type;
        this.alias = alias;
        this.index = -1;
    }

    public Column(String name, String type, String desc, Integer index) {
        this.name = name;
        this.type = type;
        this.alias = desc;
        this.index = index;
    }
    public Column(String name, String type, String alias, Integer index, boolean primary) {
        this.name = name;
        this.type = type;
        this.alias = alias;
        this.index = index;
        this.primary = primary;
    }

    public Column(String database, String tablename, String name, String type, String alias, Integer index, boolean primary) {
        this.database = database;
        this.tablename = tablename;
        this.name = name;
        this.type = type;
        this.alias = alias;
        this.index = index;
        this.primary = primary;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isAutoAlias() {
        return autoAlias;
    }

    public void setAutoAlias(boolean autoAlias) {
        this.autoAlias = autoAlias;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public static Column column(String name){
        return new Column(name);
    }
    public static Column column(String name, String type){
        return new Column(name, type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public boolean isPrimary() {
        return primary;
    }
    public boolean getPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        if(resourceType == null){
            resourceType = null;
        }
        this.resourceType = resourceType.toLowerCase();
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String toColumnStr(){
        String columnStr = null;
        if(getIndex() == null){
            columnStr = ("`"+getName()+"`").replace(".","`.`");
        }else{
            columnStr = getIndex() >= 0 ? ("`"+getName()+"`").replace(".","`.`"): "'"+getName()+"'";
        }
        return convertHumpToUnderline(columnStr);
    }

    public String fullName(){
        return (tablename != null ? (database != null ? convertHumpToUnderline(database)+".": "") + convertHumpToUnderline(tablename) +".": "")+ toColumnStr();
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public int getComputable() {
        return computable;
    }

    public void setComputable(int computable) {
        this.computable = computable;
    }

    public String getResourceInstance() {
        return resourceInstance;
    }

    public void setResourceInstance(String resourceInstance) {
        this.resourceInstance = resourceInstance;
    }

    public void autoAlias(boolean b){
        this.autoAlias = b;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
