package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.framework.core.commom.util.ObjectUtils;
import com.ahdms.framework.core.commom.util.OmpContextUtils;
import com.ahdms.framework.core.commom.util.StringUtils;
import com.ahdms.framework.core.web.response.ResultAssert;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.bo.AuditInfoReqBo;
import com.ahdms.user.center.bean.bo.ComBusiRecordRspBo;
import com.ahdms.user.center.bean.bo.CompanyBusinessReqBo;
import com.ahdms.user.center.bean.entity.AuditInfo;
import com.ahdms.user.center.bean.entity.CustomerInfo;
import com.ahdms.user.center.bean.entity.User;
import com.ahdms.user.center.bean.vo.CompanyBusinessInfoRspVo;
import com.ahdms.user.center.bean.vo.CustomerComBusiRspVo;
import com.ahdms.user.center.bo.CompanyBusinessInfoPageBo;
import com.ahdms.user.center.code.ApiCode;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.dao.ICompanyBusinessInfoDao;
import com.ahdms.user.center.message.MQProducerSend;
import com.ahdms.user.center.service.IAuditInfoService;
import com.ahdms.user.center.service.ICompanyBusinessInfoService;
import com.ahdms.user.center.service.ICustomerInfoService;
import com.ahdms.user.center.service.IUserService;
import com.ahdms.user.center.utils.RamdonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.ahdms.user.center.bean.entity.CompanyBusinessInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
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
@Transactional
public class CompanyBusinessInfoServiceImpl extends BaseServiceImpl<ICompanyBusinessInfoDao, CompanyBusinessInfo> implements ICompanyBusinessInfoService {
    @Autowired
    private ICompanyBusinessInfoDao companyBusinessInfoDao;

    @Autowired
    private MQProducerSend mqProducerSend;

    @Autowired
    private ICustomerInfoService customerInfoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAuditInfoService auditInfoService;

    @Override
    public CompanyBusinessInfoRspVo getCompanyBusinessInfoRspVo(String customerId) {
        //获取登录客户信息
        CustomerInfo customerInfo = getCustomerInfo(customerId);
        User user = userService.getByBId(customerInfo.getUserId());
        CompanyBusinessInfoRspVo vo = BeanUtils.copy(user,CompanyBusinessInfoRspVo.class);
        //根据认证状态 返回 企业商务信息表数据
        Integer authStatus = customerInfo.getAuthStatus();

        //返回最近的一条企业商务信息记录
        List<CompanyBusinessInfo> list = super.list(new QueryWrapper<CompanyBusinessInfo>().lambda()
                .eq(CompanyBusinessInfo::getCustomerId,customerInfo.getCustomerId())
                .orderByDesc(CompanyBusinessInfo::getCreatedAt));
        CompanyBusinessInfo cbi = (list == null || list.size() ==0 ) ? new CompanyBusinessInfo() : list.get(0);
        AuditInfo auditInfo = auditInfoService.getByBId(cbi.getAuditId());

        BeanUtils.copy(cbi,vo);
        if(auditInfo != null)
            BeanUtils.copy(auditInfo,vo);
        vo.setAuthStatus(authStatus);
        return vo;

    }

