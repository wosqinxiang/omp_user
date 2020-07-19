package com.ahdms.user.center.feign;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.entity.UserRole;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.dao.IUserRoleDao;
import com.ahdms.user.center.service.IAdminUserInfoService;
import com.ahdms.user.center.service.IUserRoleService;
import com.ahdms.user.center.service.IUserService;
import com.ahdms.user.client.AdminClientService;
import com.ahdms.user.client.vo.AdminUserRspVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qinxiang
 * @date 2020/7/19 20:14
 */
@Validated
@RestController
@RequestMapping("/rmi/user/company")
@Api("Rmi-User控制器")
public class RmiAdminClientServiceImpl implements AdminClientService {
    @Autowired
    private IAdminUserInfoService adminUserInfoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IUserRoleDao userRoleDao;

    @Override
    @GetMapping("centerOper")
    public List<AdminUserRspVo> allAdmin() {
        //1.通过拼接SQL查询指定角色的userId集合
//        String inSQL = new StringBuilder().append("SELECT user_id from user_role where role_name = '")
//                    .append(BasicConstant.ROLE_ZT_CZY).append("'").toString();
//        List<User> records = userService.list(new LambdaQueryWrapper<User>()
//                .eq(User::getEnabled, BasicConstant.STATUS_ON)
//                .inSql(User::getUserId,inSQL) //用户Id
//                );
        //2.先查询指定角色的userId集合
        List<Long> userIds = userRoleDao.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleName,BasicConstant.ROLE_ZT_CZY))
                .stream().map(UserRole::getUserId).collect(Collectors.toList());

        List<User> users = userService.list(new LambdaQueryWrapper<User>()
                .eq(User::getEnabled, BasicConstant.STATUS_ON)
                .in(User::getUserId,userIds));
        return BeanUtils.copy(users,AdminUserRspVo.class);
    }
}
