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

package net.guerlab.cloud.core.autoconfigure;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.annotation.Nullable;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import net.guerlab.cloud.core.properties.NumberJsonStringFormatProperties;
import net.guerlab.cloud.core.util.SpringUtils;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.time.jackson.deserializer.DateDeserializer;
import net.guerlab.commons.time.jackson.deserializer.LocalDateDeserializer;
import net.guerlab.commons.time.jackson.deserializer.LocalDateTimeDeserializer;
import net.guerlab.commons.time.jackson.deserializer.LocalTimeDeserializer;
import net.guerlab.commons.time.jackson.deserializer.MonthDeserializer;
import net.guerlab.commons.time.jackson.deserializer.YearDeserializer;
import net.guerlab.commons.time.jackson.serializer.DateSerializer;
import net.guerlab.commons.time.jackson.serializer.LocalDateSerializer;
import net.guerlab.commons.time.jackson.serializer.LocalDateTimeSerializer;
import net.guerlab.commons.time.jackson.serializer.LocalTimeSerializer;
import net.guerlab.commons.time.jackson.serializer.MonthSerializer;
import net.guerlab.commons.time.jackson.serializer.YearSerializer;

/**
 * ObjectMapper配置.
 *
 * @author guer
 */
@AutoConfiguration
@EnableConfigurationProperties(NumberJsonStringFormatProperties.class)
public class ObjectMapperAutoConfigure {

	/**
	 * 设置ObjectMapper属性.
	 *
	 * @param objectMapper objectMapper
	 * @param properties   数值json格式化配置
	 */
	public static void setProperties(ObjectMapper objectMapper, NumberJsonStringFormatProperties properties) {
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Date.class, new DateDeserializer());
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
		module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
		module.addDeserializer(Month.class, new MonthDeserializer());
		module.addDeserializer(Year.class, new YearDeserializer());

		module.addSerializer(Date.class, new DateSerializer());
		module.addSerializer(LocalDate.class, new LocalDateSerializer());
		module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		module.addSerializer(LocalTime.class, new LocalTimeSerializer());
		module.addSerializer(Month.class, new MonthSerializer());
		module.addSerializer(Year.class, new YearSerializer());

		moduleAdvice(module, properties);

		objectMapper.findAndRegisterModules();
		objectMapper.registerModule(module);
	}

	private static void moduleAdvice(SimpleModule module, @Nullable NumberJsonStringFormatProperties properties) {
		if (properties == null) {
			return;
		}

		ToStringSerializer serializer = ToStringSerializer.instance;

		if (properties.isFormatAllNumber()) {
			module.addSerializer(Number.class, serializer);
		}
		if (properties.isFormatBigDecimal()) {
			module.addSerializer(BigDecimal.class, serializer);
		}
		if (properties.isFormatBigInteger()) {
			module.addSerializer(BigInteger.class, serializer);
		}
		if (properties.isFormatByteClass()) {
			module.addSerializer(Byte.class, serializer);
		}
		if (properties.isFormatByteType()) {
			module.addSerializer(Byte.TYPE, serializer);
		}
		if (properties.isFormatDoubleClass()) {
			module.addSerializer(Double.class, serializer);
		}
		if (properties.isFormatDoubleType()) {
			module.addSerializer(Double.TYPE, serializer);
		}
		if (properties.isFormatFloatClass()) {
			module.addSerializer(Float.class, serializer);
		}
		if (properties.isFormatFloatType()) {
			module.addSerializer(Float.TYPE, serializer);
		}
		if (properties.isFormatIntegerClass()) {
			module.addSerializer(Integer.class, serializer);
		}
		if (properties.isFormatIntegerType()) {
			module.addSerializer(Integer.TYPE, serializer);
		}
		if (properties.isFormatLongClass()) {
			module.addSerializer(Long.class, serializer);
		}
		if (properties.isFormatLongType()) {
			module.addSerializer(Long.TYPE, serializer);
		}
		if (properties.isFormatShortClass()) {
			module.addSerializer(Short.class, serializer);
		}
		if (properties.isFormatShortType()) {
			module.addSerializer(Short.TYPE, serializer);
		}

		if (CollectionUtil.isNotEmpty(properties.getFormatNumberClassList())) {
			properties.getFormatNumberClassList().stream().filter(Objects::nonNull)
					.forEach(clazz -> module.addSerializer(clazz, serializer));
		}
	}

	/**
	 * 设置ObjectMapper属性.
	 *
	 * @param objectMapper objectMapper
	 */
	public static void setProperties(ObjectMapper objectMapper) {
		setProperties(objectMapper, SpringUtils.getBean(NumberJsonStringFormatProperties.class));
	}

	/**
	 * objectMapper扩展设置.
	 *
	 * @param objectMapperProvider objectMapper提供者
	 * @param properties           数值json格式化配置
	 */
	@Autowired
	public void objectMapperAdvice(ObjectProvider<ObjectMapper> objectMapperProvider,
			NumberJsonStringFormatProperties properties) {
		objectMapperProvider.ifAvailable(objectMapper -> setProperties(objectMapper, properties));
	}
}
