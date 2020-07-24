package com.ahdms.user.center.service.impl;

//import com.ahdms.connector.client.ConnectorClientService;
import com.ahdms.framework.core.commom.util.OmpContextUtils;
import com.ahdms.framework.core.commom.util.StringUtils;
import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.entity.CustomerInfo;
import com.ahdms.user.center.bean.entity.CustomerProductInfo;
import com.ahdms.user.center.bean.vo.CustomerProductInfoPageReqVo;
import com.ahdms.user.center.dao.ICompanyInfoDao;
import com.ahdms.user.center.dao.ICustomerInfoDao;
import com.ahdms.user.center.dao.ICustomerProductInfoDao;
import com.ahdms.user.center.service.ICustomerProductInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class CustomerProductInfoServiceImpl extends BaseServiceImpl<ICustomerProductInfoDao, CustomerProductInfo> implements ICustomerProductInfoService {
    @Autowired
    private ICustomerProductInfoDao customerProductInfoDao;

    @Autowired
    private ICustomerInfoDao customerInfoDao;


//    @Autowired
//    private ConnectorClientService connectorClientService;

    @Override
    public IPage<CustomerProductInfo> findPage(CustomerProductInfoPageReqVo customerProductInfoPageReqVo) {
        CustomerInfo customerInfo = customerInfoDao.selectOne(new LambdaQueryWrapper<CustomerInfo>()
                .eq(CustomerInfo::getUserId, OmpContextUtils.getUserId()));

        LambdaQueryWrapper wrapper = Wrappers.<CustomerProductInfo>lambdaQuery()
                .eq(CustomerProductInfo::getCustomerId,customerInfo.getCustomerId())
                .like(StringUtils.isNotBlank(customerProductInfoPageReqVo.getProductName()), CustomerProductInfo::getProductName, customerProductInfoPageReqVo.getProductName())
                .orderByDesc(CustomerProductInfo::getRemainCount);
        // 设置分页参数
        IPage<CustomerProductInfo> page = new Page<>(customerProductInfoPageReqVo.getPageNum(), customerProductInfoPageReqVo.getPageSize());
        return super.page(page, wrapper);
    }

    @Override
    public void pullCustomerProductInfo() {
//        String result = connectorClientService.productCount("{}");
//        connectorClientService.pullConsumerLog("","");
//        System.out.println(result);

    }


}

