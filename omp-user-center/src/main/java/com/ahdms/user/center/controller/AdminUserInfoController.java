package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.user.center.bean.bo.*;
import com.ahdms.user.center.bean.vo.AdminUserInfoPageReqVo;
import com.ahdms.user.center.bean.vo.AdminUserInfoReqVo;
import com.ahdms.user.center.bean.vo.AdminUserInfoRspVo;
import com.ahdms.user.center.bean.vo.AdminUserPageRspVo;
import com.ahdms.user.center.service.IAdminUserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@RequestMapping("/api/user/admin")
@Api(value = "管理员管理", description = "管理员管理")
public class AdminUserInfoController {
    @Autowired
    private IAdminUserInfoService adminUserInfoService;

    @PostMapping("addAdmin")
    @ApiOperation(value = "新增管理员", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @OperLog(operModul = "用户中心", operDesc = "新增管理员")
    public void addAdmin(@Validated @RequestBody AdminUserInfoReqVo adminUserInfoReqVo) {
        UserInfoBo userInfoBo = BeanUtils.copy(adminUserInfoReqVo, UserInfoBo.class);
        AdminInfoBo adminInfoBo = BeanUtils.copy(adminUserInfoReqVo, AdminInfoBo.class);
        adminUserInfoService.addAdmin(userInfoBo,adminInfoBo);
    }

    @PostMapping("pageAdminInfos")
    @ApiOperation(value = "根据角色分页条件查看管理人员信息列表", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResult<AdminUserPageRspVo> pageAdminInfos(@Validated @RequestBody AdminUserInfoPageReqVo pageQueryVo) {
        /**
         * TODO：
         * 1.泛型对象申明类型，如：IPage<User>
         * 2.service层对象申明均为BO，在controller层做bean拷贝后返回
         */
        AdminUserPageQueryBo adminInfoBo = BeanUtils.copy(pageQueryVo, AdminUserPageQueryBo.class);

        IPage<AdminUserInfoBo> page = adminUserInfoService.pageAdminInfos(adminInfoBo);

        return PageUtils.toPageResult(page, AdminUserPageRspVo.class);
    }

    @GetMapping("showAdminInfo")
    @ApiOperation(value = "查看管理员信息", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType = "query", name = "adminId", value = "管理员业务主键", required = true, dataType = "integer")
    public AdminUserInfoRspVo showAdminInfo(@Validated @RequestParam Long adminId) {
        AdminInfoBasicBo adminInfoBasicBo = adminUserInfoService.showAdminInfo(adminId);
        return BeanUtils.copy(adminInfoBasicBo,AdminUserInfoRspVo.class);
    }

    @GetMapping("info")
    @ApiOperation(value = "查看登录管理员信息", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AdminUserInfoRspVo showAdminInfo() {
        AdminInfoBasicBo adminInfoBasicBo = adminUserInfoService.showAdminInfo();
        return BeanUtils.copy(adminInfoBasicBo,AdminUserInfoRspVo.class);
    }

    @PutMapping("updateAdminInfo")
    @ApiOperation(value = "修改管理员信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @OperLog(operModul = "用户中心", operDesc = "修改管理员信息")
    public void updateAdminInfo(@Validated @RequestBody AdminUserInfoReqVo adminUserInfoReqVo) {
        AdminInfoBasicBo adminInfoBasicBo = BeanUtils.copy(adminUserInfoReqVo,AdminInfoBasicBo.class);
        adminUserInfoService.updateAdmin(adminInfoBasicBo);
    }

    @PatchMapping("statusAdminInfo")
    @ApiOperation(value = "停用/启用管理员", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @OperLog(operModul = "用户中心", operDesc = "启用/停用管理员")
    public void statusAdminInfo(@Validated @RequestBody AdminUserInfoReqVo adminUserInfoReqVo) {
        // TODO Vo对象定义不规范，启用禁用可以定义公共的VO对象，仅包含id和status
        adminUserInfoService.statusAdminInfo(adminUserInfoReqVo.getAdminId(), adminUserInfoReqVo.getStatus());
    }

    @PatchMapping("resetAdminPWD")
    @ApiOperation(value = "重置管理员密码", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @OperLog(operModul = "用户中心", operDesc = "重置管理员密码")
    public void resetAdminPWD(@Validated @RequestBody AdminUserInfoReqVo adminUserInfoReqVo) {
        adminUserInfoService.resetAdminPWD(adminUserInfoReqVo.getAdminId(), adminUserInfoReqVo.getPassword());
    }
}
