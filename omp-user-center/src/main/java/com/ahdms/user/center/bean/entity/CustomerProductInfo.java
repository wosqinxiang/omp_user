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
 * @date 2020-07-13 13:34
 */
@TableName("customer_product_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerProductInfo extends Entity {

    /**
     * 业务主键
     */
    @TableBId
    private Long custProdId;
    /**
     * 关联客户业务主键
     */
    private Long customerId;
    /**
     * 关联产品业务主键
     */
    private Long productId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 剩余次数
     */
    private Integer remainCount;
    /**
     * 总次数
     */
    private Integer totalCount;
}
