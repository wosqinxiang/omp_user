package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.entity.Role;
import com.ahdms.user.center.bean.vo.RoleRspVo;

import java.util.List;

/**
 * <B>说明：服务</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:35
 */
public interface IRoleService extends BaseService<Role> {


    List<RoleRspVo> roleList();

    List<RoleRspVo> allList();
}
