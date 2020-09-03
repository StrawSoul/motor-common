package com.motor.common.dsl.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.motor.common.table.utils.ColumnUtils.convertHumpToUnderline;


/**
 * @author: zlj
 * @date: 2019-05-15 下午4:11
 * @description: 排序对象
 */
public class OrderBy implements Serializable{

    private List<KeyValueWeight> orderList;

    private OrderBy() {
        this.orderList = new ArrayList<>();
    }
    public static  OrderBy instance(List<KeyValueWeight> list) {
        OrderBy orderBy = new OrderBy();
        orderBy.setOrderList(list);
        return orderBy;
    }

    public static OrderBy instance(String column, Option order){
        OrderBy orderBy = new OrderBy();
        orderBy.orderBy(column, order);
        return orderBy;
    }
    public OrderBy orderBy(String column, Option order){
        this.orderList.add(new KeyValueWeight(column, order.name()));
        return this;
    }

    public String format(){
        StringBuffer sb = new StringBuffer();
        for (KeyValue keyValue : orderList) {
            if (sb.length()> 0) {
                sb.append(",");
            }
            sb.append(convertHumpToUnderline(keyValue.getName())).append(" ").append(keyValue.getValue());
        }
        return sb.toString();
    }

    public List<KeyValueWeight> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<KeyValueWeight> orderList) {
        this.orderList = orderList;
    }

    public enum Option {
        /**
         * 倒序
         */
        DESC ,

        /**
         *  正序
         */
        ASC
    }
}
