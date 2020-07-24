package com.ahdms.user.center.bean.bo;

import com.ahdms.framework.core.commom.page.PageReqParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qinxiang
 * @date 2020-07-24 9:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerPageBo extends PageReqParam {

    // 业务按需添加分页参数
    private String username;

    private String mobile;

    private String companyName;
}
