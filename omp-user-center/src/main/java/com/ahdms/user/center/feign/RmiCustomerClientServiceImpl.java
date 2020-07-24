package com.ahdms.user.center.feign;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.JsonUtils;
import com.ahdms.user.center.bean.bo.CustomerDetailBo;
import com.ahdms.user.center.service.ICustomerInfoService;
import com.ahdms.user.client.CustomerClientService;
import com.ahdms.user.client.vo.CustomerBasicInfoRspVo;
import com.ahdms.user.client.vo.CustomerProductReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author qinxiang
 * @date 2020-07-15 10:24
 */
@Validated
@RestController
@RequestMapping("/rmi/user/customer")
@Api("Rmi-User控制器")
public class RmiCustomerClientServiceImpl implements CustomerClientService {

    @Autowired
    private ICustomerInfoService customerInfoService;

    @Override
    @GetMapping("basicInfo")
    public CustomerBasicInfoRspVo getCustomerInfo(@NotNull Long userId) {
        CustomerDetailBo customerBasicInfo = customerInfoService.getCustomerBasicInfo(userId);
        return BeanUtils.copy(customerBasicInfo,CustomerBasicInfoRspVo.class);
    }

    @Override
    @PostMapping("/order")
    @ApiOperation(value = "推送客户订单数据", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int order(@Validated @NotNull @RequestBody CustomerProductReqVo customerProductReqVo){
        System.out.println(JsonUtils.toJson(customerProductReqVo).toString());
        return 0;
    }
}
