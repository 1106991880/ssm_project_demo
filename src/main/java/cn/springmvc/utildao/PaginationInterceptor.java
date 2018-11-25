package cn.springmvc.utildao;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin
	 * .Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		try {
			StatementHandler statementHandler = (StatementHandler) invocation
					.getTarget();
			MetaObject metaStatementHandler = MetaObject.forObject(
					statementHandler, new DefaultObjectFactory(),
					new DefaultObjectWrapperFactory());

			RowBounds rowBounds = (RowBounds) metaStatementHandler
					.getValue("delegate.rowBounds");
			if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
				return invocation.proceed();
			}

			DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler) metaStatementHandler
					.getValue("delegate.parameterHandler");
			Object object = defaultParameterHandler.getParameterObject();

			Object sidx = null;
			Object sord = null;

			if (object instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<Object, Object> parameterMap = (Map<Object, Object>) object;

				if (parameterMap != null) {
					sidx = parameterMap.get("_sidx");
					sord = parameterMap.get("_sord");
				}
			}

			String originalSql = (String) metaStatementHandler
					.getValue("delegate.boundSql.sql");

			if (sidx != null && sord != null) {
				originalSql = originalSql + " order by " + sidx + " " + sord;
			}

			Configuration configuration = (Configuration) metaStatementHandler
					.getValue("delegate.configuration");

			Dialect.Type databaseType = null;
			try {
				databaseType = Dialect.Type.valueOf(configuration
						.getVariables().getProperty("dialect").toUpperCase());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (databaseType == null) {
				throw new RuntimeException(
						"the value of the dialect property in configuration.xml is not defined : "
								+ configuration.getVariables().getProperty(
										"dialect"));
			}
			Dialect dialect = null;
			switch (databaseType) {
			case ORACLE:
				break;
			case MYSQL:
				dialect = new MySQLDialect();
				break;

			}

			metaStatementHandler.setValue("delegate.boundSql.sql", dialect
					.getLimitString(originalSql, rowBounds.getOffset(),
							rowBounds.getLimit()));
			metaStatementHandler.setValue("delegate.rowBounds.offset",
					RowBounds.NO_ROW_OFFSET);
			metaStatementHandler.setValue("delegate.rowBounds.limit",
					RowBounds.NO_ROW_LIMIT);
			statementHandler.getBoundSql();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return invocation.proceed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties properties) {

	}

	final Logger logger = Logger.getLogger(PaginationInterceptor.class);
}
