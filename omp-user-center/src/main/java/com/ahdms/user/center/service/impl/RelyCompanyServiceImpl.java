package com.ahdms.user.center.service.impl;

import com.ahdms.framework.core.commom.util.*;
import com.ahdms.framework.core.web.response.ResultAssert;
import com.ahdms.product.client.ProductInfoClientService;
import com.ahdms.user.center.bean.entity.*;
import com.ahdms.user.center.bean.vo.*;
import com.ahdms.user.center.code.ApiCode;
import com.ahdms.user.center.constant.BasicConstant;
import com.ahdms.user.center.dao.ICompanyInfoDao;
import com.ahdms.user.center.message.MQProducerSend;
import com.ahdms.user.center.service.*;
import com.ahdms.user.center.utils.IdGenerUtils;
import com.ahdms.user.center.utils.RamdonUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qinxiang
 * @date 2020-07-17 12:58
 */
@Service
@Transactional
public class RelyCompanyServiceImpl implements IRelyCompanyService {
    @Autowired
    private ICompanyInfoDao companyInfoDao;

    @Autowired
    private ICompanyInfoService companyInfoService;

    @Autowired
    private IAdminUserInfoService adminUserInfoService;

    @Autowired
    private ICompanyBusinessInfoService companyBusinessInfoService;

    @Autowired
    private IPayInfoService payInfoService;

    @Autowired
    private IAuditInfoService auditInfoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ProductInfoClientService productInfoClientService;

    @Autowired
    private MQProducerSend mqProducerSend;

    @Autowired
    private IdGenerUtils idGenerUtils;

    @Override
    public void addrelyCompany(String companyName) {
        //新增产品依赖方
        //获取当前登录员 的信息
        AdminUserInfo loginAdmin = adminUserInfoService.getOne(new QueryWrapper<AdminUserInfo>().lambda().
                eq(AdminUserInfo::getUserId, OmpContextUtils.getUserId()));

        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyCode(idGenerUtils.generateId(idGenerUtils.getYlfIdName()));//依赖方编码
        companyInfo.setCompanyName(companyName);
        companyInfo.setType(BasicConstant.COMPANY_TYPE_YLF);
        companyInfo.setCompanyBusinessId(loginAdmin.getCompanyId()); //依赖方所属 供应商ID
        companyInfo.setStatus(BasicConstant.COMPANY_AUDIT_OK);//添加后即 启用状态

        companyInfoService.save(companyInfo);
    }

    @Override
    public IPage<CompanyInfo> page(RelyCompanyPageVo company) {
        //根据登录人 查询 对应的依赖方
        AdminUserInfo adminUserInfo = adminUserInfoService.getOne(new LambdaQueryWrapper<AdminUserInfo>()
                .eq(AdminUserInfo::getUserId, OmpContextUtils.getUserId()));

        LambdaQueryWrapper wrapper = Wrappers.<CompanyInfo>lambdaQuery()
                .eq(CompanyInfo::getType, BasicConstant.COMPANY_TYPE_YLF)
                .eq(CompanyInfo::getCompanyBusinessId,adminUserInfo.getCompanyId())
                .likeRight(StringUtils.isNotBlank(company.getCompanyName()), CompanyInfo::getCompanyName, company.getCompanyName());
        // 设置分页参数
        IPage<CompanyInfo> page = new Page<>(company.getPageNum(), company.getPageSize());

        return companyInfoService.page(page,wrapper);
    }

    @Override
    public void updaterelyCompany(Long companyId, String companyName) {
        //判断名字是否已重复
        checkCompanyName(companyName);

        companyInfoService.update(new LambdaUpdateWrapper<CompanyInfo>()
                .eq(CompanyInfo::getCompanyId,companyId)
                .set(CompanyInfo::getCompanyName,companyName));

//        CompanyInfo companyInfo = companyInfoService.getByBId(companyId);
//        companyInfo.setCompanyName(companyName);
//        companyInfoService.updateByBId(companyInfo);
    }

    private void checkCompanyName(String companyName){
        CompanyInfo company = companyInfoService.getOne(new LambdaQueryWrapper<CompanyInfo>().eq(CompanyInfo::getCompanyName,companyName));
        ResultAssert.throwOnFalse(null == company, ApiCode.USER_COM_NAME_REPEAT);
    }

    @Override
    public void statusRelyCompany(Long companyId, Integer status) {
        companyInfoService.update(new LambdaUpdateWrapper<CompanyInfo>()
                .eq(CompanyInfo::getCompanyId,companyId)
                .set(CompanyInfo::getStatus,status));
    }

