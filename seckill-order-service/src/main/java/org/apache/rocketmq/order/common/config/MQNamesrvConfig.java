package org.apache.rocketmq.order.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/8/24 17:06
 * @className MQNamesrvConfig
 * @desc NameServer配置
 */
@Component
public class MQNamesrvConfig {

    @Value("${rocketmq.nameServer.offline}")
    String offlineNamesrv;

    @Value("${rocketmq.nameServer.aliyun}")
    String aliyunNamesrv;

    public String nameSrvAddr() {
        String envType = System.getProperty("envType");
        System.out.println(envType);
        if (StringUtils.isBlank(envType)) {
            throw new IllegalArgumentException("please insert envType");
        }
        switch (envType) {
            case "offline" : {
                return offlineNamesrv;
            }
            case "aliyun" : {
                return aliyunNamesrv;
            }
            default : {
                throw new IllegalArgumentException("please insert right envType, offline/aliyun");
            }
        }
    }
}
