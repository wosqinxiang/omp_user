package com.ahdms.user.center.service.impl;

import com.ahdms.framework.cache.client.RedisOpsClient;
import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.DigestUtils;
import com.ahdms.framework.core.commom.util.OmpContextUtils;
import com.ahdms.framework.core.commom.util.StringUtils;
import com.ahdms.framework.core.web.response.ResultAssert;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.bo.*;
import com.ahdms.user.center.bean.entity.*;
import com.ahdms.user.center.bean.vo.CustomerInfoPageReqVo;
import com.ahdms.user.center.code.ApiCode;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.dao.ICustomerInfoDao;
import com.ahdms.user.center.service.*;
import com.ahdms.user.center.utils.MD5Utils;
import com.ahdms.user.center.utils.RamdonUtils;
import com.ahdms.user.center.utils.UUIDUtils;
import com.ahdms.user.center.utils.ValidateUtils;
import com.ahdms.user.client.vo.CustomerBasicInfoRspVo;
import com.ahdms.user.client.vo.UserLoginRspVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
    public CustomerBasicInfoRspVo getCustomerBasicInfo(Long userId) {
        User user = userService.getByBId(userId);
        CustomerInfo customerInfo = super.getOne(new QueryWrapper<CustomerInfo>().lambda().eq(CustomerInfo::getUserId, userId));
        CustomerBasicInfoRspVo customerBasicInfoRspVo = BeanUtils.copy(user,CustomerBasicInfoRspVo.class);
        BeanUtils.copy(customerInfo,customerBasicInfoRspVo);
        return customerBasicInfoRspVo;
    }

    @Override
    public String sendSmsCode(String mobile) {
        //校验手机号
        ResultAssert.throwOnFalse(ValidateUtils.validateMobile(mobile),ApiCode.USER_MOBILE_FORMAT_ERROR);

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
    public void resetPassword(String newPwd) {
        Long userId = OmpContextUtils.getUserId();
        User user = userService.getByBId(userId);
        String salt = user.getSalt();
        user.setPassword(MD5Utils.md5WithSalt(newPwd,salt));

        userService.updateByBId(user);
    }

    @Override
    public void backPassword(String mobile, String smsCode) {
        User user = userService.getByBId(OmpContextUtils.getUserId());

        ResultAssert.throwOnFalse(mobile.equals(user.getMobile()), ApiCode.USER_MOBILE_MATCH);

        checkSmsCode(mobile,smsCode);
    }

    @Override
    public UserLoginRspVo register(String mobile, String smsCode) {
        User _user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getMobile,mobile));
        ResultAssert.throwOnFalse(_user == null, ApiCode.USER_MOBILE_REPEAT);

        String _smsCode = redisOpsClient.getStr(SMSCODE.getKey(mobile));
        ResultAssert.throwOnFalse(smsCode.equals(_smsCode), ApiCode.USER_SMSCODE_ERROR);
        User user = new User();
        user.setMobile(mobile);
        user.setEnabled(BasicConstant.STATUS_ON);
        userService.save(user);
        Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleName,BasicConstant.ROLE_CUSTOMER));
        UserRole userRole = new UserRole();
        userRole.setRoleId(Integer.parseInt(role.getId()+""));
        userRole.setRoleName(BasicConstant.ROLE_CUSTOMER);
        userRole.setUserId(user.getUserId());
        userRoleService.save(userRole);

        UserLoginRspVo result = new UserLoginRspVo();
        result.setRole(BasicConstant.ROLE_CUSTOMER);
        result.setUserId(user.getUserId());
        return result;

    }

    @Override
    public void updateMobile(String mobile, String smsCode) {
        User _user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getMobile,mobile));
        ResultAssert.throwOnFalse(_user == null, ApiCode.USER_EMAIL_REPEAT);

        String _smsCode = redisOpsClient.getStr(SMSCODE.getKey(mobile));
        ResultAssert.throwOnFalse(smsCode.equals(_smsCode), ApiCode.USER_SMSCODE_ERROR);

        Long userId = OmpContextUtils.getUserId();
        User user = userService.getByBId(userId);
        user.setMobile(mobile);
        userService.updateByBId(user);

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
    public void checkUsername(String username) {
        //校验用户名格式
        ResultAssert.throwOnFalse(ValidateUtils.validateUsername(username),ApiCode.USER_NAME_FORMAT_ERROR);
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername,username));
        ResultAssert.throwOnFalse(user == null, ApiCode.USER_NAME_REPEAT);
    }

    @Override
    public void updatePassword(String newPwd, String oldPwd) {
        //判断原密码是否正确
        Long userId = OmpContextUtils.getUserId();
        User user = userService.getByBId(userId);

        String salt = user.getSalt();
        String pwd = MD5Utils.md5WithSalt(oldPwd,salt);
        ResultAssert.throwOnFalse(pwd.equals(user.getPassword()), ApiCode.USER_PWD_ERROR);

        user.setPassword(MD5Utils.md5WithSalt(newPwd,salt));

        userService.updateByBId(user);

    }

    @Override
    public void setBasicInfo(CustomerInfoReqBo customerInfoReqBo) {
        checkUsername(customerInfoReqBo.getUsername());
        checkEmail(customerInfoReqBo.getEmail());

        Long userId = OmpContextUtils.getUserId();

        User user = BeanUtils.copy(customerInfoReqBo, User.class);
        user.setUserId(userId);
        String salt = UUIDUtils.getUUID();
        user.setSalt(salt);
        user.setPassword(MD5Utils.md5WithSalt(user.getPassword(),salt));
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setUserId(user.getUserId());
        customerInfo.setAuditStatus(BasicConstant.CUSTOM_AUDIT_STATUS_NO); //  审核状态
        customerInfo.setAuthStatus(BasicConstant.CUSTOM_AUTH_STATUS_NO);  // 企业认证状态

        userService.updateByBId(user);

        super.save(customerInfo);
    }

    @Override
    public IPage<CustomerInfoPageRspBo> pageCustomer(CustomerInfoPageReqVo customerInfoPageReqVo) {

//        LambdaQueryWrapper wrapper = Wrappers.<User>lambdaQuery()
//                // 用户名不为空时根据用户名右模糊查询
//                // 慎用全模糊，索引字段全模糊会导致索引失效
//                .like(StringUtils.isNotBlank(customerInfoPageReqVo.getUsername()), User::getUsername, customerInfoPageReqVo.getUsername())
//                .like(StringUtils.isNotBlank(customerInfoPageReqVo.getMobile()), User::getMobile, customerInfoPageReqVo.getMobile())
//                .like(StringUtils.isNotBlank(customerInfoPageReqVo.getCompanyName()), User::getMobile, customerInfoPageReqVo.getCompanyName())
//                // 根据创建时间倒序
//                .orderByDesc(User::getCreatedAt);

        // 设置分页参数
        IPage<CustomerInfoPageReqVo> page = new Page<>(customerInfoPageReqVo.getPageNum(), customerInfoPageReqVo.getPageSize());

        IPage<CustomerInfoPageRspBo> page1 = customerInfoDao.pageCustomer(page,customerInfoPageReqVo);

//        IPage<User> p = userService.page(page,wrapper);
//        List<CustomerInfoPageRspBo> list = BeanUtils.copy(p.getRecords(),CustomerInfoPageRspBo.class);
//        System.out.println(">>>>>>>"+list);
        return page1;
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

