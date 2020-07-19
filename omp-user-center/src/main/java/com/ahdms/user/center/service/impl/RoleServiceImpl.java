package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.OmpContextUtils;
import com.ahdms.framework.core.context.OmpContext;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.vo.RoleRspVo;
import com.ahdms.user.center.dao.IRoleDao;
import com.ahdms.user.center.service.IRoleService;
import com.ahdms.user.center.service.IUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.ahdms.user.center.bean.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
public class RoleServiceImpl extends BaseServiceImpl<IRoleDao, Role> implements IRoleService {
    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IUserRoleService userRoleService;

    @Override public List<RoleRspVo> roleList() {
        String roleName = OmpContextUtils.getRole();
        Role role = super.getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleName, roleName));
        List<Role> records = super.list(new QueryWrapper<Role>().lambda().eq(Role::getParentId, role.getId()));
        return BeanUtils.copy(records, RoleRspVo.class);
    }

    @Override
    public List<RoleRspVo> allList() {
        List<Role> records = super.list();
        return BeanUtils.copy(records, RoleRspVo.class);
    }
}

