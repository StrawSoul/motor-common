package com.motor.common.table.constants;

/**
 * @author zlj
 */

public enum ConditionType {
    /**
     *  等于， 相当于 equals
     */
    is,
    /**
     *  不等于
     */
    not,
    /**
     *  大于
     */
    gt,
    /**
     * 小于
     */
    lt,
    // 大于等于
    gte,
    // 小鱼等于
    lte,
    //包含，  1,2,3,4
    in,
    // 不包含
    notin,
    /**
     *  双侧模糊匹配
     */
    like,
    /**
     *  左侧模糊匹配
     */
    left,

    /**
     * 右侧模糊匹配
     */
    right,

    /**
     *  非空
     */
    notnull,
    /**
     *  为空
     */
    isnull

}
