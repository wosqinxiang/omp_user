package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.bo.*;
import com.ahdms.user.center.bean.entity.CustomerInfo;
import com.ahdms.user.center.bean.vo.CustomerInfoPageReqVo;
import com.ahdms.user.client.vo.CustomerBasicInfoRspVo;
import com.ahdms.user.client.vo.UserLoginRspVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <B>说明：服务</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:35
 */
public interface ICustomerInfoService extends BaseService<CustomerInfo> {


    void setBasicInfo(CustomerInfoReqBo customerInfoReqBo);

    UserLoginRspVo register(String mobile, String smsCode);

    String sendSmsCode(String mobile);

    IPage<CustomerInfoPageRspBo> pageCustomer(CustomerInfoPageReqVo customerInfoPageReqVo);

    void updateMobile(String mobile, String smsCode);

    void updatePassword(String newPwd, String oldPwd);

    UserLoginRspVo checkEmail(String email);

    void resetPassword(String newPwd);

    String checkSmsCode(String mobile, String smsCode);

    void checkUsername(String username);

    CustomerBasicInfoRspVo getCustomerBasicInfo(Long userId);

    void backPassword(String mobile, String smsCode);
}
