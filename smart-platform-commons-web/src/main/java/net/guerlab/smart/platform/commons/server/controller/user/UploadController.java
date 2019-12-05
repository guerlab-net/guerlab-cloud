package net.guerlab.smart.platform.commons.server.controller.user;

import io.swagger.annotations.Api;
import net.guerlab.smart.platform.commons.server.controller.AbstractUploadController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 上传处理
 *
 * @author guer
 */
@Api(tags = "文件上传")
@RestController("/user/upload")
@RequestMapping("/user/upload")
public class UploadController extends AbstractUploadController {}
