package net.guerlab.cloud.api.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.commons.exception.ApplicationException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import static net.guerlab.cloud.api.feign.Constants.*;

/**
 * 结果解析
 *
 * @author guer
 */
@Slf4j
public class ResultDecoder implements Decoder {

    private ObjectMapper objectMapper;

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        Response.Body body = response.body();

        if (body == null) {
            return new Decoder.Default().decode(response, type);
        }

        String resultBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        JsonNode rootNode = objectMapper.readTree(resultBody);
        try {
            Class<?> typeClass = Class.forName(type.getTypeName());
            if (rootNode.has(FIELD_STATUS) && rootNode.has(FIELD_ERROR_CODE)) {
                if (!getStatus(rootNode)) {
                    throw FailParser.parse(rootNode);
                } else if (!rootNode.has(FIELD_DATA)) {
                    return null;
                }

                return objectMapper.convertValue(rootNode.get(FIELD_DATA), typeClass);
            } else {
                return objectMapper.readValue(resultBody, typeClass);
            }
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    private boolean getStatus(JsonNode rootNode) {
        if (!rootNode.has(FIELD_STATUS)) {
            return true;
        }

        JsonNode statusNode = rootNode.get(FIELD_STATUS);

        if (!statusNode.isBoolean()) {
            return true;
        }

        BooleanNode node = (BooleanNode) statusNode;

        return node.asBoolean();
    }
}
