package com.ahdms.user.center.bean.entity;

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
@TableName("user_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends Entity {

    /**
     * 
     */
    private Long roleId;
    /**
     * 
     */
    private Long userId;
    /**
     * 
     */
    private String roleName;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
}
