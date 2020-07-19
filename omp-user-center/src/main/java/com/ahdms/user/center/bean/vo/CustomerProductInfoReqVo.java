package com.ahdms.user.center.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang    cc ReqVo  true
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Data
public class CustomerProductInfoReqVo  {

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
