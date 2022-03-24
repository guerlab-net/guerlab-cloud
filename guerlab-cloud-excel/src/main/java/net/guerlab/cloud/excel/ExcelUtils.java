/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import net.guerlab.cloud.commons.util.BeanConvertUtils;
import net.guerlab.cloud.excel.enums.ExcelType;
import net.guerlab.commons.exception.ApplicationException;

/**
 * excel utils.
 *
 * @author guer
 */
@SuppressWarnings({"unused"})
public final class ExcelUtils {

	private static final Collection<Converter<?>> CONVERTERS = new ArrayList<>();

	static {
		StreamSupport.stream(ServiceLoader.load(Converter.class).spliterator(), false).forEach(CONVERTERS::add);
	}

	private ExcelUtils() {
	}

	/**
	 * 导出excel.
	 *
	 * @param list
	 *         对象列表
	 * @param head
	 *         对象类型
	 * @param fileName
	 *         文件名
	 * @param <T>
	 *         对象类型
	 * @return 响应
	 */
	public static <T> ResponseEntity<byte[]> exportExcel(Collection<?> list, Class<T> head, String fileName) {
		if (!fileName.endsWith(ExcelType.XLSX.getSuffix()) && !fileName.endsWith(ExcelType.XLS.getSuffix())) {
			fileName += ExcelType.XLSX.getSuffix();
		}

		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			write(outputStream, head).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet()
					.doWrite(BeanConvertUtils.toList(list, head));

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.valueOf("application/vnd.ms-excel; charset=UTF-8"));
			httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());

			return new ResponseEntity<>(outputStream.toByteArray(), httpHeaders, HttpStatus.OK);
		}
		catch (Exception e) {
			throw new ApplicationException(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 获取Excel输出构建器.
	 *
	 * @return Excel输出构建器
	 */
	public static ExcelWriterBuilder write() {
		return registerConverter(EasyExcelFactory.write());
	}

	/**
	 * 获取Excel输出构建器.
	 *
	 * @param file
	 *         File to write
	 * @return Excel输出构建器
	 */
	public static ExcelWriterBuilder write(File file) {
		return registerConverter(EasyExcelFactory.write(file));
	}

	/**
	 * 获取Excel输出构建器.
	 *
	 * @param file
	 *         File to write
	 * @param head
	 *         Annotate the class for configuration information
	 * @return Excel输出构建器
	 */
	public static ExcelWriterBuilder write(File file, Class<?> head) {
		return registerConverter(EasyExcelFactory.write(file, head));
	}

	/**
	 * 获取Excel输出构建器.
	 *
	 * @param pathName
	 *         File path to write
	 * @return Excel输出构建器
	 */
	public static ExcelWriterBuilder write(String pathName) {
		return registerConverter(EasyExcelFactory.write(pathName));
	}

	/**
	 * 获取Excel输出构建器.
	 *
	 * @param pathName
	 *         File path to write
	 * @param head
	 *         Annotate the class for configuration information
	 * @return Excel输出构建器
	 */
	public static ExcelWriterBuilder write(String pathName, Class<?> head) {
		return registerConverter(EasyExcelFactory.write(pathName, head));
	}

	/**
	 * 获取Excel输出构建器.
	 *
	 * @param outputStream
	 *         Output stream to write
	 * @return Excel输出构建器
	 */
	public static ExcelWriterBuilder write(OutputStream outputStream) {
		return registerConverter(EasyExcelFactory.write(outputStream));
	}

	/**
	 * 获取Excel输出构建器.
	 *
	 * @param outputStream
	 *         Output stream to write
	 * @param head
	 *         Annotate the class for configuration information.
	 * @return Excel输出构建器
	 */
	public static ExcelWriterBuilder write(OutputStream outputStream, Class<?> head) {
		return registerConverter(EasyExcelFactory.write(outputStream, head));
	}

	/**
	 * 注册全局转换器.
	 *
	 * @param converter
	 *         转换器
	 */
	public static void registerGlobalConverter(Converter<?> converter) {
		CONVERTERS.add(converter);
	}

	/**
	 * 注册转换器对象.
	 *
	 * @param builder
	 *         ExcelWriterBuilder
	 * @return ExcelWriterBuilder
	 */
	public static ExcelWriterBuilder registerConverter(ExcelWriterBuilder builder) {
		CONVERTERS.forEach(builder::registerConverter);
		return builder;
	}
}
