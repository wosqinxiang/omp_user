package com.ahdms.user.center.feign;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.ObjectUtils;
import com.ahdms.framework.core.web.response.ResultAssert;
import com.ahdms.user.center.bean.entity.*;
import com.ahdms.user.center.code.ApiCode;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.dao.ICompanyInfoDao;
import com.ahdms.user.center.dao.IRoleDao;
import com.ahdms.user.center.dao.IUserRoleDao;
import com.ahdms.user.center.service.IAdminUserInfoService;
import com.ahdms.user.center.service.IUserRoleService;
import com.ahdms.user.center.service.IUserService;
import com.ahdms.user.client.AdminClientService;
import com.ahdms.user.client.vo.AdminUserRspVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qinxiang
 * @date 2020/7/19 20:14
 */
@Validated
@RestController
@RequestMapping("/rmi/user/admin")
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

    @Autowired
    private ICompanyInfoDao companyInfoDao;

    @Autowired
    private IRoleDao roleDao;

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
        List<Long> userIds = userRoleDao.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleName, BasicConstant.ROLE_ZT_CZY))
                .stream().map(UserRole::getUserId).collect(Collectors.toList());

        List<User> users = userService.list(new LambdaQueryWrapper<User>()
                .eq(User::getEnabled, BasicConstant.STATUS_ON)
                .in(User::getUserId,userIds));
        return BeanUtils.copy(users, AdminUserRspVo.class);
    }

    @Override
    @GetMapping("info")
    public AdminUserRspVo info(@RequestParam Long userId) {
        AdminUserRspVo rsp = new AdminUserRspVo();
        User user = userService.getByBId(userId);
        BeanUtils.copy(user,rsp);

        UserRole userRole = userRoleDao.selectOne(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,userId));
        rsp.setRoleName(userRole.getRoleName());
        Role role = roleDao.selectById(userRole.getRoleId());
        rsp.setRoleDesc(role.getRoleDesc());
        AdminUserInfo adminUserInfo = adminUserInfoService.getOne(new LambdaQueryWrapper<AdminUserInfo>().eq(AdminUserInfo::getUserId, userId));
        CompanyInfo companyInfo = companyInfoDao.selectByBId(adminUserInfo.getCompanyId());
        if(null != companyInfo){
            rsp.setCompanyName(companyInfo.getCompanyName());
            rsp.setCompanyCode(companyInfo.getCompanyCode());
            rsp.setType(companyInfo.getType());
        }
        return rsp;
    }

    @Override
    @GetMapping("companyAdminInfo")
    @ApiOperation(value = "根据服务商/供应商编码获取服务商管理员/供应商售前客服员信息", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "companyCode", value = "商家编码", required = true, dataType = "String")
    public List<AdminUserRspVo> companyAdminInfo(@Validated @NotNull @RequestParam("companyCode") String companyCode){
        //
        List<AdminUserRspVo> rsp = new ArrayList<>();
        CompanyInfo companyInfo = companyInfoDao.selectOne(new LambdaQueryWrapper<CompanyInfo>().eq(CompanyInfo::getCompanyCode, companyCode));
        ResultAssert.throwOnFalse(companyInfo != null, ApiCode.USER_COM_CODE_ERROR);
        if(BasicConstant.COMPANY_TYPE_FWS.equals(companyInfo.getType())){
            //服务商，获取服务商管理员信息
//            User user = userService.getOne(new LambdaQueryWrapper<User>().)
            String insql = "select user_id from user_role where role_name = '"+BasicConstant.ROLE_FWS_ADMIN+"'";
            AdminUserInfo adminUserInfo = adminUserInfoService.getOne(new LambdaQueryWrapper<AdminUserInfo>()
                    .eq(AdminUserInfo::getCompanyId,companyInfo.getCompanyId())
                    .inSql(AdminUserInfo::getUserId,insql));
            User user = userService.getByBId(adminUserInfo.getUserId());
            rsp.add(BeanUtils.copy(user,AdminUserRspVo.class));
        }else if(BasicConstant.COMPANY_TYPE_GYX.equals(companyInfo.getType())){
            //供应商，获取供应商售前客服员信息
            String insql = "select user_id from user_role where role_name = '"+BasicConstant.ROLE_GYS_SQKFY+"'";
            List<AdminUserInfo> adminUserInfos = adminUserInfoService.list(new LambdaQueryWrapper<AdminUserInfo>()
                    .eq(AdminUserInfo::getCompanyId,companyInfo.getCompanyId())
                    .inSql(AdminUserInfo::getUserId,insql));
            for(AdminUserInfo admin:adminUserInfos){
                User user = userService.getByBId(admin.getUserId());
                rsp.add(BeanUtils.copy(user,AdminUserRspVo.class));
            }
        }
        return rsp;
    }
}
