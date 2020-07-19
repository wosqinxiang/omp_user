package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020/7/18 12:21
 */
@Data
public class CompanySimpleRspVo {

    @ApiModelProperty("业务主键")
    private Long companyId;

    @ApiModelProperty("名称")
    private String companyName;

    @ApiModelProperty("商家编码")
    private String companyCode;
}
