package com.ahdms.user.center.controller;

import com.ahdms.user.center.bean.entity.Cities;
import com.ahdms.user.center.service.ICitiesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/cities")
@Api(" 控制器")
public class CitiesController {
    @Autowired
    private ICitiesService citiesService;

    @GetMapping("list")
    @ApiOperation(value = "查询省下的所有市", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParam(paramType="query", name = "provinceCode", value = "省的编码", required = true, dataType = "String")
    public List<Cities> list(@Validated @RequestParam String provinceCode){
        return citiesService.list(new LambdaQueryWrapper<Cities>().eq(Cities::getProvinceCode,provinceCode));
    }
}
