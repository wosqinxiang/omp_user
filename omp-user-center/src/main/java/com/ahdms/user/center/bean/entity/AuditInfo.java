package com.ahdms.user.center.bean.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ahdms.framework.mybatis.core.Entity;
import com.ahdms.framework.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@TableName("audit_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class AuditInfo extends Entity {

    /**
     * 业务主键
     */
    @TableBId
    private Long auditId;
    /**
     * 审核结果
     */
    private Integer auditResult;
    /**
     * 审核备注
     */
    private String auditDesc;
    /**
     * 审核类型
     */
    private Integer auditType;
    /**
     * 审核内容
     */
    private String auditInfo;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
}
