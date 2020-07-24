package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.user.center.bean.entity.CompanyInfo;
import com.ahdms.user.center.bean.vo.*;
import com.ahdms.user.center.bo.CompanyInfoPageBo;
import com.ahdms.user.center.service.ICompanyBusinessInfoService;
import com.ahdms.user.center.service.ICompanyInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/user/company/dls")
@Api("")

public class DLSCompanyController {

    @Autowired
    private ICompanyInfoService companyInfoService;

    @PostMapping("/addDLSCompany")
    @ApiOperation(value = "添加代理商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addDLSCompany(@Validated @RequestBody DLSCompanyReqVo reqVo) {
        // TODO 命名应符合驼峰命名法，并使用单词来描述，如：ProxyServiceCompany
        companyInfoService.addDLSCompany(reqVo);
    }

    @GetMapping("/info")
    @ApiOperation(value = "查看代理商详情", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public DLSCompanyRspVo find(@Validated @NotNull @RequestParam("companyId") Long companyId) {
       return companyInfoService.dlsInfo(companyId);
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查看代理商信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResult<DLSCompanyPageRspVo> dlsPage(@RequestBody DLSCompanyPageReqVo reqVo) {
        IPage<CompanyInfo> records = companyInfoService.pageDLS(reqVo);
        return PageUtils.toPageResult(records, DLSCompanyPageRspVo.class);
    }


    @PatchMapping("/update")
    @ApiOperation(value = "修改代理商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateFWSCompany(@Validated @RequestBody DLSCompanyReqVo reqVo) {
        companyInfoService.updateDLSCompany(reqVo);
    }

    @PatchMapping("/status")
    @ApiOperation(value = "代理商停用启用", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateServiceStatus(@Validated @RequestBody CompanyInfoReqVo companyInfoReqVo) {
        companyInfoService.dlsStatus(companyInfoReqVo.getCompanyId(), companyInfoReqVo.getStatus());
    }


}
