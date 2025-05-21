package com.etna.gpe.mycloseshop.ms_social_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
        "com.etna.gpe.mycloseshop.common_api.ms_login.api",
})
@SpringBootApplication(scanBasePackages = {
        "com.etna.gpe.mycloseshop.ms_social_api",
        "com.etna.gpe.mycloseshop.security_api",
        "com.etna.gpe.mycloseshop.common_api",
        "com.etna.gpe.mycloseshop.common_api.ms_login.api",

})
public class MyCloseshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCloseshopApplication.class, args);
    }

}
