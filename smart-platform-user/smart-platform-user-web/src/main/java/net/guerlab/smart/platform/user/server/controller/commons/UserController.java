package net.guerlab.smart.platform.user.server.controller.commons;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.server.controller.BaseFindController;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.server.entity.User;
import net.guerlab.smart.platform.user.server.service.UserService;
import net.guerlab.spring.upload.config.PathInfoConfig;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * 用户
 *
 * @author guer
 */
@Api(tags = "用户")
@RestController("/commons/user")
@RequestMapping("/commons/user")
public class UserController extends BaseFindController<UserDTO, User, UserService, UserSearchParams, Long> {

    @ApiOperation("获取头像")
    @GetMapping("/{id}/avatar")
    public void avatar(@ApiParam(value = "id", required = true) @PathVariable Long id, HttpServletResponse response) {
        String avatarUrl = findAvatar(id);
        String completePath =
                PathInfoConfig.getSaveBaseDir() + (avatarUrl.startsWith("/") ? avatarUrl : "/" + avatarUrl);

        File file = new File(completePath);

        if (!file.exists() || !file.canRead()) {
            response.setStatus(404);
            return;
        }

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.addHeader("Content-Type", "image/jpeg");
            StreamUtils.copy(new FileInputStream(file), outputStream);
        } catch (Exception e) {
            response.setStatus(404);
        }
    }

    private String findAvatar(Long id) {
        User user = service.selectById(id);

        return user == null ? UserService.DEFAULT_AVATAR : user.getAvatar();
    }
}
