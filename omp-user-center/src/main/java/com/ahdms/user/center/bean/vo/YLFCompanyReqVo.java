package com.ahdms.user.center.bean.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <B>说明：依赖方请求参数</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@Data

public class YLFCompanyReqVo {

    @ApiModelProperty("名称")
    private String companyName;

}
