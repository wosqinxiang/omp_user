package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FWSCompanyRspVo {

    @ApiModelProperty("商家业务Id")
    private Long companyId;

    @ApiModelProperty("名称")
    private String companyName;

    @ApiModelProperty("商家编码")
    private String companyCode;

    @ApiModelProperty("行业")
    private String trade;

    @ApiModelProperty("区域")
    private String area;

    @ApiModelProperty("银行卡账户信息")
    private BankInfo bankInfo;

//    @ApiModelProperty("开户行")
//    private String bankName;
//
//    @ApiModelProperty("开户人姓名")
//    private String bankCardUsername;

    @ApiModelProperty("常用联系人姓名")
    private String contactName;

    @ApiModelProperty("常用联系人手机号")
    private String contactPhone;

    @ApiModelProperty("审核备注")
    private String auditDesc;

    @ApiModelProperty("审核结果")
    private Integer auditResult;

    @ApiModelProperty("审核内容")
    private String auditInfo;

    @ApiModelProperty("审核状态")
    private Integer auditStatus;
}
