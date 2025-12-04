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

package net.guerlab.commons.time.jackson.serializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.annotation.Nullable;

/**
 * 时间序列化处理.
 *
 * @author guer
 */
public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Override
	public void serialize(@Nullable LocalTime localTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (localTime == null) {
			gen.writeNull();
			return;
		}

		String value = localTime.format(FORMATTER);

		gen.writeString(value);
	}
}
