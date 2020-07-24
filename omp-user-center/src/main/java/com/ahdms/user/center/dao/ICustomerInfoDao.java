package com.ahdms.user.center.dao;

import com.ahdms.framework.mybatis.mapper.IMapper;
import com.ahdms.user.center.bean.bo.CustomerInfoPageBo;
import com.ahdms.user.center.bean.bo.CustomerPageBo;
import com.ahdms.user.center.bean.entity.CustomerInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@Mapper
public interface ICustomerInfoDao extends IMapper<CustomerInfo> {

    IPage<CustomerInfoPageBo> pageCustomer(@Param("page") IPage<CustomerInfoPageBo> page, @Param("customerInfoPageBo") CustomerPageBo customerInfoPageBo);

}
