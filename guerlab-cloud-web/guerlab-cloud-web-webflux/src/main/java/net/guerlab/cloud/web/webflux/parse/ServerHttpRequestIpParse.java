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
package net.guerlab.cloud.web.webflux.parse;

import net.guerlab.cloud.commons.ip.IpParser;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;

import java.net.InetSocketAddress;

/**
 * IP地址工具类
 *
 * @author guer
 */
public class ServerHttpRequestIpParse implements IpParser {

    @Override
    public boolean accept(Object request) {
        return request instanceof ServerHttpRequest;
    }

    @Nullable
    @Override
    public String getIpByHeader(Object request, String headerName) {
        return ((ServerHttpRequest) request).getHeaders().getFirst(headerName);
    }

    @Nullable
    @Override
    public String getIpByRemoteAddress(Object request) {
        InetSocketAddress socketAddress = ((ServerHttpRequest) request).getRemoteAddress();
        return socketAddress != null ? socketAddress.getHostName() : null;
    }
}
