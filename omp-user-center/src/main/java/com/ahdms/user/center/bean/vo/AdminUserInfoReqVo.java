package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang    cc ReqVo  true
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Data
public class AdminUserInfoReqVo  {

    @ApiModelProperty("业务主键")
    private Long adminId;

    @ApiModelProperty("用户名")
    @Pattern(regexp = ".{8,16}",message = "输入的账号名格式有误")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("商家业务主键")
    private Long companyId;

    @ApiModelProperty("所属部门")
    private Integer departmentId;

    @ApiModelProperty("0.启用，1.停用")
    private Integer status;

}
