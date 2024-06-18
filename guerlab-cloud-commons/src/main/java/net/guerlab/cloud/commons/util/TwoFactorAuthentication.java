/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.commons.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import jakarta.annotation.Nullable;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

import net.guerlab.commons.random.RandomUtil;

/**
 * 双因子认证.
 *
 * @author guerlab
 */
@SuppressWarnings("unused")
public final class TwoFactorAuthentication {

	private static final int SECRET_SIZE = 10;

	private static final int SEED_LENGTH = 76;

	private static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

	private static final String OTP_QR_CODE_FORMAT = "otpauth://totp/%s?secret=%s";

	private static final int DATA_DEVIATION = 8;

	private static final String VERIFY_CODE_ALGORITHM = "HmacSHA1";

	private static final int TRUNCATED_HASH_CALCULATION_FREQUENCY = 4;

	private static final String OPT_FORMAT = "%06d";

	private TwoFactorAuthentication() {

	}

	/**
	 * Generate a random secret key. This must be saved by the server and
	 * associated with the users account to verify the code displayed by Google
	 * Authenticator. The user must register this secret on their device.
	 *
	 * @return secret key
	 */
	@Nullable
	public static String generateSecretKey() {
		try {
			SecureRandom sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
			sr.setSeed(Base64.decodeBase64(RandomUtil.nextString(SEED_LENGTH)));
			byte[] buffer = sr.generateSeed(SECRET_SIZE);
			byte[] bEncodedKey = new Base32().encode(buffer);
			return new String(bEncodedKey);
		}
		catch (NoSuchAlgorithmException e) {
			// should never occur... configuration error
			return null;
		}
	}

	/**
	 * 生成OTP识别的字符串，只需要把该方法返回值生成二维码扫描就可以了.
	 *
	 * @param user   账号
	 * @param secret 密钥
	 * @return OTP识别的字符串
	 */
	public static String getQrCode(String user, String secret) {
		return String.format(OTP_QR_CODE_FORMAT, user, secret);
	}

	/**
	 * Check the code entered by the user to see if it is valid 验证code是否合法.
	 *
	 * @param secret The users secret.
	 * @param code   The code displayed on the users device
	 * @return authentication succeed return true, authentication failed return false
	 */
	public static boolean checkCode(String secret, String code) {
		return checkCode(secret, code, System.currentTimeMillis());
	}

	/**
	 * Check the code entered by the user to see if it is valid 验证code是否合法.
	 *
	 * @param secret      The users secret.
	 * @param code        The code displayed on the users device
	 * @param millisecond The millisecond (System.currentTimeMillis() for example)
	 * @return authentication succeed return true, authentication failed return false
	 */
	@SuppressWarnings("WeakerAccess")
	public static boolean checkCode(String secret, String code, long millisecond) {
		return checkCode(secret, code, millisecond, 1);
	}

	/**
	 * Check the code entered by the user to see if it is valid 验证code是否合法.
	 *
	 * @param secret      The users secret.
	 * @param code        The code displayed on the users device
	 * @param millisecond The millisecond (System.currentTimeMillis() for example)
	 * @param windowSize  the windows size. This is an integer value representing the number of
	 *                    * 30 second windows we allow The bigger the window, the more tolerant of
	 *                    * clock skew we are.
	 * @return authentication succeed return true, authentication failed return false
	 */
	@SuppressWarnings("WeakerAccess")
	public static boolean checkCode(String secret, String code, long millisecond, int windowSize) {
		byte[] decodedKey = new Base32().decode(secret);
		long t = (millisecond / 1000L) / 30L;
		for (int i = -Math.abs(windowSize); i <= windowSize; ++i) {
			try {
				String tempCode = verifyCode(decodedKey, t + i);
				if (Objects.equals(tempCode, code)) {
					return true;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	private static String verifyCode(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = new byte[DATA_DEVIATION];
		long value = t;
		for (int i = DATA_DEVIATION; i-- > 0; value >>>= DATA_DEVIATION) {
			data[i] = (byte) value;
		}
		SecretKeySpec signKey = new SecretKeySpec(key, VERIFY_CODE_ALGORITHM);
		Mac mac = Mac.getInstance(VERIFY_CODE_ALGORITHM);
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);
		int offset = hash[20 - 1] & 0xF;
		long truncatedHash = 0;
		for (int i = 0; i < TRUNCATED_HASH_CALCULATION_FREQUENCY; ++i) {
			truncatedHash <<= DATA_DEVIATION;
			truncatedHash |= (hash[offset + i] & 0xFF);
		}
		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;
		return String.format(OPT_FORMAT, (int) truncatedHash);
	}
}
