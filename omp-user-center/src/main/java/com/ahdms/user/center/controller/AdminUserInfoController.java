package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.user.center.bean.vo.AdminUserInfoPageReqVo;
import com.ahdms.user.center.bean.vo.AdminUserInfoReqVo;
import com.ahdms.user.center.bean.vo.AdminUserInfoRspVo;
import com.ahdms.user.center.bean.vo.AdminUserPageRspVo;
import com.ahdms.user.center.service.IAdminUserInfoService;
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
 * <B>说明： 控制器</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/admin-user-info")
@Api("管理员管理")
public class AdminUserInfoController {
    @Autowired
    private IAdminUserInfoService adminUserInfoService;

    @PostMapping("addAdmin")
    @ApiOperation(value = "新增管理员", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addAdmin(@Validated @RequestBody AdminUserInfoReqVo adminUserInfoReqVo){
        adminUserInfoService.addAdmin(adminUserInfoReqVo);
    }

    @PostMapping("pageAdminInfos")
    @ApiOperation(value = "根据角色分页条件查看管理人员信息列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResult<AdminUserPageRspVo> pageAdminInfos(@Validated @RequestBody AdminUserInfoPageReqVo pageQueryVo){
        IPage page = adminUserInfoService.pageAdminInfos(pageQueryVo);
        return PageUtils.toPageResult(page);
    }

    @GetMapping("showAdminInfo")
    @ApiOperation(value = "查看管理员信息", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "adminId", value = "管理员业务主键", required = true, dataType = "integer")
    public AdminUserInfoRspVo showAdminInfo(@Validated @RequestParam Long adminId){
        return adminUserInfoService.showAdminInfo(adminId);
    }

    @PostMapping("updateAdminInfo")
    @ApiOperation(value = "修改管理员信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateAdminInfo(@Validated @RequestBody AdminUserInfoReqVo adminUserInfoReqVo){
        adminUserInfoService.updateAdmin(adminUserInfoReqVo);
    }

    @PostMapping("statusAdminInfo")
    @ApiOperation(value = "停用/启用管理员", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "adminId", value = "管理员业务主键", required = true, dataType = "integer"),
            @ApiImplicitParam(paramType="query", name = "status", value = "0.启用，1.停用", required = true, dataType = "integer")
    })
    public void statusAdminInfo(@Validated @RequestParam Long adminId,@Validated @RequestParam Integer status){
        adminUserInfoService.statusAdminInfo(adminId,status);
    }

    @PostMapping("resetAdminPWD")
    @ApiOperation(value = "重置管理员密码", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "adminId", value = "管理员业务主键", required = true, dataType = "integer"),
            @ApiImplicitParam(paramType="query", name = "newPassword", value = "新密码(MD5后的值)", required = true, dataType = "String")
    })
    public void resetAdminPWD(@Validated @RequestParam Long adminId,@Validated @RequestParam String newPassword){
        adminUserInfoService.resetAdminPWD(adminId,newPassword);
    }
}
