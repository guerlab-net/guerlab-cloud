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

package net.guerlab.cloud.server.mybatis.plus.methods;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.session.Configuration;

/**
 * 自动加载注入方法加载器.
 *
 * @author guer
 */
public class AutoLoadMethodLoader extends DefaultSqlInjector {

	@SuppressWarnings("rawtypes")
	private final List<AbstractAutoLoadMethod> methods;

	/**
	 * 初始化自动加载注入方法加载器.
	 */
	public AutoLoadMethodLoader() {
		methods = StreamSupport.stream(ServiceLoader.load(AbstractAutoLoadMethod.class).spliterator(), false).toList();
	}

	@Override
	public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
		List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
		methodList.addAll(methods);
		return methodList;
	}
}
