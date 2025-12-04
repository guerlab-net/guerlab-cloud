/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import jakarta.annotation.Nullable;

/**
 * RSA工具类.
 *
 * @author guer
 */
public final class RsaUtils {

	/**
	 * 加密算法 RSA.
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 变化类型.
	 */
	public static final String TRANSFORMATION = "RSA/ECB/PKCS1PADDING";

	/**
	 * 默认密钥长度.
	 */
	public static final int DEFAULT_KEY_SIZE = 2048;

	/**
	 * 公钥前缀.
	 */
	public static final String PUBLIC_KEY_FILE_PREFIX = "-----BEGIN CERTIFICATE-----";

	/**
	 * 公钥后缀.
	 */
	public static final String PUBLIC_KEY_FILE_SUFFIX = "-----END CERTIFICATE-----";

	/**
	 * 私钥前缀.
	 */
	public static final String PRIVATE_KEY_FILE_PREFIX = "-----BEGIN PRIVATE KEY-----";

	/**
	 * 私钥后缀.
	 */
	public static final String PRIVATE_KEY_FILE_SUFFIX = "-----END PRIVATE KEY-----";

	/**
	 * 单行文本长度.
	 */
	public static final int LINE_LENGTH = 64;

	/**
	 * 换行.
	 */
	public static final String LINE_FEED = "\r\n";

	/**
	 * RSA 最大加密明文长度.
	 */
	private static final int MAX_ENCRYPT_BLOCK = 245;

	/**
	 * RSA 最大解密秘文长度.
	 */
	private static final int MAX_DECRYPT_BLOCK = 256;

	private RsaUtils() {

	}

