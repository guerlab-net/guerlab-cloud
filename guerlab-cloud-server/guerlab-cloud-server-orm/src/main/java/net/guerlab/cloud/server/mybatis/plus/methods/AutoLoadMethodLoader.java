/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

package net.guerlab.cloud.server.mybatis.plus.methods;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

/**
 * 自动加载注入方法加载器.
 *
 * @author guer
 */
public class AutoLoadMethodLoader extends DefaultSqlInjector {

	private final List<AbstractAutoLoadMethod> methods;

	/**
	 * 初始化自动加载注入方法加载器.
	 */
	public AutoLoadMethodLoader() {
		methods = StreamSupport.stream(ServiceLoader.load(AbstractAutoLoadMethod.class).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
		List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
		methodList.addAll(methods);
		return methodList;
	}
}
