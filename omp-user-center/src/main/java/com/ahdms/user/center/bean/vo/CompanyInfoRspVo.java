package com.ahdms.user.center.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Data
public class CompanyInfoRspVo  {

    @ApiModelProperty("")
    private Integer id;

    @ApiModelProperty("业务主键")
    private Long companyId;

    @ApiModelProperty("名称")
    private String companyName;

    @ApiModelProperty("商家编码")
    private String companyCode;

    @ApiModelProperty("类型(1.服务商、2.供应商、3.代理商, 4.产品依赖服务方)")
    private Integer type;

    @ApiModelProperty("审核状态(0.已审核、1.未审核)")
    private Integer auditStatus;

    @ApiModelProperty("审核信息业务ID")
    private Long auditId;

    @ApiModelProperty("商家状态(0.启用、1.停用)")
    private Integer status;

    @ApiModelProperty("行业")
    private String trade;

    @ApiModelProperty("区域")
    private String area;

    @ApiModelProperty("企业商务信息表id")
    private Long companyBusinessId;

    @ApiModelProperty("支付账号信息表业务主键")
    private Long payInfoId;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("修改人")
    private Long updatedBy;

    @ApiModelProperty("更新时间")
    private Date updatedAt;
}
