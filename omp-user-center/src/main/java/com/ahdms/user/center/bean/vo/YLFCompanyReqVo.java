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

    @ApiModelProperty("类型(1.服务商、2.供应商、3.代理商, 4.产品依赖服务方)")
    private Integer type;
}
