/*
 * Copyright 2018-2025 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * RSA工具类测试类.
 *
 * @author guer
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RsaUtilsTest {

	private static final String SYSTEM_TEMP_DIR = System.getProperty("java.io.tmpdir");

	private static String rsaKeysFilePath = SYSTEM_TEMP_DIR + "/rsaKeys.pem";

	@BeforeAll
	static void beforeTest() {
		String dirPath = SYSTEM_TEMP_DIR + "/tmp_" + ThreadLocalRandom.current().nextInt(1, 99999999);
		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			Assertions.assertTrue(dir.mkdirs());
		}
		rsaKeysFilePath = dirPath + "/rsaKeys.pem";
		System.out.println("rsaKeysFilePath: " + rsaKeysFilePath);
	}

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
		File file = new File(rsaKeysFilePath);
		RsaUtils.writeTo(rsaKeys, new FileOutputStream(file));
		Assertions.assertTrue(file.isFile());
	}

	@Test
	@Order(2)
	void read() throws IOException {
		RsaKeys rsaKeys = RsaUtils.read(new FileInputStream(rsaKeysFilePath));
		Assertions.assertNotNull(rsaKeys);
		Assertions.assertNotNull(rsaKeys.getPublicKey());
		Assertions.assertNotNull(rsaKeys.getPrivateKey());
	}

	@Test
	@Order(3)
	void writeAndRead() throws IOException {
		RsaKeys rsaKeys = RsaUtils.buildKeys();
		RsaUtils.writeTo(rsaKeys, new FileOutputStream(rsaKeysFilePath));

		Assertions.assertEquals(rsaKeys, RsaUtils.read(new FileInputStream(rsaKeysFilePath)));
	}

	@Test
	@Order(4)
	void publicKeyEncryptAndPrivateKeyDecrypt() throws Exception {
		RsaKeys rsaKeys = RsaUtils.buildKeys();
		RsaUtils.writeTo(rsaKeys, new FileOutputStream(rsaKeysFilePath));

		byte[] data = RsaUtils.encryptByPublicKey("test".getBytes(StandardCharsets.UTF_8), rsaKeys.getPublicKeyRef());

		Assertions.assertEquals("test", new String(RsaUtils.decryptByPrivateKey(data, rsaKeys.getPrivateKeyRef())));
	}

	@Test
	@Order(5)
	void privateKeyEncryptAndPublicKeyDecrypt() throws Exception {
		RsaKeys rsaKeys = RsaUtils.buildKeys();
		RsaUtils.writeTo(rsaKeys, new FileOutputStream(rsaKeysFilePath));

		byte[] data = RsaUtils.encryptByPrivateKey("test".getBytes(StandardCharsets.UTF_8), rsaKeys.getPrivateKeyRef());

		Assertions.assertEquals("test", new String(RsaUtils.decryptByPublicKey(data, rsaKeys.getPublicKeyRef())));
	}
}
