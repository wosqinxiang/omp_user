package com.ahdms.user.center.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang    cc ReqVo  true
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@Data
public class UserRoleReqVo  {

    @ApiModelProperty("")
    private Integer roleId;

    @ApiModelProperty("")
    private Long userId;

    @ApiModelProperty("")
    private String roleName;
}
