package net.guerlab.cloud.commons.util.test;

import net.guerlab.cloud.commons.entity.RsaKeys;
import net.guerlab.cloud.commons.util.RsaUtils;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author guer
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RsaUtilsTest {

    @Test
    @Order(0)
    public void create() {
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        Assertions.assertNotNull(rsaKeys.getPublicKey());
        Assertions.assertNotNull(rsaKeys.getPrivateKey());

        System.out.println(rsaKeys.getPublicKeyFormattedContent());
        System.out.println(rsaKeys.getPrivateKeyFormattedContent());
    }

    @Test
    @Order(1)
    public void write() throws IOException {
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        RsaUtils.writeTo(rsaKeys, new FileOutputStream("/tmp/rsaKeys.pem"));
    }

    @Test
    @Order(2)
    public void read() throws IOException {
        RsaKeys rsaKeys = RsaUtils.read(new FileInputStream("/tmp/rsaKeys.pem"));
        Assertions.assertNotNull(rsaKeys);
        Assertions.assertNotNull(rsaKeys.getPublicKey());
        Assertions.assertNotNull(rsaKeys.getPrivateKey());
    }

    @Test
    @Order(3)
    public void writeAndRead() throws IOException {
        String filePath = "/tmp/rsaKeys.pem";
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        RsaUtils.writeTo(rsaKeys, new FileOutputStream(filePath));

        Assertions.assertEquals(rsaKeys, RsaUtils.read(new FileInputStream(filePath)));
    }
}
