/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.smart.platform.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * excel utils
 *
 * @author guer
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class ExcelUtils {

    private static final Collection<Converter> CONVERTERS = new ArrayList<>();

    static {
        StreamSupport.stream(ServiceLoader.load(Converter.class).spliterator(), false).forEach(CONVERTERS::add);
    }

    /**
     * 导出excel
     *
     * @param response
     *         响应
     * @param list
     *         对象列表
     * @param head
     *         对象类型
     * @param fileName
     *         文件名
     * @param <T>
     *         对象类型
     */
    public static <T> void exportExcel(HttpServletResponse response, Collection<?> list, Class<T> head,
            String fileName) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            write(response.getOutputStream(), head).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet().doWrite(BeanConvertUtils.toList(list, head));
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Build excel the write
     *
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write() {
        return registerConverter(EasyExcel.write());
    }

    /**
     * Build excel the write
     *
     * @param file
     *         File to write
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(File file) {
        return registerConverter(EasyExcel.write(file));
    }

    /**
     * Build excel the write
     *
     * @param file
     *         File to write
     * @param head
     *         Annotate the class for configuration information
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(File file, Class head) {
        return registerConverter(EasyExcel.write(file, head));
    }

    /**
     * Build excel the write
     *
     * @param pathName
     *         File path to write
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(String pathName) {
        return registerConverter(EasyExcel.write(pathName));
    }

    /**
     * Build excel the write
     *
     * @param pathName
     *         File path to write
     * @param head
     *         Annotate the class for configuration information
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(String pathName, Class head) {
        return registerConverter(EasyExcel.write(pathName, head));
    }

    /**
     * Build excel the write
     *
     * @param outputStream
     *         Output stream to write
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(OutputStream outputStream) {
        return registerConverter(EasyExcel.write(outputStream));
    }

    /**
     * Build excel the write
     *
     * @param outputStream
     *         Output stream to write
     * @param head
     *         Annotate the class for configuration information.
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(OutputStream outputStream, Class head) {
        return registerConverter(EasyExcel.write(outputStream, head));
    }

    /**
     * 注册全局转换器
     *
     * @param converter
     *         转换器
     */
    public static void registerGlobalConverter(Converter converter) {
        CONVERTERS.add(converter);
    }

    private static ExcelWriterBuilder registerConverter(ExcelWriterBuilder builder) {
        CONVERTERS.forEach(builder::registerConverter);
        return builder;
    }
}
