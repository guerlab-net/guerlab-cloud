/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.web.webmvc.autoconfigure;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.core.result.Result;

/**
 * 自定义异常控制器.
 *
 * @author guer
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomerErrorController extends AbstractErrorController {

	/**
	 * 实例化.
	 *
	 * @param errorAttributes    errorAttributes
	 * @param errorViewResolvers errorViewResolvers
	 */
	public CustomerErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
		super(errorAttributes, errorViewResolvers);
	}

	@RequestMapping
	public Result<Void> error(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		return new Fail<>(status.getReasonPhrase(), status.value());
	}
}
