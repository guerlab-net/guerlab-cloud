/*
 * Copyright 2018-2025 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.server.autoconfigure;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis配置.
 *
 * @author guer
 */
@AutoConfiguration
@ConditionalOnClass({DataSource.class, SqlSessionTemplate.class, SqlSessionFactory.class,
		PlatformTransactionManager.class})
@EnableTransactionManagement
public class MyBatisAutoConfigure {

	/**
	 * 默认事务管理器名称.
	 */
	public static final String DEFAULT_TRANSACTION_MANAGER_BEAN_NAME = "defaultTransactionManager";

	/**
	 * 会话模版配置.
	 *
	 * @param sqlSessionFactory 会话工厂
	 * @return 会话模板
	 */
	@Bean
	@ConditionalOnMissingBean(SqlSessionTemplate.class)
	@ConditionalOnBean(SqlSessionFactory.class)
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	/**
	 * 配置默认事务管理器.
	 *
	 * @param dataSource 数据源
	 * @return 事务管理器
	 */
	@Bean(DEFAULT_TRANSACTION_MANAGER_BEAN_NAME)
	@ConditionalOnBean(DataSource.class)
	public PlatformTransactionManager defaultTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
