package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.OmpContextUtils;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.user.center.bean.bo.*;
import com.ahdms.user.center.bean.vo.CustomerBasicReqVo;
import com.ahdms.user.center.bean.vo.CustomerInfoPageReqVo;
import com.ahdms.user.center.bean.vo.CustomerInfoPageRspVo;
import com.ahdms.user.center.bean.vo.MobileSmsCodeVo;
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
import javax.validation.constraints.Pattern;


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
@RequestMapping("/api/user/customer")
@Api(" 控制器")
public class CustomerInfoController {
    @Autowired
    private ICustomerInfoService customerInfoService;

    /**
     * 发送验证码
     * @param mobile
     * @return
     */
    @GetMapping("permit/sendSmsCode")
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
     * @param mobileVo
     * @return
     */
    @PostMapping("permit/password/back")
    @ApiOperation(value = "找回密码，验证手机号与验证码", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MobileSmsCodeVo backPassword(@Validated @NotNull @RequestBody MobileSmsCodeVo mobileVo){
        MobileSmsCodeBo mobileSmsCodeBo = BeanUtils.copy(mobileVo, MobileSmsCodeBo.class);
        MobileSmsCodeBo mobileBo = customerInfoService.backPassword(mobileSmsCodeBo);
        return BeanUtils.copy(mobileBo,MobileSmsCodeVo.class);
    }

    /**
     * 通过手机号验证码重置密码
     * @param mobileVo
     * @return
     */
    @PostMapping("permit/password/reset")
    @ApiOperation(value = "找回密码,通过手机号验证码验证后，重置密码", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void resetPassword(@Validated @NotNull @RequestBody MobileSmsCodeVo mobileVo){

        customerInfoService.resetPassword(mobileVo);

    }

    /**
     * 客户通过手机号、验证码注册
     * @param mobile
     * @param smsCode
     * @return
     */
    @PostMapping("permit/register")
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
    @GetMapping("/checkEmail")
    @ApiOperation(value = "检验邮箱是否已经存在", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "email", value = "邮箱", required = true, dataType = "String")
    public void checkEmail(@Validated @NotNull @RequestParam("email") String email){

        customerInfoService.checkEmail(email);
    }

    /**
     * 获取客户基本信息
     *
     * @return
     */
    @GetMapping("/getCustomerInfo")
    @ApiOperation(value = "获取客户基本信息", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CustomerBasicInfoRspVo getCustomerInfo(){
        Long userId = OmpContextUtils.getUserId();
        CustomerDetailBo customerBasicInfo = customerInfoService.getCustomerBasicInfo(userId);
        return BeanUtils.copy(customerBasicInfo,CustomerBasicInfoRspVo.class);
    }

    /**
     * 检验用户名是否已经存在
     * @param username
     * @return
     */
    @GetMapping("/checkUsername")
    @ApiOperation(value = "检验用户名是否已经存在", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "username", value = "用户名", required = true, dataType = "String")
    public void checkUsername(@Validated @NotNull @RequestParam("username") String username){

        customerInfoService.checkUsername(username);
    }

    /**
     * 客户基本信息设置
     * @param customerInfoReqVo
     * @return
     */
    @PostMapping("/setBasicInfo")
    @ApiOperation(value = "客户基本信息设置", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void setBasicInfo(@Validated @NotNull @RequestBody CustomerBasicReqVo customerInfoReqVo){
        CustomerInfoBasicBo customerInfoBo = BeanUtils.copy(customerInfoReqVo,CustomerInfoBasicBo.class);
        customerInfoService.setBasicInfo(customerInfoBo);

    }

    /**
     * 分页条件查询客户信息
     * @param customerInfoPageReqVo
     * @return
     */
    @PostMapping("/page/customer")
    @ApiOperation(value = "分页条件查询客户信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageResult<CustomerInfoPageRspVo> pageCustomer(@Validated @NotNull @RequestBody CustomerInfoPageReqVo customerInfoPageReqVo){
        CustomerPageBo customerPageBo = BeanUtils.copy(customerInfoPageReqVo, CustomerPageBo.class);
        IPage<CustomerInfoPageBo> customerRecords = customerInfoService.pageCustomer(customerPageBo);
        return PageUtils.toPageResult(customerRecords,CustomerInfoPageRspVo.class);
    }
}
