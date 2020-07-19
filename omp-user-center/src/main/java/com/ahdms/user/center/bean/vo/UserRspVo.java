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
 * @date 2020-07-13 13:35
 */
@Data
public class UserRspVo  {

    @ApiModelProperty("")
    private Integer id;

    @ApiModelProperty("业务主键")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("密码(MD5)")
    private String password;

    @ApiModelProperty("")
    private String salt;

    @ApiModelProperty("状态(0.可用，1.禁用)")
    private Integer enabled;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("修改人")
    private Long updatedBy;

    @ApiModelProperty("更新时间")
    private Date updatedAt;
}
