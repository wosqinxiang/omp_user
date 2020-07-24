package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.DigestUtils;
import com.ahdms.framework.core.commom.util.StringUtils;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.entity.UserRole;
import com.ahdms.user.center.bean.vo.UserReqVo;
import com.ahdms.user.center.bo.UserPageBo;
import com.ahdms.user.center.dao.IUserDao;
import com.ahdms.user.center.service.IAdminUserInfoService;
import com.ahdms.user.center.service.IUserRoleService;
import com.ahdms.user.center.service.IUserService;
import com.ahdms.user.center.utils.MD5Utils;
import com.ahdms.user.center.utils.UUIDUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public User selectByUsername(String username) {

        return userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public User selectByMobile(String mobile) {
        return userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile));
    }

    @Override
    public User resetPassword(Long userId, String password) {
        User user = userDao.selectByBId(userId);
        String salt = UUIDUtils.getUUID();
        user.setSalt(salt);
        user.setPassword(MD5Utils.md5WithSalt(password, salt));
        userDao.updateByBId(user);
        return user;
    }
}

