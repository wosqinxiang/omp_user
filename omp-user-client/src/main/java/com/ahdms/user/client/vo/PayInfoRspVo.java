package com.ahdms.user.client.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020/7/19 21:53
 */
@Data
public class PayInfoRspVo {

    @ApiModelProperty("银行卡信息")
    private BankInfo bankInfo;

    @ApiModelProperty("支付宝支付信息")
    private AlipayInfo alipayInfo;

    @ApiModelProperty("微信支付信息")
    private WechatInfo wechatInfo;

}
