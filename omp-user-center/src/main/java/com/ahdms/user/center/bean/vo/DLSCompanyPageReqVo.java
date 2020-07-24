package com.ahdms.user.center.bean.vo;

import com.ahdms.framework.core.commom.page.PageReqParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DLSCompanyPageReqVo extends PageReqParam {

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("供应商编码")
    private String companyCode;

    @ApiModelProperty("企业组织机构代码")
    private String companyBusiCode;
}
