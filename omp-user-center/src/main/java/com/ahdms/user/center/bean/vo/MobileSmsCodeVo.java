package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-22 12:30
 */
@Data
public class MobileSmsCodeVo {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("手机验证码")
    private String smsCode;

    @ApiModelProperty("重置密码Token")
    private String pwdToken;

    @ApiModelProperty("新密码(MD5)")
    private String password;
}
