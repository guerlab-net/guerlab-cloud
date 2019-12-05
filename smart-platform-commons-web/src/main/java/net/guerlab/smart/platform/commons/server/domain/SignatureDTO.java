package net.guerlab.smart.platform.commons.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.Collection;

/**
 * 签名数据
 *
 * @author guer
 */
@Data
@ApiModel("签名数据")
public class SignatureDTO {

    /**
     * 默认js api列表
     */
    public static final Collection<String> DEFAULT_JS_API_LIST = Arrays
            .asList("onMenuShareTimeline", "onMenuShareAppMessage", "onMenuShareQQ", "onMenuShareWeibo",
                    "onMenuShareQZone", "startRecord", "stopRecord", "onVoiceRecordEnd", "playVoice", "pauseVoice",
                    "stopVoice", "onVoicePlayEnd", "uploadVoice", "downloadVoice", "chooseImage", "previewImage",
                    "uploadImage", "downloadImage", "translateVoice", "getNetworkType", "openLocation", "getLocation",
                    "hideOptionMenu", "showOptionMenu", "hideMenuItems", "showMenuItems", "hideAllNonBaseMenuItem",
                    "showAllNonBaseMenuItem", "closeWindow", "scanQRCode", "chooseWXPay", "openProductSpecificView",
                    "addCard", "chooseCard", "openCard");

    /**
     * debug
     */
    @ApiModelProperty("debug")
    private Boolean debug;

    /**
     * 公众号的唯一标识
     */
    @ApiModelProperty("公众号的唯一标识")
    private String appId;

    /**
     * 生成签名的时间戳
     */
    @ApiModelProperty("生成签名的时间戳")
    private Long timestamp;

    /**
     * 生成签名的随机串
     */
    @ApiModelProperty("生成签名的随机串")
    private String nonceStr;

    /**
     * 签名
     */
    @ApiModelProperty("签名")
    private String signature;

    /**
     * jsApiList
     */
    @ApiModelProperty("jsApiList")
    private Collection<String> jsApiList;

}
