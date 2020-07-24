package com.ahdms.user.center.bean.vo;

import com.ahdms.framework.core.commom.page.PageReqParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FWSCompanyPageReqVo extends PageReqParam {

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("商家编码")
    private String companyCode;

}