    @Override
    @Transactional
    public void addGYSCompany(SupplierCompanyReqVo supplierCompanyReqVo) {
        //添加信息至CompanyInfo表
        CompanyInfo companyInfo = BeanUtils.copy(supplierCompanyReqVo, CompanyInfo.class);
//        companyInfo.setCompanyBusinessId(companyBusinessInfo.getComBusinessId());
        companyInfo.setType(BasicConstant.COMPANY_TYPE_GYX); //类型为供应商
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT);//待审核状态
        companyInfo.setStatus(BasicConstant.STATUS_OFF); //禁用状态
        companyInfo.setCompanyCode(idGenerUtils.generateId(idGenerUtils.getGysIdName()));//设置供应商编码
        companyInfoService.save(companyInfo);

        //添加信息至 企业商务信息表
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(supplierCompanyReqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
        companyBusinessInfoService.save(companyBusinessInfo);


        //添加信息至支付账号信息表
        BankInfo bankInfo = supplierCompanyReqVo.getBankInfo(); //银行卡信息
        PayInfo bankPayInfo = new PayInfo();
        bankPayInfo.setType(BasicConstant.PAYINFO_TYPE_BANK);
        bankPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        bankPayInfo.setPayInfoDesc(JsonUtils.toJson(bankInfo));
        payInfoService.save(bankPayInfo);

        AlipayInfo alipayInfo = supplierCompanyReqVo.getAlipayInfo(); //支付宝账号信息
        PayInfo aliPayInfo = new PayInfo();
        aliPayInfo.setType(BasicConstant.PAYINFO_TYPE_ALIPAY);
        aliPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        aliPayInfo.setPayInfoDesc(JsonUtils.toJson(alipayInfo));
        payInfoService.save(aliPayInfo);

        WechatInfo wechatInfo = supplierCompanyReqVo.getWechatInfo(); //微信账号信息
        PayInfo wxPayInfo = new PayInfo();
        wxPayInfo.setType(BasicConstant.PAYINFO_TYPE_WECHAT);
        wxPayInfo.setCompanyInfoId(companyInfo.getCompanyId());
        wxPayInfo.setPayInfoDesc(JsonUtils.toJson(wechatInfo));
        payInfoService.save(wxPayInfo);
    }

    @Override
    public SupplierCompanyRspVo gysInfo(Long companyId) {
        CompanyInfo companyInfo = companyInfoService.getByBId(companyId);
        //获取企业商务信息
        CompanyBusinessInfo companyBusinessInfo = companyBusinessInfoService.getOne(new QueryWrapper<CompanyBusinessInfo>().lambda().
                eq(CompanyBusinessInfo::getCustomerId, companyId));
        SupplierCompanyRspVo supplierCompanyRspVo = BeanUtils.copy(companyBusinessInfo, SupplierCompanyRspVo.class);

        supplierCompanyRspVo.setCompanyCode(companyInfo.getCompanyCode()); //供应商编码
        supplierCompanyRspVo.setCompanyId(companyId);
        //获取支付账号信息
        List<PayInfo> payInfos = payInfoService.list(new QueryWrapper<PayInfo>().lambda()
                .eq(PayInfo::getCompanyInfoId,companyId));
        if(ObjectUtils.isNotEmpty(payInfos)){
            for(PayInfo pageInfo:payInfos){
                if(BasicConstant.PAYINFO_TYPE_BANK == pageInfo.getType()){
                    supplierCompanyRspVo.setBankInfo(JsonUtils.convertValue(pageInfo.getPayInfoDesc(), BankInfo.class));
                } else if(BasicConstant.PAYINFO_TYPE_ALIPAY == pageInfo.getType()){
                    supplierCompanyRspVo.setAlipayInfo(JsonUtils.convertValue(pageInfo.getPayInfoDesc(), AlipayInfo.class));
                } else if(BasicConstant.PAYINFO_TYPE_WECHAT == pageInfo.getType()){
                    supplierCompanyRspVo.setWechatInfo(JsonUtils.convertValue(pageInfo.getPayInfoDesc(), WechatInfo.class));
                }
            }
        }
        //获取审核信息
        Long auditId = companyInfo.getAuditId();
        if(null != auditId){
            AuditInfo auditInfo = auditInfoService.getByBId(auditId);
            supplierCompanyRspVo.setAuditDesc(auditInfo.getAuditDesc());
        }

        return supplierCompanyRspVo;
    }

