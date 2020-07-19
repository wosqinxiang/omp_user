package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.DigestUtils;
import com.ahdms.framework.core.commom.util.StringUtils;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.entity.UserRole;
import com.ahdms.user.center.bo.UserPageBo;
import com.ahdms.user.center.dao.IUserDao;
import com.ahdms.user.center.service.IAdminUserInfoService;
import com.ahdms.user.center.service.IUserRoleService;
import com.ahdms.user.center.service.IUserService;
import com.ahdms.user.center.utils.UUIDUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.ahdms.user.center.bean.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * <B>说明：服务实现</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:35
 */
@Slf4j
@Service
@Validated
public class UserServiceImpl extends BaseServiceImpl<IUserDao, User> implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserRoleService userRoleService;

//    @Autowired
    private IAdminUserInfoService adminUserInfoService;

    @Override
    public IPage<User> findPage(UserPageBo pageBo) {
        LambdaQueryWrapper wrapper = Wrappers.<User>lambdaQuery()
                .like(StringUtils.isNotBlank(pageBo.getUserName()), User::getUsername, pageBo.getUserName())
                .orderByDesc(User::getCreatedAt);
        IPage<User> page = new Page<>(pageBo.getPageNum(), pageBo.getPageSize());
        return super.page(page, wrapper);
    }

    @Override
    public void updateStatus(Long userId, Integer status) {
        User user = super.getByBId(userId);
        user.setEnabled(status);
        super.updateByBId(user);
    }

    @Override
    public void updatePassword(Long userId, String password) {
        User user = super.getByBId(userId);
        String salt = user.getSalt();
        user.setPassword(DigestUtils.md5Hex(user.getPassword()+salt));
        super.updateByBId(user);
    }

    @Override
    public void updateUser(User user, UserRole userRole, AdminUserInfo adminUserInfo) {
        super.updateByBId(user);
        userRoleService.updateByBId(userRole);
        adminUserInfoService.updateByBId(adminUserInfo);
    }

    @Override
    public void saveUser(User user, UserRole userRole, AdminUserInfo adminUserInfo) {
        user.setEnabled(0);
        String salt = UUIDUtils.getUUID();
        user.setPassword(DigestUtils.md5Hex(user.getPassword()+salt));

        super.save(user);

        userRole.setUserId(user.getUserId());
        userRoleService.save(userRole);

        adminUserInfo.setUserId(user.getUserId());
        adminUserInfoService.save(adminUserInfo);

    }


}

