package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.entity.UserRole;
import com.ahdms.user.center.bean.vo.UserReqVo;
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

    User selectByUsername(String username);

    User selectByMobile(String mobile);

    User resetPassword(Long userId,String password);

//    User selectByEmail(String e)

}
