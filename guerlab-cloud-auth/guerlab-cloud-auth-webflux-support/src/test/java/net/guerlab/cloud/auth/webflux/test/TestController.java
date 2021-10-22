package net.guerlab.cloud.auth.webflux.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.guerlab.cloud.auth.annotation.IgnoreLogin;
import net.guerlab.cloud.auth.domain.AbstractLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guer
 */
@Tag(name = "test")
@RestController("/test/controlPanel")
@RequestMapping("/test/controlPanel")
public class TestController {

    private TestJwtTokenFactory tokenFactory;

    @IgnoreLogin
    @Operation(summary = "登录")
    @GetMapping("/login")
    public AbstractLoginResponse<TestUserInfo> login() {
        TestUserInfo info = new TestUserInfo();
        info.setName("name");

        AbstractLoginResponse<TestUserInfo> response = new AbstractLoginResponse<>();
        response.setAccessToken(tokenFactory.generateByAccessToken(info));
        response.setInfo(info);

        return response;
    }

    @Operation(summary = "个人信息")
    @GetMapping("/info")
    public TestUserInfo info() {
        TestUserInfo info = new TestUserInfo();
        info.setName(TestContentHandler.getName());

        return info;
    }

    @Autowired
    public void setTokenFactory(TestJwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }
}
