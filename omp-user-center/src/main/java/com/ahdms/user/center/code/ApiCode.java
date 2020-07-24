package com.ahdms.user.center.code;


import com.ahdms.framework.core.alert.IAlertAble;
import com.ahdms.framework.core.web.response.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoumin
 * @version 1.0.0
 * @date 2020/7/6 17:13
 */
@Getter
@AllArgsConstructor
public enum ApiCode implements IAlertAble {
    USER_NOT_EXIST("00001", "用户账号或密码有误"),
    USER_PWD_ERROR("00002", "用户密码有误"),
    USER_SMSCODE_ERROR("00003", "手机验证码错误"),
    USER_EMAIL_REPEAT("00004", "邮箱已被占用"),
    USER_NAME_REPEAT("00005", "账号名已被占用"),

    USER_MOBILE_REPEAT("00006", "手机号已注册"),
    USER_MOBILE_NOT("00009", "手机号未注册"),
    USER_MOBILE_MATCH("00007", "手机号不是注册时的号码"),
    USER_MOBILE_FORMAT_ERROR("00012", "手机号格式有误"),

    USER_NAME_FORMAT_ERROR("00008", "输入的账号名格式有误"),

    USER_CMB_ISAUTH("00010", "客户已提交企业认证"),
    USER_CMB_NOAUTH("00011", "客户未完成企业认证"),

    USER_PWD_RESET_ERROR("00040","密码重置失败，请重试!"),

    USER_LOGIN_DISABLED("00020", "用户账号被禁用"),
    USER_LOGIN_PWD_ERROR("00021",""),
    USER_ADMIN_COM_NOTNULL("00030", "服务商/供应商未选择"),

    USER_COM_DISABLED("00031", "服务商/供应商已被停用"),

    USER_ROLE_NOAUTH("00101", "无权限"),

    USER_COM_CODE_ERROR("01001","商家编码有误!"),

    USER_COM_NAME_REPEAT("00201","企业名称已存在")
    ;

    private String code;
    private String message;
    
}
