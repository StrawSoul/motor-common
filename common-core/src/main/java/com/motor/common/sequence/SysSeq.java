package com.motor.common.sequence;

import com.motor.common.domain.BaseEntity;

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
 * version: 0.0.0  2020/9/4 11:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class SysSeq extends BaseEntity<Integer> {
    static char[] digits ="0123456789ABCDEFGHJKLMNPRSTVWXYZ".toCharArray();
    private Long value;

    public SysSeq(String code, Long value) {
        this.setCode(code);
        this.value = value;
    }

    public SysSeq() {
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    static String digits32(long val) {
        // 32=2^5=二进制100000
        int shift = 5;
        // numberOfLeadingZeros 获取long值从高位连续为0的个数，比如val=0，则返回64
        // 此处mag=long值二进制减去高位0之后的长度
        int mag = Long.SIZE - Long.numberOfLeadingZeros(val);
        int len = Math.max(((mag + (shift - 1)) / shift), 1);
        char[] buf = new char[len];
        do {
            // &31相当于%32
            buf[--len] = digits[((int) val) & 31];
            val >>>= shift;
        } while (val != 0 && len > 0);
        return new String(buf);
    }

    public String toValue32(){
        String str = digits32(value);
        return str;
    }

    public static void main(String[] args) {
        String s = digits32(33);
        System.out.println(s);
    }

}
