package com.ahdms.user.center.dao;

import com.ahdms.user.center.bean.vo.SupplierCompanyPageReqVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import com.ahdms.framework.mybatis.mapper.IMapper;
import com.ahdms.user.center.bean.entity.CompanyInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Mapper
public interface ICompanyInfoDao extends IMapper<CompanyInfo> {
    IPage<SupplierCompanyPageReqVo> pageCompanyInfo(@Param("page") IPage<CompanyInfo> page,
                                                    @Param("reqVo") SupplierCompanyPageReqVo reqVo,
                                                    @Param("type") Integer companyType);
}
