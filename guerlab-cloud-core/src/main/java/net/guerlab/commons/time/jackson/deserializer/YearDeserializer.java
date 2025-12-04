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

package net.guerlab.commons.time.jackson.deserializer;

import java.io.IOException;
import java.time.Year;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import jakarta.annotation.Nullable;

/**
 * 年份反序列化处理.
 *
 * @author guer
 */
public class YearDeserializer extends JsonDeserializer<Year> {

	@Override
	@Nullable
	public Year deserialize(JsonParser jp, DeserializationContext context) throws IOException {
		return Year.of(jp.getValueAsInt());
	}
}
