package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.entity.CompanyBusinessInfo;
import com.ahdms.user.center.bean.entity.CompanyInfo;
import com.ahdms.user.center.bean.entity.PayInfo;
import com.ahdms.user.center.bean.vo.*;
import com.ahdms.user.center.bo.CompanyInfoPageBo;
import com.ahdms.user.center.bo.UserPageBo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <B>说明：服务商管理服务</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
public interface ICompanyInfoService extends BaseService<CompanyInfo> {

    IPage pageFWS(FWSCompanyPageReqVo reqVo);

    IPage pageDLS(DLSCompanyPageReqVo reqVo);

    void addFWSCompany(FWSCompanyReqVo reqVo);

    void addDLSCompany(DLSCompanyReqVo reqVo);

    void updateFWSCompany(FWSCompanyReqVo reqVo);

    void updateDLSCompany(DLSCompanyReqVo reqVo);

    FWSCompanyRspVo fwsInfo(Long companyId);

    DLSCompanyRspVo dlsInfo(Long companyId);

    void fwsStatus(Long companyId, Integer status);

    void dlsStatus(Long companyId, Integer status);

    void audit(AuditInfoReqVo reqVo);


    List<CompanySimpleRspVo> listByBinding(Integer bindingStatus);
}
