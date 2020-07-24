package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DLSCompanyRspVo {

    @ApiModelProperty("商家业务Id")
    private Long companyId;

    @ApiModelProperty("名称")
    private String companyName;

    @ApiModelProperty("商家编码")
    private String companyCode;

    @ApiModelProperty("企业组织机构代码")
    private String companyBusiCode;

    @ApiModelProperty("行业")
    private String trade;

    @ApiModelProperty("企业营业执照副本附件")
    private String companyPic;

    @ApiModelProperty("银行账户")
    private String bankCardNum;

    @ApiModelProperty("银行卡账户信息")
    private BankInfo bankInfo;

    @ApiModelProperty("开户人姓名")
    private String bankCardUsername;

    @ApiModelProperty("企业法人姓名")
    private String legalName;

    @ApiModelProperty("企业法人联系信息")
    private String legalContactInfo;

    @ApiModelProperty("常用联系人姓名")
    private String contactName;

    @ApiModelProperty("常用联系人手机号")
    private String contactPhone;

    @ApiModelProperty("企业邮箱")
    private String companyEmail;

    @ApiModelProperty("企业办公地址")
    private String companyAddress;
}
