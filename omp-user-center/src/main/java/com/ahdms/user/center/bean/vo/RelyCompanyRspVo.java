package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-17 10:35
 */
@Data
public class RelyCompanyRspVo {

    @ApiModelProperty("依赖方业务主键")
    private Long companyId;

    @ApiModelProperty("依赖方状态")
    private Integer status;

    @ApiModelProperty("依赖方编码")
    private String companyCode;

    @ApiModelProperty("依赖方名称")
    private String companyName;

}
