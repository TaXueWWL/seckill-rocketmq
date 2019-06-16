package org.apache.rocketmq.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author snowalker
 * @date 2019/6/14 13:46
 * 秒杀网关启动器
 */
@SpringBootApplication
@ImportResource({"classpath*:META-INF/applicationContext-bean.xml"})
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

}
