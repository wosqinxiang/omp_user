package com.ahdms.user.center.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@TableName("provinces")
@Data
//@EqualsAndHashCode(callSuper = true)
public class Provinces {

    /**
     * 
     */
    @TableId
    private String code;
    /**
     * 
     */
    private String name;
}
