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
 * @date 2020-07-13 13:35
 */
@TableName("user")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Entity {

    /**
     * 业务主键
     */
    @TableBId
    private Long userId;
    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String mobile;
    /**
     * 
     */
    private String email;
    /**
     * 密码(MD5)
     */
    private String password;
    /**
     * 
     */
    private String salt;
    /**
     * 
     */
    private Integer enabled;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

}
