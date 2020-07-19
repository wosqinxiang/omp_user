package com.ahdms.user.client;

import com.ahdms.user.client.vo.CompanyInfoRmiRspVo;
import com.ahdms.user.client.vo.PayInfoRspVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author qinxiang
 * @date 2020-07-16 20:00
 */
@FeignClient(value = "${client.omp.user.center.group:omp-user-center}", contextId = "user")
@RequestMapping("/rmi/user/company")
public interface CompanyClientService {
    /**
     * 获取用户下 的服务商或供应商信息
     * @param userId
     * @return
     */
    @GetMapping("info")
    CompanyInfoRmiRspVo getCompanyInfo(@Validated @NotNull @RequestParam("userId")Long userId);

    /**
     * 获取用户下 所有启用的代理商或依赖方
     * @param userId
     * @return
     */
    @GetMapping("infos")
    List<CompanyInfoRmiRspVo> getCompanyInfos(@Validated @NotNull @RequestParam("userId")Long userId);

    /**
     * 根据商家业务主键查询支付账号信息
     * @param companyId
     * @return
     */
    @GetMapping("getPayInfos")
    PayInfoRspVo getPayInfos(@Validated @NotNull @RequestParam("companyId") Long companyId);

}
