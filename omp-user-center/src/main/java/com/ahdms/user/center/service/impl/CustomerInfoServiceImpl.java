package com.ahdms.user.center.service.impl;

import com.ahdms.framework.cache.client.RedisOpsClient;
import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.ObjectUtils;
import com.ahdms.framework.core.commom.util.OmpContextUtils;
import com.ahdms.framework.core.commom.util.StringUtils;
import com.ahdms.framework.core.web.response.ResultAssert;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.bo.*;
import com.ahdms.user.center.bean.entity.*;
import com.ahdms.user.center.bean.vo.CustomerInfoPageReqVo;
import com.ahdms.user.center.bean.vo.MobileSmsCodeVo;
import com.ahdms.user.center.code.ApiCode;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.constant.CacheKey;
import com.ahdms.user.center.dao.ICustomerInfoDao;
import com.ahdms.user.center.service.*;
import com.ahdms.user.center.utils.MD5Utils;
import com.ahdms.user.center.utils.RamdonUtils;
import com.ahdms.user.center.utils.UUIDUtils;
import com.ahdms.user.center.utils.ValidateUtils;
import com.ahdms.user.client.vo.CustomerBasicInfoRspVo;
import com.ahdms.user.client.vo.UserLoginRspVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static com.ahdms.user.center.constant.CacheKey.PWDTOKEN;
import static com.ahdms.user.center.constant.CacheKey.SMSCODE;

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
@Transactional
public class CustomerInfoServiceImpl extends BaseServiceImpl<ICustomerInfoDao, CustomerInfo> implements ICustomerInfoService {
    @Autowired
    private ICustomerInfoDao customerInfoDao;

//    @Autowired
//    private ICompanyBusinessInfoService companyBusinessInfoService;

    @Autowired
    private RedisOpsClient redisOpsClient;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IAuditInfoService auditInfoService;

