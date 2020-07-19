package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.user.center.bean.entity.CompanyInfo;
import com.ahdms.user.center.bean.vo.RelyCompanyPageVo;
import com.ahdms.user.center.bean.vo.RelyCompanyRspVo;
import com.ahdms.user.center.service.IRelyCompanyService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author qinxiang
 * @date 2020-07-17 12:56
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/user/company/rely")
@Api("产品依赖方管理")
public class RelyCompanyController {

    @Autowired
    private IRelyCompanyService relyCompanyService;

    @PostMapping("/add")
    @ApiOperation(value = "添加产品依赖方", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "companyName", value = "依赖方名称", required = true, dataType = "String")
    public void addRelyCompany(@Validated @RequestParam String companyName) {
        relyCompanyService.addrelyCompany(companyName);
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询产品依赖方", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResult<RelyCompanyRspVo> page(@Validated @RequestBody RelyCompanyPageVo company) {
        IPage<CompanyInfo> records = relyCompanyService.page(company);
        return PageUtils.toPageResult(records,RelyCompanyRspVo.class);
    }

    @PatchMapping("/update")
    @ApiOperation(value = "修改产品依赖方", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "companyId", value = "依赖方业务主键", required = true, dataType = "integer"),
            @ApiImplicitParam(paramType="query", name = "companyName", value = "依赖方名称", required = true, dataType = "String")
    })
    public void updateRelyCompany(@Validated @RequestParam Long companyId,@Validated @RequestParam String companyName) {
        relyCompanyService.updaterelyCompany(companyId,companyName);
    }

    @PostMapping("/status")
    @ApiOperation(value = "启用禁用依赖方", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "companyId", value = "依赖方业务主键", required = true, dataType = "integer(int64)"),
            @ApiImplicitParam(paramType="query", name = "status", value = "0.启用，1.停用", required = true, dataType = "java.lang.Integer")
    })
    public void statusRelyCompany(@Validated @RequestParam Long companyId,@Validated @RequestParam Integer status) {
        relyCompanyService.statusRelyCompany(companyId,status);
    }


}
