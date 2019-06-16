package org.apache.rocketmq.gateway.common.manager;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.request.QueryOrderRequest;
import org.apache.rocketmq.common.response.QueryOrderResponse;
import org.apache.rocketmq.gateway.common.dto.CodeMsg;
import org.apache.rocketmq.gateway.common.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/16 11:08
 * @className OrderQueryManager
 * @desc 订单查询防腐层
 */
@Service
public class OrderQueryManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderQueryManager.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("${seckill.order.query.url}")
    private String queryUrl;

    /**
     * 远程订单查询代理
     * @param queryOrderRequest
     * @param sessionId
     * @return
     */
    public Result<QueryOrderResponse> queryOrder(QueryOrderRequest queryOrderRequest, String sessionId) {

        // 请求参数
        String userPhoneNum = queryOrderRequest.getUserPhoneNum();
        String prodId = queryOrderRequest.getProdId();
        // 1. 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 2. 设置请求参数
        MultiValueMap<String, String> requestParam= new LinkedMultiValueMap<String, String>();
        requestParam.add("userPhoneNum", userPhoneNum);
        requestParam.add("prodId", prodId);
        LOGGER.info("请求远端查单地址:{}, 下单入参:{}", queryUrl, requestParam.toString());
        // 3. 请求开始
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(requestParam, headers);
        ResponseEntity<String> queryResponse = restTemplate.exchange(
                queryUrl, HttpMethod.POST, requestEntity, String.class);
        // 4. 返回参校验
        if (queryResponse == null) {
            LOGGER.info("[queryOrder]-当前订单查询返回为空,sessionId={}", sessionId);
            return Result.error(CodeMsg.SECKILL_ORDER_NOT_EXIST);
        }
        // 5. 解析返回参
        String queryOrderBody = queryResponse.getBody();
        LOGGER.error("[queryOrder]-调用内部订单查询接口出参:[{}],sessionId={}", queryOrderBody, sessionId);
        if (StringUtils.isBlank(queryOrderBody)) {
            LOGGER.info("[queryOrder]-当前订单查询返回为空,sessionId={}", sessionId);
            return Result.error(CodeMsg.SECKILL_ORDER_NOT_EXIST);
        }
        // 6. 参数反序列化
        ResultData resultData = JSON.parseObject(queryOrderBody, ResultData.class);

        if (resultData.getCode().equals(CodeMsg.BIZ_ERROR.getCode())) {
            LOGGER.error("[queryOrder]-当前订单查询失败.出参:[{}],sessionId={}", JSON.toJSONString(resultData), sessionId);
            return Result.error(CodeMsg.SECKILL_ORDER_NOT_EXIST);
        }
        if (!resultData.getCode().equals(CodeMsg.SUCCESS.getCode())) {
            LOGGER.error("[queryOrder]-当前订单查询失败.出参:[{}],sessionId={}", JSON.toJSONString(resultData), sessionId);
            return Result.error(CodeMsg.BIZ_ERROR);
        }
        // 查询成功
        if (resultData.getCode().equals(CodeMsg.SUCCESS.getCode())) {
            QueryOrderResponse queryOrderResponse = new QueryOrderResponse();
            BeanUtils.copyProperties(resultData.getData(), queryOrderResponse);
            Result<QueryOrderResponse> successResult =
                    new Result<>(CodeMsg.SUCCESS.getCode(), CodeMsg.SUCCESS.getMsg(), queryOrderResponse);
            LOGGER.info("[queryOrder]-当前订单查询成功.出参:[{}],sessionId={}", JSON.toJSONString(successResult), sessionId);
            return successResult;
        }
        return Result.error(CodeMsg.SECKILL_ORDER_NOT_EXIST);
    }

}

class ResultData {

    private String code;

    private String msg;

    private Data data;

    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
}

class Data {
    private String orderId;

    private int orderStatus;

    private String userPhoneNo;

    private String prodId;

    private String prodName;

    private String chargeMoney;

    private String chargeTime;

    private String finishTime;

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }
    public String getOrderId(){
        return this.orderId;
    }
    public void setOrderStatus(int orderStatus){
        this.orderStatus = orderStatus;
    }
    public int getOrderStatus(){
        return this.orderStatus;
    }
    public void setUserPhoneNo(String userPhoneNo){
        this.userPhoneNo = userPhoneNo;
    }
    public String getUserPhoneNo(){
        return this.userPhoneNo;
    }
    public void setProdId(String prodId){
        this.prodId = prodId;
    }
    public String getProdId(){
        return this.prodId;
    }
    public void setProdName(String prodName){
        this.prodName = prodName;
    }
    public String getProdName(){
        return this.prodName;
    }
    public void setChargeMoney(String chargeMoney){
        this.chargeMoney = chargeMoney;
    }
    public String getChargeMoney(){
        return this.chargeMoney;
    }
    public void setChargeTime(String chargeTime){
        this.chargeTime = chargeTime;
    }
    public String getChargeTime(){
        return this.chargeTime;
    }
    public void setFinishTime(String finishTime){
        this.finishTime = finishTime;
    }
    public String getFinishTime(){
        return this.finishTime;
    }

}
