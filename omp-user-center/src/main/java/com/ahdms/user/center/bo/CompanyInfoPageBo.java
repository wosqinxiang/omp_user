package com.ahdms.user.center.bo;

import com.ahdms.framework.core.commom.page.PageReqParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyInfoPageBo extends PageReqParam {

    private String typeId;

}
