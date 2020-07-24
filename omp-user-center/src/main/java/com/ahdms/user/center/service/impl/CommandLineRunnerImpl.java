package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.DigestUtils;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.entity.Role;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.entity.UserRole;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.service.IAdminUserInfoService;
import com.ahdms.user.center.service.IRoleService;
import com.ahdms.user.center.service.IUserRoleService;
import com.ahdms.user.center.service.IUserService;
import com.ahdms.user.center.utils.MD5Utils;
import com.ahdms.user.center.utils.UUIDUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qinxiang
 * @date 2020/7/18 10:13
 */
@Service
public class CommandLineRunnerImpl implements CommandLineRunner {
    protected static final Logger logger = LoggerFactory.getLogger(CommandLineRunnerImpl.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IAdminUserInfoService adminUserInfoService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        int count = userService.count(new LambdaQueryWrapper<User>().
                eq(User::getUsername, BasicConstant.SUPER_ADMIN_USERNAME));
        if(count == 0){
            if (logger.isDebugEnabled()) {
                logger.debug("初始化超级管理员信息..");
            }
            User user = new User();
            user.setUsername(BasicConstant.SUPER_ADMIN_USERNAME);
            String salt = UUIDUtils.getUUID();
            String pwd = MD5Utils.md5WithSalt(DigestUtils.md5Hex(BasicConstant.SUPER_ADMIN_PASSWORD),salt);
            user.setSalt(salt);
            user.setPassword(pwd);
            user.setEnabled(BasicConstant.STATUS_ON);
            userService.save(user);

            AdminUserInfo adminUserInfo = new AdminUserInfo();
            adminUserInfo.setUserId(user.getUserId());
            adminUserInfoService.save(adminUserInfo);

            Role role = roleService.getOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, BasicConstant.ROLE_SUPER_ADMIN));
            UserRole userRole = new UserRole();
            userRole.setRoleName(role.getRoleName());
            userRole.setRoleId(role.getId());
            userRole.setUserId(user.getUserId());

            userRoleService.save(userRole);

        }
    }

    public static void main(String[] args){
        System.out.println(DigestUtils.md5Hex("AHdms520"));
    }
}
