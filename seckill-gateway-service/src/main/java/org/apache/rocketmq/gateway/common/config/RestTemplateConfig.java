package org.apache.rocketmq.gateway.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/10 13:37
 * @className RestTemplateConfig
 * @desc RestTemplate配置
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    @Bean
    public RestTemplate restTemplateNew(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        /**读超时单位为ms*/
        factory.setReadTimeout(10000);
        /**连接超时单位为ms*/
        factory.setConnectTimeout(10000);
        return factory;
    }

}
