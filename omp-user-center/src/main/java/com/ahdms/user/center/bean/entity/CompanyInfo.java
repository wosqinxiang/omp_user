package com.ahdms.user.center.bean.entity;

import com.ahdms.framework.mybatis.annotation.TableBId;
import com.ahdms.framework.mybatis.core.Entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@TableName("company_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyInfo extends Entity {

    /**
     * 业务主键
     */
    @TableBId
    private Long companyId;
    /**
     * 名称
     */
    private String companyName;
    /**
     * 商家编码
     */
    private String companyCode;
    /**
     * 类型(1.服务商、2.供应商、3.代理商, 4.产品依赖服务方)
     */
    private Integer type;
    /**
     * 审核状态(0.已审核、1.未审核)
     */
    private Integer auditStatus;
    /**
     * 审核信息业务ID
     */
    private Long auditId;
    /**
     * 商家状态(0.启用、1.停用)
     */
    private Integer status;
    /**
     * 行业
     */
    private String trade;
    /**
     * 区域
     */
    private String area;
    /**
     * 企业商务信息表id
     */
    private Long companyBusinessId;
    /**
     * 支付账号信息表业务主键
     */
    private Long payInfoId;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

}
