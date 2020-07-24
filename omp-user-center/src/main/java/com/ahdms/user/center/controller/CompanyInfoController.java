package com.ahdms.user.center.controller;

import com.ahdms.user.center.service.ICompanyInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <B>说明：服务商管理、供应商管理控制器</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/company-info")
@Api("")
public class CompanyInfoController {
    @Autowired
    private ICompanyInfoService companyInfoService;



}
