package com.ahdms.user.center.controller;

import com.ahdms.user.client.UserClientService;
import com.ahdms.user.client.vo.UserLoginRspVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author qinxiang
 * @date 2020-07-13 18:19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/customer-info")
@Api(" 控制器")
public class TestController {

    @Autowired
    private UserClientService userClientService;

    @PostMapping("/testLogin")
    public UserLoginRspVo loginPassword(@Validated @NotNull @RequestParam("identity") String identity,
                                 @Validated @NotNull @RequestParam("password") String password){
        return userClientService.loginPassword(identity,password);
    }
}
