package org.apache.rocketmq.gateway.common;

import org.apache.rocketmq.gateway.common.init.SecKillProductConfig;
import org.apache.rocketmq.gateway.common.service.SecKillProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/8/21 11:41
 * @className ProdCommandLineRunner
 * @desc
 */
@Component
public class ProdCommandLineRunner implements CommandLineRunner {

    @Autowired
    SecKillProductConfig secKillProductConfig;

    @Autowired
    SecKillProductService productService;

    @Override
    public void run(String... args) throws Exception {
        String prodId = "pid_0004";
//        secKillProductConfig.preReduceProdStock(prodId);
        System.out.println(productService.queryProdById(prodId));
    }
}
