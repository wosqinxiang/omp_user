package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-16 19:02
 */
@Data
public class BankInfo {

    @ApiModelProperty("对公银行卡账户")
    private String bankNum;

    @ApiModelProperty("开户行")
    private String bankName;

    @ApiModelProperty("开户人姓名")
    private String bankUsername;
}
