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

package net.guerlab.cloud.server.mybatis.plus;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import net.guerlab.cloud.core.sequence.Sequence;

/**
 * 雪花ID构造器.
 *
 * @author guer
 */
public class SnowflakeIdGenerator implements IdentifierGenerator {

	/**
	 * 雪花ID序列.
	 */
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
