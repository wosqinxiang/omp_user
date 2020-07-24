package com.ahdms.user.center.constant;

/**
 * @author qinxiang
 * @date 2020-07-13 19:22
 */
public class BasicConstant {
    public static final String SUPER_ADMIN_USERNAME = "admin";  //超级管理员 账号
    public static final String SUPER_ADMIN_PASSWORD = "AHdms520";  //超级管理员 密码

    //
    public static final Integer STATUS_ON = 0;  // 可用
    public static final Integer STATUS_OFF = 1;  //禁用

    public static final Integer COM_BUS_STATUS_ON = 0;  // 在使用
    public static final Integer COM_BUS_STATUS_AUTHING = 1;  //企业认证中
    public static final Integer COM_BUS_STATUS_CHANGE = 2;   //变更中
    public static final Integer COM_BUS_STATUS_OFF = 5;  //已作废


    public static final Integer AUDIT_TYPE_AUTH = 1;  //企业认证
    public static final Integer AUDIT_TYPE_CHANAGE = 2; //企业商务信息变更

    //企业认证状态
    public static final Integer CUSTOM_AUTH_STATUS_NO = 0; //未认证
    public static final Integer CUSTOM_AUTH_STATUS_AUTHING = 1;  //认证中
    public static final Integer CUSTOM_AUTH_STATUS_CHANGING = 2;  //变更中
    public static final Integer CUSTOM_AUTH_STATUS_BACK = 3;  //已退回
    public static final Integer CUSTOM_AUTH_STATUS_OK = 5;  //已认证

    //企业审核状态
    public static final Integer CUSTOM_AUDIT_STATUS_NO = 0; //无
    public static final Integer CUSTOM_AUDIT_STATUS_WAIT = 1; //待审核
    public static final Integer CUSTOM_AUDIT_STATUS_AUDITING = 2;  //审核中
    public static final Integer CUSTOM_AUDIT_STATUS_OK = 3;  //已审核

    //商家类型
    public static final Integer COMPANY_TYPE_FWS = 1; //服务商
    public static final Integer COMPANY_TYPE_GYX = 2; //供应商
    public static final Integer COMPANY_TYPE_DLS = 3;  //代理商
    public static final Integer COMPANY_TYPE_YLF = 4;  //依赖方

    public static final Integer COMPANY_AUDIT_WAIT = 1; //商家审核状态 待审核
    public static final Integer COMPANY_AUDIT_OK = 0; //已审核
    public static final Integer COMPANY_AUDIT_BACK = 2; //未通过

    public static final Integer PAYINFO_TYPE_BANK = 1; //银行卡
    public static final Integer PAYINFO_TYPE_ALIPAY = 2; //支付宝支付
    public static final Integer PAYINFO_TYPE_WECHAT = 3;  //微信支付


    public static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";  //超级管理员
    public static final String ROLE_ZT_ADMIN = "CENTER_ADMIN";  //中台管理员
    public static final String ROLE_ZT_CZY = "CENTER_OPERATOR"; //中台操作员
    public static final String ROLE_ZT_AUDIT = "CENTER_AUDITOR"; //中台审计员
    public static final String ROLE_GYS_ADMIN = "SUPPLIER_ADMIN"; //供应商管理员
    public static final String ROLE_GYS_CP_ADMIN = "PRODUCT_ADMIN"; //供应商产品管理员
    public static final String ROLE_GYS_SQKFY = "CUSTOMER_SERVICE"; //售前客服员
    public static final String ROLE_GYS_CW_ADUIT = "GYS_CW_ADUIT"; //财务审计员
    public static final String ROLE_FWS_ADMIN = "SERVICE_ADMIN"; //服务商管理员
    public static final String ROLE_FWS_TGY = "MARKET_SALESMAN"; //市场推广员
    public static final String ROLE_FWS_CW_ADMIN = "FINANCE_AUDITOR"; //财务审计员
    public static final String ROLE_CUSTOMER = "CUSTOMER"; //注册客户



}
