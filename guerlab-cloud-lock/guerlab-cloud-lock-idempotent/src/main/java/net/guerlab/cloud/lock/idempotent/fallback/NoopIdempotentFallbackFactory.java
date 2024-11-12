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

package net.guerlab.cloud.lock.idempotent.fallback;

import net.guerlab.cloud.lock.core.fallback.NoopFallbackFactory;

/**
 * 处理幂等快速失败处理工厂的无操作实现.
 *
 * @author guer
 */
public final class NoopIdempotentFallbackFactory extends NoopFallbackFactory implements IdempotentFallbackFactory { }
