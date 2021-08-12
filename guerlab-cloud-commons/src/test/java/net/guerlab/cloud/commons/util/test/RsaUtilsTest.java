package net.guerlab.cloud.commons.util.test;

import net.guerlab.cloud.commons.entity.RsaKeys;
import net.guerlab.cloud.commons.util.RsaUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author guer
 */
public class RsaUtilsTest {

    @Test
    public void create() {
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        Assertions.assertNotNull(rsaKeys.getPublicKey());
        Assertions.assertNotNull(rsaKeys.getPrivateKey());

        System.out.println(rsaKeys.getPublicKeyFormattedContent());
        System.out.println(rsaKeys.getPrivateKeyFormattedContent());
    }

    @Test
    public void write() throws IOException {
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        RsaUtils.writeTo(rsaKeys, new FileOutputStream("/tmp/rsaKeys.pem"));
    }

    @Test
    public void read() throws IOException {
        RsaKeys rsaKeys = RsaUtils.read(new FileInputStream("/tmp/rsaKeys.pem"));
        Assertions.assertNotNull(rsaKeys);
        Assertions.assertNotNull(rsaKeys.getPublicKey());
        Assertions.assertNotNull(rsaKeys.getPrivateKey());
    }

    @Test
    public void writeAndRead() throws IOException {
        String filePath = "/tmp/rsaKeys.pem";
        RsaKeys rsaKeys = RsaUtils.buildKeys();
        RsaUtils.writeTo(rsaKeys, new FileOutputStream(filePath));

        Assertions.assertEquals(rsaKeys, RsaUtils.read(new FileInputStream(filePath)));
    }
}
