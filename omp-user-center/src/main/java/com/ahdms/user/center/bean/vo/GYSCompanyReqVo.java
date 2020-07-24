package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 供应商
 * @author qinxiang
 * @date 2020-07-16 18:47
 */
@Data
public class GYSCompanyReqVo {

    @ApiModelProperty("供应商业务ID")
    private Long companyId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("企业组织机构代码")
    private String companyBusiCode;

    @ApiModelProperty("企业营业执照副本附件")
    private String companyPic;

    @ApiModelProperty("企业法人姓名")
    private String legalName;

    @ApiModelProperty("企业法人联系信息")
    private String legalContactInfo;

    @ApiModelProperty("常用联系人姓名")
    private String contactName;

    @ApiModelProperty("常用联系人身份证号")
    private String contactIdcard;

    @ApiModelProperty("常用联系人手机号")
    private String contactPhone;

    @ApiModelProperty("企业所属行业")
    private String companyType;

    @ApiModelProperty("企业办公地址")
    private String companyAddress;

    @ApiModelProperty("企业邮箱")
    private String companyEmail;

    @ApiModelProperty("银行卡账户信息")
    private BankInfo bankInfo;

//    @ApiModelProperty("开户行")
//    private String bankName;
//
//    @ApiModelProperty("开户人姓名")
//    private String bankUsername;

    @ApiModelProperty("支付宝账号信息")
    private AlipayInfo alipayInfo;

    @ApiModelProperty("微信账号信息")
    private WechatInfo wechatInfo;
}
