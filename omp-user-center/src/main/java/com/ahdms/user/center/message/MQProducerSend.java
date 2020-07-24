package com.ahdms.user.center.message;

import com.ahdms.data.client.constant.StreamConstants;
import com.ahdms.data.client.stream.DataStreamClient;
import com.ahdms.data.client.vo.DataCompanyInfoReqVo;
import com.ahdms.data.client.vo.DataCustomerInfoReqVo;
import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.user.center.bean.entity.CompanyInfo;
import com.ahdms.user.center.bean.entity.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qinxiang
 * @date 2020-07-16 16:16
 */
@Component
public class MQProducerSend {

    @Autowired
    private DataStreamClient dataStreamClient;

//    @Autowired
//    private PushCustomerStreamClient pushCustomerStreamClient;


    public void pushCompanyInfo(CompanyInfo companyInfo){
        DataCompanyInfoReqVo dataCompanyInfoReqVo = BeanUtils.copy(companyInfo,DataCompanyInfoReqVo.class);
        dataCompanyInfoReqVo.setMethod(StreamConstants.METHOD_INSERT);
        dataStreamClient.pushCompanyInfo(dataCompanyInfoReqVo);
    }

    public void enableCompanyInfo(CompanyInfo companyInfo){
        DataCompanyInfoReqVo dataCompanyInfoReqVo = BeanUtils.copy(companyInfo,DataCompanyInfoReqVo.class);
        dataCompanyInfoReqVo.setMethod(StreamConstants.METHOD_UPDATE_STATUS);
        dataStreamClient.pushCompanyInfo(dataCompanyInfoReqVo);
    }

    public void pushCustomerInfo(CustomerInfo customerInfo){
        DataCustomerInfoReqVo dataCustomerInfoReqVo = BeanUtils.copy(customerInfo,DataCustomerInfoReqVo.class);
        dataCustomerInfoReqVo.setMethod(StreamConstants.METHOD_INSERT);
        dataStreamClient.pushCustomerInfo(dataCustomerInfoReqVo);
    }

    public void pushCustomerInfoToConnect(CustomerInfo customerInfo){
//        ConnectorReqVo connectorReqVo = new ConnectorReqVo();
//        connectorReqVo.setTarget(Contants.TARGET_JF);
//        connectorReqVo.setToString(JsonUtils.toJson(customerInfo));
//        pushCustomerStreamClient.pushCustomer(connectorReqVo);
    }


}
