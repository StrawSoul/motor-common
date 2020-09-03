package com.motor.common.mybatis;

import com.motor.common.paging.PageList;
import com.motor.common.paging.Paging;
import com.motor.common.paging.PagingRequest;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


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
 * version: 0.0.0  2020/9/1 11:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)})
public class MotorMybatisPagingInterceptor implements Interceptor {

    private final static String COUNT_SUFFIX = "_COUNT";
    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList(0);
    private Map<String,MappedStatement> countMappedStatementCache = new ConcurrentHashMap<>(16);

    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement)args[0];
        Object parameterObject = args[1];
        RowBounds rowBounds = (RowBounds)args[2];
        ResultHandler resultHandler = (ResultHandler)args[3];
        String mid = mappedStatement.getId();
        if(mid.matches(".+ByPage$") || mid.matches(".+WithPage$")){
            Configuration configuration = mappedStatement.getConfiguration();
            BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);

            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

            String sql = boundSql.getSql();
            Paging paging = getPaging(parameterObject);
            String sqlLimit = sql + " limit "+ paging.offset() + ","+ paging.limit();
            BoundSql boundSqlLimit = new BoundSql(configuration, sqlLimit,parameterMappings, parameterObject );
            CacheKey countKey1 = executor.createCacheKey(mappedStatement, parameterObject, RowBounds.DEFAULT, boundSqlLimit);
            List list = executor.query(mappedStatement, parameterObject, rowBounds == null ? RowBounds.DEFAULT : rowBounds, resultHandler,countKey1,boundSqlLimit);



            int index = sql.indexOf("from");
            if(index< 0){
                index = sql.indexOf("FROM");
            }
            String countMid = mid + COUNT_SUFFIX;
            MappedStatement countMappedStatement = countMappedStatementCache.computeIfAbsent(countMid, (k)-> newCountMappedStatement(mappedStatement, countMid));
            String sqlCount = "select count(1) c "+ sql.substring(index);
            BoundSql boundSqlCount = new BoundSql(configuration, sqlCount,parameterMappings, parameterObject );
            CacheKey countKey = executor.createCacheKey(countMappedStatement, parameterObject, RowBounds.DEFAULT, boundSqlCount);
            List result = executor.query(countMappedStatement, parameterObject,  RowBounds.DEFAULT , resultHandler,countKey, boundSqlCount);
            Integer count = (Integer) result.get(0);
            paging.setTotal(count.intValue());
            return Arrays.asList(new PageList<>(paging, list));
        }

        return invocation.proceed();
    }

    private Paging getPaging(Object parameterObject){
        Paging paging = null;
        if(parameterObject instanceof Paging){
            paging = (Paging) parameterObject;
        } else if(parameterObject instanceof PagingRequest){
            paging = ((PagingRequest)parameterObject).toPaging();
        } else if(parameterObject instanceof Map){

            Map<String,Object> map = (Map<String,Object>)parameterObject;
            paging = (Paging) map.get("paging");
            if(paging == null){
                for (Object value : map.values()) {
                    if (value instanceof Paging) {
                        paging = (Paging)value;
                        break;
                    }
                }
            }

            if(paging == null){
                Integer pageNumber = (Integer)map.getOrDefault("pageNumber", 1);
                Integer pageSize = (Integer)map.getOrDefault("pageSize", 10);
                paging = new Paging(pageNumber, pageSize, 0);
            }

        }
        if(paging == null){
            paging = new Paging(1,10,0);
        }
        return paging;
    }
    @Override
    public void setProperties(Properties properties) {
        properties.setProperty("a","1");
    }

    public static MappedStatement newCountMappedStatement(MappedStatement ms, String newMsId) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), newMsId, ms.getSqlSource(), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            String[] var4 = ms.getKeyProperties();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String keyProperty = var4[var6];
                keyProperties.append(keyProperty).append(",");
            }

            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }

        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        List<ResultMap> resultMaps = new ArrayList();
        ResultMap resultMap = (new ResultMap.Builder(ms.getConfiguration(), ms.getId(), Integer.class, EMPTY_RESULTMAPPING)).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }
}
