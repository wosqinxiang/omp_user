package com.ahdms.user.center.bean.entity;

import com.ahdms.framework.mybatis.annotation.TableBId;
import com.ahdms.framework.mybatis.core.Entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@TableName("admin_user_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserInfo extends Entity implements Serializable {

    /**
     * 业务主键
     */
    @TableBId
    private Long adminId;
    /**
     * 
     */
    private Long userId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 商家业务主键
     */
    private Long companyId;
    /**
     * 所属部门
     */
    private Integer departmentId;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
}
