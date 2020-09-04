package com.motor.common.domain;

import com.motor.common.dsl.bean.Condition;
import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.exception.ErrorCode;
import com.motor.common.paging.PageList;
import com.motor.common.paging.Paging;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
public class UnSupportRepository<T, E extends Entity<T>> implements Repository<T,E> {

    private static ErrorCode UN_SUPPORT_METHOD = new ErrorCode("00000","UN_SUPPORT_METHOD", "未实现的方法");
    @Override
    public void insert(E entity) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public void insert(Collection<E> entities) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public void save(E entity) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public void save(Collection<E> entities) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public void delete(T id) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int delete(SearchCondition searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int delete(Condition searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int delete(Map<String, Object> searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public void remove(T id) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int remove(SearchCondition searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int remove(Condition searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int remove(Map<String, Object> searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int deleteAll() {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int removeAll() {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public void update(E entity) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public void updateSelective(E entity) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public E findOne(SearchCondition searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public E findOne(Condition searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public E findOne(Map<String, Object> searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public E findById(T id) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public List<E> findByIds(Collection<T> ids) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public List<E> findByIds(T... ids) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public List<E> select(SearchCondition searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public List<E> select(Condition searchCondition){
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }
    @Override
    public List<E> select(Map<String,Object>  searchCondition){
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public PageList<E> search(SearchCondition searchCondition, Paging paging) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }
    public PageList<E> search(Condition searchCondition, Paging paging){
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    public PageList<E> search(Map<String,Object> searchCondition, Paging paging){
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int count(Condition searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int count(SearchCondition searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }

    @Override
    public int count(Map<String, Object> searchCondition) {
        throw new BusinessRuntimeException(UN_SUPPORT_METHOD);
    }


}
