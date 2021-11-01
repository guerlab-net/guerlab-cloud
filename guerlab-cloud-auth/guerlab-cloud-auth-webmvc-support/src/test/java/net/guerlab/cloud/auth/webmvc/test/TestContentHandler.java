package net.guerlab.cloud.auth.webmvc.test;

import net.guerlab.cloud.auth.context.AbstractContextHandler;
import org.springframework.lang.Nullable;

/**
 * @author guer
 */
public class TestContentHandler extends AbstractContextHandler {

    @Nullable
    public static String getName() {
        return get("name");
    }

    public static void setName(String name) {
        set("name", name);
    }
}
