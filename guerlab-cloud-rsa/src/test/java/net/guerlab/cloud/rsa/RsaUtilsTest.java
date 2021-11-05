package net.guerlab.cloud.rsa;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * RSA工具类测试类
 *
 * @author guer
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RsaUtilsTest {

    @Test
    @Order(0)
    void create() {
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        Assertions.assertNotNull(rsaKeys.getPublicKey());
        Assertions.assertNotNull(rsaKeys.getPrivateKey());

        System.out.println(rsaKeys.getPublicKeyFormattedContent());
        System.out.println(rsaKeys.getPrivateKeyFormattedContent());
    }

    @Test
    @Order(1)
    void write() throws IOException {
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        RsaUtils.writeTo(rsaKeys, new FileOutputStream("/tmp/rsaKeys.pem"));
        Assertions.assertTrue(new File("/tmp/rsaKeys.pem").isFile());
    }

    @Test
    @Order(2)
    void read() throws IOException {
        RsaKeys rsaKeys = RsaUtils.read(new FileInputStream("/tmp/rsaKeys.pem"));
        Assertions.assertNotNull(rsaKeys);
        Assertions.assertNotNull(rsaKeys.getPublicKey());
        Assertions.assertNotNull(rsaKeys.getPrivateKey());
    }

    @Test
    @Order(3)
    void writeAndRead() throws IOException {
        String filePath = "/tmp/rsaKeys.pem";
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        RsaUtils.writeTo(rsaKeys, new FileOutputStream(filePath));

        Assertions.assertEquals(rsaKeys, RsaUtils.read(new FileInputStream(filePath)));
    }

    @Test
    @Order(4)
    void publicKeyEncryptAndPrivateKeyDecrypt() throws Exception {
        String filePath = "/tmp/rsaKeys.pem";
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        RsaUtils.writeTo(rsaKeys, new FileOutputStream(filePath));

        byte[] data = RsaUtils.encryptByPublicKey("test".getBytes(StandardCharsets.UTF_8), rsaKeys.getPublicKeyRef());

        Assertions.assertEquals("test", new String(RsaUtils.decryptByPrivateKey(data, rsaKeys.getPrivateKeyRef())));
    }

    @Test
    @Order(5)
    void privateKeyEncryptAndPublicKeyDecrypt() throws Exception {
        String filePath = "/tmp/rsaKeys.pem";
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        RsaUtils.writeTo(rsaKeys, new FileOutputStream(filePath));

        byte[] data = RsaUtils.encryptByPrivateKey("test".getBytes(StandardCharsets.UTF_8), rsaKeys.getPrivateKeyRef());

        Assertions.assertEquals("test", new String(RsaUtils.decryptByPublicKey(data, rsaKeys.getPublicKeyRef())));
    }
}
