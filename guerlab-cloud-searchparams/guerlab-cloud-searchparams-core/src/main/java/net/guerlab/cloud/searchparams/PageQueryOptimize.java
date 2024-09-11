package net.guerlab.cloud.searchparams;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

/**
 * 分页查询优化.
 *
 * @author guer
 */
@Schema(name = "PageQueryOptimize", description = "分页查询优化")
public interface PageQueryOptimize extends SearchParams {

	/**
	 * 是否允许查询总数.
	 *
	 * @return 允许查询总数
	 */
	@Nullable
	default Boolean allowSelectCount() {
		return null;
	}

}
