package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DLSCompanyPageRspVo {

    @ApiModelProperty("商家业务Id")
    private Long companyId;

    @ApiModelProperty("商家编码")
    private String companyCode;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("企业组织机构代码")
    private String companyBusiCode;

    @ApiModelProperty("供应商状态")
    private Integer status;

    @ApiModelProperty("审核状态")
    private Integer auditStatus;
}
