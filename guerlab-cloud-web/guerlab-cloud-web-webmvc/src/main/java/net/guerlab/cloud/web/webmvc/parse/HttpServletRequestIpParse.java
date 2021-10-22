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
package net.guerlab.cloud.web.webmvc.parse;

import net.guerlab.cloud.commons.ip.IpParser;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址工具类
 *
 * @author guer
 */
public class HttpServletRequestIpParse implements IpParser {

    @Override
    public boolean accept(Object request) {
        return request instanceof HttpServletRequest;
    }

    @Nullable
    @Override
    public String getIpByHeader(Object request, String headerName) {
        return ((HttpServletRequest) request).getHeader(headerName);
    }

    @Nullable
    @Override
    public String getIpByRemoteAddress(Object request) {
        return ((HttpServletRequest) request).getRemoteAddr();
    }
}
