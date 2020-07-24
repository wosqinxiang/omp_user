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
 * @date 2020-07-13 13:35
 */
@TableName("customer_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerInfo extends Entity {

    /**
     * 业务主键
     */
    @TableBId
    private Long customerId;
    /**
     * 关联用户业务主键ID
     */
    private Long userId;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 审核状态(0.未审核，1.已审核)
     */
    private Integer auditStatus;
    /**
     * 认证状态(0.未认证，1.已认证)
     */
    private Integer authStatus;
    /**
     * 服务权限信息ID
     */
    private String secretId;
    /**
     * 服务权限信息KEY
     */
    private String secretKey;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
}
