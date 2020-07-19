package com.ahdms.user.center.bean.entity;

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
@TableName("trade_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class TradeInfo extends Entity {

    /**
     * 
     */
    private String tradeName;
    /**
     * 
     */
    private String tradeCode;
    /**
     * 
     */
    private Integer level;
    /**
     * 
     */
    private Integer parentId;
}
