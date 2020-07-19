package com.ahdms.user.center.feign;

import com.ahdms.user.center.service.ICustomerInfoService;
import com.ahdms.user.client.CustomerClientService;
import com.ahdms.user.client.vo.CustomerBasicInfoRspVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author qinxiang
 * @date 2020-07-15 10:24
 */
@Validated
@RestController
@RequestMapping("/rmi/omp/user")
@Api("Rmi-User控制器")
public class RmiCustomerClientServiceImpl implements CustomerClientService {

    @Autowired
    private ICustomerInfoService customerInfoService;

    @Override
    public CustomerBasicInfoRspVo getCustomerInfo(@NotNull Long userId) {
        return customerInfoService.getCustomerBasicInfo(userId);
    }
}
