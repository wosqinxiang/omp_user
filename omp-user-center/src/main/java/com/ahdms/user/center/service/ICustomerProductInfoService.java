package com.ahdms.user.center.service;

import com.ahdms.framework.mybatis.service.BaseService;
import com.ahdms.user.center.bean.entity.CustomerProductInfo;
import com.ahdms.user.center.bean.vo.CustomerProductInfoPageReqVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <B>说明：服务</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
public interface ICustomerProductInfoService extends BaseService<CustomerProductInfo> {


    IPage<CustomerProductInfo> findPage(CustomerProductInfoPageReqVo customerProductInfoPageReqVo);

    void pullCustomerProductInfo();
}
