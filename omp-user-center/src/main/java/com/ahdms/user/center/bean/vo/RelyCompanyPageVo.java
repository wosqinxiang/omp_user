package com.ahdms.user.center.bean.vo;

import com.ahdms.framework.core.commom.page.PageReqParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qinxiang
 * @date 2020-07-17 13:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RelyCompanyPageVo extends PageReqParam {

    // 业务按需添加分页参数
    @ApiModelProperty("依赖方名称")
    private String companyName;

}
