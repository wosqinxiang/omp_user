package com.ahdms.user.center.service;

import com.ahdms.user.center.bean.entity.CompanyInfo;
import com.ahdms.user.center.bean.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author qinxiang
 * @date 2020-07-17 12:58
 */
public interface IRelyCompanyService {

    void addrelyCompany(String companyName);

    IPage<CompanyInfo> page(RelyCompanyPageVo company);

    void updaterelyCompany(Long companyId, String companyName);

    void statusRelyCompany(Long companyId, Integer status);

    void addGYSCompany(SupplierCompanyReqVo reqVo);

    SupplierCompanyRspVo gysInfo(Long companyId);

    void gysStatus(Long companyId, Integer status);

    void audit(AuditInfoReqVo reqVo);

    void updateGYS(SupplierCompanyReqVo reqVo);

    IPage pageGYS(SupplierCompanyPageReqVo reqVo);

    List<CompanySimpleRspVo> listCompanyInfos(Long roleId);

    List<RelyCompanyRspVo> listRelyCom();
}
