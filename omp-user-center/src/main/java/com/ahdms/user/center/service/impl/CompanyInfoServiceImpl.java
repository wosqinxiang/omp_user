package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.JsonUtils;
import com.ahdms.framework.core.commom.util.ObjectUtils;
import com.ahdms.framework.core.commom.util.OmpContextUtils;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.entity.*;
import com.ahdms.user.center.bean.vo.*;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.dao.ICompanyInfoDao;
import com.ahdms.user.center.service.*;
import com.ahdms.user.center.utils.IdGenerUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

/**
 * <B>说明：服务实现</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
@Slf4j
@Service
@Validated
public class CompanyInfoServiceImpl extends BaseServiceImpl<ICompanyInfoDao, CompanyInfo> implements ICompanyInfoService {
    @Autowired
    private ICompanyInfoDao companyInfoDao;

    @Autowired
    private IPayInfoService payInfoService;

    @Autowired
    private ICompanyBusinessInfoService companyBusinessInfoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAdminUserInfoService adminUserInfoService;

    @Autowired
    private IAuditInfoService auditInfoService;

    @Autowired
    private IdGenerUtils idGenerUtils;

    @Override
    public IPage pageFWS(FWSCompanyPageReqVo reqVo) {
        IPage<CompanyInfo> page = new Page<>(reqVo.getPageNum(), reqVo.getPageSize());
        IPage<FWSCompanyPageReqVo> records = companyInfoDao.fwsPage(page, reqVo, BasicConstant.COMPANY_TYPE_FWS);
        return records;
    }

    @Override
    public IPage pageDLS(DLSCompanyPageReqVo reqVo) {
        IPage<CompanyInfo> page = new Page<>(reqVo.getPageNum(), reqVo.getPageSize());
        IPage<DLSCompanyPageReqVo> records = companyInfoDao.dlsPage(page, reqVo, BasicConstant.COMPANY_TYPE_DLS);
        return records;
    }

    @Override
    @Transactional
    public void addFWSCompany(FWSCompanyReqVo fwsCompanyReqVo) {
        //添加信息至CompanyInfo表
        CompanyInfo companyInfo = BeanUtils.copy(fwsCompanyReqVo, CompanyInfo.class);
//        companyInfo.setCompanyBusinessId(companyBusinessInfo.getComBusinessId());
        companyInfo.setType(BasicConstant.COMPANY_TYPE_FWS); //类型为服务商
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT);//审核状态
        companyInfo.setStatus(BasicConstant.COMPANY_AUDIT_WAIT); //启用状态
        companyInfo.setCompanyCode(idGenerUtils.generateId(idGenerUtils.getFwsIdName()));//设置服务商编码
        super.save(companyInfo);

        //添加信息至 企业商务信息表
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(fwsCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
        companyBusinessInfoService.save(companyBusinessInfo);

        //添加信息至支付账号信息表
        BankInfo bankInfo = fwsCompanyReqVo.getBankInfo(); //银行卡信息
        PayInfo bankPayInfo = new PayInfo();
        bankPayInfo.setType(1);
        bankPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        bankPayInfo.setPayInfoDesc(JsonUtils.toJson(bankInfo));
        payInfoService.save(bankPayInfo);

    }

    @Override
    @Transactional
    public void addDLSCompany(DLSCompanyReqVo dlsCompanyReqVo) {

        //获取当前登录员 的信息
        AdminUserInfo loginAdmin = adminUserInfoService.getOne(new QueryWrapper<AdminUserInfo>().lambda().
                eq(AdminUserInfo::getUserId, OmpContextUtils.getUserId()));

        //添加信息至CompanyInfo表
        CompanyInfo companyInfo = BeanUtils.copy(dlsCompanyReqVo, CompanyInfo.class);
        companyInfo.setType(BasicConstant.COMPANY_TYPE_DLS); //类型为代理商
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT);//审核状态
        companyInfo.setStatus(BasicConstant.COMPANY_AUDIT_WAIT); //启用状态
        companyInfo.setCompanyCode(idGenerUtils.generateId(idGenerUtils.getDlsIdName()));//设置代理商编码
        companyInfo.setCompanyBusinessId(loginAdmin.getCompanyId()); //代理商所属 服务商ID
        super.save(companyInfo);

        //添加信息至 企业商务信息表
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(dlsCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
        companyBusinessInfo.setCreatedAt(new Date());

        companyBusinessInfoService.save(companyBusinessInfo);


        //添加信息至支付账号信息表
        BankInfo bankInfo = dlsCompanyReqVo.getBankInfo(); //银行卡信息
        PayInfo bankPayInfo = new PayInfo();
        bankPayInfo.setType(1);
        bankPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        bankPayInfo.setPayInfoDesc(JsonUtils.toJson(bankInfo));
        payInfoService.save(bankPayInfo);

    }

    @Override
    @Transactional
    public void updateFWSCompany(FWSCompanyReqVo reqVo) {

        //修改服务商 则修改状态为 停用以及待审核
        CompanyInfo companyInfo = super.getByBId(reqVo.getCompanyId());
        companyInfo.setStatus(BasicConstant.STATUS_OFF); //停用
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT); //待审核
        companyInfo.setCompanyName(reqVo.getCompanyName());
        super.updateByBId(companyInfo);

        //修改供应商商务信息
        //查找原企业商务信息
        CompanyBusinessInfo _comBus = companyBusinessInfoService.getOne(new LambdaQueryWrapper<CompanyBusinessInfo>()
                .eq(CompanyBusinessInfo::getCustomerId, companyInfo.getCompanyId()));
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(reqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
        companyBusinessInfo.setComBusinessId(_comBus.getComBusinessId());
        companyBusinessInfoService.updateByBId(companyBusinessInfo);

        //修改支付账号信息
        List<PayInfo> payInfos = payInfoService.list(new QueryWrapper<PayInfo>().lambda()
                .eq(PayInfo::getCompanyInfoId, companyInfo.getCompanyId()));
        if (ObjectUtils.isNotEmpty(payInfos)) {
            for (PayInfo pageInfo : payInfos) {
                if (BasicConstant.PAYINFO_TYPE_BANK == pageInfo.getType()) {
//                    gysCompanyRspVo.setBankInfo(JsonUtils.convertValue(pageInfo.getPayInfoDesc(),BankInfo.class));
                    pageInfo.setPayInfoDesc(JsonUtils.toJson(reqVo.getBankInfo()));
                }
            }
            payInfoService.updateBatchByBId(payInfos);
        }

        //停用供应商的所有管理人员账号
        // TODO 命名不规范
        oFFFWSAdminUser(companyInfo);

        //下线供应商的所有产品


    }

    private void oFFFWSAdminUser(CompanyInfo companyInfo) {
        List<AdminUserInfo> adminList = adminUserInfoService.list(new QueryWrapper<AdminUserInfo>().lambda()
                .eq(AdminUserInfo::getCompanyId, companyInfo.getCompanyId()));
        // TODO 先遍历出userIds, 再根据userIds批量更新，避免循环操作数据库
        for (AdminUserInfo adminUserInfo : adminList) {
            User user = userService.getByBId(adminUserInfo.getUserId());
            user.setEnabled(user.getEnabled() + 1);
            userService.updateByBId(user);
        }
    }


    @Override
    @Transactional
    public void updateDLSCompany(DLSCompanyReqVo reqVo) {

        CompanyInfo companyInfo = super.getByBId(reqVo.getCompanyId());
        companyInfo.setCompanyName(reqVo.getCompanyName());
        super.updateByBId(companyInfo);

        //修改供应商商务信息
        //查找原企业商务信息
        CompanyBusinessInfo _comBus = companyBusinessInfoService.getOne(new LambdaQueryWrapper<CompanyBusinessInfo>()
                .eq(CompanyBusinessInfo::getCustomerId, companyInfo.getCompanyId()));
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(reqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
        companyBusinessInfo.setComBusinessId(_comBus.getComBusinessId());
        companyBusinessInfoService.updateByBId(companyBusinessInfo);

        //修改支付账号信息
        List<PayInfo> payInfos = payInfoService.list(new QueryWrapper<PayInfo>().lambda()
                .eq(PayInfo::getCompanyInfoId, companyInfo.getCompanyId()));
        if (ObjectUtils.isNotEmpty(payInfos)) {
            for (PayInfo pageInfo : payInfos) {
                if (BasicConstant.PAYINFO_TYPE_BANK == pageInfo.getType()) {
//                    gysCompanyRspVo.setBankInfo(JsonUtils.convertValue(pageInfo.getPayInfoDesc(),BankInfo.class));
                    pageInfo.setPayInfoDesc(JsonUtils.toJson(reqVo.getBankInfo()));
                }
            }
            payInfoService.updateBatchByBId(payInfos);
        }
    }


    @Override
    public void audit(AuditInfoReqVo reqVo) {

        CompanyInfo companyInfo = super.getByBId(reqVo.getAuditInfo());

        AuditInfo auditInfo = BeanUtils.copy(reqVo, AuditInfo.class);
        auditInfoService.save(auditInfo);

        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_OK); //已审核
        companyInfo.setAuditId(auditInfo.getAuditId()); //审核表ID

        //根据审核结果，启用停用供应商
        Integer auditResult = auditInfo.getAuditResult();
        // TODO if简单化， 0 ！= auditResult直接return，减少if的嵌套，提高可读性
        if (0 == auditResult) { //审核成功
            //判断是启用服务商 或者是 停用服务商
            //根据审核类型 以及 服务商状态
            if (BasicConstant.STATUS_ON == companyInfo.getStatus()) { //原状态是启用，则审核内容是  停用服务商
                companyInfo.setStatus(BasicConstant.STATUS_OFF);

//                //停用服务商下的所有代理商
//                List<CompanyInfo> dlsList = super.list(new QueryWrapper<CompanyInfo>().lambda()
//                        .eq(CompanyInfo::getCompanyBusinessId,companyInfo.getCompanyId()));
//                for(CompanyInfo com:dlsList){
//                    com.setStatus(com.getStatus()+1);
//                }
//                super.updateBatchByBId(dlsList);
                //停用服务商的所有管理人员
                // TODO 避免循环调用数据库
                List<AdminUserInfo> adminList = adminUserInfoService.list(new QueryWrapper<AdminUserInfo>().lambda()
                        .eq(AdminUserInfo::getCompanyId, companyInfo.getCompanyId()));
                for (AdminUserInfo adminUserInfo : adminList) {
                    User user = userService.getByBId(adminUserInfo.getUserId());
                    // TODO 使用枚举或者常亮定义状态值，避免后续字段值扩展造成代码BUG
                    user.setEnabled(user.getEnabled() + 1);
                    userService.updateByBId(user);
                }
                //下线服务商的所有产品
            } else {//原状态是停用，则审核内容是  启用服务商
                //启用服务商下的所有代理商
//                List<CompanyInfo> dlsList = super.list(new QueryWrapper<CompanyInfo>().lambda()
//                        .eq(CompanyInfo::getCompanyBusinessId,companyInfo.getCompanyId()));
//                for(CompanyInfo com:dlsList){
//                    com.setStatus(com.getStatus()-1);
//                }
//                super.updateBatchByBId(dlsList);
                //启用服务商的所有管理人员
                List<AdminUserInfo> adminList = adminUserInfoService.list(new QueryWrapper<AdminUserInfo>().lambda()
                        .eq(AdminUserInfo::getCompanyId, companyInfo.getCompanyId()));
                // TODO 避免循环调用数据库
                for (AdminUserInfo adminUserInfo : adminList) {
                    User user = userService.getByBId(adminUserInfo.getUserId());
                    user.setEnabled(user.getEnabled() - 1);
                    userService.updateByBId(user);
                }

                companyInfo.setStatus(BasicConstant.STATUS_ON);
                //上线服务商的所有产品
            }
            super.updateByBId(companyInfo);
        } else { //审核失败，只需修改服务审核状态

        }

    }

    @Override
    public void fwsStatus(Long companyId, Integer status) {
        super.update(new LambdaUpdateWrapper<CompanyInfo>()
                .eq(CompanyInfo::getCompanyId, companyId)
                .set(CompanyInfo::getAuditStatus, BasicConstant.COMPANY_AUDIT_WAIT));
    }

    @Override
    public void dlsStatus(Long companyId, Integer status) {
        //修改代理商状态
        CompanyInfo companyInfo = super.getByBId(companyId);
        //查看代理商是否为待审核状态，如果是 则直接返回
//        companyInfo.getAuditStatus();
        //修改审核状态为 待审核
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT); //修改为待审核状态
        super.updateByBId(companyInfo);
    }

    @Override
    public FWSCompanyRspVo fwsInfo(Long companyId) {
        CompanyInfo companyInfo = super.getByBId(companyId);
        //获取企业商务信息
        CompanyBusinessInfo companyBusinessInfo = companyBusinessInfoService.getOne(new QueryWrapper<CompanyBusinessInfo>().lambda().
                eq(CompanyBusinessInfo::getCustomerId, companyId));
        FWSCompanyRspVo fwsCompanyRspVo = BeanUtils.copy(companyBusinessInfo, FWSCompanyRspVo.class);
        fwsCompanyRspVo.setTrade(companyInfo.getTrade());
        fwsCompanyRspVo.setArea(companyInfo.getArea());

        //获取支付账号信息
        List<PayInfo> payInfos = payInfoService.list(new QueryWrapper<PayInfo>().lambda()
                .eq(PayInfo::getCompanyInfoId, companyId));
        // TODO if简化，建议：
//        if (ObjectUtils.isEmpty(payInfos)) {
//            return fwsCompanyRspVo;
//        }
//        for (PayInfo pageInfo : payInfos) {
//            if (BasicConstant.PAYINFO_TYPE_BANK == pageInfo.getType()) {
//                fwsCompanyRspVo.setBankInfo(JsonUtils.convertValue(pageInfo.getPayInfoDesc(), BankInfo.class));
//            }
//        }
        if (ObjectUtils.isNotEmpty(payInfos)) {
            for (PayInfo pageInfo : payInfos) {
                if (BasicConstant.PAYINFO_TYPE_BANK == pageInfo.getType()) {
                    fwsCompanyRspVo.setBankInfo(JsonUtils.convertValue(pageInfo.getPayInfoDesc(), BankInfo.class));
                }
            }
        }
        //获取审核信息
        // TODO 同上一条
        Long auditId = companyInfo.getAuditId();
        if (null != auditId) {
            AuditInfo auditInfo = auditInfoService.getByBId(auditId);
            fwsCompanyRspVo.setAuditDesc(auditInfo.getAuditDesc());
        }

        return fwsCompanyRspVo;
    }

    @Override
    public DLSCompanyRspVo dlsInfo(Long companyId) {

        CompanyInfo companyInfo = super.getByBId(companyId);
        //获取企业商务信息
        CompanyBusinessInfo companyBusinessInfo = companyBusinessInfoService.getOne(new QueryWrapper<CompanyBusinessInfo>().lambda().
                eq(CompanyBusinessInfo::getCustomerId, companyId));
        DLSCompanyRspVo dlsCompanyRspVo = BeanUtils.copy(companyBusinessInfo, DLSCompanyRspVo.class);
        dlsCompanyRspVo.setTrade(companyInfo.getTrade());
        dlsCompanyRspVo.setCompanyCode(companyInfo.getCompanyCode());

        //获取支付账号信息
        List<PayInfo> payInfos = payInfoService.list(new QueryWrapper<PayInfo>().lambda()
                .eq(PayInfo::getCompanyInfoId, companyId));
        // TODO 同上一条
        if (ObjectUtils.isNotEmpty(payInfos)) {
            for (PayInfo pageInfo : payInfos) {
                if (BasicConstant.PAYINFO_TYPE_BANK == pageInfo.getType()) {
                    dlsCompanyRspVo.setBankInfo(JsonUtils.convertValue(pageInfo.getPayInfoDesc(), BankInfo.class));
                }
            }
        }

        return dlsCompanyRspVo;
    }

    @Override
    public List<CompanySimpleRspVo> listByBinding(Integer bindingStatus) {
        Wrapper queryWrapper = new LambdaQueryWrapper<CompanyInfo>()
                .eq(CompanyInfo::getStatus, BasicConstant.STATUS_ON)
                .eq(CompanyInfo::getType, BasicConstant.COMPANY_TYPE_FWS)
                .inSql(1 == bindingStatus, CompanyInfo::getCompanyId, "select company_id from admin_user_info where company_id is not null");
        List<CompanyInfo> records = companyInfoDao.selectList(queryWrapper);
        return BeanUtils.copy(records, CompanySimpleRspVo.class);
    }
}

