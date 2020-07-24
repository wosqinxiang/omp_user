package com.ahdms.user.center.bean.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qinxiang
 * @date 2020-07-23 17:55
 */
@Data
public class AdminInfoBo {

    @ApiModelProperty("业务主键")
    private Long adminId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("商家业务主键")
    private Long companyId;

    @ApiModelProperty("所属部门")
    private Integer departmentId;

    @ApiModelProperty("0.启用，1.停用")
    private Integer status;

}
