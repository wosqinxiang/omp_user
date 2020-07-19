package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-14 15:39
 */
@Data
public class CustomerComBusiRspVo {

    @ApiModelProperty("客户ID")
    private Long customerId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("审核状态(0.未审核，1.已审核)")
    private Integer auditStatus;

    @ApiModelProperty("认证状态(0.未认证，1.已认证)")
    private Integer authStatus;

    @ApiModelProperty("服务权限信息ID")
    private String secretId;

    @ApiModelProperty("服务权限信息KEY")
    private String secretKey;

    @ApiModelProperty("企业组织机构代码")
    private String companyCode;

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

    @ApiModelProperty("企业客服码")
    private String companyServcieCode;

    @ApiModelProperty("企业邮箱")
    private String companyEmail;

    @ApiModelProperty("审核结果")
    private Integer auditResult;

    @ApiModelProperty("审核备注")
    private String auditDesc;

    @ApiModelProperty("审核类型")
    private Integer auditType;

    @ApiModelProperty("审核内容")
    private String auditInfo;
}
