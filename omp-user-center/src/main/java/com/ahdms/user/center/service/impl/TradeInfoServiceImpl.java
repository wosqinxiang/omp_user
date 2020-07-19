package com.ahdms.user.center.service.impl;

import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.dao.ITradeInfoDao;
import com.ahdms.user.center.service.ITradeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ahdms.user.center.bean.entity.TradeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * <B>说明：服务实现</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:35
 */
@Slf4j
@Service
@Validated
public class TradeInfoServiceImpl extends BaseServiceImpl<ITradeInfoDao, TradeInfo> implements ITradeInfoService {
    @Autowired
    private ITradeInfoDao tradeInfoDao;

}

