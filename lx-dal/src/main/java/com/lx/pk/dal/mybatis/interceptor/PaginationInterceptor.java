
package com.lx.pk.dal.mybatis.interceptor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import com.lx.pk.client.common.result.LxPageResult;
import com.lx.pk.dal.mybatis.ReflectHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;


/**
 * @ClassName: PaginationInterceptor
 */
@SuppressWarnings("all")
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PaginationInterceptor implements Interceptor {

    private static String dialect = "mysql"; // 数据库方言
    private static String pageSqlId = ""; // mapper.xml中需要拦截的ID(正则匹配)

    public Object intercept(Invocation ivk) throws Throwable {
        if (ivk.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");

            if (mappedStatement.getId().matches(pageSqlId)) { // 拦截需要分页的SQL
                BoundSql boundSql = delegate.getBoundSql();
                Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
                if (parameterObject == null) {
                    throw new NullPointerException("parameterObject尚未实例化！");
                } else {

                    LxPageResult pageResult = null;
                    if (parameterObject instanceof LxPageResult) { // 参数就是Page实体
                        pageResult = (LxPageResult) parameterObject;
                    } else { // 参数为某个实体，该实体拥有Pages属性
                        Field pageField = ReflectHelper.getFieldByFieldName(parameterObject, "page");
                        if (pageField != null) {
                            pageResult = (LxPageResult) ReflectHelper.getValueByFieldName(parameterObject, "page");
                            if (pageResult == null) {
                                return ivk.proceed();
                            }
                        } else {
                            // 不存在page属性
                            return ivk.proceed();
                        }
                    }
                    Connection connection = (Connection) ivk.getArgs()[0];
                    String sql = boundSql.getSql();
                    String countSql = "select count(0) from (" + sql + ") tmp_count"; // 记录统计
                    PreparedStatement countStmt = connection.prepareStatement(countSql);
                    ReflectHelper.setValueByFieldName(boundSql, "sql", countSql);
                    DefaultParameterHandler ParameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
                    ParameterHandler.setParameters(countStmt);
                    ResultSet rs = countStmt.executeQuery();
                    int count = 0;
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                    rs.close();
                    countStmt.close();
                    pageResult.setTotalRows(count);
                    String pageSql = generatePagesSql(sql, pageResult);
                    ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.

                }
            }
        }
        return ivk.proceed();
    }

    /**
     * 根据数据库方言，生成特定的分页sql
     *
     * @param sql
     * @param pageResult
     * @return
     */
    private String generatePagesSql(String sql, LxPageResult pageResult) {
        if (pageResult != null && StringUtils.isNotEmpty(dialect)) {
            StringBuffer pageSql = new StringBuffer();
            if ("mysql".equals(dialect)) {
                pageSql.append(sql);
                pageSql.append(" limit " + (pageResult.getPageNum() - 1) * pageResult.getPageSize() + "," + pageResult.getPageSize());
            } else if ("oracle".equals(dialect)) {
                pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
                pageSql.append(sql);
                pageSql.append(") tmp_tb where ROWNUM<=");
                pageSql.append(pageResult.getPageNum() + pageResult.getPageSize());
                pageSql.append(") where row_id>");
                pageSql.append((pageResult.getPageNum() - 1) * pageResult.getPageSize());
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        if (StringUtils.isEmpty(dialect)) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
        pageSqlId = p.getProperty("pageSqlId");
        if (StringUtils.isEmpty(pageSqlId)) {
            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
    }
}
