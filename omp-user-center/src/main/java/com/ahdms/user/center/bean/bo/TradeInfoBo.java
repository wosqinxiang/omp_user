package com.ahdms.user.center.bean.bo;

import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-23 18:06
 */
@Data
public class TradeInfoBo {

    private Integer id;

    private String tradeName;

    private String tradeCode;

    private Integer level;

    private Integer parentId;

}
