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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.annotation.Nullable;

/**
 * 日期序列化处理.
 *
 * @author guer
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

	private static final LocalTime TIME = LocalTime.of(0, 0, 0, 0);

	@Override
	public void serialize(@Nullable LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		LocalDateTime dateTime = LocalDateTime.of(value, TIME);

		long time = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

		gen.writeNumber(time);
	}
}
