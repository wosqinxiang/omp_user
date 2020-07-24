package com.ahdms.user.center.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-23 19:25
 */
@Data
public class AdminInfoBasicBo {

    @ApiModelProperty("业务主键")
    private Long adminId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleDesc;

    @ApiModelProperty("商家业务主键")
    private Long companyId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("所属部门")
    private Integer departmentId;

}
