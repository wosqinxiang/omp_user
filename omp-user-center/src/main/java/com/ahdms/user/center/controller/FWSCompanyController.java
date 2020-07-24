package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.user.center.bean.entity.CompanyInfo;
import com.ahdms.user.center.bean.vo.*;
import com.ahdms.user.center.service.ICompanyBusinessInfoService;
import com.ahdms.user.center.service.ICompanyInfoService;
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

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/user/company/fws")
@Api("")

public class FWSCompanyController {

    @Autowired
    private ICompanyInfoService companyInfoService;


    @PostMapping("/addFWSCompany")
    @ApiOperation(value = "添加服务商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addFWSCompany(@Validated @RequestBody FWSCompanyReqVo reqVo) {
        companyInfoService.addFWSCompany(reqVo);
    }

    @GetMapping("/info")
    @ApiOperation(value = "查看服务商详情", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public FWSCompanyRspVo find(@Validated @NotNull @RequestParam("companyId") Long companyId) {
        return companyInfoService.fwsInfo(companyId);
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查看服务商信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResult<FWSCompanyPageRspVo> fwsPage(@RequestBody FWSCompanyPageReqVo reqVo) {

        IPage<CompanyInfo> records = companyInfoService.pageFWS(reqVo);
        return PageUtils.toPageResult(records, FWSCompanyPageRspVo.class);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查看所有可用的服务商(根据绑定状态)", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParam(paramType="query", name = "bindingStatus", value = "是否绑定服务商管理员(1.是)", required = false, dataType = "integer")
    public List<CompanySimpleRspVo> list(@Validated @RequestParam(required = false,defaultValue = "0") Integer bindingStatus) {

        return companyInfoService.listByBinding(bindingStatus);
    }


    @PatchMapping("/status")
    @ApiOperation(value = "启用停用服务商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void status(@Validated @RequestBody CompanyInfoReqVo companyInfoReqVo) {
        companyInfoService.fwsStatus(companyInfoReqVo.getCompanyId(), companyInfoReqVo.getStatus());
    }

    @PatchMapping("/update")
    @ApiOperation(value = "修改服务商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateFWSCompany(@Validated @RequestBody FWSCompanyReqVo reqVo) {
        companyInfoService.updateFWSCompany(reqVo);
    }

    @PostMapping("/audit")
    @ApiOperation(value = "审核服务商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void audit(@Validated @RequestBody AuditInfoReqVo reqVo) {
        companyInfoService.audit(reqVo);
    }




}
