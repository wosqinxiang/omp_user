package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-16 19:02
 */
@Data
public class WechatInfo {

    @ApiModelProperty("微信AppId")
    private String wechatAppId;

    @ApiModelProperty("微信PartnerId")
    private String wechatPartnerId;

    @ApiModelProperty("微信Key")
    private String wechatKey;
}
