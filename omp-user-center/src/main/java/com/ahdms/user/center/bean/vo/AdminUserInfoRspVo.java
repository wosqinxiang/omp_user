package com.ahdms.user.center.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Data
public class AdminUserInfoRspVo  {


    @ApiModelProperty("业务主键")
    private Long adminId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("角色名称")
    private String roleDesc;

    @ApiModelProperty("商家业务主键")
    private Long companyId;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("所属部门")
    private Integer departmentId;


}
