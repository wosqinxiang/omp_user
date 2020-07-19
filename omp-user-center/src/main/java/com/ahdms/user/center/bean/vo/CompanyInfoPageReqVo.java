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
public class CompanyInfoPageReqVo extends PageReqParam {

    @ApiModelProperty("类型(1.服务商、2.供应商、3.代理商, 4.产品依赖服务方)")
    private Integer type;
}
