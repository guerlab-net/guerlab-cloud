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

package net.guerlab.cloud.server.autoconfigure;

import com.baomidou.mybatisplus.autoconfigure.IdentifierGeneratorAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

import net.guerlab.cloud.core.sequence.Sequence;
import net.guerlab.cloud.server.mybatis.plus.metadata.BaseEntityMetaObjectHandler;
import net.guerlab.cloud.server.mybatis.plus.methods.AutoLoadMethodLoader;

/**
 * mybatis-plus自动配置.
 *
 * @author guer
 */
@AutoConfiguration(before = IdentifierGeneratorAutoConfiguration.class)
public class MybatisPlusAutoConfigure {

	/**
	 * 构造mybatisPlus拦截器.
	 *
	 * @return mybatisPlus拦截器
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		return interceptor;
	}

	/**
	 * 构造基础对象元信息处理.
	 *
	 * @return 基础对象元信息处理
	 */
	@Bean
	public MetaObjectHandler baseEntityMetaObjectHandler() {
		return new BaseEntityMetaObjectHandler();
	}

	/**
	 * 构造自动加载注入方法加载器.
	 *
	 * @return 自动加载注入方法加载器
	 */
	@Bean
	public AutoLoadMethodLoader autoLoadMethodLoader() {
		return new AutoLoadMethodLoader();
	}

	/**
	 * 构造雪花ID生成器.
	 *
	 * @param sequence 雪花ID序列
	 * @return 雪花ID生成器
	 */
	@Bean
	@ConditionalOnBean(Sequence.class)
	public SnowflakeIdGenerator snowflakeIdGenerator(Sequence sequence) {
		return new SnowflakeIdGenerator(sequence);
	}

	/**
	 * 雪花ID构造器.
	 */
	public static class SnowflakeIdGenerator implements IdentifierGenerator {

		private final Sequence sequence;

		/**
		 * 通过雪花ID序列进行初始化.
		 *
		 * @param sequence 雪花ID序列
		 */
		public SnowflakeIdGenerator(Sequence sequence) {
			this.sequence = sequence;
		}

		@Override
		public Long nextId(Object entity) {
			return sequence.nextId();
		}
	}
}
