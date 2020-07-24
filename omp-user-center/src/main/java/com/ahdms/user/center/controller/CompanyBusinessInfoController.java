package com.ahdms.user.center.controller;

import com.ahdms.user.center.bean.bo.AuditInfoReqBo;
import com.ahdms.user.center.bean.bo.ComBusiRecordRspBo;
import com.ahdms.user.center.bean.bo.CompanyBusinessReqBo;
import com.ahdms.user.center.bean.vo.CompanyBusinessInfoRspVo;
import com.ahdms.user.center.service.ICompanyBusinessInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


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
@RequestMapping("/api/user/comBus")
@Api("企业认证")
public class CompanyBusinessInfoController {

    @Autowired
    private ICompanyBusinessInfoService companyBusinessInfoService;


    /**
     * 客户企业认证
     * @param companyBusinessReqBo
     * @return
     */
    @PostMapping("/auth")
    @ApiOperation(value = "客户企业认证", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void authCompanyBusiness(@Validated @NotNull @RequestBody CompanyBusinessReqBo companyBusinessReqBo){

        companyBusinessInfoService.authCompanyBusiness(companyBusinessReqBo);

    }

    /**
     * 客户企业认证变更
     * @param companyBusinessReqBo
     * @return
     */
    @PatchMapping("/update")
    @ApiOperation(value = "客户企业认证变更", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateCompanyBusiness(@Validated @NotNull @RequestBody CompanyBusinessReqBo companyBusinessReqBo){

        companyBusinessInfoService.updateCompanyBusiness(companyBusinessReqBo);

    }

//    /**
//     * 查看企业商务信息
//     *
//     * @return
//     */
//    @GetMapping("/getCompanyBussInfo")
//    @ApiOperation(value = "查看企业商务信息", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public CustomerComBusiRspVo getCompanyBussInfo(@RequestParam(required = false) String customerId){
//
//        CustomerComBusiRspVo customerComBusiRspVo = companyBusinessInfoService.getCompanyBussInfo(customerId);
//        return customerComBusiRspVo;
//    }

    /**
     * 查看企业商务信息变更记录列表
     *
     * @return
     */
    @GetMapping("/updateRecords")
    @ApiOperation(value = "查看企业商务信息变更记录列表", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "customerId", value = "客户业主主键", required = false, dataType = "String")
    public List<ComBusiRecordRspBo> getCompanyBussRecords(@RequestParam(required = false) String customerId){
        // TODO 代码简洁，直接 return companyBusinessInfoService.getCompanyBussRecords(customerId);
        List<ComBusiRecordRspBo> records = companyBusinessInfoService.getCompanyBussRecords(customerId);
        return records;
    }

    /**
     * 根据客户认证状态查看企业商务信息记录表详情
     *
     * @return
     */
    @GetMapping("/info/customerId")
    @ApiOperation(value = "根据客户认证状态查看企业商务信息记录表详情", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "customerId", value = "客户业主主键", required = false, dataType = "String")
    public CompanyBusinessInfoRspVo getCompanyBussRecord(@RequestParam(required = false) String customerId){

        CompanyBusinessInfoRspVo record = companyBusinessInfoService.getCompanyBusinessInfoRspVo(customerId);
        return record;
    }

    /**
     * 根据企业商务信息ID查看企业商务信息记录表详情
     */
    @GetMapping("/info/comBusId")
    @ApiOperation(value = "根据企业商务信息ID查看企业商务信息记录表详情", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "comBusId", value = "企业商务信息ID", required = true, dataType = "String")
    public CompanyBusinessInfoRspVo getComBussInfo(@RequestParam(required = true) String comBusId){

        CompanyBusinessInfoRspVo record = companyBusinessInfoService.getCompanyBussRecord(comBusId);
        return record;
    }

    /**
     * 客户企业认证审核
     * @param auditInfoReqBo
     * @return
     */
    @PostMapping("/audit")
    @ApiOperation(value = "客户企业认证审核", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void auditCompanyBusiness(@Validated @NotNull @RequestBody AuditInfoReqBo auditInfoReqBo){

        companyBusinessInfoService.auditCompanyBusiness(auditInfoReqBo);
    }

}
