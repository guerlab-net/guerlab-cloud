package net.guerlab.smart.platform.basic.auth.interceptor;

import net.guerlab.smart.platform.basic.auth.AbstractContextHandler;
import net.guerlab.smart.platform.basic.auth.annotation.IgnoreLogin;
import net.guerlab.spring.commons.properties.ResponseAdvisorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * 抽象拦截器处理
 *
 * @author guer
 */
public abstract class AbstractHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final String[] METHODS = new String[] { "OPTIONS", "TRACE" };

    @Autowired
    protected ResponseAdvisorProperties responseAdvisorProperties;

    /**
     * 获取注解
     *
     * @param handlerMethod
     *         处理方法
     * @param annotationClass
     *         注解类
     * @return 注解对象
     */
    protected static <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationClass) {
        A annotation = handlerMethod.getBeanType().getAnnotation(annotationClass);
        if (annotation == null) {
            annotation = handlerMethod.getMethodAnnotation(annotationClass);
        }
        return annotation;
    }

    private static boolean methodMatch(HttpServletRequest request) {
        String requestMethod = request.getMethod();

        return Arrays.asList(METHODS).contains(requestMethod);
    }

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (methodMatch(request) || uriMatch(request) || !(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        IgnoreLogin ignoreLogin = getAnnotation(handlerMethod, IgnoreLogin.class);

        if (ignoreLogin == null) {
            preHandle0(request, handlerMethod);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        AbstractContextHandler.clean();
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 前置处理
     *
     * @param request
     *         请求
     * @param handlerMethod
     *         处理方法
     */
    protected abstract void preHandle0(HttpServletRequest request, HandlerMethod handlerMethod);

    private boolean uriMatch(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return responseAdvisorProperties.getExcluded().stream().anyMatch(uri::startsWith);
    }
}
