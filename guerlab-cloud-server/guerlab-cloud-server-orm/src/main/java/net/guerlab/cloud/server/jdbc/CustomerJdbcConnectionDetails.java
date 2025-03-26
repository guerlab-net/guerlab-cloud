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

package net.guerlab.cloud.server.jdbc;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;

/**
 * jdbc链接信息.
 *
 * @author guer
 */
public class CustomerJdbcConnectionDetails implements JdbcConnectionDetails {

	private final DataSourceProperties properties;

	public CustomerJdbcConnectionDetails(DataSourceProperties properties) {
		this.properties = properties;
	}

	@Override
	public String getUsername() {
		return this.properties.determineUsername();
	}

	@Override
	public String getPassword() {
		return this.properties.determinePassword();
	}

	@Override
	public String getJdbcUrl() {
		return this.properties.determineUrl();
	}

	@Override
	public String getDriverClassName() {
		return this.properties.determineDriverClassName();
	}

	@Override
	public String getXaDataSourceClassName() {
		return (this.properties.getXa().getDataSourceClassName() != null)
				? this.properties.getXa().getDataSourceClassName()
				: JdbcConnectionDetails.super.getXaDataSourceClassName();
	}

}
