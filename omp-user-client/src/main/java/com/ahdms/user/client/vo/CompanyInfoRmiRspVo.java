package com.ahdms.user.client.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-16 20:00
 */
@Data
public class CompanyInfoRmiRspVo {

    @ApiModelProperty("企业业务主键")
    private Long companyId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("企业编码")
    private String companyCode;

}
