package com.motor.common.dsl.sql;


import com.motor.common.dsl.ConditionUtils;
import com.motor.common.dsl.bean.*;
import com.motor.common.paging.Paging;
import com.motor.common.table.utils.ColumnUtils;

import java.util.*;
import java.util.function.Supplier;

public class SimpleSqlBuilder {

    DSLDialect dialect;

    public SimpleSqlBuilder(DSLDialect dialect) {
        this.dialect = dialect;
    }

    public FromBuilder from(TableWrapper wrap){
        return  new FromBuilder(wrap);
    }

    public class InsertBuilder extends WrapDslBuilder implements DSLBuilder<PersistentCommand> {
        private Map<String,Object> params;

        public InsertBuilder(TableWrapper wrap, Map<String,Object> params) {
            super(wrap);
            this.params = params;
        }

        @Override
        public PersistentCommand build() {
            if(params == null){
                params = new HashMap<>();
            }
            StringBuffer upsert = new StringBuffer(dialect.insertInto() ).append(dialect.tableName(wrap)).append(" ");
            StringBuffer columns = new StringBuffer();
            StringBuffer values = new StringBuffer();
            params.forEach((k,v)->{
                if(columns.length()!=0){
                    columns.append(",");
                    values.append(",");
                }
                String s = ColumnUtils.convertHumpToUnderline(k);
                ColumnWrapper column = (ColumnWrapper)wrap.findColumn(s);
                if(column == null){
                    throw new RuntimeException(k + "=> "+ s +" in "+wrap.getName()+" not exists");
                }
                columns.append(dialect.columnName(column));
                values.append(dialect.formatValue(column, v));
            });
            upsert.append("(").append(columns).append(")");
            upsert.append(" values ").append("(").append(values).append(")");
            return new PersistentCommand(upsert.toString(),Collections.EMPTY_LIST);
        }
    }
    public class UpdateBuilder extends WrapDslBuilder implements DSLBuilder<PersistentCommand> {
        private Map<String,Object> params;

        public UpdateBuilder(TableWrapper wrap, Map<String,Object> params) {
            super(wrap);
            this.params = params;
        }

        public WhereBuilder where(Condition condition){
            return new WhereBuilder(this, condition, wrap);
        }

        @Override
        public PersistentCommand build() {
            StringBuffer update = new StringBuffer(dialect.update() ).append(dialect.tableName(wrap)).append(" ");
            StringBuffer set = new StringBuffer();
            params.forEach((k,v)->{
                if(set.length()!=0){
                    set.append(",");
                }
                ColumnWrapper column = (ColumnWrapper)wrap.findColumn(k);
                set.append(dialect.columnName(column)).append(" = ").append(dialect.formatValue(column, v));
            });
            update.append(" set ").append(set);
            return new PersistentCommand(update.toString(),Collections.EMPTY_LIST);
        }
    }
    public class DeleteBuilder extends WrapDslBuilder implements DSLBuilder<PersistentCommand> {

        private  String DELETE = "DELETE FROM ";
        private DSLBuilder<PersistentCommand> dslBuilder;
        public DeleteBuilder(TableWrapper wrap) {
            super(wrap);
            this.dslBuilder = dslBuilder;

        }

        public WhereBuilder where(Condition condition){
            return  new WhereBuilder(this, condition, wrap);
        }

        @Override
        public PersistentCommand build() {
            PersistentCommand cmd = new PersistentCommand();
            cmd.setBody(DELETE + dialect.tableName(wrap));
            return cmd;
        }
    }
    public class SelectBuilder extends WrapDslBuilder implements DSLBuilder<PersistentCommand> {

        private String select = "select %s \n";

        List<ColumnWrapper> views;
        private DSLBuilder<PersistentCommand> dslBuilder;

        public SelectBuilder(DSLBuilder<PersistentCommand> dslBuilder, List<ColumnWrapper> columns, TableWrapper wrap) {
            super(wrap);
            this.views = columns;
            this.dslBuilder = dslBuilder;
        }



