package com.ahdms.user.center.controller;

import com.ahdms.user.center.bean.entity.Provinces;
import com.ahdms.user.center.bean.vo.ProvincesRspVo;
import com.ahdms.user.center.service.IProvincesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <B>说明： 控制器</B><BR>
 *
 * @author qinxiang
 * @version 1.0.0
 * @since 2020-07-13 13:35
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/provinces")
@Api(" 控制器")
public class ProvincesController {
    @Autowired
    private IProvincesService provincesService;

    @GetMapping("list")
    public List<Provinces> list(){
        List<Provinces> list = provincesService.list();
        return list;
    }

}
