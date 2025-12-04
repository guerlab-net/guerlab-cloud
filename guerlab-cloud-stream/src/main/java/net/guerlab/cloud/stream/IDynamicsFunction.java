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

package net.guerlab.cloud.stream;

import java.lang.reflect.InvocationTargetException;

/**
 * 动态方法接口.
 *
 * @author guer
 */
public interface IDynamicsFunction {

	/**
	 * 调用.
	 *
	 * @param input 输入
	 * @throws InstantiationException      如果声明底层构造函数的类是一个抽象类.
	 * @throws IllegalAccessException      如果内部指向的构造函数对象正在执行Java语言的访问控制，且底层构造函数不可访问.
	 * @throws IllegalArgumentException    如果实际参数和形式参数的数量不同；如果基本类型参数的拆包转换失败；或者，在可能的拆包之后，参数值无法通过方法调用转换转换为相应的形式参数类型；或者，此构造函数与枚举类相关.
	 * @throws InvocationTargetException   如果底层构造函数抛出异常.
	 * @throws ExceptionInInitializerError 如果此方法触发的初始化失败.
	 */
	void invoke(Object input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
