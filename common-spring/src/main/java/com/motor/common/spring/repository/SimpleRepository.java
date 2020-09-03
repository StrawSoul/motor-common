package com.motor.common.spring.repository;

import com.motor.common.domain.Entity;
import com.motor.common.domain.Repository;
import com.motor.common.domain.SearchCondition;
import com.motor.common.domain.UnSupportRepository;
import com.motor.common.dsl.ConditionUtils;
import com.motor.common.dsl.bean.*;
import com.motor.common.dsl.handler.PersistentDSLBuilders;
import com.motor.common.paging.PageList;
import com.motor.common.paging.Paging;
import com.motor.common.table.bean.Table;
import com.motor.common.table.handler.TableManager;
import com.motor.common.utils.BeanMapperUtil;
import com.motor.common.utils.MotorUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.*;

/**
 * ===========================================================================================
 * 设计说明
 * -------------------------------------------------------------------------------------------
 * <p>
 * ===========================================================================================
 * 方法简介
 * -------------------------------------------------------------------------------------------
 * {methodName}     ->  {description}
 * ===========================================================================================
 * 变更记录
 * -------------------------------------------------------------------------------------------
 * version: 0.0.0  2020/9/3 09:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class SimpleRepository<T, E extends Entity<T>> extends UnSupportRepository<T,E> implements Repository<T,E> {

    String dialect;
    Class<E> clazz;
    String tableName;
    String repository;
    JdbcTemplate jdbcTemplate;
    TableManager tableManager;
    PersistentDSLBuilders persistentDSLBuilders;
    TableWrapper tableWrapper;

    public SimpleRepository(String repository,String dialect, String tableName, Class<E> clazz, JdbcTemplate jdbcTemplate, PersistentDSLBuilders persistentDSLBuilders) {
        this.repository = repository;
        this.tableName = tableName;
        this.clazz = clazz;
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
        tableManager = new TableManager(dialect,jdbcTemplate.getDataSource());
        this.persistentDSLBuilders = persistentDSLBuilders;
        try {
            Table table = tableManager.findTableByName(tableName);
            tableWrapper = new TableWrapper(table, persistentDSLBuilders.get(dialect).getDialect());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public SimpleRepository( String dialect, String tableName, Class<E> clazz, JdbcTemplate jdbcTemplate, PersistentDSLBuilders persistentDSLBuilders) {
        this( "default", dialect, tableName, clazz, jdbcTemplate ,persistentDSLBuilders);
    }
    public SimpleRepository( String tableName, Class<E> clazz, JdbcTemplate jdbcTemplate, PersistentDSLBuilders persistentDSLBuilders) {
        this( "default", "mysql", tableName, clazz, jdbcTemplate ,persistentDSLBuilders);
    }

    private int update(PersistentCommand cmd){
        int n = jdbcTemplate.update(cmd.getBody(), cmd.argValues());
        return n;
    }

    private List query(PersistentCommand cmd){
        List<Map<String, Object>> list = jdbcTemplate.queryForList(cmd.getBody(), cmd.argValues());
        return list;
    }
    private Map<String,Object> queryOne(PersistentCommand cmd){
        List<Map<String, Object>> list = jdbcTemplate.queryForList(cmd.getBody(), cmd.argValues().toArray());
        return list.stream().findFirst().orElse(null);
    }
    @Override
    public void insert(E entity) {
        Map<String, Object> map = BeanMapperUtil.toSimpleMap(entity);
        PersistentCommand cmd = persistentDSLBuilders.get(dialect)
                .toInsert(tableWrapper, map);
        update(cmd);
    }

    @Override
    public void insert(Collection<E> entities) {
        for (E entity : entities) {
            insert(entity);
        }
    }

    @Override
    public void delete(T id) {
        Condition condition = Condition.is(tableWrapper.findPrimaryColumn(), id);
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toRemove(tableWrapper, condition);
        update(cmd);
    }

    @Override
    public int delete(SearchCondition searchCondition) {
        Condition condition = searchCondition.toCondition();
        return delete(condition);
    }

    @Override
    public int delete(Condition condition) {
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toRemove(tableWrapper, condition);
        return update(cmd);
    }

    @Override
    public int delete(Map<String, Object> searchCondition) {
        Condition condition = ConditionUtils.fromMap(searchCondition, tableWrapper.getColumns());
        return delete(condition);
    }

    @Override
    public void remove(T id) {
        Condition condition = Condition.is(tableWrapper.findPrimaryColumn(), id);
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toRemove(tableWrapper, condition);
        update(cmd);
    }

    @Override
    public int remove(SearchCondition searchCondition) {
        Condition condition = searchCondition.toCondition();
        return remove(condition);
    }

    @Override
    public int remove(Condition condition) {
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toRemove(tableWrapper, condition);
        return update(cmd);
    }

    @Override
    public int remove(Map<String, Object> searchCondition) {
        Condition condition = ConditionUtils.fromMap(searchCondition, tableWrapper.getColumns());
        return remove(condition);
    }

    @Override
    public int deleteAll() {
        Condition condition = ConditionUtils.notDeleted();
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toRemove(tableWrapper, condition);
        return update(cmd);
    }

    @Override
    public int removeAll() {
        Condition condition = ConditionUtils.notDeleted();
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toRemove(tableWrapper, condition);
        return update(cmd);
    }

    @Override
    public void update(E entity) {
        Condition condition = Condition.is(tableWrapper.findPrimaryColumn(), entity.getId());
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toUpdate(tableWrapper, BeanMapperUtil.toSimpleMap(entity), condition);
        update(cmd);
    }

    @Override
    public E findOne(SearchCondition searchCondition) {
        Condition condition = searchCondition.toCondition();
        return findOne(condition);
    }

    @Override
    public E findOne(Condition condition) {
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toQuery(tableWrapper, tableWrapper.getColumnWrappers(), condition);
        Map map = queryOne(cmd);
        if(MotorUtils.isEmpty(map)){
            return null;
        }
        E result = BeanMapperUtil.map(map, clazz);
        return result;
    }

    @Override
    public E findOne(Map<String, Object> searchCondition) {
        Condition condition = ConditionUtils.fromMap(searchCondition, tableWrapper.getColumns());
        return findOne(condition);
    }

    @Override
    public E findById(T id) {
        Condition condition = Condition.is(tableWrapper.findPrimaryColumn(), id);
        return findOne(condition);
    }

    @Override
    public List<E> findByIds(Collection<T> ids) {
        Condition condition = Condition.in(tableWrapper.findPrimaryColumn(), ids);
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toQuery(tableWrapper, tableWrapper.getColumnWrappers(), condition);
        List list = query(cmd);
        if(MotorUtils.isEmpty(list)){
            return null;
        }
        List<E> l = BeanMapperUtil.mapList(list, clazz);
        return l;
    }

    @Override
    public List<E> findByIds(T... ids) {
        return findByIds(Arrays.asList(ids));
    }

    @Override
    public List<E> select(SearchCondition searchCondition) {
        Condition condition = searchCondition.toCondition();
        return select(condition);
    }

    @Override
    public List<E> select(Condition condition) {
        PersistentCommand cmd = persistentDSLBuilders.get(dialect).toQuery(tableWrapper, tableWrapper.getColumnWrappers(), condition);
        List list = query(cmd);
        if(MotorUtils.isEmpty(list)){
            return null;
        }
        List<E> l = BeanMapperUtil.mapList(list, clazz);
        return l;
    }

    @Override
    public List<E> select(Map<String, Object> searchCondition) {
        Condition condition = ConditionUtils.fromMap(searchCondition, tableWrapper.getColumns());
        return select(condition);
    }

    @Override
    public PageList<E> search(SearchCondition searchCondition, Paging paging) {
        Condition condition = searchCondition.toCondition();
        return search(condition, paging);
    }

    @Override
    public PageList<E> search(Condition condition, Paging paging) {
        QueryPageCommand cmd = persistentDSLBuilders.get(dialect)
                .toQueryPage(tableWrapper, tableWrapper.getColumnWrappers(), condition, paging);
        List list = query(cmd.getQueryCommand());
        Map<String, Object> map = queryOne(cmd.getCountCommand());
        Integer count = (Integer)map.getOrDefault("data",0);
        paging.setTotal(count);
        if(MotorUtils.isEmpty(list)){
            return new PageList<>(paging, Collections.EMPTY_LIST);
        }
        List<E> l = BeanMapperUtil.mapList(list, clazz);
        return new PageList<>(paging, l);
    }

    @Override
    public PageList<E> search(Map<String, Object> searchCondition, Paging paging) {
        Condition condition = ConditionUtils.fromMap(searchCondition, tableWrapper.getColumns());
        return search(condition, paging);
    }
}
