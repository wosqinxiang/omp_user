package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.entity.UserRole;
import com.ahdms.user.center.bo.UserPageBo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <B>说明：服务</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:35
 */
public interface IUserService extends BaseService<User> {

    /**
     * 分页查询
     *
     * @param pageBo
     * @return
     */
    IPage<User> findPage(UserPageBo pageBo);

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 状态：0启用、1禁用
     */
    void updateStatus(Long userId, Integer status);

    /**
     * 修改用户密码
     *
     * @param userId 用户ID
     * @param password 新密码
     */
    void updatePassword(Long userId, String password);

    /**
     * 修改
     *
     * @param user
     */
    void updateUser(User user, UserRole userRole, AdminUserInfo adminUserInfo);

    /**
     * 新增
     *
     * @param user
     */

    void saveUser(User user, UserRole userRole, AdminUserInfo adminUserInfo);



}
