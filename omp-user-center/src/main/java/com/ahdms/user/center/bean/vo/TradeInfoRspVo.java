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
 * @date 2020-07-13 13:35
 */
@Data
public class TradeInfoRspVo  {

    @ApiModelProperty("")
    private Integer id;

    @ApiModelProperty("")
    private String tradeName;

    @ApiModelProperty("")
    private String tradeCode;

    @ApiModelProperty("")
    private Integer level;

    @ApiModelProperty("")
    private Integer parentId;
}
