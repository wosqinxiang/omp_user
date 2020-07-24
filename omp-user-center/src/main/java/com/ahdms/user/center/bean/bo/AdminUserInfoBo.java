package com.ahdms.user.center.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-23 19:15
 */
@Data
public class AdminUserInfoBo {

    @ApiModelProperty("业务主键")
    private Long adminId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("角色ID")
    private String roleId;

    @ApiModelProperty("角色描述")
    private String roleDesc;

    @ApiModelProperty("状态(0.正常，1.停用)")
    private Integer enabled;

}
