package net.guerlab.smart.platform.api.feign;

import com.fasterxml.jackson.databind.JsonNode;
import net.guerlab.commons.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.guerlab.smart.platform.api.feign.Constants.*;

/**
 * @author guer
 */
public class FailParser {

    private FailParser() {

    }

    public static ApplicationException parse(JsonNode rootNode) {
        String message = getMessage(rootNode);
        List<List<String>> stackTraces = getStackTraces(rootNode);
        int errorCode = getErrorCode(rootNode);
        RemoteException remoteException = RemoteException.build(message, stackTraces);
        return new ApplicationException(message, remoteException, errorCode);
    }

    /**
     * 解析message字段
     *
     * @param rootNode
     *         跟节点
     * @return message字段
     */
    private static String getMessage(JsonNode rootNode) {
        return StringUtils.trimToNull(rootNode.get(FIELD_MESSAGE).asText());
    }

    /**
     * 解析错误编码
     *
     * @param rootNode
     *         跟节点
     * @return 错误编码
     */
    private static int getErrorCode(JsonNode rootNode) {
        if (!rootNode.has(FIELD_ERROR_CODE)) {
            return 0;
        }

        return rootNode.get(FIELD_ERROR_CODE).intValue();
    }

    /**
     * 解析堆栈信息列表
     *
     * @param rootNode
     *         跟节点
     * @return 堆栈信息列表
     */
    private static List<List<String>> getStackTraces(JsonNode rootNode) {
        if (!rootNode.has(FIELD_STACK_TRACE)) {
            return Collections.emptyList();
        }

        JsonNode stackTraceNode = rootNode.get(FIELD_STACK_TRACE);

        if (!stackTraceNode.isArray()) {
            return Collections.emptyList();
        }

        List<List<String>> stackTrace = new ArrayList<>(stackTraceNode.size());
        for (JsonNode node : stackTraceNode) {
            stackTrace.add(getSubStackTraces(node));
        }

        return stackTrace;
    }

    /**
     * 解析堆栈信息
     *
     * @param parentNode
     *         父节点
     * @return 堆栈信息
     */
    private static List<String> getSubStackTraces(JsonNode parentNode) {
        if (!parentNode.isArray()) {
            return Collections.emptyList();
        }

        List<String> stackTrace = new ArrayList<>(parentNode.size());

        for (JsonNode node : parentNode) {
            stackTrace.add(node.asText());
        }

        return stackTrace;
    }
}
