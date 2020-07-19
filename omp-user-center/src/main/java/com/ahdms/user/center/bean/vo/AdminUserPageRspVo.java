package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020/7/18 9:42
 */
@Data
public class AdminUserPageRspVo {

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
