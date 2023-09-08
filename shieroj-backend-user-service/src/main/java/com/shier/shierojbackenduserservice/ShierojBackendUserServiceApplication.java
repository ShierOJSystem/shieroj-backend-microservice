package com.shier.shierojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Shier
 */
@SpringBootApplication()
@MapperScan("com.shier.shierojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.shier")
@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.shier.shierojbackendserviceclient.service"})
public class ShierojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShierojBackendUserServiceApplication.class, args);
    }

}
