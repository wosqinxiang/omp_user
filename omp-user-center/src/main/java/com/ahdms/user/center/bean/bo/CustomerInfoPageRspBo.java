package com.ahdms.user.center.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-14 9:32
 */
@Data
public class CustomerInfoPageRspBo {

    @ApiModelProperty("业务主键")
    private Long customerId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("审核状态(0.无，1.待审核，3.已审核)")
    private Integer auditStatus;

    @ApiModelProperty("认证状态(0.未认证，1.认证中，2.变更中 3.已退回 5.已认证)")
    private Integer authStatus;

    @ApiModelProperty("服务权限信息ID")
    private String secretId;

    @ApiModelProperty("服务权限信息KEY")
    private String secretKey;

}
