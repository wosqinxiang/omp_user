package com.ahdms.user.center.bean.entity;

import com.ahdms.framework.mybatis.annotation.TableBId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * <B>说明：供应商管理</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@TableName("company_business_info")
@Data
@EqualsAndHashCode
public class CompanyBusinessInfo {

    @TableId
    private Long id;

    /**
     * 业务主键
     */
    @TableBId
    private Long comBusinessId;
    /**
     * 客户业务主键ID
     */
    private Long customerId;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业组织机构代码
     */
    private String companyBusiCode;
    /**
     * 企业营业执照副本附件
     */
    private String companyPic;
    /**
     * 企业法人姓名
     */
    private String legalName;
    /**
     * 企业法人联系信息
     */
    private String legalContactInfo;
    /**
     * 常用联系人姓名
     */
    private String contactName;
    /**
     * 常用联系人身份证号
     */
    private String contactIdcard;
    /**
     * 常用联系人手机号
     */
    private String contactPhone;
    /**
     * 企业所属行业
     */
    private String companyType;
    /**
     * 企业办公地址
     */
    private String companyAddress;
    /**
     * 企业客服码
     */
    private String companyServcieCode;
    /**
     * 企业邮箱
     */
    private String companyEmail;
    /**
     * 状态（0.在使用，1.待变更，2.已作废）
     */
    private Integer status;
    /**
     * 审核信息业务ID
     */
    private Long auditId;
    /**
     * 信息描述(如 企业认证，商务信息变更)
     */
    private String infoDesc;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date createdAt;

}