	/**
	 * 创建公/私钥对.
	 *
	 * @param keySize key长度
	 * @return 公/私钥对
	 */
	public static KeyPair createKeyPair(int keySize) {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(keySize, new SecureRandom());
			return keyPairGen.generateKeyPair();
		}
		catch (Exception e) {
			throw new RsaException(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 创建公/私钥对.
	 *
	 * @return 公/私钥对
	 */
	public static KeyPair createKeyPair() {
		return createKeyPair(DEFAULT_KEY_SIZE);
	}

	/**
	 * 构造RSA公/私钥对.
	 *
	 * @param keyPair 公/私钥对
	 * @return RSA公/私钥对
	 */
	public static RsaKeys buildKeys(KeyPair keyPair) {
		Base64.Encoder encoder = Base64.getEncoder();
		RsaKeys rsaKeys = new RsaKeys();
		rsaKeys.setPublicKey(encoder.encodeToString(keyPair.getPublic().getEncoded()));
		rsaKeys.setPrivateKey(encoder.encodeToString(keyPair.getPrivate().getEncoded()));
		return rsaKeys;
	}

	/**
	 * 构造RSA公/私钥对.
	 *
	 * @return RSA公/私钥对
	 */
	public static RsaKeys buildKeys() {
		return buildKeys(createKeyPair());
	}

	/**
	 * 解析公钥.
	 *
	 * @param bytes 公钥byte数组
	 * @return 公钥
	 */
	public static PublicKey parsePublicKey(byte[] bytes) {
		try {
			X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			return factory.generatePublic(spec);
		}
		catch (Exception e) {
			throw new RsaException(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 解析公钥.
	 *
	 * @param keyString 公钥字符串
	 * @return 公钥
	 */
	public static PublicKey parsePublicKey(String keyString) {
		return parsePublicKey(Base64.getDecoder().decode(cleanFormat(keyString)));
	}

	/**
	 * 解析私钥.
	 *
	 * @param bytes 私钥byte数组
	 * @return 私钥
	 */
	public static PrivateKey parsePrivateKey(byte[] bytes) {
		try {
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			return factory.generatePrivate(spec);
		}
		catch (Exception e) {
			throw new RsaException(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 解析私钥.
	 *
	 * @param keyString 私钥字符串
	 * @return 私钥
	 */
	public static PrivateKey parsePrivateKey(String keyString) {
		return parsePrivateKey(Base64.getDecoder().decode(cleanFormat(keyString)));
	}

	/**
	 * 清理密钥字符串格式.
	 *
	 * @param keyString 原始字符串
	 * @return 清理后的字符串内容
	 */
	public static String cleanFormat(String keyString) {
		keyString = keyString.replace(PUBLIC_KEY_FILE_PREFIX, "");
		keyString = keyString.replace(PUBLIC_KEY_FILE_SUFFIX, "");
		keyString = keyString.replace(PRIVATE_KEY_FILE_PREFIX, "");
		keyString = keyString.replace(PRIVATE_KEY_FILE_SUFFIX, "");
		keyString = keyString.replace(LINE_FEED, "");
		keyString = keyString.replace("\n", "");
		return keyString;
	}

	/**
	 * 格式化公钥字符串.
	 *
	 * @param keyString 原始字符串
	 * @return 格式化后的公钥字符串
	 */
	public static String formatPublicKey(String keyString) {
		if (keyString.startsWith(PUBLIC_KEY_FILE_PREFIX) && keyString.endsWith(PUBLIC_KEY_FILE_SUFFIX)) {
			return keyString;
		}
		return PUBLIC_KEY_FILE_PREFIX + LINE_FEED + formatKey(keyString) + LINE_FEED + PUBLIC_KEY_FILE_SUFFIX;
	}

	/**
	 * 格式化私钥字符串.
	 *
	 * @param keyString 原始字符串
	 * @return 格式化后的私钥字符串
	 */
	public static String formatPrivateKey(String keyString) {
		if (keyString.startsWith(PRIVATE_KEY_FILE_PREFIX) && keyString.endsWith(PRIVATE_KEY_FILE_SUFFIX)) {
			return keyString;
		}
		return PRIVATE_KEY_FILE_PREFIX + LINE_FEED + formatKey(keyString) + LINE_FEED + PRIVATE_KEY_FILE_SUFFIX;
	}

	/**
	 * 格式化key字符串.
	 *
	 * @param keyString 原始字符串
	 * @return 格式化后的key字符串
	 */
	public static String formatKey(String keyString) {
		StringBuilder result = new StringBuilder();
		while (true) {
			if (keyString.length() > LINE_LENGTH) {
				result.append(keyString, 0, LINE_LENGTH);
				result.append(LINE_FEED);
				keyString = keyString.substring(LINE_LENGTH);
			}
			else {
				result.append(keyString);
				break;
			}
		}

		return result.toString();
	}

	/**
	 * 将RSA公/私钥对写入输出流.
	 *
	 * @param rsaKeys      RSA公/私钥对
	 * @param outputStream 输出流
	 * @throws IOException 当IO发生问题的时候抛出IOException
	 */
	public static void writeTo(RsaKeys rsaKeys, OutputStream outputStream) throws IOException {
		String contentBuilder =
				rsaKeys.getPublicKeyFormattedContent() + LINE_FEED + rsaKeys.getPrivateKeyFormattedContent();
		outputStream.write(contentBuilder.getBytes());
	}

	/**
	 * 从流中读取RSA公/私钥对.
	 *
	 * @param inputStream 输入流
	 * @return RSA公/私钥对
	 * @throws IOException 当IO发生问题的时候抛出IOException
	 */
	@Nullable
	public static RsaKeys read(InputStream inputStream) throws IOException {
		byte[] bytes = inputStream.readAllBytes();
		if (bytes.length == 0) {
			return null;
		}
		String content = new String(bytes);

		String publicKey = readPublicKey(content);
		String privateKey = readPrivateKey(content);

		if (publicKey == null || privateKey == null) {
			return null;
		}

		return new RsaKeys(publicKey, privateKey);
	}

	/**
	 * 从内容中读取公钥内容.
	 *
	 * @param content 待读取内容
	 * @return 公钥内容
	 */
	@Nullable
	public static String readPublicKey(String content) {
		return readKey(content, PUBLIC_KEY_FILE_PREFIX, PUBLIC_KEY_FILE_SUFFIX);
	}

	/**
	 * 从内容中读取私钥内容.
	 *
	 * @param content 待读取内容
	 * @return 私钥内容
	 */
	@Nullable
	public static String readPrivateKey(String content) {
		return readKey(content, PRIVATE_KEY_FILE_PREFIX, PRIVATE_KEY_FILE_SUFFIX);
	}

	/**
	 * 从内容中读取内容.
	 *
	 * @param content 待读取内容
	 * @param prefix  前缀
	 * @param suffix  后缀
	 * @return 内容
	 */
	@Nullable
	public static String readKey(String content, String prefix, String suffix) {
		int startIndex = content.indexOf(prefix);
		int endIndex = content.indexOf(suffix);

		if (startIndex >= 0 && endIndex >= 0) {
			return content.substring(startIndex, endIndex + suffix.length());
		}

		return null;
	}

	/**
	 * 公钥加密.
	 *
	 * @param data      待加密数据
	 * @param publicKey 公钥
	 * @return 加密后数据
	 */
	public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) {
		return dataHandler(data, publicKey, Cipher.ENCRYPT_MODE, MAX_ENCRYPT_BLOCK);
	}

	/**
	 * 私钥加密.
	 *
	 * @param data       待加密数据
	 * @param privateKey 私钥
	 * @return 加密后数据
	 */
	public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) {
		return dataHandler(data, privateKey, Cipher.ENCRYPT_MODE, MAX_ENCRYPT_BLOCK);
	}

	/**
	 * 公钥解密.
	 *
	 * @param data      待解密数据
	 * @param publicKey 公钥
	 * @return 解密后数据
	 */
	public static byte[] decryptByPublicKey(byte[] data, PublicKey publicKey) {
		return dataHandler(data, publicKey, Cipher.DECRYPT_MODE, MAX_DECRYPT_BLOCK);
	}

	/**
	 * 私钥解密.
	 *
	 * @param data       待解密数据
	 * @param privateKey 私钥
	 * @return 解密后数据
	 */
	public static byte[] decryptByPrivateKey(byte[] data, PrivateKey privateKey) {
		return dataHandler(data, privateKey, Cipher.DECRYPT_MODE, MAX_DECRYPT_BLOCK);
	}

	/**
	 * 加密/解密处理.
	 *
	 * @param data          待加密/解密数据
	 * @param key           密钥
	 * @param operationMode 操作方式
	 * @param size          单次处理长度
	 * @return 处理后数据
	 */
	private static byte[] dataHandler(byte[] data, Key key, int operationMode, int size) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(operationMode, key);

			byte[] cache;
			int start = 0;
			int end;
			while ((end = data.length - start) > 0) {
				cache = cipher.doFinal(data, start, Math.min(end, size));
				out.write(cache, 0, cache.length);
				start += size;
			}

			return out.toByteArray();
		}
		catch (Exception e) {
			throw new RsaException(e.getLocalizedMessage(), e);
		}
	}
}
