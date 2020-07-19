package com.ahdms.user.center.bean.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author qinxiang
 * @date 2020-07-14 14:47
 */
@Data
public class ComBusiRecordRspBo {

    private String infoDesc;

    private Long comBusinessId;

    private Date created_at;

    private Date audit_at;

    private String auditResult;

}
