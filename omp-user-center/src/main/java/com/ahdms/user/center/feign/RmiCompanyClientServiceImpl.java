package com.ahdms.user.center.feign;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.user.center.bean.entity.AdminUserInfo;
import com.ahdms.user.center.bean.entity.CompanyBusinessInfo;
import com.ahdms.user.center.bean.entity.CompanyInfo;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.service.IAdminUserInfoService;
import com.ahdms.user.center.service.ICompanyInfoService;
import com.ahdms.user.client.CompanyClientService;
import com.ahdms.user.client.vo.CompanyInfoRmiRspVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qinxiang
 * @date 2020-07-16 20:03
 */
@Validated
@RestController
@RequestMapping("/rmi/user/company")
@Api("Rmi-User控制器")
public class RmiCompanyClientServiceImpl implements CompanyClientService {
    @Autowired
    private ICompanyInfoService companyInfoService;

    @Autowired
    private IAdminUserInfoService adminUserInfoService;

    @Override
    @GetMapping("info")
    public CompanyInfoRmiRspVo getCompanyInfo(Long userId) {
        //根据用户ID 得到管理员信息
        AdminUserInfo adminUserInfo = adminUserInfoService.getOne(new QueryWrapper<AdminUserInfo>().lambda().eq(AdminUserInfo::getUserId,userId));
        //根据 管理员ID 查询所有的服务商或供应商
        CompanyInfo companyInfo = companyInfoService.getByBId(adminUserInfo.getCompanyId());

        return BeanUtils.copy(companyInfo,CompanyInfoRmiRspVo.class);
    }

    @Override
    @GetMapping("infos")
    public List<CompanyInfoRmiRspVo> getCompanyInfos(Long userId) {
        //根据用户ID 得到管理员信息
        AdminUserInfo adminUserInfo = adminUserInfoService.getOne(new QueryWrapper<AdminUserInfo>().lambda().eq(AdminUserInfo::getUserId,userId));
        //根据 管理员ID 查询所有的代理商或依赖方
        List<CompanyInfo> records = companyInfoService.list(new QueryWrapper<CompanyInfo>().lambda().
                eq(CompanyInfo::getCompanyBusinessId,adminUserInfo.getCompanyId())
                .eq(CompanyInfo::getStatus, BasicConstant.COMPANY_AUDIT_OK));
        return BeanUtils.copy(records,CompanyInfoRmiRspVo.class);
    }
}
