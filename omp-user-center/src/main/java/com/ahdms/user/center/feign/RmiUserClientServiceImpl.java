package com.ahdms.user.center.feign;

import com.ahdms.framework.cache.client.RedisOpsClient;
import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.DigestUtils;
import com.ahdms.framework.core.commom.util.ObjectUtils;
import com.ahdms.framework.core.web.response.ResultAssert;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.entity.UserRole;
import com.ahdms.user.center.code.ApiCode;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.constant.CacheKey;
import com.ahdms.user.center.service.IUserRoleService;
import com.ahdms.user.center.service.IUserService;
import com.ahdms.user.client.UserClientService;
import com.ahdms.user.client.vo.UserLoginRspVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author qinxiang
 * @date 2020-07-13 14:21
 */
@Validated
@RestController
@RequestMapping("/rmi/security/user")
@Api("Rmi-User控制器")
public class RmiUserClientServiceImpl implements UserClientService {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisOpsClient redisOpsClient;

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    @PostMapping("/login/password")
    public UserLoginRspVo loginPassword(@NotNull String identity, @NotNull String password) {


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getMobile, identity).or(queryWrapper1 -> queryWrapper1.eq(User::getUsername, identity));
        User user = userService.getOne(queryWrapper);
        ResultAssert.throwOnFalse(ObjectUtils.isNotNull(user), ApiCode.USER_NOT_EXIST);
        String salt = user.getSalt();
        String pwd = DigestUtils.md5Hex(password+salt);
        ResultAssert.throwOnFalse(pwd.equals(user.getPassword()),ApiCode.USER_PWD_ERROR);

        //判断是否被禁用
        ResultAssert.throwOnFalse(BasicConstant.STATUS_ON.equals(user.getEnabled()),ApiCode.USER_LOGIN_DISABLED);

        QueryWrapper<UserRole> queryWrapperRole = new QueryWrapper<>();
        queryWrapperRole.lambda().eq(UserRole::getUserId, user.getUserId());
        UserRole userRole = userRoleService.getOne(queryWrapperRole);


        return new UserLoginRspVo(userRole.getUserId(),userRole.getRoleName());

    }

    @Override
    @PostMapping("/login/smsCode")
    public UserLoginRspVo loginSmsCode(@NotNull String mobile, @NotNull String smsCode) {
        String _smsCode = redisOpsClient.getStr(CacheKey.SMSCODE.name(),mobile);

        ResultAssert.throwOnFalse(smsCode.equals(_smsCode),ApiCode.USER_SMSCODE_ERROR);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getMobile, mobile);

        User user = userService.getOne(queryWrapper);

        ResultAssert.throwOnFalse(user != null,ApiCode.USER_MOBILE_NOT);
        //判断是否被禁用
        ResultAssert.throwOnFalse(BasicConstant.STATUS_ON.equals(user.getEnabled()),ApiCode.USER_LOGIN_DISABLED);

        QueryWrapper<UserRole> queryWrapperRole = new QueryWrapper<>();
        queryWrapperRole.lambda().eq(UserRole::getUserId, user.getUserId());
        UserRole userRole = userRoleService.getOne(queryWrapperRole);
        redisOpsClient.del(CacheKey.SMSCODE.name(),mobile);
        return new UserLoginRspVo(userRole.getUserId(),userRole.getRoleName());

    }
}
