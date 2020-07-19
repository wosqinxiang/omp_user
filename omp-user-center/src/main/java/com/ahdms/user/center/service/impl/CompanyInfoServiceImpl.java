package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.*;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.entity.CompanyBusinessInfo;
import com.ahdms.user.center.bean.entity.PayInfo;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.vo.*;
import com.ahdms.user.center.bo.CompanyInfoPageBo;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.dao.ICompanyInfoDao;
import com.ahdms.user.center.service.ICompanyBusinessInfoService;
import com.ahdms.user.center.service.ICompanyInfoService;
import com.ahdms.user.center.service.ICustomerInfoService;
import com.ahdms.user.center.service.IPayInfoService;
import com.ahdms.user.center.utils.RamdonUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.ahdms.user.center.bean.entity.CompanyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
@Transactional
public class CompanyInfoServiceImpl extends BaseServiceImpl<ICompanyInfoDao, CompanyInfo> implements ICompanyInfoService {
    @Autowired
    private ICompanyInfoDao companyInfoDao;


    @Autowired
    private IPayInfoService payInfoService;

    @Autowired
    private ICustomerInfoService customerInfoService;

    @Autowired
    private ICompanyBusinessInfoService companyBusinessInfoService;


    @Override
    public IPage<CompanyInfo> findPage(CompanyInfoPageBo pageBo) {
        LambdaQueryWrapper wrapper = Wrappers.<User>lambdaQuery();
        IPage<CompanyInfo> page = new Page<>(pageBo.getPageNum(), pageBo.getPageSize());
        return super.page(page, wrapper);
    }

    @Override
    public void updateStatus(Long companyId, Integer status) {
        CompanyInfo companyInfo = super.getByBId(companyId);
        companyInfo.setStatus(status);
        super.updateByBId(companyInfo);
    }

    @Override
    public void update(CompanyInfo companyInfo) {

    }

    @Override
    public void saveCompanyInfo(CompanyBusinessInfo companyBusinessInfo, CompanyInfo companyInfo, PayInfo payInfo) {

    }

    @Override
    public void addrelyCompany(String companyName) {

    }

    @Override
    @Transactional
    public void addFWSCompany(FWSCompanyReqVo fwsCompanyReqVo) {
        //添加信息至CompanyInfo表
        CompanyInfo companyInfo = BeanUtils.copy(fwsCompanyReqVo,CompanyInfo.class);
//        companyInfo.setCompanyBusinessId(companyBusinessInfo.getComBusinessId());
        companyInfo.setType(BasicConstant.COMPANY_TYPE_FWS); //类型为服务商
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT);//审核状态
        companyInfo.setStatus(BasicConstant.COMPANY_AUDIT_WAIT); //启用状态
        companyInfo.setCompanyCode("FWS"+ RamdonUtils.randomNumeric(4));//设置服务商编码
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
    public void addGYSCompany(SupplierCompanyReqVo supplierCompanyReqVo) {
        //添加信息至CompanyInfo表
        CompanyInfo companyInfo = BeanUtils.copy(supplierCompanyReqVo,CompanyInfo.class);
//        companyInfo.setCompanyBusinessId(companyBusinessInfo.getComBusinessId());
        companyInfo.setType(BasicConstant.COMPANY_TYPE_GYX); //类型为供应商
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT);//审核状态
        companyInfo.setStatus(BasicConstant.COMPANY_AUDIT_WAIT); //启用状态
        companyInfo.setCompanyCode("GYS"+ RamdonUtils.randomNumeric(4));//设置供应商编码
        super.save(companyInfo);
        //添加信息至 企业商务信息表
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(supplierCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
        companyBusinessInfoService.save(companyBusinessInfo);


        //添加信息至支付账号信息表
        BankInfo bankInfo = supplierCompanyReqVo.getBankInfo(); //银行卡信息
        PayInfo bankPayInfo = new PayInfo();
        bankPayInfo.setType(1);
        bankPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        bankPayInfo.setPayInfoDesc(JsonUtils.toJson(bankInfo));
        payInfoService.save(bankPayInfo);

        AlipayInfo alipayInfo = supplierCompanyReqVo.getAlipayInfo(); //支付宝账号信息
        PayInfo aliPayInfo = new PayInfo();
        aliPayInfo.setType(2);
        aliPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        aliPayInfo.setPayInfoDesc(JsonUtils.toJson(alipayInfo));
        payInfoService.save(aliPayInfo);

        WechatInfo wechatInfo = supplierCompanyReqVo.getWechatInfo(); //微信账号信息
        PayInfo wxPayInfo = new PayInfo();
        wxPayInfo.setType(3);
        wxPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        wxPayInfo.setPayInfoDesc(JsonUtils.toJson(wechatInfo));
        payInfoService.save(wxPayInfo);

    }

