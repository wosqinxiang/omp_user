package com.ahdms.user.center.controller;

import com.ahdms.framework.cache.client.RedisOpsClient;
import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.OmpContextUtils;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.user.center.bean.bo.*;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.vo.*;
import com.ahdms.user.center.service.ICustomerInfoService;
import com.ahdms.user.client.vo.CustomerBasicInfoRspVo;
import com.ahdms.user.client.vo.UserLoginRspVo;
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


/**
 * <B>说明： 控制器</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:35
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/customer-info")
@Api(" 控制器")
public class CustomerInfoController {
    @Autowired
    private ICustomerInfoService customerInfoService;

    /**
     * 发送验证码
     * @param mobile
     * @return
     */
    @GetMapping("sendSmsCode")
    @ApiOperation(value = "发送验证码", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "mobile", value = "手机号码", required = true, dataType = "String")
    public String sendSmsCode(@Validated @NotNull @RequestParam("mobile") String mobile){

        String smsCode = customerInfoService.sendSmsCode(mobile);

        return smsCode;
    }

    /**
     * 校验验证码
     * @param mobile
     * @return
     */
    @GetMapping("checkSmsCode")
    @ApiOperation(value = "校验验证码", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "mobile", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "smsCode", value = "手机验证码", required = true, dataType = "String")
    })
    public void checkSmsCode(@Validated @NotNull @RequestParam("mobile") String mobile,
                               @Validated @NotNull @RequestParam("smsCode") String smsCode){

        customerInfoService.checkSmsCode(mobile,smsCode);
    }

    /**
     * 通过手机号验证码修改绑定手机号
     * @param mobile
     * @return
     */
    @GetMapping("updateMobile")
    @ApiOperation(value = "通过手机号验证码修改绑定手机号", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "mobile", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "smsCode", value = "手机验证码", required = true, dataType = "String")
    })
    public void updateMobile(@Validated @NotNull @RequestParam("mobile") String mobile,
                               @Validated @NotNull @RequestParam("smsCode") String smsCode){

        customerInfoService.updateMobile(mobile,smsCode);

    }

    /**
     * 通过原密码修改密码
     * @param newPwd
     * @param oldPwd
     * @return
     */
    @GetMapping("updatePassword")
    @ApiOperation(value = "通过原密码修改密码", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "newPwd", value = "新密码(MD5后)", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "oldPwd", value = "原密码(MD5后)", required = true, dataType = "String")
    })
    public void updatePassword(@Validated @NotNull @RequestParam("newPwd") String newPwd,
                               @Validated @NotNull @RequestParam("oldPwd") String oldPwd){

        customerInfoService.updatePassword(newPwd,oldPwd);

    }

    /**
     * 找回密码，验证手机号与验证码
     * @param mobile
     * @param smsCode
     * @return
     */
    @GetMapping("password/back")
    @ApiOperation(value = "找回密码，验证手机号与验证码", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "mobile", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "smsCode", value = "手机验证码", required = true, dataType = "String")
    })
    public void backPassword(@Validated @NotNull @RequestParam("mobile") String mobile,
                              @Validated @NotNull @RequestParam("smsCode") String smsCode){

        customerInfoService.backPassword(mobile,smsCode);

    }

    /**
     * 通过手机号验证码重置密码
     * @param newPwd
     * @return
     */
    @GetMapping("password/reset")
    @ApiOperation(value = "通过手机号验证码重置密码", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "newPwd", value = "新密码(MD5后)", required = true, dataType = "String")
    public void resetPassword(@Validated @NotNull @RequestParam("newPwd") String newPwd){

        customerInfoService.resetPassword(newPwd);

    }

    /**
     * 客户通过手机号、验证码注册
     * @param mobile
     * @param smsCode
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "客户通过手机号、验证码注册", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "mobile", value = "手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "smsCode", value = "手机验证码", required = true, dataType = "String")
    })
    public UserLoginRspVo register(@Validated @NotNull @RequestParam("mobile") String mobile,
                                   @Validated @NotNull @RequestParam("smsCode") String smsCode){


        return customerInfoService.register(mobile,smsCode);
    }

    /**
     * 检验邮箱是否已经存在
     * @param email
     * @return
     */
    @PostMapping("/checkEmail")
    @ApiOperation(value = "检验邮箱是否已经存在", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "email", value = "邮箱", required = true, dataType = "String")
    public void checkEmail(@Validated @NotNull @RequestParam("email") String email){

        customerInfoService.checkEmail(email);
    }

    /**
     * 获取客户基本信息
     * @return
     */
    @GetMapping("/getCustomerInfo")
    @ApiOperation(value = "获取客户基本信息", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CustomerBasicInfoRspVo getCustomerInfo(){
        Long userId = OmpContextUtils.getUserId();
        return customerInfoService.getCustomerBasicInfo(userId);
    }

    /**
     * 检验用户名是否已经存在
     * @param username
     * @return
     */
    @PostMapping("/checkUsername")
    @ApiOperation(value = "检验用户名是否已经存在", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "username", value = "用户名", required = true, dataType = "String")
    public void checkUsername(@Validated @NotNull @RequestParam("username") String username){

        customerInfoService.checkUsername(username);
    }

    /**
     * 客户基本信息设置
     * @param customerInfoReqBo
     * @return
     */
    @PostMapping("/setBasicInfo")
    @ApiOperation(value = "客户基本信息设置", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void setBasicInfo(@Validated @NotNull @RequestBody CustomerInfoReqBo customerInfoReqBo){

        customerInfoService.setBasicInfo(customerInfoReqBo);

    }

    /**
     * 分页条件查询客户信息
     * @param customerInfoPageReqVo
     * @return
     */
    @PostMapping("/page/customer")
    @ApiOperation(value = "分页条件查询客户信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResult<CustomerInfoPageRspBo> pageCustomer(@Validated @NotNull @RequestBody CustomerInfoPageReqVo customerInfoPageReqVo){

        IPage<CustomerInfoPageRspBo> customerRecords = customerInfoService.pageCustomer(customerInfoPageReqVo);
        return PageUtils.toPageResult(customerRecords);
    }
}
