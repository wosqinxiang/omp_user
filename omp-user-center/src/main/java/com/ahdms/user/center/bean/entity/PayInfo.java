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
@TableName("pay_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class PayInfo extends Entity {

    /**
     * 业务主键
     */
    @TableBId
    private Long payInfoId;
    /**
     * 关联商家业务ID
     */
    private Long companyInfoId;
    /**
     * 类型(1.银行，2支付宝，3.微信)
     */
    private Integer type;
    /**
     * 支付账户详情
     */
    private String payInfoDesc;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
}
