package net.guerlab.smart.platform.commons.server.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.spring.upload.entity.FileInfo;
import net.guerlab.spring.upload.helper.UploadFileHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * 上传处理
 *
 * @author guer
 */
public class AbstractUploadController {

    private static final String DEFAULT_PATH = "/";

    private static String buildSavePath(String path) {
        LocalDate localDate = LocalDate.now();

        if (path == null) {
            return localDate.getYear() + DEFAULT_PATH + localDate.getMonthValue();
        } else {
            return path + DEFAULT_PATH + localDate.getYear() + DEFAULT_PATH + localDate.getMonthValue();
        }
    }

    @ApiOperation("上传文件")
    @PostMapping({ "/single", "/single/{path}" })
    public FileInfo upload(@ApiParam(value = "上传路径") @PathVariable(required = false) final String path,
            @ApiParam(value = "待上传文件", required = true) @RequestParam final MultipartFile file) {
        return UploadFileHelper.upload(file, buildSavePath(path));
    }

    @ApiOperation("多文件列表上传")
    @PostMapping({ "/multiple", "/multiple/{path}" })
    public List<FileInfo> uploadFileList(@ApiParam(value = "上传路径") @PathVariable(required = false) final String path,
            @ApiParam(value = "待上传文件", required = true) @RequestParam final List<MultipartFile> fileList) {
        return UploadFileHelper.multiUpload(fileList, buildSavePath(path));
    }
}
