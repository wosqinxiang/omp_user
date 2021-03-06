package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang    cc ReqVo  true
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@Data
public class RoleReqVo  {

    @ApiModelProperty("")
    private String roleName;

    @ApiModelProperty("")
    private String roleDesc;

    @ApiModelProperty("")
    private Integer parentId;
}
