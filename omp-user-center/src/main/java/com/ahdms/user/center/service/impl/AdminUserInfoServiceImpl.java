package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.ObjectUtils;
import com.ahdms.framework.core.commom.util.OmpContextUtils;
import com.ahdms.framework.core.web.response.ResultAssert;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.bo.*;
import com.ahdms.user.center.bean.entity.*;
import com.ahdms.user.center.bean.vo.AdminUserInfoReqVo;
import com.ahdms.user.center.bean.vo.AdminUserInfoRspVo;
import com.ahdms.user.center.code.ApiCode;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.dao.IAdminUserInfoDao;
import com.ahdms.user.center.dao.ICompanyInfoDao;
import com.ahdms.user.center.service.IAdminUserInfoService;
import com.ahdms.user.center.service.IRoleService;
import com.ahdms.user.center.service.IUserRoleService;
import com.ahdms.user.center.service.IUserService;
import com.ahdms.user.center.utils.MD5Utils;
import com.ahdms.user.center.utils.UUIDUtils;
import com.ahdms.user.center.utils.ValidateUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

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
    private ICompanyInfoDao companyInfoDao;

    @Override
    public AdminUserInfo selectByUserId(Long userId) {
        return adminUserInfoDao.selectOne(new LambdaQueryWrapper<AdminUserInfo>()
                .eq(AdminUserInfo::getUserId, userId));
    }

    @Override
    @Transactional
    public void addAdmin(UserInfoBo userInfoBo, AdminInfoBo adminInfoBo) {
        //判断用户名是否已经存在
        ResultAssert.throwOnFalse(ObjectUtils.isEmpty(userService.selectByUsername(userInfoBo.getUsername())), ApiCode.USER_NAME_REPEAT);
        //判断手机号是否已注册
        ResultAssert.throwOnFalse(ObjectUtils.isEmpty(userService.selectByMobile(userInfoBo.getMobile())), ApiCode.USER_MOBILE_REPEAT);
        //判断登录用户是否有权限
        Role loginRole = roleService.selectByRoleName(OmpContextUtils.getRole());
        Role role = roleService.selectByIdAndParentId(userInfoBo.getRoleId(), loginRole.getId()); //要添加的用户角色
        ResultAssert.throwOnFalse(ObjectUtils.isNotEmpty(role), ApiCode.USER_ROLE_NOAUTH);
        //判断是否是添加服务商管理员 或 供应商管理员
        if (BasicConstant.ROLE_FWS_ADMIN.equals(role.getRoleName()) || BasicConstant.ROLE_GYS_ADMIN.equals(role.getRoleName())) {
            ResultAssert.throwOnFalse(adminInfoBo.getCompanyId() != null, ApiCode.USER_NAME_REPEAT);
        }
        //添加用户表
        User user = BeanUtils.copy(userInfoBo,User.class);
        String salt = UUIDUtils.getUUID();
        user.setSalt(salt);
        user.setPassword(MD5Utils.md5WithSalt(userInfoBo.getPassword(), salt));
        user.setEnabled(BasicConstant.STATUS_ON);
        userService.save(user);
        //添加用户权限表
        UserRole userRole = new UserRole();
        userRole.setRoleName(role.getRoleName());
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(userInfoBo.getRoleId());
        userRoleService.save(userRole);
        //添加admin_user_info表
        AdminUserInfo adminUserInfo = BeanUtils.copy(adminInfoBo, AdminUserInfo.class);
        adminUserInfo.setUserId(user.getUserId());
        //如果登录管理员有companyId ,则 添加
        AdminUserInfo loginAdmin = selectByUserId(OmpContextUtils.getUserId());
        Optional.ofNullable(loginAdmin).map(AdminUserInfo::getCompanyId)
                        .ifPresent(companyId -> adminUserInfo.setCompanyId(companyId));
        adminUserInfoDao.insert(adminUserInfo);
    }

    @Override
    public IPage<AdminUserInfoBo> pageAdminInfos(AdminUserPageQueryBo adminInfoBo) {
        //根据登录用户角色 查看对应得 管理员信息
        //中台管理人员可查看所有用户信息；供应商和服务商只能查看各自用户信息。
        AdminUserInfo adminUserInfo = this.selectByUserId(OmpContextUtils.getUserId());

        IPage<AdminUserInfo> page = new Page<>(adminInfoBo.getPageNum(), adminInfoBo.getPageSize());
        IPage<AdminUserInfoBo> records = adminUserInfoDao.queryAdminSimple(page, adminInfoBo, adminUserInfo.getCompanyId());
        return records;
    }

    @Override
    public void resetAdminPWD(Long adminId, String newPassword) {
        AdminUserInfo adminUserInfo = super.getByBId(adminId);
        userService.resetPassword(adminUserInfo.getUserId(),newPassword);
    }

    @Override
    @Transactional
    public void statusAdminInfo(Long adminId, Integer status) {
        //判断当前登录人是否有权限 启用停用
        // TODO 未使用对象为什么要声明？
        User loginUser = userService.getByBId(OmpContextUtils.getUserId());
        Role loginRole = roleService.getOne(new QueryWrapper<Role>().lambda()
                .eq(Role::getRoleName, OmpContextUtils.getRole()));

        AdminUserInfo adminUserInfo = super.getByBId(adminId);
        User user = userService.getByBId(adminUserInfo.getUserId());
        UserRole userRole = userRoleService.getOne(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, user.getUserId()));

        //判断登录用户是否有权限
        // TODO 对于可复用型逻辑在roleService中实现一个方法不香吗
        Role role = roleService.getOne(new QueryWrapper<Role>().lambda()
                .eq(Role::getId, userRole.getRoleId())
                .eq(Role::getParentId, loginRole.getId()));
        ResultAssert.throwOnFalse(role != null, ApiCode.USER_ROLE_NOAUTH);

        //判断服务商、供应商是否是停用状态
        //服务商、供应商被停用，服务商、供应商管理员不能被中台操作员启用
        if (null != adminUserInfo.getCompanyId()) {
            CompanyInfo companyInfo = companyInfoDao.selectByBId(adminUserInfo.getCompanyId());
            ResultAssert.throwOnFalse(BasicConstant.STATUS_ON.equals(companyInfo.getStatus()), ApiCode.USER_COM_DISABLED);
        }

        //修改用户账号状态
        user.setEnabled(status);
        userService.updateByBId(user);

    }

    @Override
    @Transactional
    public void updateAdmin(AdminInfoBasicBo adminInfoBasicBo) {
        //检查用户名、手机号是否已被占用
        AdminUserInfo admin = super.getByBId(adminInfoBasicBo.getAdminId());
        User user = userService.getByBId(admin.getUserId());
        // TODO 使用框架提供的工具类做逻辑判断，避免空指针
        //  ObjectUtils.nullSafeEquals(_user.getUsername(), adminUserInfoReqVo.getUsername())
        if (!user.getUsername().equals(adminInfoBasicBo.getUsername())) {
//            ResultAssert.throwOnFalse(ValidateUtils.validateUsername(adminUserInfoReqVo.getUsername()), ApiCode.USER_NAME_FORMAT_ERROR);
            ResultAssert.throwOnFalse(ObjectUtils.isEmpty(userService.selectByUsername(adminInfoBasicBo.getUsername())), ApiCode.USER_NAME_REPEAT);
        }

        if (!user.getMobile().equals(adminInfoBasicBo.getMobile())) {
//            ResultAssert.throwOnFalse(ValidateUtils.validateMobile(adminUserInfoReqVo.getMobile()), ApiCode.USER_MOBILE_FORMAT_ERROR);
            ResultAssert.throwOnFalse(ObjectUtils.isEmpty(userService.selectByMobile(adminInfoBasicBo.getMobile())), ApiCode.USER_MOBILE_REPEAT);
        }

        //更新用户表
        user.setUsername(adminInfoBasicBo.getUsername());
        user.setMobile(adminInfoBasicBo.getMobile());
        userService.updateByBId(user);
        //更新admin_user表
        admin.setName(adminInfoBasicBo.getName());
        admin.setCompanyId(adminInfoBasicBo.getCompanyId());
        super.updateByBId(admin);
        //更新用户角色表
        UserRole userRole = userRoleService.selectByUserId(user.getUserId());
        Role role = roleService.getById(adminInfoBasicBo.getRoleId());
        // TODO  尽量保持字段类型统一，避免频繁类型转换
        userRole.setRoleId(role.getId());
        userRole.setRoleName(role.getRoleName());
        userRoleService.updateById(userRole);
    }

    @Override
    public AdminInfoBasicBo showAdminInfo(Long adminId) {

        AdminUserInfo admin = super.getByBId(adminId);
        User user = userService.getByBId(admin.getUserId());
        return showAdminInfo(admin,user);
    }

    @Override
    public AdminInfoBasicBo showAdminInfo() {
        User user = userService.getByBId(OmpContextUtils.getUserId());
        AdminUserInfoRspVo rsp = BeanUtils.copy(user, AdminUserInfoRspVo.class);

        AdminUserInfo adminUserInfo = adminUserInfoDao.selectOne(new LambdaQueryWrapper<AdminUserInfo>().eq(AdminUserInfo::getUserId, user.getUserId()));
        return showAdminInfo(adminUserInfo,user);
    }

    private AdminInfoBasicBo showAdminInfo(AdminUserInfo adminUserInfo,User user){
        AdminInfoBasicBo rsp = BeanUtils.copy(user, AdminInfoBasicBo.class);
        rsp.setAdminId(adminUserInfo.getAdminId());
        rsp.setName(adminUserInfo.getName());
        if (null != adminUserInfo.getCompanyId()) {
            rsp.setCompanyId(adminUserInfo.getCompanyId());
            rsp.setCompanyName(companyInfoDao.selectByBId(adminUserInfo.getCompanyId()).getCompanyName());
        }
        UserRole userRole = userRoleService.selectByUserId(user.getUserId());
        Role role = roleService.getById(userRole.getRoleId());
        rsp.setRoleDesc(role.getRoleDesc());
        return rsp;
    }
}

