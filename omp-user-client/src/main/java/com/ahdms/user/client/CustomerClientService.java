package com.ahdms.user.client;

import com.ahdms.user.client.vo.CustomerBasicInfoRspVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

/**
 * @author qinxiang
 * @date 2020-07-15 10:20
 */
@FeignClient(value = "${client.omp.user.center.group:omp-user-center}", contextId = "user")
@RequestMapping("/rmi/user/customer")
public interface CustomerClientService {

    @GetMapping("/basicInfo")
    public CustomerBasicInfoRspVo getCustomerInfo(@Validated @NotNull @RequestParam("userId") Long userId);
}
