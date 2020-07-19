package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.vo.AdminUserInfoPageReqVo;
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


    void addAdmin(AdminUserInfoReqVo adminUserInfoReqVo);

    IPage pageAdminInfos(AdminUserInfoPageReqVo pageQueryVo);

    void resetAdminPWD(Long adminId, String newPassword);

    void statusAdminInfo(Long adminId, Integer status);

    void updateAdmin(AdminUserInfoReqVo adminUserInfoReqVo);

    AdminUserInfoRspVo showAdminInfo(Long adminId);
}
