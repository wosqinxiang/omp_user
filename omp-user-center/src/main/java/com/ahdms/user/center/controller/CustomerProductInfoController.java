package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.user.center.bean.entity.CustomerProductInfo;
import com.ahdms.user.center.bean.vo.CustomerProductInfoPageReqVo;
import com.ahdms.user.center.bean.vo.CustomerProductInfoRspVo;
import com.ahdms.user.center.service.ICustomerProductInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <B>说明： 控制器</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/customer-product-info")
@Api(" 控制器")
public class CustomerProductInfoController {
    @Autowired
    private ICustomerProductInfoService customerProductInfoService;

    @ApiOperation(value = "分页查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/page")
    public PageResult<CustomerProductInfoRspVo> page(@RequestBody CustomerProductInfoPageReqVo customerProductInfoPageReqVo){
        IPage<CustomerProductInfo> records = customerProductInfoService.findPage(customerProductInfoPageReqVo);
        return PageUtils.toPageResult(records,CustomerProductInfoRspVo.class);
    }

    @ApiOperation(value = "pullCustomerProductInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/pullCustomerProductInfo")
    public void page(){
        customerProductInfoService.pullCustomerProductInfo();
//        return PageUtils.toPageResult(records,CustomerProductInfoRspVo.class);
    }

}
