package com.ahdms.user.client.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qinxiang
 * @date 2020-07-13 14:12
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRspVo {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("角色名称")
    private String role;


}
