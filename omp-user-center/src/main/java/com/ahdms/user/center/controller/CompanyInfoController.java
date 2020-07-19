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

    @Autowired
    private ICompanyBusinessInfoService companyBusinessInfoService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询服务商/供应商/代理商/产品依赖服务方", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResult<CompanyInfoRspVo> servicePage(@RequestBody CompanyInfoPageReqVo reqVo) {
        CompanyInfoPageBo pageBo = BeanUtils.copy(reqVo, CompanyInfoPageBo.class);
        IPage<CompanyInfo> userRecords = companyInfoService.findPage(pageBo);
        return PageUtils.toPageResult(userRecords, CompanyInfoRspVo.class);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "查看服务商/供应商/代理商/产品依赖服务方详情", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CompanyInfoRspVo find(@Validated @NotNull @RequestParam("companyId") Long companyId) {
        CompanyInfo companyInfo = companyInfoService.getByBId(companyId);
        return BeanUtils.copy(companyInfo, CompanyInfoRspVo.class);
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "服务商/供应商/产品依赖服务方停用与启用", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void updateServiceStatus(@Validated @NotNull @RequestParam("companyId") Long companyId,
                                    @Validated @NotNull @RequestParam("status") Integer status) {
        companyInfoService.updateStatus(companyId, status);
    }


    @PostMapping("/addFWSCompany")
    @ApiOperation(value = "添加服务商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addFWSCompany(@Validated @RequestBody FWSCompanyReqVo reqVo) {
        companyInfoService.addFWSCompany(reqVo);
    }

    @PostMapping("/addGYSCompany")
    @ApiOperation(value = "添加供应商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addGYSCompany(@Validated @RequestBody SupplierCompanyReqVo reqVo) {
        companyInfoService.addGYSCompany(reqVo);
    }

    @PostMapping("/addDLSCompany")
    @ApiOperation(value = "添加代理商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addDLSCompany(@Validated @RequestBody DLSCompanyReqVo reqVo) {
        companyInfoService.addDLSCompany(reqVo);
    }

    @PostMapping("/addYLFCompany")
    @ApiOperation(value = "添加依赖方", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addYLSCompany(@Validated @RequestBody YLFCompanyReqVo reqVo) {
        companyInfoService.addYLFCompany(reqVo);
    }

    @PostMapping("/updateFWSCompany")
    @ApiOperation(value = "更新服务商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateFWSCompany(@Validated @RequestBody FWSCompanyReqVo reqVo) {
        companyInfoService.updateFWSCompany(reqVo);
    }

    @PostMapping("/updateGYSCompany")
    @ApiOperation(value = "更新供应商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateGYSCompany(@Validated @RequestBody SupplierCompanyReqVo reqVo) {
        companyInfoService.updateGYSCompany(reqVo);
    }

    @PostMapping("/updateDLSCompany")
    @ApiOperation(value = "更新代理商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateDLSCompany(@Validated @RequestBody DLSCompanyReqVo reqVo) {
        companyInfoService.updateDLSCompany(reqVo);
    }

    @PostMapping("/updateYLFCompany")
    @ApiOperation(value = "更新依赖方", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateYLSCompany(@Validated @RequestBody YLFCompanyReqVo reqVo) {
        companyInfoService.updateYLFCompany(reqVo);
    }

    @PostMapping("/addrelyCompany")
    @ApiOperation(value = "添加产品依赖方", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void addrelyCompany(@Validated @RequestParam String companyName) {
        companyInfoService.addrelyCompany(companyName);
    }

}
