# seckill-rocketmq[基于RocketMQ-电商高并发场景实战]

> 场景为：示例项目以用户访问秒杀网关进行秒杀订单下单，平台通过RocketMQ对秒杀流量进行削峰填谷。用户通过主动查询订单
获取下单结果的完整业务流程，加深对RocketMQ的理解，并学会如何在实战场景下使用RocketMQ。

> 项目基本按照实际秒杀场景进行设计与编码，提供了充分的预校验逻辑，可以作为业务中开发的参考demo。

## 主要技术

    消息队列（RocketMQ）: 作用，长流程异步化，提升吞吐量，削峰填谷
        |-普通消息的发布及订阅
    SpringBoot
        |-配置资源预加载
    RestTemplate
        |-application/x-www-form-urlencoded格式数据的发送
    H2
        |-嵌入式数据库

## 思路

业务流程图如下

![项目流程图](./pic/seckill.png)


## 模块描述

| 模块 | 说明 |
|  :------ |  :------ |
|  seckill-rocketmq  |  父工程  |
|  seckill-gateway-service  |  秒杀收单网关，提供秒杀下单入口，提供用户主动查单接口   |
|  seckill-order-service  |  秒杀订单平台，通过整合RocketMQ，消费秒杀下单消息；提供内部订单查询实现   |
|  seckill-message-protocol  |  消息协议封装  |

## 业务描述
1. 用户访问秒杀网关seckill-gateway-service，对感兴趣的产品发起秒杀操作。
2. 网关对秒杀订单进行充分的预校验之后，将秒杀下单消息投递到RocketMQ中，同步给用户返回排队中
3. 秒杀订单平台seckill-order-service订阅秒杀下单消息，对消息进行幂等处理，并对商品库存进行真实校验后，进行真实下单操作
4. 用户通过秒杀网关seckill-gateway-service提供的查单进行对自己下的秒杀订单进行查询。

真实场景下，在下单前有绑定账号、绑定收货地址等操作；下单完成后还有支付、物流等操作。

本demo的重点在于如何在秒杀核心场景使用RocketMQ进行业务异步化及订单的压单操作，因此对于细节不予实现，敬请谅解。

> 注意：商品信息直接在初始化的时候记载到seckill-gateway-service，进行前置库存校验。此处依据缓存已经做了一次流量的过滤。

> 订单真实下单的时候，进行库存真实校验，此时请求量已经不大了，从而做到对核心资源（DB）的保护。





