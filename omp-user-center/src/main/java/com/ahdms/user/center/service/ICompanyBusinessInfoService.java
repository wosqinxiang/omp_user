package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.bo.AuditInfoReqBo;
import com.ahdms.user.center.bean.bo.ComBusiRecordRspBo;
import com.ahdms.user.center.bean.bo.CompanyBusinessReqBo;
import com.ahdms.user.center.bean.entity.CompanyBusinessInfo;
import com.ahdms.user.center.bean.vo.CompanyBusinessInfoRspVo;
import com.ahdms.user.center.bean.vo.CustomerComBusiRspVo;

import java.util.List;

/**
 * <B>说明：服务</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
public interface ICompanyBusinessInfoService extends BaseService<CompanyBusinessInfo> {

    void authCompanyBusiness(CompanyBusinessReqBo companyBusinessReqBo);

    void updateCompanyBusiness(CompanyBusinessReqBo companyBusinessReqBo);

    CustomerComBusiRspVo getCompanyBussInfo(String customerId);

    List<ComBusiRecordRspBo> getCompanyBussRecords(String customerId);

    void auditCompanyBusiness(AuditInfoReqBo auditInfoReqBo);

    CompanyBusinessInfoRspVo getCompanyBussRecord(String comBusId);

//    CompanyBusinessInfoRspVo getCompanyBussRecord();
    CompanyBusinessInfoRspVo getCompanyBusinessInfoRspVo(String customerId);


}
