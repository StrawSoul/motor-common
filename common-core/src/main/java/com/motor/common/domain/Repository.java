package com.motor.common.domain;

import com.motor.common.dsl.bean.Condition;
import com.motor.common.paging.PageList;
import com.motor.common.paging.Paging;
import com.motor.common.utils.MotorUtils;

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

    default void save(E entity){

        T id = entity.getId();
        if (existsId(id)) {
            update(entity);
        } else {
            insert(entity);
        }
    }

    default void save(Collection<E> entities){
        List<E> insertList = new ArrayList<>();
        Map<T,E> updateMap = new HashMap<>();
        for (E entity : entities) {
            if (MotorUtils.isEmpty(entity.getId())) {
                insertList.add(entity);
            } else {
                updateMap.put(entity.getId(), entity);
            }
        }
        if(!MotorUtils.isEmpty(insertList)){
            insert(insertList);
        }
        if(!MotorUtils.isEmpty(updateMap)){
            Set<T> idSet = updateMap.keySet();
            List<E> oldList = findByIds(idSet);
            for (E oldEntity : oldList) {
                T id = oldEntity.getId();
                E entity = updateMap.get(id);
                update(entity);
                updateMap.remove(id);
            }
            if(!MotorUtils.isEmpty(updateMap)){
                insert(updateMap.values());
            }
        }
    }

    default boolean existsId(T id){
        return !MotorUtils.isEmpty(id)  || findById(id) != null;
    }

    void delete(T id);

    int delete(SearchCondition searchCondition);

    int delete(Condition searchCondition);

    int delete(Map<String,Object> searchCondition);

    void remove(T id);

    int remove(SearchCondition searchCondition);

    int remove(Condition searchCondition);

    int remove(Map<String,Object>  searchCondition);

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

    E findOne(Condition searchCondition);

    E findOne(Map<String,Object> searchCondition);

    E findById(T id);

    List<E> findByIds(Collection<T> ids);

    List<E> findByIds(T... ids);

    List<E> select(SearchCondition searchCondition);

    List<E> select(Condition searchCondition);

    List<E> select(Map<String,Object>  searchCondition);

    PageList<E> search(SearchCondition searchCondition, Paging paging);

    PageList<E> search(Condition searchCondition, Paging paging);

    PageList<E> search(Map<String,Object>  searchCondition, Paging paging);
}
