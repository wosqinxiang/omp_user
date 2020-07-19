package com.ahdms.user.center.service.impl;

import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.dao.IUserRoleDao;
import com.ahdms.user.center.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ahdms.user.center.bean.entity.UserRole;
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
public class UserRoleServiceImpl extends BaseServiceImpl<IUserRoleDao, UserRole> implements IUserRoleService {
    @Autowired
    private IUserRoleDao userRoleDao;

}

