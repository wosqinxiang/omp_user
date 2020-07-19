package com.ahdms.user.center.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-13 19:48
 */
@Data
public class AuditInfoReqBo {


    @ApiModelProperty("审核结果(0.通过 1.退回)")
    private Integer auditResult;

    @ApiModelProperty("审核备注")
    private String auditDesc;


    @ApiModelProperty("审核类型(1.企业认证 2.企业商务信息变更)")
    private Integer auditType;

    @ApiModelProperty("审核内容的业务主键")
    private String auditInfo;

}
