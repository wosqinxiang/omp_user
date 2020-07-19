package com.ahdms.user.client.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qinxiang
 * @date 2020-07-14 14:12
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserRspVo {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("企业编码")
    private String companyCode;

}
