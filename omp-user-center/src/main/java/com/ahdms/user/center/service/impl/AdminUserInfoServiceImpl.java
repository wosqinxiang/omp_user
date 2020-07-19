package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.*;
import com.ahdms.framework.core.web.response.ResultAssert;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.entity.Role;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.entity.UserRole;
import com.ahdms.user.center.bean.vo.AdminUserInfoPageReqVo;
import com.ahdms.user.center.bean.vo.AdminUserInfoReqVo;
import com.ahdms.user.center.bean.vo.AdminUserInfoRspVo;
import com.ahdms.user.center.bean.vo.AdminUserPageRspVo;
import com.ahdms.user.center.code.ApiCode;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.dao.IAdminUserInfoDao;
import com.ahdms.user.center.service.*;
import com.ahdms.user.center.utils.MD5Utils;
import com.ahdms.user.center.utils.UUIDUtils;
import com.ahdms.user.center.utils.ValidateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * <B>说明：服务实现</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
@Slf4j
@Service
@Validated
public class AdminUserInfoServiceImpl extends BaseServiceImpl<IAdminUserInfoDao, AdminUserInfo> implements IAdminUserInfoService {
    @Autowired
    private IAdminUserInfoDao adminUserInfoDao;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ICompanyInfoService companyInfoService;

