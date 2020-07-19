package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <B>说明：服务商请求参数</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@Data
public class FWSCompanyReqVo {

    @ApiModelProperty("名称")
    private String companyName;

    @ApiModelProperty("类型(1.服务商、2.供应商、3.代理商, 4.产品依赖服务方)")
    private Integer type;

    @ApiModelProperty("行业")
    private String trade;

    @ApiModelProperty("区域")
    private String area;

    @ApiModelProperty("银行卡账户信息")
    private BankInfo bankInfo;

    @ApiModelProperty("开户行")
    private String bankName;

    @ApiModelProperty("开户人姓名")
    private String bankCardUsername;

    @ApiModelProperty("常用联系人姓名")
    private String contactName;

    @ApiModelProperty("常用联系人手机号")
    private String contactPhone;
}