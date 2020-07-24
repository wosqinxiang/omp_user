package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang    cc ReqVo  true
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Data
public class CompanyInfoReqVo  {

    @ApiModelProperty("业务主键")
    private Long companyId;

    @ApiModelProperty("名称")
    private String companyName;

    @ApiModelProperty("商家状态(0.启用、1.停用)")
    private Integer status;


}
