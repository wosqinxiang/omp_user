package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.entity.CompanyBusinessInfo;
import com.ahdms.user.center.bean.entity.CompanyInfo;
import com.ahdms.user.center.bean.entity.PayInfo;
import com.ahdms.user.center.bean.vo.*;
import com.ahdms.user.center.bo.CompanyInfoPageBo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <B>说明：服务商管理服务</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
public interface ICompanyInfoService extends BaseService<CompanyInfo> {


    /**
     * 分页查询
     *
     * @param pageBo
     * @return
     */
    IPage<CompanyInfo> findPage(CompanyInfoPageBo pageBo);

    /**
     * 修改商户状态
     *
     * @param companyId 商户ID
     * @param status 状态：0启用、1禁用
     */
    void updateStatus(Long companyId, Integer status);

    /**
     * 修改
  *
     * @param companyInfo
     */
    void update(CompanyInfo companyInfo);

    void addFWSCompany(FWSCompanyReqVo reqVo);

    void addGYSCompany(SupplierCompanyReqVo reqVo);
    void saveCompanyInfo(CompanyBusinessInfo companyBusinessInfo, CompanyInfo companyInfo, PayInfo payInfo);

    void addrelyCompany(String companyName);

    void addDLSCompany(DLSCompanyReqVo reqVo);

    void addYLFCompany(YLFCompanyReqVo reqVo);

    void updateFWSCompany(FWSCompanyReqVo reqVo);

    void updateGYSCompany(SupplierCompanyReqVo reqVo);

    void updateDLSCompany(DLSCompanyReqVo reqVo);

    void updateYLFCompany(YLFCompanyReqVo reqVo);

}
