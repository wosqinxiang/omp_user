package com.ahdms.user.center.service.impl;

import com.ahdms.framework.mybatis.service.impl.BaseServiceImpl;
import com.ahdms.user.center.bean.entity.PayInfo;
import com.ahdms.user.center.dao.IPayInfoDao;
import com.ahdms.user.center.service.IPayInfoService;
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
public class PayInfoServiceImpl extends BaseServiceImpl<IPayInfoDao, PayInfo> implements IPayInfoService {
    @Autowired
    private IPayInfoDao payInfoDao;

}

