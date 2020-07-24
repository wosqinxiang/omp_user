package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@Data
public class CustomerInfoRspVo  {

    @ApiModelProperty("")
    private Integer id;

    @ApiModelProperty("业务主键")
    private Long customerId;

    @ApiModelProperty("关联用户业务主键ID")
    private Long userId;

    @ApiModelProperty("审核状态(0.未审核，1.已审核)")
    private Integer auditStatus;

    @ApiModelProperty("认证状态(0.未认证，1.已认证)")
    private Integer authStatus;

    @ApiModelProperty("服务权限信息ID")
    private String secretId;

    @ApiModelProperty("服务权限信息KEY")
    private String secretKey;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("修改人")
    private Long updatedBy;

    @ApiModelProperty("更新时间")
    private Date updatedAt;

    @ApiModelProperty("角色名称")
    private String roleName;

}
