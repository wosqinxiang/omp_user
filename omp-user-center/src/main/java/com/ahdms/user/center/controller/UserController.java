package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.page.PageResult;
import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.PageUtils;
import com.ahdms.framework.core.validation.PatchGroup;
import com.ahdms.framework.core.validation.PostGroup;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.entity.UserRole;
import com.ahdms.user.center.bean.vo.UserPageReqVo;
import com.ahdms.user.center.bean.vo.UserReqVo;
import com.ahdms.user.center.bean.vo.UserRspVo;
import com.ahdms.user.center.bo.UserPageBo;
import com.ahdms.user.center.service.IUserService;
import com.ahdms.user.client.vo.UserLoginRspVo;
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
 * <B>说明： 用户管理控制器</B><BR>
 *
 * @author YuanCan
 * @version 1.0.0
 * @since 2020-07-13 13:35
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/user")
@Api("用户管理控制器")
public class UserController {
    @Autowired
    private IUserService userService;

//    @PostMapping("/page")
//    @ApiOperation(value = "分页查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public PageResult<UserRspVo> page(@RequestBody UserPageReqVo reqVo) {
//        UserPageBo pageBo = BeanUtils.copy(reqVo, UserPageBo.class);
//        IPage<User> userRecords = userService.findPage(pageBo);
//        return PageUtils.toPageResult(userRecords, UserRspVo.class);
//    }
//
//    @GetMapping("/id")
//    @ApiOperation(value = "查看用户信息", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public UserRspVo find(@Validated @NotNull @RequestParam("userId") Long userId) {
//        User user = userService.getByBId(userId);
//        return BeanUtils.copy(user, UserRspVo.class);
//    }
//
//    @PostMapping("/updateStatus")
//    @ApiOperation(value = "更改用户状态", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public void updateStatus(@Validated @NotNull @RequestParam("userId") Long userId,
//                                  @Validated @NotNull @RequestParam("status (0:启用 1:禁用)") Integer status) {
//        userService.updateStatus(userId, status);
//    }
//
//    @PostMapping("/updatePassword")
//    @ApiOperation(value = "更改用户密码", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public void updatePassword(@Validated @NotNull @RequestParam("userId") Long userId,
//                             @Validated @NotNull @RequestParam("password") String password) {
//        userService.updatePassword(userId, password);
//    }
//
//    @PostMapping("/add")
//    @ApiOperation(value = "新增用户", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public void save(@Validated({PostGroup.class}) @RequestBody UserReqVo userReqVo) {
//        userService.saveUser(userReqVo);
//    }
//
//    @PostMapping("/updateUser")
//    @ApiOperation(value = "修改用户", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public void update(@Validated({PostGroup.class}) @RequestBody UserReqVo userReqVo) {
//        userService.updateUser(userReqVo);
//    }
   
}