    @Override
    public void authCompanyBusiness(CompanyBusinessReqBo companyBusinessReqBo) {
        //判断客户是否已经企业认证
        CustomerInfo customerInfo = getCustomerInfo(null);
        ResultAssert.throwOnFalse(BasicConstant.CUSTOM_AUTH_STATUS_NO == customerInfo.getAuthStatus(), ApiCode.USER_CMB_ISAUTH);

        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(companyBusinessReqBo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(customerInfo.getCustomerId());
        companyBusinessInfo.setStatus(BasicConstant.COM_BUS_STATUS_AUTHING); //修改企业商务信息表 状态 为  认证中
        companyBusinessInfo.setCreatedAt(new Date());

        customerInfo.setCompanyName(companyBusinessReqBo.getCompanyName());
        customerInfo.setAuthStatus(BasicConstant.CUSTOM_AUTH_STATUS_AUTHING); //修改客户企业认证状态为   认证中
        customerInfo.setAuditStatus(BasicConstant.CUSTOM_AUDIT_STATUS_WAIT); // 修改客户企业审核状态为   待审核
        customerInfoService.updateByBId(customerInfo);

        super.save(companyBusinessInfo);

//        saveCompanyBussiness(companyBusinessReqBo,BasicConstant.COM_BUS_STATUS_AUTHING);
    }

    @Override
    public void updateCompanyBusiness(CompanyBusinessReqBo companyBusinessReqBo) {
        //判断客户是否已经企业认证
        CustomerInfo customerInfo = getCustomerInfo(null);
        ResultAssert.throwOnFalse(BasicConstant.CUSTOM_AUTH_STATUS_OK == customerInfo.getAuthStatus(), ApiCode.USER_CMB_NOAUTH);

        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(companyBusinessReqBo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(customerInfo.getCustomerId());
        companyBusinessInfo.setStatus(BasicConstant.COM_BUS_STATUS_CHANGE); //修改企业商务信息表状态 为  变更中
        companyBusinessInfo.setCreatedAt(new Date());

        customerInfo.setCompanyName(companyBusinessReqBo.getCompanyName());
        customerInfo.setAuthStatus(BasicConstant.CUSTOM_AUTH_STATUS_AUTHING); //修改客户企业认证状态为   变更中
        customerInfo.setAuditStatus(BasicConstant.CUSTOM_AUDIT_STATUS_WAIT); // 修改客户企业审核状态为   待审核
        customerInfoService.updateByBId(customerInfo);

        super.save(companyBusinessInfo);

//        saveCompanyBussiness(companyBusinessReqBo,BasicConstant.COM_BUS_STATUS_CHANGE);
    }

    @Override
    public List<ComBusiRecordRspBo> getCompanyBussRecords(String customerId) {
        CustomerInfo customerInfo = getCustomerInfo(customerId);

        //根据客户ID查询所有的 企业商务信息
        List<CompanyBusinessInfo> list = super.list(new QueryWrapper<CompanyBusinessInfo>().lambda().eq(CompanyBusinessInfo::getCustomerId,customerInfo.getCustomerId()).orderByDesc(CompanyBusinessInfo::getCreatedAt));

        List<ComBusiRecordRspBo> records = new ArrayList<>();
        for (CompanyBusinessInfo cbInfo:list) {
            ComBusiRecordRspBo bo = new ComBusiRecordRspBo();

            Long auditId = cbInfo.getAuditId();
            AuditInfo auditInfo = auditInfoService.getByBId(auditId);

            if(null != auditId){
                bo.setAuditResult(0 == auditInfo.getAuditResult() ? "通过" : "退回");
                bo.setAudit_at(auditInfo.getCreatedAt());
            }
            bo.setComBusinessId(cbInfo.getComBusinessId());
            bo.setCreated_at(cbInfo.getCreatedAt());
            bo.setInfoDesc(cbInfo.getInfoDesc());

            records.add(bo);
        }

        return records;
    }

    @Override
    public CompanyBusinessInfoRspVo getCompanyBussRecord(String comBusId) {
        CompanyBusinessInfo companyBusinessInfo = super.getByBId(comBusId);

        return BeanUtils.copy(companyBusinessInfo,CompanyBusinessInfoRspVo.class);
    }


    @Override
    public CustomerComBusiRspVo getCompanyBussInfo(String customerId) {
        CustomerInfo customerInfo = getCustomerInfo(customerId);

        User user = userService.getByBId(customerInfo.getUserId());

        CompanyBusinessInfo companyBusinessInfo = super.getOne(new QueryWrapper<CompanyBusinessInfo>().lambda()
                .eq(CompanyBusinessInfo::getCustomerId,customerInfo.getCustomerId())
                .eq(CompanyBusinessInfo::getStatus,0));

        CustomerComBusiRspVo customerComBusiRspVo = BeanUtils.copy(customerInfo,CustomerComBusiRspVo.class);
        BeanUtils.copy(user,customerComBusiRspVo);
        BeanUtils.copy(companyBusinessInfo,customerComBusiRspVo);

        AuditInfo auditInfo = auditInfoService.getByBId(companyBusinessInfo.getAuditId());
        if(auditInfo != null){
            BeanUtils.copy(auditInfo,customerComBusiRspVo);
        }
        return customerComBusiRspVo;
    }

    @Override
    @Transactional
    public void auditCompanyBusiness(AuditInfoReqBo auditInfoReqBo) {
        Integer auditResult = auditInfoReqBo.getAuditResult();
        Integer auditType = auditInfoReqBo.getAuditType(); //审核类型
        String infoDesc = BasicConstant.AUDIT_TYPE_AUTH == auditType ? "企业认证" : "企业商务信息变更";

        //添加一条审核记录
        AuditInfo auditInfo = BeanUtils.copy(auditInfoReqBo,AuditInfo.class);
        auditInfo.setAuditType(auditType); //设置认证类型
        auditInfoService.save(auditInfo);

        //根据审核结果  修改企业商务信息表，以及 客户信息表的认证状态
        String comBusId = auditInfoReqBo.getAuditInfo();
        CompanyBusinessInfo companyBusinessInfo = super.getByBId(comBusId);
        CustomerInfo customerInfo = customerInfoService.getByBId(companyBusinessInfo.getCustomerId());
        if(0 == auditResult){ //审核通过
            companyBusinessInfo.setInfoDesc(infoDesc);
            companyBusinessInfo.setAuditId(auditInfo.getAuditId());
            companyBusinessInfo.setStatus(BasicConstant.COM_BUS_STATUS_ON); //根据认证结果 修改企业商务信息状态

            //更新客户认证以及审核状态
            customerInfo.setAuditStatus(BasicConstant.CUSTOM_AUDIT_STATUS_OK); //已审核
            customerInfo.setAuthStatus(BasicConstant.CUSTOM_AUTH_STATUS_OK); //已认证
            //如果是企业认证,认证通过  产生 服务权限信息ID 与 服务权限信息KEY
//            if(BasicConstant.AUDIT_TYPE_AUTH == auditType){
                if(StringUtils.isAnyBlank(customerInfo.getSecretId(),customerInfo.getSecretKey())){
                    customerInfo.setSecretId(RamdonUtils.randomString(8));
                    customerInfo.setSecretKey(RamdonUtils.randomString(8));
                    //发送消息到 kafka, 推送 客户数据 到 对接中心
                    mqProducerSend.send();
                }
//            }
        }else{
            companyBusinessInfo.setInfoDesc(infoDesc);
            companyBusinessInfo.setAuditId(auditInfo.getAuditId());
            companyBusinessInfo.setStatus(BasicConstant.COM_BUS_STATUS_OFF); //根据认证结果 修改企业商务信息状态 已作废

            //更新客户认证以及审核状态
            customerInfo.setAuditStatus(BasicConstant.CUSTOM_AUDIT_STATUS_OK); //已审核
            customerInfo.setAuthStatus(BasicConstant.CUSTOM_AUTH_STATUS_NO); //未认证
        }
        super.updateByBId(companyBusinessInfo);
        customerInfoService.updateByBId(customerInfo);

    }

    private void saveCompanyBussiness(CompanyBusinessReqBo companyBusinessReqBo, int type){
        CustomerInfo customerInfo =getCustomerInfo(null);

        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(companyBusinessReqBo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(customerInfo.getCustomerId());
        companyBusinessInfo.setStatus(BasicConstant.COM_BUS_STATUS_AUTHING); //修改企业商务信息表 状态 为  认证中
        companyBusinessInfo.setCreatedAt(new Date());

        customerInfo.setCompanyName(companyBusinessReqBo.getCompanyName());
        customerInfo.setAuthStatus(BasicConstant.CUSTOM_AUTH_STATUS_AUTHING); //修改客户企业认证状态为   认证中
        customerInfo.setAuditStatus(BasicConstant.CUSTOM_AUDIT_STATUS_WAIT); // 修改客户企业审核状态为   待审核
        customerInfoService.updateByBId(customerInfo);

        super.save(companyBusinessInfo);
    }

    private CustomerInfo getCustomerInfo(String customerId){
        CustomerInfo customerInfo = null;
        if(StringUtils.isBlank(customerId)){
            Long userId = OmpContextUtils.getUserId();
            customerInfo = customerInfoService.getOne(new QueryWrapper<CustomerInfo>().lambda().eq(CustomerInfo::getUserId, userId));
        }else{
            customerInfo = customerInfoService.getByBId(customerId);
        }
        return customerInfo;
    }
}

