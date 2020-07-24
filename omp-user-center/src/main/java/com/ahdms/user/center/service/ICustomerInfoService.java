package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.bo.*;
import com.ahdms.user.center.bean.entity.CustomerInfo;
import com.ahdms.user.center.bean.vo.MobileSmsCodeVo;
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


    void setBasicInfo(CustomerInfoBasicBo customerInfoReqBo);

    UserLoginRspVo register(String mobile, String smsCode);

    String sendSmsCode(String mobile);

    IPage<CustomerInfoPageBo> pageCustomer(CustomerPageBo customerPageBo);

    void updateMobile(String mobile, String smsCode);

    void updatePassword(String newPwd, String oldPwd);

    UserLoginRspVo checkEmail(String email);

    void resetPassword(MobileSmsCodeVo mobileVo);

    String checkSmsCode(String mobile, String smsCode);

    void checkUsername(String username);

    CustomerDetailBo getCustomerBasicInfo(Long userId);

    MobileSmsCodeBo backPassword(MobileSmsCodeBo mobileSmsCodeBo);

    CustomerInfo selectByUserId(Long userId);

    void checkMobile(String mobile);
}
