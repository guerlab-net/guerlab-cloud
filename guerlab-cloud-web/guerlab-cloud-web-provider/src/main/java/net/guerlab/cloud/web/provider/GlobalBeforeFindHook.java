package net.guerlab.cloud.web.provider;

import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 全局查询前置处理钩子.
 *
 * @author guer
 */
public interface GlobalBeforeFindHook {

	/**
	 * 是否允许处理该对象.
	 *
	 * @param searchParams 搜索参数对象
	 * @return 是否允许处理
	 */
	boolean accept(SearchParams searchParams);

	/**
	 * 搜索前置处理.
	 *
	 * @param searchParams 搜索参数对象
	 */
	void handler(SearchParams searchParams);
}