    @Override
    @Transactional
    public void addDLSCompany(DLSCompanyReqVo dlsCompanyReqVo) {

        //添加信息至CompanyInfo表
        CompanyInfo companyInfo = BeanUtils.copy(dlsCompanyReqVo,CompanyInfo.class);
//        companyInfo.setCompanyBusinessId(companyBusinessInfo.getComBusinessId());
        companyInfo.setType(BasicConstant.COMPANY_TYPE_DLS); //类型为代理商
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT);//审核状态
        companyInfo.setStatus(BasicConstant.COMPANY_AUDIT_WAIT); //启用状态
        companyInfo.setCompanyCode("DLS"+ RamdonUtils.randomNumeric(4));//设置供应商编码
        super.save(companyInfo);

        //添加信息至 企业商务信息表
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(dlsCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
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
    public void addYLFCompany(YLFCompanyReqVo ylfCompanyReqVo) {

        //添加信息至CompanyInfo表
        CompanyInfo companyInfo = BeanUtils.copy(ylfCompanyReqVo,CompanyInfo.class);
//        companyInfo.setCompanyBusinessId(companyBusinessInfo.getComBusinessId());
        companyInfo.setType(BasicConstant.COMPANY_TYPE_YLF); //类型为依赖方
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT);//审核状态
        companyInfo.setStatus(BasicConstant.COMPANY_AUDIT_WAIT); //启用状态
        companyInfo.setCompanyCode("YLF"+ RamdonUtils.randomNumeric(4));//设置供应商编码
        super.save(companyInfo);

        //添加信息至 企业商务信息表
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(ylfCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
        companyBusinessInfoService.save(companyBusinessInfo);

    }

    @Override
    @Transactional
    public void updateFWSCompany(FWSCompanyReqVo fwsCompanyReqVo) {

        CompanyInfo companyInfo = BeanUtils.copy(fwsCompanyReqVo,CompanyInfo.class);
        super.save(companyInfo);

        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(fwsCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfoService.save(companyBusinessInfo);

        BankInfo bankInfo = fwsCompanyReqVo.getBankInfo(); //银行卡信息
        PayInfo bankPayInfo = new PayInfo();
        bankPayInfo.setType(1);
        bankPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        bankPayInfo.setPayInfoDesc(JsonUtils.toJson(bankInfo));
        payInfoService.save(bankPayInfo);

    }

    @Override
    @Transactional
    public void updateGYSCompany(SupplierCompanyReqVo supplierCompanyReqVo) {

        CompanyInfo companyInfo = BeanUtils.copy(supplierCompanyReqVo,CompanyInfo.class);
        super.save(companyInfo);

        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(supplierCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
        companyBusinessInfoService.save(companyBusinessInfo);


        //添加信息至支付账号信息表
        BankInfo bankInfo = supplierCompanyReqVo.getBankInfo(); //银行卡信息
        PayInfo bankPayInfo = new PayInfo();
        bankPayInfo.setType(1);
        bankPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        bankPayInfo.setPayInfoDesc(JsonUtils.toJson(bankInfo));
        payInfoService.save(bankPayInfo);

        AlipayInfo alipayInfo = supplierCompanyReqVo.getAlipayInfo(); //支付宝账号信息
        PayInfo aliPayInfo = new PayInfo();
        aliPayInfo.setType(2);
        aliPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        aliPayInfo.setPayInfoDesc(JsonUtils.toJson(alipayInfo));
        payInfoService.save(aliPayInfo);

        WechatInfo wechatInfo = supplierCompanyReqVo.getWechatInfo(); //微信账号信息
        PayInfo wxPayInfo = new PayInfo();
        wxPayInfo.setType(3);
        wxPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        wxPayInfo.setPayInfoDesc(JsonUtils.toJson(wechatInfo));
        payInfoService.save(wxPayInfo);
    }

    @Override
    @Transactional
    public void updateDLSCompany(DLSCompanyReqVo dlsCompanyReqVo) {

        CompanyInfo companyInfo = BeanUtils.copy(dlsCompanyReqVo,CompanyInfo.class);
        super.save(companyInfo);

        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(dlsCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfoService.save(companyBusinessInfo);


        BankInfo bankInfo = dlsCompanyReqVo.getBankInfo(); //银行卡信息
        PayInfo bankPayInfo = new PayInfo();
        bankPayInfo.setType(1);
        bankPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        bankPayInfo.setPayInfoDesc(JsonUtils.toJson(bankInfo));
        payInfoService.save(bankPayInfo);
    }

    @Override
    @Transactional
    public void updateYLFCompany(YLFCompanyReqVo ylfCompanyReqVo) {

        CompanyInfo companyInfo = BeanUtils.copy(ylfCompanyReqVo,CompanyInfo.class);
        super.save(companyInfo);

        //添加信息至 企业商务信息表
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(ylfCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfoService.save(companyBusinessInfo);
    }
}

