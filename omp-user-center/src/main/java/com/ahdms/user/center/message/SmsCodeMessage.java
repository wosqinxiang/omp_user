package com.ahdms.user.center.message;

import com.ahdms.message.client.MessageClientService;
import com.ahdms.message.client.config.TemplateConfig;
import com.ahdms.user.center.utils.RamdonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author qinxiang
 * @date 2020-07-21 14:03
 */
@Component
public class SmsCodeMessage {

    @Autowired
    private MessageClientService messageClientService;

    @Autowired
    private TemplateConfig templateConfig;

    /**
     * 发送短信验证码
     * @param mobile
     */
    public void sendSmsCode(String mobile){
        String[] mobiles = new String[]{mobile};
        String[] params = new String[]{RamdonUtils.randomNumeric(6)};
        sendSmsCode(mobiles,params,templateConfig.getMessageVerificationNote());
    }

    /**
     * 企业认证审核通过通知
     * @param mobile
     */
    public void sendAuthComBus(String mobile){
        String[] mobiles = new String[]{mobile};
        String[] params = new String[]{};
        sendSmsCode(mobiles,params,templateConfig.getUserEnterpriseAuditPass());
    }

    /**
     * 企业认证审核通知
     * @param mobile
     */
    public void sendEnterpriseAudit(String mobile,String identity){
        String[] mobiles = new String[]{mobile};
        String[] params = new String[]{identity};
        sendSmsCode(mobiles,params,templateConfig.getUserEnterpriseAudit());
    }

    /**
     * 企业认证审核退回通知
     * @param mobile
     */
    public void sendBackAuthComBus(String mobile){
        String[] mobiles = new String[]{mobile};
        String[] params = new String[]{};
        sendSmsCode(mobiles,params,templateConfig.getUserEnterpriseAuditReturn());
    }

    /**
     * 产品续费通知
     * @param mobile
     */
    public void sendProdRenew(String mobile,String productName){
        String[] mobiles = new String[]{mobile};
        String[] params = new String[]{productName};
        sendSmsCode(mobiles,params,templateConfig.getOrderRenewNote());
    }


    public void sendSmsCode(String[] mobiles,String[] params,String templateId){

        messageClientService.send(mobiles,params,templateId);
    }
}
