package com.ahdms.user.client.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-15 10:09
 */
@Data
public class CustomerBasicInfoRspVo {

    @ApiModelProperty("账号名")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("业务主键")
    private Long customerId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("审核状态(0.未审核，1.已审核)")
    private Integer auditStatus;

    @ApiModelProperty("认证状态(0.未认证，1.已认证)")
    private Integer authStatus;

    @ApiModelProperty("服务权限信息ID")
    private String secretId;

    @ApiModelProperty("服务权限信息KEY")
    private String secretKey;


}
