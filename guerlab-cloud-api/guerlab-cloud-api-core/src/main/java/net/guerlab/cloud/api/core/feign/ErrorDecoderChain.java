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
package net.guerlab.cloud.api.core.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import net.guerlab.commons.exception.ApplicationException;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * 错误解析器链
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class ErrorDecoderChain implements ErrorDecoder {

    /**
     * 错误解析器列表
     */
    private List<OrderedErrorDecoder> decoders = new ArrayList<>();

    /**
     * 设置错误解析器列表
     *
     * @param decoders
     *         错误解析器列表
     */
    public void setDecoders(List<OrderedErrorDecoder> decoders) {
        this.decoders = new ArrayList<>(decoders);
        sort();
    }

    /**
     * 添加错误解析器
     *
     * @param decoder
     *         错误解析器
     */
    public void addDecoder(OrderedErrorDecoder decoder) {
        this.decoders.add(decoder);
        sort();
    }

    /**
     * 添加错误解析器列表
     *
     * @param decoders
     *         错误解析器列表
     */
    public void addDecoders(Collection<OrderedErrorDecoder> decoders) {
        if (decoders.isEmpty()) {
            return;
        }

        this.decoders.addAll(decoders);
        sort();
    }

    private void sort() {
        decoders.sort(Comparator.comparingInt(Ordered::getOrder));
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        Response reentrantResponse = reentrantResponse(response);
        Exception exception;
        if (decoders != null) {
            for (OrderedErrorDecoder decoder : decoders) {
                exception = decoder.decode(methodKey, reentrantResponse);
                if (exception != null) {
                    return exception;
                }
            }
        }
        return new ErrorDecoder.Default().decode(methodKey, reentrantResponse);
    }

    private Response reentrantResponse(Response response) {
        Response.Builder builder = Response.builder();

        if (response.body() != null) {
            try {
                builder.body(response.body().asInputStream().readAllBytes());
            } catch (Exception e) {
                throw new ApplicationException(e.getLocalizedMessage(), e);
            }
        }

        return builder.headers(response.headers()).reason(response.reason()).request(response.request()).build();
    }
}
