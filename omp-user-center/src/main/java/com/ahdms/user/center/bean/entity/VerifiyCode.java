package com.ahdms.user.center.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ahdms.framework.mybatis.core.Entity;
import com.ahdms.framework.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@TableName("verifiy_code")
@Data
@EqualsAndHashCode(callSuper = true)
public class VerifiyCode extends Entity {

    /**
     * 1 手机号码 2 EMAIL地址
     */
    private Integer type;
    /**
     * 
     */
    private String mobile;
    /**
     * 
     */
    private String email;
    /**
     * 
     */
    private String verificationCode;
    /**
     * 
     */
    private Date sendDate;
    /**
     * 验证后 被修改 1有效 0 无效
     */
    private Integer isValid;
}
