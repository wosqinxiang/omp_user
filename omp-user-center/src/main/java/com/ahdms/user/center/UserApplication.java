package com.ahdms.user.center;

import com.ahdms.connector.client.ConnectorClientService;
import com.ahdms.connector.client.stream.PushCustomerStreamClient;
import com.ahdms.data.client.DataLogClientService;
import com.ahdms.data.client.stream.DataStreamClient;
import com.ahdms.framework.stream.client.annotation.EnableStreamClients;
import com.ahdms.message.client.MessageClientService;
import com.ahdms.product.client.ProductInfoClientService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhoumin
 * @version 1.0.0
 * @date 2020/7/9 9:28
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = RedisReactiveAutoConfiguration.class)
@MapperScan("com.ahdms.user.center.dao")
@EnableFeignClients(clients = {
        ConnectorClientService.class, DataLogClientService.class,
        MessageClientService.class, ProductInfoClientService.class
})
@EnableStreamClients(clients = {DataStreamClient.class,PushCustomerStreamClient.class})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
