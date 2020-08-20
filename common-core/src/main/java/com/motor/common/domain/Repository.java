package com.motor.common.domain;

import com.motor.common.paging.PageList;
import com.motor.common.paging.Paging;

import java.util.Collection;
import java.util.List;

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
 * version: 0.0.0  2020/8/20 15:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public interface Repository<T, E extends Entity<T>> {

    void insert(E entity);

    void insert(Collection<E> entities);

    void save(E entity);

    void save(Collection<E> entities);

    void delete(T id);

    int delete(SearchCondition searchCondition);

    void remove(T id);

    int remove(SearchCondition searchCondition);

    int deleteAll();

    int removeAll();

    /**
     *  更行全部内容
     * @param entity
     */
    void update(E entity);

    /**
     *  选择性更新
     * @param entity
     */
    void updateSelective(E entity);

    E findOne(SearchCondition searchCondition);

    E findById(String id);

    List<E> findByIds(Collection<T> ids);

    List<E> findByIds(T... ids);

    List<E> select(SearchCondition searchCondition);

    PageList<E> search(SearchCondition searchCondition, Paging paging);
}
