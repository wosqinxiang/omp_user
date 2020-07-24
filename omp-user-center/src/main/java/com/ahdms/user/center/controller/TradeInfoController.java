package com.ahdms.user.center.controller;

import com.ahdms.framework.core.commom.util.BeanUtils;
import com.ahdms.user.center.bean.bo.TradeInfoBo;
import com.ahdms.user.center.bean.entity.TradeInfo;
import com.ahdms.user.center.bean.vo.TradeInfoRspVo;
import com.ahdms.user.center.service.ITradeInfoService;
import io.swagger.annotations.Api;
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
@RequestMapping("/api/user/trade")
@Api(" 控制器")
public class TradeInfoController {
    @Autowired
    private ITradeInfoService tradeInfoService;

    @GetMapping("list")
    @ApiOperation(value = "获取所有一级行业", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public List<TradeInfoRspVo> list(){
        List<TradeInfoBo> records = tradeInfoService.listLevel();
        return BeanUtils.copy(records,TradeInfoRspVo.class);
    }

//    @GetMapping("")
//    @ApiOperation(value = "根据一级行业ID,获取所有二级行业", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public List<TradeInfo> list(@RequestParam Integer id){
//        return tradeInfoService.listByParentId(id);
//    }
}
