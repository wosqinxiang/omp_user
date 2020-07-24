package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Data
public class CustomerProductInfoRspVo  {

    @ApiModelProperty("")
    private Integer id;

    @ApiModelProperty("业务主键")
    private Long custProdId;

    @ApiModelProperty("关联客户业务主键")
    private Long customerId;

    @ApiModelProperty("关联产品业务主键")
    private Long productId;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("剩余次数")
    private Integer remainCount;

    @ApiModelProperty("总次数")
    private Integer totalCount;


}
