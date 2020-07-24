package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FWSCompanyPageRspVo {

    @ApiModelProperty("商家业务ID")
    private Long companyId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("商家编码")
    private String companyCode;

    @ApiModelProperty("供应商状态")
    private Integer status;

    @ApiModelProperty("审核状态")
    private Integer auditStatus;

}
