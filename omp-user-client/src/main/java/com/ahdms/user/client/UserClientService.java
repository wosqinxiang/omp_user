package com.ahdms.user.client;

import com.ahdms.user.client.vo.UserLoginRspVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

/**
 * @author qinxiang
 * @date 2020-07-13 13:53
 */
@FeignClient(value = "${client.omp.user.center.group:omp-user-center}", contextId = "user")
@RequestMapping("/rmi/security/user")
public interface UserClientService {

    @PostMapping("/login/password")
    UserLoginRspVo loginPassword(@Validated @NotNull @RequestParam("identity") String identity,
                                 @Validated @NotNull @RequestParam("password") String password);


    @PostMapping("/login/smsCode")
    UserLoginRspVo loginSmsCode(@Validated @NotNull @RequestParam("mobile") String mobile,
                                 @Validated @NotNull @RequestParam("smsCode") String smsCode);

}