        public QueryWhereBuilder where(Condition condition){
            return new QueryWhereBuilder(this, condition, wrap);
        }
        public QueryWhereBuilder where(Supplier<Condition> supplier){
            return new QueryWhereBuilder(this, supplier.get(),wrap);
        }


        @Override
        public PersistentCommand build() {
            String viewStr = dialect.views(this.views);
            PersistentCommand cmd = dslBuilder.build();
            String fromBody = cmd.getBody();
            String selectBody = String.format(select, viewStr) + fromBody;
            cmd.setBody(selectBody);
            return cmd;
        }
    }
    public class ViewBuilder extends WrapDslBuilder implements DSLBuilder<PersistentCommand> {

        private String select = "select %s \n";

        String viewStr;
        private DSLBuilder<PersistentCommand> dslBuilder;

        public ViewBuilder(DSLBuilder<PersistentCommand> dslBuilder, String viewStr, TableWrapper wrap) {
            super(wrap);
            this.viewStr = viewStr;
            this.dslBuilder = dslBuilder;
        }



        public QueryWhereBuilder where(Condition condition){
            return new QueryWhereBuilder(this, condition, wrap);
        }
        public QueryWhereBuilder where(Supplier<Condition> supplier){
            return new QueryWhereBuilder(this, supplier.get(),wrap);
        }


        @Override
        public PersistentCommand build() {
            PersistentCommand cmd = dslBuilder.build();
            String fromBody = cmd.getBody();
            String selectBody = String.format(select, viewStr) + fromBody;
            cmd.setBody(selectBody);
            return cmd;
        }
    }


    public class WhereBuilder extends WrapDslBuilder  implements DSLBuilder<PersistentCommand>{
        DSLBuilder<PersistentCommand> dslBuilder;
        Condition condition;

        public WhereBuilder(DSLBuilder<PersistentCommand> dslBuilder, Condition condition, TableWrapper wrap) {
            super(wrap);
            this.dslBuilder = dslBuilder;
            this.condition = condition;
        }

        @Override
        public PersistentCommand build() {
            DSLTemplate translate = ConditionUtils.translate(condition);
            PersistentCommand cmd = dslBuilder.build();
            String body = cmd.getBody();
            cmd.setBody(body+ " \nwhere \n "+  dialect.replaceAliasFlag(translate.getTemplate()));
            return cmd;
        }
    }
    public class QueryWhereBuilder extends WhereBuilder  implements DSLBuilder<PersistentCommand>{


        public QueryWhereBuilder(DSLBuilder<PersistentCommand> dslBuilder, Condition condition, TableWrapper wrap) {
            super(dslBuilder, condition, wrap);
        }

        public SortBuilder sorted(OrderBy orderBy){
            return new SortBuilder(this, orderBy, wrap);
        }
        public GroupBuilder groupBy(String... names){
            return new GroupBuilder(this, wrap, names);
        }

        public DSLBuilder<PersistentCommand> limit(Paging paging) {
            return new LimitBuilder(this, paging, wrap);
        }
    }

    public class LimitBuilder extends WrapDslBuilder implements DSLBuilder<PersistentCommand> {
        DSLBuilder<PersistentCommand> dslBuilder;
        Paging paging;
        public LimitBuilder(DSLBuilder<PersistentCommand> dslBuilder, Paging paging, TableWrapper wrap) {
            super(wrap);
            this.dslBuilder = dslBuilder;
            this.paging = paging;
        }

        @Override
        public PersistentCommand build() {
            PersistentCommand cmd = dslBuilder.build();
            String body = cmd.getBody();
            String limit = dialect.limit(paging);
            cmd.setBody(body +" "+ limit);
            return cmd;
        }
    }
    public class SortBuilder extends WrapDslBuilder implements DSLBuilder<PersistentCommand> {

        private DSLBuilder<PersistentCommand> selectBuilder;
        private OrderBy orderBy;
        public SortBuilder(DSLBuilder<PersistentCommand> selectBuilder, OrderBy orderBy, TableWrapper wrap) {
            super(wrap);
            this.selectBuilder = selectBuilder;
            this.orderBy = orderBy;
        }

