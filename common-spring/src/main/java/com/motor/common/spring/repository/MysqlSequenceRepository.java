package com.motor.common.spring.repository;

import com.motor.common.sequence.SequenceRepository;
import com.motor.common.sequence.SysSeq;
import com.motor.common.utils.BeanMapperUtil;
import com.motor.common.utils.M;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
 * version: 0.0.0  2020/9/4 10:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class MysqlSequenceRepository implements SequenceRepository {

    private final static String T_SYS_SEQ = "sys_seq";
    private final static String SQL_CURRENT_SEQ = "select * from "+ T_SYS_SEQ+ " where code = '%s'";
    private final static String SQL_CREATE_SEQ = "insert into "+ T_SYS_SEQ+ " (code, value) values ('%s', 1)";
    private final static String SQL_SET_SEQ = "update "+ T_SYS_SEQ+ " set value = %d where code = %s";
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public SysSeq next(String code){
        List<Map<String, Object>> list = jdbcTemplate.queryForList(String.format(SQL_CURRENT_SEQ, code));
        if (M.isEmpty(list)) {
            int n = jdbcTemplate.update(String.format(SQL_CREATE_SEQ, code));
            if(n >0){
                return new SysSeq(code, 1l);
            }
            throw new RuntimeException(" sys seq create failed !!");
        } else {
            Map<String, Object> map = list.stream().findFirst().get();
            SysSeq seq = BeanMapperUtil.map(map, SysSeq.class);
            long value = seq.getValue();
            seq.setValue(++value);
            int n = jdbcTemplate.update(String.format(SQL_SET_SEQ, value, code));
            if(n >0){
                return seq;
            }
            throw new RuntimeException(" sys seq update failed !!");
        }
    }
}
