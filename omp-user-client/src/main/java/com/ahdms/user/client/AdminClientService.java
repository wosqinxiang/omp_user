package com.ahdms.user.client;

import com.ahdms.user.client.vo.AdminUserRspVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author qinxiang
 * @date 2020/7/19 20:09
 */
@FeignClient(value = "${client.omp.user.center.group:omp-user-center}", contextId = "user")
@RequestMapping("/rmi/user/admin")
public interface AdminClientService {

    /**
     * 获取所有可用的中台操作员信息
     * @return
     */
    @GetMapping("centerOper")
    List<AdminUserRspVo> allAdmin();

}
