package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.bo.AdminInfoBasicBo;
import com.ahdms.user.center.bean.bo.AdminInfoBo;
import com.ahdms.user.center.bean.bo.AdminUserPageQueryBo;
import com.ahdms.user.center.bean.bo.UserInfoBo;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.vo.AdminUserInfoReqVo;
import com.ahdms.user.center.bean.vo.AdminUserInfoRspVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <B>说明：服务</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
public interface IAdminUserInfoService extends BaseService<AdminUserInfo> {


    IPage pageAdminInfos(AdminUserPageQueryBo adminInfoBo);

    void resetAdminPWD(Long adminId, String newPassword);

    void statusAdminInfo(Long adminId, Integer status);

    void updateAdmin(AdminInfoBasicBo adminInfoBasicBo);

    AdminInfoBasicBo showAdminInfo(Long adminId);

    AdminInfoBasicBo showAdminInfo();

    void addAdmin(UserInfoBo userInfoBo, AdminInfoBo adminInfoBo);

    AdminUserInfo selectByUserId(Long userId);
}
