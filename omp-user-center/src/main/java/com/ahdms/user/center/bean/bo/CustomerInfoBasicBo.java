package com.ahdms.user.center.bean.bo;

import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-13 14:44
 */
@Data
public class CustomerInfoBasicBo {

    private Long userId;

    private String username;

    private String email;

    private String password;

}