        public LimitBuilder limit(Paging paging){
            return new LimitBuilder(this, paging, wrap);
        }

        @Override
        public PersistentCommand build(){
            PersistentCommand cmd = selectBuilder.build();
            String body = cmd.getBody()  + dialect.orderBy(wrap, orderBy) ;
            cmd.setBody(body);
            return cmd;
        }
    }

    public class GroupBuilder extends WrapDslBuilder implements DSLBuilder<PersistentCommand>{

        private DSLBuilder<PersistentCommand> builder;
        private String[] names;

        public GroupBuilder(DSLBuilder<PersistentCommand> builder, TableWrapper wrap, String... names) {
            super(wrap);
            this.builder = builder;
            this.names = names;
        }

        @Override
        public PersistentCommand build() {
            PersistentCommand cmd = builder.build();
            String body = cmd.getBody();
            String s = Arrays.asList(names).stream().reduce((a, b) -> a + "," + b).orElse("");
            body = body + "\ngroup by "+ s;
            cmd.setBody(body);
            return cmd;
        }
    }
    public class FromBuilder implements DSLBuilder<PersistentCommand>{

        private static final  String FROM = "FROM %s ";
        TableWrapper wrap;
        public FromBuilder(TableWrapper wrap) {
            this.wrap = wrap;
        }
        public SelectBuilder views(ColumnWrapper... columns){
            List<ColumnWrapper> list = Arrays.asList(columns);
            return new SelectBuilder(this, list, wrap);
        }
        public ViewBuilder views( String viewStr){
            return new ViewBuilder(this, viewStr, wrap);
        }
        public SelectBuilder views( List<ColumnWrapper> columns){
            return new SelectBuilder(this, columns, wrap);
        }
        public InsertBuilder insert(Map<String,Object> params){
            return new InsertBuilder(wrap, params);
        }
        public UpdateBuilder update(Map<String,Object> params){
            return new UpdateBuilder(wrap, params);
        }
        public DeleteBuilder remove(){
            return new DeleteBuilder(wrap);
        }

        public SortBuilder sorted(OrderBy orderBy){
            return new SortBuilder(this, orderBy, wrap);
        }

        @Override
        public PersistentCommand build() {
            StringBuffer sb = new StringBuffer(dialect.tableName(wrap));
            leftJoin(sb);
            return new PersistentCommand(String.format(FROM, sb.toString()), Collections.EMPTY_LIST);
        }


        /**
         *  拼装sql的关联表
         * @param sb
         */
        private void leftJoin(StringBuffer sb){
            Set<String> set = new HashSet<>();
            leftJoin(sb, wrap, set);
        }

        /**
         * 拼装sql的关联表
         * @param sb
         * @param template
         * @param tables   已经拼装的表格列表,用于表名去重, 一条sql的left join 里一个表只能出现一次
         */
        private void leftJoin(StringBuffer sb, TableWrapper template, Set tables){
            template.each((column)->{
                ColumnWrapper columnWrap =(ColumnWrapper)column;
                if(columnWrap.getRef() != null){
                    if(columnWrap.getRef() instanceof TableWrapper){
                        TableWrapper wrap = (TableWrapper)columnWrap.getRef();
                        String table = dialect.tableName(wrap);
                        ColumnWrapper primaryColumn = wrap.findPrimaryColumn();
                        if(tables.contains(table)) {
                            return;
                        }else{
                            sb.append( " left join ").append(table).append(" on ").append(dialect.columnName(primaryColumn)).append(" = ").append(dialect.columnName(columnWrap));
                            tables.add(table);
                        }
                        leftJoin(sb, wrap, tables);
                    }
                }
            });
        }
    }

    static abstract class WrapDslBuilder{
        protected TableWrapper wrap;
        public WrapDslBuilder(TableWrapper wrap) {
            this.wrap = wrap;
        }

        public TableWrapper getWrap() {
            return wrap;
        }
    }

    public static SimpleSqlBuilder sql(DSLDialect dialect){
        return new SimpleSqlBuilder(dialect);
    }
}


