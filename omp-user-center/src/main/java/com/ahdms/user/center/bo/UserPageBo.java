package com.ahdms.user.center.bo;

import com.ahdms.framework.core.commom.page.PageReqParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserPageBo extends PageReqParam {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
