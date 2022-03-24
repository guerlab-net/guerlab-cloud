/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

/**
 * RSA公/私钥对.
 *
 * @author guer
 */
@Getter
public class RsaKeys {

	/**
	 * 公钥.
	 */
	private String publicKey;

	/**
	 * 私钥.
	 */
	private String privateKey;

	/**
	 * 公钥对象.
	 */
	@JsonIgnore
	private PublicKey publicKeyRef;

	/**
	 * 私钥对象.
	 */
	@JsonIgnore
	private PrivateKey privateKeyRef;

	/**
	 * 公钥内容.
	 */
	@JsonIgnore
	private String publicKeyContent;

	/**
	 * 私钥内容.
	 */
	@JsonIgnore
	private String privateKeyContent;

	/**
	 * 公钥格式化后内容.
	 */
	@JsonIgnore
	private String publicKeyFormattedContent;

	/**
	 * 私钥格式化后内容.
	 */
	@JsonIgnore
	private String privateKeyFormattedContent;

	/**
	 * 创建RSA公/私钥对.
	 */
	public RsaKeys() {

	}

	/**
	 * 创建RSA公/私钥对.
	 *
	 * @param publicKey
	 *         RSA私钥
	 * @param privateKey
	 *         RSA私钥
	 */
	public RsaKeys(String publicKey, String privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;

		formatPublicKey();
		formatPrivateKey();
	}

	/**
	 * 设置RSA公钥.
	 *
	 * @param publicKey
	 *         RSA公钥
	 */
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
		formatPublicKey();
	}

	/**
	 * 设置RSA私钥.
	 *
	 * @param privateKey
	 *         RSA私钥
	 */
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
		formatPrivateKey();
	}

	private void formatPublicKey() {
		publicKeyRef = RsaUtils.parsePublicKey(this.publicKey);
		publicKeyContent = RsaUtils.cleanFormat(publicKey);
		publicKeyFormattedContent = RsaUtils.formatPublicKey(publicKey);
	}

	private void formatPrivateKey() {
		privateKeyRef = RsaUtils.parsePrivateKey(this.privateKey);
		privateKeyContent = RsaUtils.cleanFormat(privateKey);
		privateKeyFormattedContent = RsaUtils.formatPrivateKey(privateKey);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		else if (o == null || getClass() != o.getClass()) {
			return false;
		}
		RsaKeys rsaKeys = (RsaKeys) o;
		return Objects.equals(publicKeyContent, rsaKeys.publicKeyContent) && Objects.equals(privateKeyContent,
				rsaKeys.privateKeyContent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(publicKeyContent, privateKeyContent);
	}
}