    @Override
    public CustomerDetailBo getCustomerBasicInfo(Long userId) {
        User user = userService.getByBId(userId);

        CustomerInfo customerInfo = this.selectByUserId(userId);
        CustomerDetailBo customerBasicInfoRspVo = BeanUtils.copy(user, CustomerDetailBo.class);
        if (StringUtils.isBlank(user.getPassword())) {
            customerBasicInfoRspVo.setPwdStatus("0");
        } else  {
            customerBasicInfoRspVo.setPwdStatus("1");
        }
        UserRole userRole = userRoleService.getOne(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, user.getUserId()));
        customerBasicInfoRspVo.setRoleName(userRole.getRoleName());
        BeanUtils.copy(customerInfo,customerBasicInfoRspVo);
        return customerBasicInfoRspVo;
    }

    @Override
    public String sendSmsCode(String mobile) {
        //校验手机号
        ResultAssert.throwOnFalse(ValidateUtils.validateMobile(mobile), ApiCode.USER_MOBILE_FORMAT_ERROR);

        String smsCode = RamdonUtils.randomNumeric(6);
        //发送短信

        redisOpsClient.setex(SMSCODE.getKey(mobile),smsCode, SMSCODE.getExpire().getSeconds());
        return smsCode;
    }

    @Override
    public String checkSmsCode(String mobile, String smsCode) {
        String _smsCode = redisOpsClient.getStr(SMSCODE.getKey(mobile));
        ResultAssert.throwOnFalse(smsCode.equals(_smsCode), ApiCode.USER_SMSCODE_ERROR);
        return null;
    }

    @Override
    @Transactional
    public void resetPassword(MobileSmsCodeVo mobileVo) {
        //验证pwdToken，判断是否是手机验证码验证通过的手机号
        String _pwdToken = redisOpsClient.getStr(PWDTOKEN.getKey(mobileVo.getMobile()));
        ResultAssert.throwOnFalse(StringUtils.isNotBlank(_pwdToken) && _pwdToken.equals(mobileVo.getPwdToken()), ApiCode.USER_PWD_RESET_ERROR);
        //验证通过后，更新密码
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getMobile,mobileVo.getMobile()));
        String salt = user.getSalt();
        user.setPassword(MD5Utils.md5WithSalt(mobileVo.getPassword(),salt));
        userService.updateByBId(user);
        redisOpsClient.del(PWDTOKEN.getKey(mobileVo.getMobile()));
    }

    @Override
    public MobileSmsCodeBo backPassword(MobileSmsCodeBo mobileSmsCodeBo) {
        //判断手机号是否已注册
        checkMobile(mobileSmsCodeBo.getMobile());
        //校验验证码
        checkSmsCode(mobileSmsCodeBo.getMobile(),mobileSmsCodeBo.getSmsCode());
        //生成重置密码的token
        String pwdToken = RamdonUtils.randomString(8);
        //将token存入redis中
        redisOpsClient.setex(CacheKey.PWDTOKEN.getKey(mobileSmsCodeBo.getMobile()),pwdToken,CacheKey.PWDTOKEN.getExpire().getSeconds());
        //将token返回给前端
        mobileSmsCodeBo.setPwdToken(pwdToken);
        mobileSmsCodeBo.setSmsCode(null);
        return mobileSmsCodeBo;
    }

    @Override
    public UserLoginRspVo register(String mobile, String smsCode) {
        checkMobile(mobile);

        checkSmsCode(mobile,smsCode);

        User user = new User();
        user.setMobile(mobile);
        user.setEnabled(BasicConstant.STATUS_ON);
        userService.save(user);

        Role role = roleService.selectByRoleName(BasicConstant.ROLE_CUSTOMER);
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setRoleName(BasicConstant.ROLE_CUSTOMER);
        userRole.setUserId(user.getUserId());
        userRoleService.save(userRole);

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setUserId(user.getUserId());
        customerInfo.setAuditStatus(BasicConstant.CUSTOM_AUDIT_STATUS_NO); //  审核状态
        customerInfo.setAuthStatus(BasicConstant.CUSTOM_AUTH_STATUS_NO);  // 企业认证状态
        super.save(customerInfo);


        UserLoginRspVo result = new UserLoginRspVo();
        result.setRole(BasicConstant.ROLE_CUSTOMER);
        result.setUserId(user.getUserId());
        return result;

    }

    @Override
    public void updateMobile(String mobile, String smsCode) {
        ResultAssert.throwOnFalse(ObjectUtils.isNotEmpty(userService.selectByMobile(mobile)), ApiCode.USER_MOBILE_REPEAT);

        this.checkSmsCode(mobile,smsCode);

        User user = userService.getByBId(OmpContextUtils.getUserId());
        user.setMobile(mobile);
        userService.updateByBId(user);
//        userService.update(new LambdaQueryWrapper<User>().eq(User::getUserId,OmpContextUtils.getUserId())

    }

    @Override
    public UserLoginRspVo checkEmail(String email) {
        //校验邮箱格式
//        new EmailValidator().
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getEmail,email));
        ResultAssert.throwOnFalse(user == null, ApiCode.USER_EMAIL_REPEAT);
        return null;
    }

    @Override
    public void checkMobile(String mobile) {
        ResultAssert.throwOnFalse(ValidateUtils.validateMobile(mobile), ApiCode.USER_NAME_FORMAT_ERROR);
        ResultAssert.throwOnFalse(ObjectUtils.isNotEmpty(userService.selectByMobile(mobile)), ApiCode.USER_NAME_REPEAT);
    }

    @Override
    public void checkUsername(String username) {
        //校验用户名格式
        ResultAssert.throwOnFalse(ValidateUtils.validateUsername(username), ApiCode.USER_NAME_FORMAT_ERROR);
        ResultAssert.throwOnFalse(ObjectUtils.isNotEmpty(userService.selectByUsername(username)), ApiCode.USER_NAME_REPEAT);
    }

    @Override
    public void updatePassword(String newPwd, String oldPwd) {
        //判断原密码是否正确
        User user = userService.getByBId(OmpContextUtils.getUserId());

        String salt = user.getSalt();
        String pwd = MD5Utils.md5WithSalt(oldPwd,salt);
        ResultAssert.throwOnFalse(pwd.equals(user.getPassword()), ApiCode.USER_PWD_ERROR);

        user.setPassword(MD5Utils.md5WithSalt(newPwd,salt));
        userService.updateByBId(user);

    }

    @Override
    @Transactional
    public void setBasicInfo(CustomerInfoBasicBo customerInfoBo) {
        checkUsername(customerInfoBo.getUsername());
        checkEmail(customerInfoBo.getEmail());

        Long userId = OmpContextUtils.getUserId();
        User user = userService.getByBId(userId);
        user.setUsername(customerInfoBo.getUsername());
        user.setEmail(customerInfoBo.getEmail());
        String salt = UUIDUtils.getUUID();
        user.setSalt(salt);
        user.setPassword(MD5Utils.md5WithSalt(user.getPassword(),salt));

        CustomerInfo customerInfo = this.selectByUserId(userId);
        customerInfo.setAuditStatus(BasicConstant.CUSTOM_AUDIT_STATUS_NO); //  审核状态
        customerInfo.setAuthStatus(BasicConstant.CUSTOM_AUTH_STATUS_NO);  // 企业认证状态

        userService.updateByBId(user);

        super.updateByBId(customerInfo);
    }

    @Override
    public IPage<CustomerInfoPageBo> pageCustomer(CustomerPageBo customerPageBo) {

        // 设置分页参数
        IPage<CustomerInfoPageBo> page = new Page<>(customerPageBo.getPageNum(), customerPageBo.getPageSize());

        IPage<CustomerInfoPageBo> page1 = customerInfoDao.pageCustomer(page,customerPageBo);

        return page1;
    }

    @Override
    public CustomerInfo selectByUserId(Long userId) {
        if(userId == null){
            userId = OmpContextUtils.getUserId();
        }
        return customerInfoDao.selectOne(new LambdaQueryWrapper<CustomerInfo>().eq(CustomerInfo::getUserId,userId));
    }

    private CustomerInfo getCustomerInfo(String customerId){
        CustomerInfo customerInfo = null;
        if(StringUtils.isBlank(customerId)){
            Long userId = OmpContextUtils.getUserId();
            customerInfo = super.getOne(new QueryWrapper<CustomerInfo>().lambda().eq(CustomerInfo::getUserId, userId));
        }else{
            customerInfo = super.getByBId(customerId);
        }
        return customerInfo;
    }
}

