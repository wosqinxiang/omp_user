package com.ahdms.user.center.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang    cc ReqVo  true
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Data
public class AuditInfoReqVo  {

    @ApiModelProperty("业务主键")
    private Long auditId;

    @ApiModelProperty("审核结果")
    private Integer auditResult;

    @ApiModelProperty("审核备注")
    private String auditDesc;

    @ApiModelProperty("审核类型")
    private Integer auditType;

    @ApiModelProperty("审核内容(审核内容的业务主键)")
    private String auditInfo;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("创建时间")
    private Date createdAt;
}
