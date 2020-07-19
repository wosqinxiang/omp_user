package com.ahdms.user.client.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qinxiang
 * @date 2020-07-14 14:10
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRspVo {

    private String username;

    private String mobile;

    private String email;



}
