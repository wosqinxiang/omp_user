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
 * @date 2020-07-13 13:35
 */
@Data
public class VerifiyCodeReqVo  {

    @ApiModelProperty("1 手机号码 2 EMAIL地址")
    private Integer type;

    @ApiModelProperty("")
    private String mobile;

    @ApiModelProperty("")
    private String email;

    @ApiModelProperty("")
    private String verificationCode;

    @ApiModelProperty("")
    private Date sendDate;

    @ApiModelProperty("验证后 被修改 1有效 0 无效")
    private Integer isValid;
}
