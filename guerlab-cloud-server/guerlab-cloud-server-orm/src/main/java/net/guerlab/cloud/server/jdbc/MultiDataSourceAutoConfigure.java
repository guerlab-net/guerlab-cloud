/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

package net.guerlab.cloud.server.jdbc;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 多数据源自动配置.
 *
 * @author guer
 */
@Slf4j
@AutoConfiguration(
		before = DataSourceAutoConfiguration.class
)
@EnableConfigurationProperties({
		DataSourceProperties.class, MultiDataSourceProperties.class
})
@ConditionalOnClass(DataSource.class)
@ConditionalOnProperty(prefix = MultiDataSourceProperties.PREFIX, name = "enabled", havingValue = "true")
public class MultiDataSourceAutoConfigure {

	/**
	 * 数据源配置.
	 */
	private final DataSourceProperties dataSourceProperties;

	/**
	 * 多数据源配置.
	 */
	private final MultiDataSourceProperties multiDataSourceProperties;

	/**
	 * 创建多数据源自动配置.
	 *
	 * @param dataSourceProperties      数据源配置
	 * @param multiDataSourceProperties 多数据源配置
	 */
	public MultiDataSourceAutoConfigure(DataSourceProperties dataSourceProperties, MultiDataSourceProperties multiDataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
		this.multiDataSourceProperties = multiDataSourceProperties;
	}

	/**
	 * 创建数据源上下文保持.
	 *
	 * @return 数据源上下文保持
	 */
	@Bean
	public DataSourceContextHolder dataSourceContextHolder() {
		return new DataSourceContextHolder(multiDataSourceProperties.getDefaultDataSource());
	}

	/**
	 * 创建动态数据源.
	 *
	 * @param dataSourceContextHolder 数据源上下文保持
	 * @return 动态数据源
	 */
	@Primary
	@Bean(name = "dynamicDataSource")
	public DataSource dynamicDataSource(DataSourceContextHolder dataSourceContextHolder) {
		String defaultDataSourceKey = multiDataSourceProperties.getDefaultDataSource();
		Assert.hasText(defaultDataSourceKey, "cannot found default dataSource properties[" + MultiDataSourceProperties.PREFIX + ".default-data-source]");

		Class<? extends DataSource> defaultDataSourceType = getDefaultDataSourceType();
		ClassLoader classLoader = dataSourceProperties.getClassLoader();

		Map<String, DataSourceProperties> sourceMap = multiDataSourceProperties.getSources();
		if (CollectionUtils.isEmpty(sourceMap)) {
			throw new IllegalArgumentException("multi dataSource configure cannot be empty[" + MultiDataSourceProperties.PREFIX + ".sources]");
		}

		Map<Object, Object> dataSourceMap = new HashMap<>();
		for (Map.Entry<String, DataSourceProperties> entry : sourceMap.entrySet()) {
			String sourceKey = entry.getKey();
			DataSourceProperties sourceProperties = entry.getValue();

			Assert.hasText(sourceProperties.getUrl(), "jdbcUrl is empty, source is \"%s\"".formatted(sourceKey));

			JdbcConnectionDetails connectionDetails = new CustomerJdbcConnectionDetails(sourceProperties);

			DataSource dataSource = createDataSource(connectionDetails, defaultDataSourceType, classLoader);
			BeanUtils.copyProperties(sourceKey, dataSource);
			dataSourceMap.put(sourceKey, dataSource);

			log.debug("add multi dataSource, source \"{}\"", sourceKey);
		}

		DataSource defaultDataSource = (DataSource) dataSourceMap.get(defaultDataSourceKey);
		if (defaultDataSource == null) {
			throw new RuntimeException("cannot found default dataSource");
		}

		DynamicDataSource dynamicDataSource = new DynamicDataSource(dataSourceContextHolder);
		dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
		dynamicDataSource.setTargetDataSources(dataSourceMap);
		return dynamicDataSource;
	}

	/**
	 * 获取默认数据源类型.
	 *
	 * @return 默认数据源类型
	 */
	private Class<? extends DataSource> getDefaultDataSourceType() {
		Class<? extends DataSource> type = dataSourceProperties.getType();
		if (type == null) {
			type = HikariDataSource.class;
		}
		return type;
	}

	private static <T extends DataSource> T createDataSource(JdbcConnectionDetails connectionDetails, Class<T> type,
			ClassLoader classLoader) {
		return DataSourceBuilder.create(classLoader)
				.type(type)
				.driverClassName(connectionDetails.getDriverClassName())
				.url(connectionDetails.getJdbcUrl())
				.username(connectionDetails.getUsername())
				.password(connectionDetails.getPassword())
				.build();
	}

}
