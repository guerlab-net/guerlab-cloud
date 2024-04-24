/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.searchparams.mybatisplus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

import org.springframework.core.Ordered;

/**
 * 数据库类型工具类.
 *
 * @author guer
 */
public final class DbTypeUtils {

	private static List<DbTypeProvider> dbTypeProviders;

	static {
		dbTypeProviders = ServiceLoader.load(DbTypeProvider.class).stream()
				.map(ServiceLoader.Provider::get)
				.sorted(Comparator.comparingInt(Ordered::getOrder)).toList();
	}

	private DbTypeUtils() {

	}

	public static void addProvider(DbTypeProvider provider) {
		if (dbTypeProviders.stream().anyMatch(p -> p.getClass().isInstance(provider))) {
			return;
		}

		List<DbTypeProvider> providers = new ArrayList<>(dbTypeProviders);
		providers.add(provider);
		dbTypeProviders = providers.stream().sorted(Comparator.comparingInt(Ordered::getOrder)).toList();
	}

	public static void removeProvider(Class<? extends DbTypeProvider> providerClass) {
		dbTypeProviders = dbTypeProviders.stream().filter(p -> !providerClass.isInstance(p))
				.sorted(Comparator.comparingInt(Ordered::getOrder)).toList();
	}

	/**
	 * 根据对象获取该对象的数据库类型.
	 *
	 * @param object 对象
	 * @return 数据库类型
	 */
	public static DbType getDbType(Object object) {
		DbType dbType;
		for (DbTypeProvider dbTypeProvider : dbTypeProviders) {
			dbType = dbTypeProvider.getDbType(object);
			if (dbType != null) {
				return dbType;
			}
		}
		return DbType.OTHER;
	}
}
