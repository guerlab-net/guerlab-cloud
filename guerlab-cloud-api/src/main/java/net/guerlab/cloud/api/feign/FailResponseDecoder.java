package net.guerlab.cloud.api.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

/**
 * 失败响应解析
 *
 * @author guer
 */
@Slf4j
public class FailResponseDecoder implements OrderedErrorDecoder {

    private ObjectMapper objectMapper;

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.body() == null) {
            return null;
        }

        try {
            String resultBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            JsonNode rootNode = objectMapper.readTree(resultBody);

            if (rootNode.has(Constants.FIELD_ERROR_CODE)) {
                return FailParser.parse(rootNode);
            }

            return null;
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
