package net.guerlab.smart.platform.commons;

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
