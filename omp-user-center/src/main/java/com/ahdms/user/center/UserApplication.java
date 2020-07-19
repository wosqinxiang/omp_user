package com.ahdms.user.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhoumin
 * @version 1.0.0
 * @date 2020/7/9 9:28
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = RedisReactiveAutoConfiguration.class)
@MapperScan("com.ahdms.user.center.dao")
//@EnableFeignClients(clients = {
//        ConnectorClientService.class
//})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
