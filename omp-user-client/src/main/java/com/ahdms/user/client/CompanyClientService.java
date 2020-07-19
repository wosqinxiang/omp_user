package com.ahdms.user.client;

import com.ahdms.user.client.vo.CompanyInfoRmiRspVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    CompanyInfoRmiRspVo getCompanyInfo(Long userId);

    /**
     * 获取用户下 所有启用的代理商或依赖方
     * @param userId
     * @return
     */
    @GetMapping("infos")
    List<CompanyInfoRmiRspVo> getCompanyInfos(Long userId);

}
