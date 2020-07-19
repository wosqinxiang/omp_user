package com.ahdms.user.center.controller;

import com.ahdms.user.center.service.IAuditInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <B>说明： 控制器</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:34
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/audit-info")
@Api(" 控制器")
public class AuditInfoController {
    @Autowired
    private IAuditInfoService auditInfoService;
}
