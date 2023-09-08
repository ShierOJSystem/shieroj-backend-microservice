package com.shier.shierojbackendjudgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.shier.shierojbackendjudgeservice.mq.CodeMqInitMain.doInitCodeMq;

/**
 * @author Shier
 */
@SpringBootApplication()
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.shier")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.shier.shierojbackendserviceclient.service"})
public class ShierojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        // 初始化消息队列
        doInitCodeMq();
        SpringApplication.run(ShierojBackendJudgeServiceApplication.class, args);
    }
}
