//package org.apache.rocketmq.gateway.common;
//
//import org.apache.rocketmq.gateway.common.dao.dataobject.SecKillProductDO;
//import org.apache.rocketmq.gateway.common.init.SecKillProductConfig;
//import org.apache.rocketmq.gateway.common.service.SecKillProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//
///**
// * @author snowalker
// * @version 1.0
// * @date 2019/8/21 11:41
// * @className ProdCommandLineRunner
// * @desc
// */
//@Component
//public class ProdCommandLineRunner implements CommandLineRunner {
//
//    @Autowired
//    SecKillProductConfig secKillProductConfig;
//
//    @Autowired
//    SecKillProductService productService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String prodId = "pid_0001";
//
//        System.out.println(productService.queryProdById(prodId));
//        SecKillProductDO secKillProductDO = new SecKillProductDO();
//        secKillProductDO.setProdName("华为P200至尊无敌版5G手机")
//                .setProdId(prodId)
//                .setProdPrice(new BigDecimal("10001"));
//        productService.updateProdInfo(secKillProductDO);
//        System.out.println(productService.queryProdById(prodId));
//    }
//}
