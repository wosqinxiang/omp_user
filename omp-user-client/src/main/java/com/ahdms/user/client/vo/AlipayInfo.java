package com.ahdms.user.client.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-16 19:00
 */
@Data
public class AlipayInfo {

    @ApiModelProperty("支付宝APPID")
    private String alipayAppId;

    @ApiModelProperty("支付宝账户私钥")
    private String alipayPrivateKey;

    @ApiModelProperty("支付宝账户公钥")
    private String alipayPublicKey;

    @ApiModelProperty("支付宝SellerId")
    private String alipaySellerId;

    @ApiModelProperty("支付宝seller邮箱")
    private String alipaySellerEmail;

}
