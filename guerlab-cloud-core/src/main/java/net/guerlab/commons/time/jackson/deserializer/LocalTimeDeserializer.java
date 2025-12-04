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
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import jakarta.annotation.Nullable;

import net.guerlab.commons.time.TimeHelper;

/**
 * 时间反序列化处理.
 *
 * @author guer
 */
public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

	@Override
	@Nullable
	public LocalTime deserialize(JsonParser jp, DeserializationContext context) throws IOException {
		return TimeHelper.parseLocalTime(jp.getValueAsString());
	}
}
