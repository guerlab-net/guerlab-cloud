/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.commons;

/**
 * 正则表达式常量
 *
 * @author guer
 */
public interface RegexConstants {

    /**
     * 邮箱正则表达式
     */
    String EMAIL_REG = "^[\\.a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 电话号码正则表达式
     */
    String PHONE_REG = "^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}$";

}
