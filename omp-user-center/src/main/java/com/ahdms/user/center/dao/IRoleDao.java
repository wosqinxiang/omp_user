package com.ahdms.user.center.dao;

import org.apache.ibatis.annotations.Mapper;
import com.ahdms.framework.mybatis.mapper.IMapper;
import com.ahdms.user.center.bean.entity.Role;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@Mapper
public interface IRoleDao extends IMapper<Role> {
}
