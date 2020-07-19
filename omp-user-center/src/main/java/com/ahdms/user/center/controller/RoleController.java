package com.ahdms.user.center.controller;

import com.ahdms.user.center.bean.entity.Role;
import com.ahdms.user.center.bean.vo.RoleRspVo;
import com.ahdms.user.center.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/role")
@Api(" 控制器")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @GetMapping("roleList")
    @ApiOperation(value = "根据当前登录人角色获取可添加的角色列表", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public List<RoleRspVo> roleList(){
        return roleService.roleList();
    }

    @GetMapping("all")
    @ApiOperation(value = "获取所有角色列表", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public List<RoleRspVo> allList(){
        return roleService.allList();
    }
}
