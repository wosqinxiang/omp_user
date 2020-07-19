package com.ahdms.user.center.bean.vo;

import com.ahdms.framework.core.commom.page.PageReqParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单分页请求模型
 *
 * @author qinxiang
 * @version 1.0.0.
 * @date 2020-07-13 13:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerProductInfoPageReqVo extends PageReqParam {

    // 业务按需添加分页参数
    @ApiModelProperty("产品名称")
    private String productName;
}