    @Override
    @Transactional
    public void addAdmin(AdminUserInfoReqVo adminUserInfoReqVo) {
        //判断用户名是否已经存在
        ResultAssert.throwOnFalse(ValidateUtils.validateUsername(adminUserInfoReqVo.getUsername()), ApiCode.USER_NAME_FORMAT_ERROR);
        User _user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername,adminUserInfoReqVo.getUsername()));
        ResultAssert.throwOnFalse(_user == null, ApiCode.USER_NAME_REPEAT);

        //判断手机号是否已注册
        ResultAssert.throwOnFalse(ValidateUtils.validateMobile(adminUserInfoReqVo.getMobile()), ApiCode.USER_MOBILE_FORMAT_ERROR);
        int mobileCount = userService.count(new QueryWrapper<User>().lambda().eq(User::getMobile,adminUserInfoReqVo.getMobile()));
        ResultAssert.throwOnFalse(mobileCount == 0, ApiCode.USER_MOBILE_REPEAT);

        //登录管理员信息
        AdminUserInfo loginAdmin = super.getOne(new QueryWrapper<AdminUserInfo>().lambda().eq(AdminUserInfo::getUserId,OmpContextUtils.getUserId()));
        Role loginRole = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleName,OmpContextUtils.getRole()));
        //判断登录用户是否有权限
        Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getId,adminUserInfoReqVo.getRoleId())
                        .eq(Role::getParentId,loginRole.getId()));
        ResultAssert.throwOnFalse(role != null, ApiCode.USER_ROLE_NOAUTH);

        //判断是否是添加服务商管理员 或 供应商管理员
        if(BasicConstant.ROLE_FWS_ADMIN.equals(role.getRoleName()) || BasicConstant.ROLE_GYS_ADMIN.equals(role.getRoleName())){
            ResultAssert.throwOnFalse(adminUserInfoReqVo.getCompanyId() != null, ApiCode.USER_NAME_REPEAT);
        }

        //添加用户表
        String salt = UUIDUtils.getUUID();
        User user = new User();
        user.setUsername(adminUserInfoReqVo.getUsername());
        user.setSalt(salt);
        user.setPassword(MD5Utils.md5WithSalt(adminUserInfoReqVo.getPassword(),salt));
        user.setEnabled(BasicConstant.STATUS_ON);
        user.setMobile(adminUserInfoReqVo.getMobile());
        userService.save(user);

        //添加用户权限表
        UserRole userRole = new UserRole();
        userRole.setRoleName(role.getRoleName());
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(Integer.parseInt(adminUserInfoReqVo.getRoleId()));
        userRoleService.save(userRole);

        //添加admin_user_info表
        AdminUserInfo adminUserInfo = BeanUtils.copy(adminUserInfoReqVo,AdminUserInfo.class);
        adminUserInfo.setUserId(user.getUserId());

        //如果登录管理员有companyId ,则 添加
        if(null != loginAdmin.getCompanyId())
            adminUserInfo.setCompanyId(loginAdmin.getCompanyId());

        super.save(adminUserInfo);
    }

    @Override
    public IPage pageAdminInfos(AdminUserInfoPageReqVo pageQueryVo) {
        //根据登录用户角色 查看对应得 管理员信息
        //中台管理人员可查看所有用户信息；供应商和服务商只能查看各自用户信息。
        AdminUserInfo adminUserInfo = super.getOne(new LambdaQueryWrapper<AdminUserInfo>()
                .eq(AdminUserInfo::getUserId,OmpContextUtils.getUserId()));
//        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<AdminUserInfo>()
//                .eq(null != adminUserInfo.getCompanyId(),AdminUserInfo::getCompanyId,adminUserInfo.getCompanyId());

        IPage<AdminUserInfo> page = new Page<>(pageQueryVo.getPageNum(), pageQueryVo.getPageSize());
        IPage<AdminUserPageRspVo> records = adminUserInfoDao.queryAdminSimple(page,pageQueryVo,adminUserInfo.getCompanyId());

        return  records;
    }

    @Override
    public void resetAdminPWD(Long adminId, String newPassword) {
        AdminUserInfo adminUserInfo = super.getByBId(adminId);
        User user = userService.getByBId(adminUserInfo.getUserId());
        String salt = UUIDUtils.getUUID();
        user.setSalt(salt);
        user.setPassword(MD5Utils.md5WithSalt(newPassword,salt));
        userService.updateByBId(user);
    }

    @Override
    @Transactional
    public void statusAdminInfo(Long adminId, Integer status) {
        //判断当前登录人是否有权限 启用停用
        User loginUser = userService.getByBId(OmpContextUtils.getUserId());
        Role loginRole = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleName,OmpContextUtils.getRole()));

        AdminUserInfo adminUserInfo = super.getByBId(adminId);
        User user = userService.getByBId(adminUserInfo.getUserId());
        UserRole userRole = userRoleService.getOne(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,user.getUserId()));

        //判断登录用户是否有权限
        Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getId,userRole.getRoleId())
                .eq(Role::getParentId,loginRole.getId()));
        ResultAssert.throwOnFalse(role != null, ApiCode.USER_ROLE_NOAUTH);


        //修改用户账号状态
        user.setEnabled(status);
        userService.updateByBId(user);

    }

    @Override
    @Transactional
    public void updateAdmin(AdminUserInfoReqVo adminUserInfoReqVo) {
        //检查用户名、手机号是否已被占用
        AdminUserInfo _admin = super.getByBId(adminUserInfoReqVo.getAdminId());
        User _user = userService.getByBId(_admin.getUserId());

        if(!_user.getUsername().equals(adminUserInfoReqVo.getUsername())){
            ResultAssert.throwOnFalse(ValidateUtils.validateUsername(adminUserInfoReqVo.getUsername()), ApiCode.USER_NAME_FORMAT_ERROR);
            User _user1 = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername,adminUserInfoReqVo.getUsername()));
            ResultAssert.throwOnFalse(_user == null, ApiCode.USER_NAME_REPEAT);
        }

        if(!_user.getMobile().equals(adminUserInfoReqVo.getMobile())){
            ResultAssert.throwOnFalse(ValidateUtils.validateMobile(adminUserInfoReqVo.getMobile()), ApiCode.USER_MOBILE_FORMAT_ERROR);
            int mobileCount = userService.count(new LambdaQueryWrapper<User>().eq(User::getMobile,adminUserInfoReqVo.getMobile()));
            ResultAssert.throwOnFalse(mobileCount == 0, ApiCode.USER_MOBILE_REPEAT);
        }

        //更新用户表
        _user.setUsername(adminUserInfoReqVo.getUsername());
        _user.setMobile(adminUserInfoReqVo.getMobile());
        userService.updateByBId(_user);
        //更新admin_user表
        _admin.setName(adminUserInfoReqVo.getName());
        _admin.setCompanyId(adminUserInfoReqVo.getCompanyId());
        super.updateByBId(_admin);
        //更新用户角色表
        UserRole userRole = userRoleService.getOne(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,_user.getUserId()));
        Role role = roleService.getById(adminUserInfoReqVo.getRoleId());
        userRole.setRoleId(Integer.parseInt(role.getId()+""));
        userRole.setRoleName(role.getRoleName());
        userRoleService.updateByBId(userRole);
    }

    @Override
    public AdminUserInfoRspVo showAdminInfo(Long adminId) {

        AdminUserInfo admin = super.getByBId(adminId);
        User user = userService.getByBId(admin.getUserId());
        UserRole userRole = userRoleService.getOne(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,user.getUserId()));

        AdminUserInfoRspVo rsp = BeanUtils.copy(user,AdminUserInfoRspVo.class);
        rsp.setAdminId(adminId);
        rsp.setName(admin.getName());
        if(null != admin.getCompanyId()){
            rsp.setCompanyId(admin.getCompanyId());
            rsp.setCompanyName(companyInfoService.getByBId(admin.getCompanyId()).getCompanyName());
        }
        BeanUtils.copy(userRole,rsp);
        Role role = roleService.getById(userRole.getRoleId());
        rsp.setRoleDesc(role.getRoleDesc());
        return rsp;
    }
}

