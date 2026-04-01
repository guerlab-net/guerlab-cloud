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

package net.guerlab.cloud.context.core;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Nullable;
import lombok.Getter;
import org.slf4j.MDC;

import net.guerlab.cloud.core.Constants;

/**
 * 上下文属性.
 *
 * @author guer
 */
@Getter
public class ContextAttributes extends HashMap<Object, Object> {

	/**
	 * 保存KEY.
	 */
	public static final String KEY = ContextAttributes.class.getName();

	/**
	 * 请求保存KEY.
	 */
	public static final String REQUEST_KEY = KEY + ".request";

	/**
	 * 响应保存KEY.
	 */
	public static final String RESPONSE_KEY = KEY + ".response";

	/**
	 * 创建源.
	 */
	private final String initSource;

	/**
	 * 创建上下文属性.
	 *
	 * @param initSource 创建源
	 */
	public ContextAttributes(String initSource) {
		this.initSource = initSource;
	}

	/**
	 * 创建上下文属性.
	 *
	 * @param parent 上级上下文属性
	 */
	public ContextAttributes(ContextAttributes parent) {
		this.initSource = parent.initSource;
		putAll(parent);
	}

	@Override
	public String toString() {
		return "ContextAttributes[" + initSource + "]: " + super.toString();
	}

	/**
	 * 拷贝当前上下文内容属性到新的上下文属性中.
	 *
	 * @return 新上下文属性
	 */
	public ContextAttributes copy() {
		return new ContextAttributes(this);
	}

	@Nullable
	@Override
	public Object put(Object key, Object value) {
		putToMdc(key, value);
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<?, ?> m) {
		super.putAll(m);

		for (Entry<?, ?> entry : m.entrySet()) {
			putToMdc(entry.getKey(), entry.getValue());
		}
	}

	private void putToMdc(Object key, Object value) {
		if (key instanceof String k && value instanceof String v) {
			MDC.put(k, v);

			if (Constants.REQUEST_TRACE_ID_HEADER.equals(k)) {
				MDC.put(Constants.MDC_TRACE_ID_KEY, v);
			}
		}
	}
}
