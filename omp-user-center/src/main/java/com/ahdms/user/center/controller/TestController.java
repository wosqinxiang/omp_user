package com.ahdms.user.center.controller;

import com.ahdms.connector.client.ConnectorClientService;
import com.ahdms.connector.client.vo.CustomerInfoReqVo;
import com.ahdms.framework.core.commom.util.IdGenerateUtils;
import com.ahdms.user.center.bean.entity.CustomerInfo;
import com.ahdms.user.center.message.MQProducerSend;
import com.ahdms.user.center.message.SmsCodeMessage;
import com.ahdms.user.center.utils.RamdonUtils;
import com.ahdms.user.client.UserClientService;
import com.ahdms.user.client.vo.UserLoginRspVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author qinxiang
 * @date 2020-07-13 18:19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/customer-info")
@Api(" 控制器")
public class TestController {

    @Autowired
    private UserClientService userClientService;

    @Autowired
    private SmsCodeMessage smsCodeMessage;

    @Autowired
    private MQProducerSend mqProducerSend;

    @Autowired
    private ConnectorClientService connectorClientService;

    @PostMapping("/testLogin")
    public UserLoginRspVo loginPassword(@Validated @NotNull @RequestParam("identity") String identity,
                                 @Validated @NotNull @RequestParam("password") String password){
        return userClientService.loginPassword(identity,password);
    }

    @PostMapping("/send")
    public void send(@Validated @NotNull @RequestParam("mobile") String mobile){
        String id = RamdonUtils.generateId("USER-DLS","DLS",4);

        System.out.println(id);
//        smsCodeMessage.sendSmsCode(mobile);
    }

    @PostMapping("/sendUser")
    public void sendUser(@Validated @NotNull @RequestBody CustomerInfo customerInfo){
//        mqProducerSend.pushCustomerInfoToConnect(customerInfo);
        connectorClientService.pushCustomerInfo("sss",new CustomerInfoReqVo());
    }
}
