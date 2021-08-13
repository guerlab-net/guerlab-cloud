package net.guerlab.cloud.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 推送类型格式化名称测试
 *
 * @author guer
 */
public class PutTypeTest {

    @Test
    public void input1() {
        Assertions.assertEquals("onTest-in-0", PutType.IN.formatName("test"));
    }

    @Test
    public void input2() {
        Assertions.assertEquals("onTest-in-0", PutType.IN.formatName("onTest"));
    }

    @Test
    public void output() {
        Assertions.assertEquals("test-out-0", PutType.OUT.formatName("test"));
    }
}
