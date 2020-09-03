package com.motor.common.domain;

import com.motor.common.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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
 * version: 0.0.0  2020/8/31 10:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class DefaultPrimaryKeyProducer implements PrimaryKeyProducer {

    private Map<String,AtomicLong> cache = new ConcurrentHashMap<>();

    private int  length = 6;

    private String domain;

    public DefaultPrimaryKeyProducer() {
        domain = randomStr(3);
    }

    private long getAtomicId(AtomicLong compute){
        long atomicId = compute.addAndGet(1);
        if(String.valueOf(atomicId).length()> length()){
            compute.set(1);
            atomicId = 1;
        }
        return atomicId;
    }
    private long getAtomicId(String prefix){
        AtomicLong atomicLong = getAtomicLong(prefix);
        return getAtomicId(atomicLong);
    }
    private AtomicLong getAtomicLong(String prefix ){
        AtomicLong compute = cache.compute(prefix, (k, v) -> {
            if (v == null) {
                return new AtomicLong(0);
            }
            return v;
        });
        return compute;
    }

    @Override
    public String produce(String prefix) {
        long atomicId = getAtomicId(prefix);
        String id = formatId(prefix, atomicId);
        return id;
    }
    private String formatId(String prefix, long atomicId){
        String idLast = fixId(String.valueOf(atomicId), length());
        LocalDateTime now = LocalDateTime.now();
        String idMiddle = DateUtils.format(now, "yyyyMMddhhmmss");
        String id = prefix+  idMiddle + domainId()+idLast ;
        return id;
    }

    public String fixId(String id, int l){
        int lId = id.length();
        if(lId == l){
            return id;
        } else if(lId < l){
            StringBuffer sb = new StringBuffer();
            int n = l - lId;
            for (int i = 0; i < n; i++) {
                sb.append(0);
            }
            return sb.append(id).toString();
        } else {
            throw new RuntimeException("id is too lang to fixed");
        }
    }
    public String randomStr(int n){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            int random = (int)(Math.random() *10);
            sb.append(random);
        }
        return sb.toString();
    }

    @Override
    public String[] produce(String prefix, int n) {
        String[] arr = new String[n];
        for (int i = 0; i < n; i++) {
            AtomicLong atomicLong = getAtomicLong(prefix);
            long atomicId = getAtomicId(atomicLong);
            arr[i] = formatId(prefix, atomicId);
        }
        return arr;
    }

    protected String domainId(){
        return domain;
    }

    protected int length(){
        return length;
    }

    public static void main(String[] args) {
//        test1();
        test2();
    }

    public static void test1(){
        DefaultPrimaryKeyProducer producer = new DefaultPrimaryKeyProducer();
        long l = System.currentTimeMillis();
        HashSet set = new HashSet();
        for (int i = 0; i < 500000; i++) {
            String u = producer.produce("U");
            set.add(u);
        }
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(set.size());
    }

    public static void test2(){
        DefaultPrimaryKeyProducer producer = new DefaultPrimaryKeyProducer();
        long l = System.currentTimeMillis();
        String[] us = producer.produce("U", 120000);
        System.out.println(System.currentTimeMillis() - l);
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(us));
        System.out.println(hashSet.size());
        System.out.println(us[us.length-5]);
        System.out.println(us[us.length-4]);
        System.out.println(us[us.length-3]);
        System.out.println(us[us.length-2]);
        System.out.println(us[us.length-1]);
    }

}
