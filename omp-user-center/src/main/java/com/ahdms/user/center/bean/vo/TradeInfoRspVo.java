package com.ahdms.user.center.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:35
 */
@Data
public class TradeInfoRspVo  {

    @ApiModelProperty("主键Id")
    private Integer id;

    @ApiModelProperty("行业名称")
    private String tradeName;

    @ApiModelProperty("行业编码")
    private String tradeCode;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("")
    private Integer parentId;
}