    @Override
    public void gysStatus(Long companyId, Integer status) {
        //修改供应商状态
//        CompanyInfo companyInfo = companyInfoService.getByBId(companyId);
        //查看供应商是否为待审核状态，如果是 则直接返回
//        companyInfo.getAuditStatus();
        //修改审核状态为 待审核
//        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT); //修改为待审核状态
//        companyInfoService.updateByBId(companyInfo);

        companyInfoService.update(new LambdaUpdateWrapper<CompanyInfo>()
                .eq(CompanyInfo::getCompanyId,companyId)
                .set(CompanyInfo::getAuditStatus, BasicConstant.COMPANY_AUDIT_WAIT));

    }

    @Override
    public void audit(AuditInfoReqVo reqVo) {
        CompanyInfo companyInfo = companyInfoService.getByBId(reqVo.getAuditInfo());
        //
        AuditInfo auditInfo = BeanUtils.copy(reqVo, AuditInfo.class);
        auditInfoService.save(auditInfo);

        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_OK); //已审核
        companyInfo.setAuditId(auditInfo.getAuditId()); //审核表ID
        //根据审核结果，启用停用供应商
        Integer auditResult = auditInfo.getAuditResult();
        if(0 == auditResult){ //审核成功
            //判断是启用供应商 或者是 停用供应商
            //根据审核类型 以及 供应商状态
            if(BasicConstant.STATUS_ON == companyInfo.getStatus()){ //原状态是启用，则审核内容是  停用供应商
                companyInfo.setStatus(BasicConstant.STATUS_OFF);

                //停用供应商的所有管理人员
                oFFGYSAdminUser(companyInfo);
                //下线供应商的所有产品
                productInfoClientService.stopProduct(companyInfo.getCompanyCode());
                //发送mq到 数据中心
                mqProducerSend.enableCompanyInfo(companyInfo);
            }else{//原状态是停用，则审核内容是  启用供应商

                //启用供应商的所有管理人员
                List<AdminUserInfo> adminList = adminUserInfoService.list(new QueryWrapper<AdminUserInfo>().lambda()
                        .eq(AdminUserInfo::getCompanyId,companyInfo.getCompanyId()));
                if(ObjectUtils.isNotEmpty(adminList)){
                    for(AdminUserInfo adminUserInfo:adminList){
                        User user = userService.getByBId(adminUserInfo.getUserId());
                        user.setEnabled(user.getEnabled() - 1);
                        userService.updateByBId(user);
                    }
                }
                companyInfo.setStatus(BasicConstant.STATUS_ON);
                //发送mq到 数据中心
                mqProducerSend.pushCompanyInfo(companyInfo);
                //上线供应商的所有产品
            }
        }else{ //审核失败，只需修改供应商审核状态
            companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_BACK); //修改为 未通过
        }
        companyInfoService.updateByBId(companyInfo);
    }

    @Override
    public void updateGYS(SupplierCompanyReqVo reqVo) {
        //修改供应商 则修改状态为 停用以及待审核
        CompanyInfo companyInfo = companyInfoService.getByBId(reqVo.getCompanyId());
        companyInfo.setStatus(BasicConstant.STATUS_OFF); //停用
        companyInfo.setAuditStatus(BasicConstant.COMPANY_AUDIT_WAIT); //待审核
        companyInfo.setCompanyName(reqVo.getCompanyName());
        companyInfoService.updateByBId(companyInfo);

        //修改供应商商务信息
        //查找原企业商务信息
        CompanyBusinessInfo _comBus = companyBusinessInfoService.getOne(new LambdaQueryWrapper<CompanyBusinessInfo>()
                .eq(CompanyBusinessInfo::getCustomerId,companyInfo.getCompanyId()));
        CompanyBusinessInfo companyBusinessInfo = BeanUtils.copy(reqVo, CompanyBusinessInfo.class);
        companyBusinessInfo.setCustomerId(companyInfo.getCompanyId());
        companyBusinessInfo.setComBusinessId(_comBus.getComBusinessId());
        companyBusinessInfoService.updateByBId(companyBusinessInfo);

        //修改支付账号信息
        List<PayInfo> payInfos = payInfoService.list(new QueryWrapper<PayInfo>().lambda()
                .eq(PayInfo::getCompanyInfoId,companyInfo.getCompanyId()));
        if(ObjectUtils.isNotEmpty(payInfos)){
            for(PayInfo pageInfo:payInfos){
                if(BasicConstant.PAYINFO_TYPE_BANK == pageInfo.getType()){
//                    gysCompanyRspVo.setBankInfo(JsonUtils.convertValue(pageInfo.getPayInfoDesc(),BankInfo.class));
                    pageInfo.setPayInfoDesc(JsonUtils.toJson(reqVo.getBankInfo()));
                } else if(BasicConstant.PAYINFO_TYPE_ALIPAY == pageInfo.getType()){
                    pageInfo.setPayInfoDesc(JsonUtils.toJson(reqVo.getAlipayInfo()));
                } else if(BasicConstant.PAYINFO_TYPE_WECHAT == pageInfo.getType()){
                    pageInfo.setPayInfoDesc(JsonUtils.toJson(reqVo.getWechatInfo()));
                }
            }
            payInfoService.updateBatchByBId(payInfos);
        }

        //停用供应商的所有管理人员账号
        oFFGYSAdminUser(companyInfo);

        //下线供应商的所有产品
        productInfoClientService.stopProduct(companyInfo.getCompanyCode());

    }

    @Override
    public IPage pageGYS(SupplierCompanyPageReqVo reqVo) {
//        LambdaQueryWrapper wrapper = Wrappers.<CompanyInfo>lambdaQuery()
//                .eq(CompanyInfo::getType,BasicConstant.COMPANY_TYPE_GYX)
//                .likeRight(StringUtils.isNotBlank(reqVo.getCompanyName()), CompanyInfo::getCompanyName, reqVo.getCompanyName())
//                .like(StringUtils.isNotBlank(reqVo.getCompanyName()), CompanyInfo::getCompanyName, reqVo.getCompanyName());
        // 设置分页参数
        IPage<CompanyInfo> page = new Page<>(reqVo.getPageNum(), reqVo.getPageSize());
        IPage<SupplierCompanyPageReqVo> records = companyInfoDao.pageCompanyInfo(page,reqVo, BasicConstant.COMPANY_TYPE_GYX);
        return records;
    }

    @Override
    public List<CompanySimpleRspVo> listCompanyInfos(Long roleId) {
        Role role = roleService.getOne(new LambdaQueryWrapper<Role>().eq(Role::getId,roleId));
        Integer type = null;
        if(BasicConstant.ROLE_GYS_ADMIN.equals(role.getRoleName())){//查询供应商
            type = BasicConstant.COMPANY_TYPE_GYX;
        }else if(BasicConstant.ROLE_FWS_ADMIN.equals(role.getRoleName())){//查询服务商
            type = BasicConstant.COMPANY_TYPE_FWS;
        }
        List<CompanyInfo> records = companyInfoService.list(new LambdaQueryWrapper<CompanyInfo>()
                .eq(CompanyInfo::getType,type)
                .eq(CompanyInfo::getStatus, BasicConstant.STATUS_ON)
                .notInSql(CompanyInfo::getCompanyId,"select company_id from admin_user_info where company_id is not null"));

        if(ObjectUtils.isNotEmpty(records))
            return BeanUtils.copy(records,CompanySimpleRspVo.class);
        return null;
    }

    @Override
    public List<RelyCompanyRspVo> listRelyCom() {
        AdminUserInfo adminUserInfo = adminUserInfoService.getOne(
                new LambdaQueryWrapper<AdminUserInfo>().eq(AdminUserInfo::getUserId,OmpContextUtils.getUserId()));
        List<CompanyInfo> records = companyInfoDao.selectList(new LambdaQueryWrapper<CompanyInfo>()
                .eq(CompanyInfo::getStatus,BasicConstant.STATUS_ON)
                .eq(CompanyInfo::getType,BasicConstant.COMPANY_TYPE_YLF)
                .eq(CompanyInfo::getCompanyBusinessId,adminUserInfo.getCompanyId()));
        return BeanUtils.copy(records,RelyCompanyRspVo.class);
    }

    private void oFFGYSAdminUser(CompanyInfo companyInfo) {
        List<AdminUserInfo> adminList = adminUserInfoService.list(new QueryWrapper<AdminUserInfo>().lambda()
                .eq(AdminUserInfo::getCompanyId, companyInfo.getCompanyId()));
        for (AdminUserInfo adminUserInfo : adminList) {
            User user = userService.getByBId(adminUserInfo.getUserId());
            user.setEnabled(user.getEnabled() + 1);
            userService.updateByBId(user);
        }
    }
}
