package com.ahdms.user.center.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Data
public class CompanyBusinessInfoRspVo  {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("业务主键")
    private Long comBusinessId;

    @ApiModelProperty("客户业务主键ID")
    private Long customerId;

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

    @ApiModelProperty("企业客服码")
    private String companyServcieCode;

    @ApiModelProperty("企业邮箱")
    private String companyEmail;

    @ApiModelProperty("状态（0.在使用，1.待变更，2.已作废）")
    private Integer status;

    @ApiModelProperty("审核信息业务ID")
    private Long auditId;

    @ApiModelProperty("信息描述(如 企业认证，商务信息变更)")
    private String infoDesc;

    @ApiModelProperty("")
    private Date createdAt;

    @ApiModelProperty("审核结果")
    private Integer auditResult;

    @ApiModelProperty("审核备注")
    private String auditDesc;

    @ApiModelProperty("认证状态(0:未认证,1:认证中,2:变更中,3:已退回,5:已认证)")
    private Integer authStatus;
}
