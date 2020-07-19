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
 * @date 2020-07-16 18:06
 */
@Data
public class PayInfoReqVo  {

    @ApiModelProperty("业务主键")
    private Long payInfoId;

    @ApiModelProperty("关联商家业务ID")
    private Long companyInfoId;

    @ApiModelProperty("类型(1.银行，2支付宝，3.微信)")
    private Integer type;

    @ApiModelProperty("支付账户详情")
    private String payInfoDesc;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("修改人")
    private Long updatedBy;

    @ApiModelProperty("更新时间")
    private Date updatedAt;
}
