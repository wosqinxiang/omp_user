package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.user.center.bean.vo.*;
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

import java.util.List;

/**
 * @author qinxiang
 * @date 2020-07-17 13:25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/user/company/supplier")
@Api("供应商管理")
public class SupplierCompanyController {
    @Autowired
    private IRelyCompanyService relyCompanyService;

    @PostMapping("/add")
    @ApiOperation(value = "添加供应商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addGYSCompany(@Validated @RequestBody SupplierCompanyReqVo reqVo) {
        relyCompanyService.addGYSCompany(reqVo);
    }

    @PostMapping("/info")
    @ApiOperation(value = "查看供应商详情", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "companyId", value = "供应商业务主键", required = true, dataType = "integer")
    public SupplierCompanyRspVo info(@Validated @RequestParam Long companyId) {
        return relyCompanyService.gysInfo(companyId);
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查看供应商信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResult<SupplierCompanyPageRspVo> page(@Validated @RequestBody SupplierCompanyPageReqVo reqVo) {
        IPage records = relyCompanyService.pageGYS(reqVo);
        return PageUtils.toPageResult(records, SupplierCompanyPageRspVo.class);
    }

    @PatchMapping("/update")
    @ApiOperation(value = "修改供应商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@Validated @RequestBody SupplierCompanyReqVo reqVo) {
        relyCompanyService.updateGYS(reqVo);
    }

    @PostMapping("/status")
    @ApiOperation(value = "停用启用供应商", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "companyId", value = "供应商业务主键", required = true, dataType = "integer"),
            @ApiImplicitParam(paramType="query", name = "status", value = "0.启用，1.停用", required = true, dataType = "integer")
    })
    public void status(@Validated @RequestParam Long companyId,@Validated @RequestParam Integer status) {
        relyCompanyService.gysStatus(companyId,status);
    }

    @PostMapping("/audit")
    @ApiOperation(value = "审核供应商", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void audit(@Validated @RequestBody AuditInfoReqVo reqVo) {
        relyCompanyService.audit(reqVo);
    }

    @GetMapping("/listCompanyInfos")
    @ApiOperation(value = "获取未绑定得可用得服务商或供应商", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "roleId", value = "角色Id", required = true, dataType = "integer")
    public List<CompanySimpleRspVo> listCompanyInfos(@Validated @RequestParam Long roleId) {
        return relyCompanyService.listCompanyInfos(roleId);
    }
}
