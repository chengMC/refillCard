package com.mc.refillCard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.mc.refillCard.common.Enum.GoodsRelateTypeEnum;
import com.mc.refillCard.common.Enum.PlatformEnum;
import com.mc.refillCard.common.Enum.TransactionStateEnum;
import com.mc.refillCard.config.supplier.JinglanApiProperties;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.*;
import com.mc.refillCard.service.*;
import com.mc.refillCard.util.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * @Author: MC
 * @Description: FinanceRecord业务层接口实现类
 * @Date 2021-5-3 13:54:54
 *****/
@Slf4j
@Service
public class OrderPushJingLanServiceImpl implements OrderPushJingLanService {

    @Lazy
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private OriginalOrderService originalOrderService;
    @Autowired
    private GoodsRelateFuluService goodsRelateFuluService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private BlacklistService blacklistService;
    @Autowired
    private GameServerService gameServerService;

    /**
     *  净蓝下单
     *
     * @param transactionDto
     * @param userRelate
     * @return
     */
    @Override
    public Map jinglanPlaceOrder(TransactionDto transactionDto, UserRelate userRelate) {
        Map resultOrderMap = new HashMap();
        Long userId = userRelate.getUserId();

        Integer platform = PlatformEnum.JINGLAN.getCode();
        //订单保存添加人
        String tid = transactionDto.getTid();
        Transaction transaction = transactionService.findById(Long.valueOf(tid));
        //订单
        List<OriginalOrderDto> orders = transactionDto.getOrders();
        for (OriginalOrderDto orderDto : orders) {
            //订单的宝贝id
            Long numIid = orderDto.getNumIid();
            GoodsRelateFulu goodsRelateFulu = goodsRelateFuluService.findByGoodId(numIid, userId);
            if(goodsRelateFulu == null){
                resultOrderMap.put("fail", "订单号：" + tid+",没有找到对应的宝贝Id："+numIid);
                return resultOrderMap;
            }

            Integer type = goodsRelateFulu.getType();
            //类型是DNF
            if (type.equals(GoodsRelateTypeEnum.DNF.getCode())) {
                //判断余额
                Map judgeMap = originalOrderService.judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                //DNF下单
                Map dnfOrderPushMap = jinglandnfOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(dnfOrderPushMap.get("success") != null){
                    //更新余额
                    originalOrderService.updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return dnfOrderPushMap;
            }else  if (type.equals(GoodsRelateTypeEnum.LOL.getCode())) {
                //类型是LOL
                //判断余额
                Map judgeMap = originalOrderService.judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                //LOL下单
                Map dnfOrderPushMap = jinglanlolOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(dnfOrderPushMap.get("success") != null){
                    //更新余额
                    originalOrderService.updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return dnfOrderPushMap;
            }
        }
        resultOrderMap.put("fail", "订单号：" + tid);
        return resultOrderMap;
    }

    /**
     * 净蓝DNF下单
     *
     * @param transaction
     * @param transactionDto
     * @param userRelate
     * @param tid
     * @param originalOrderDto
     * @param goodsRelateFulu
     * @param platform
     * @return
     */
    private Map jinglandnfOrderPush(Transaction transaction, TransactionDto transactionDto, UserRelate userRelate,
                                    String tid, OriginalOrderDto originalOrderDto, GoodsRelateFulu goodsRelateFulu, Integer platform) {
        Integer type = goodsRelateFulu.getType();
        Long userId = userRelate.getUserId();
        String originalTid = tid;

        //根据类型查询所对应商品
        List<Goods> goods = goodsService.findListByTypeAndPlatform(platform, type);

        //充值账号 针对DNF 备注中可能有两个账号
        String receiverAddress = transactionDto.getReceiverAddress();
        if(receiverAddress.indexOf("账号")>-1){
            receiverAddress = receiverAddress.substring(receiverAddress.indexOf("账号"));
        }
        String chargeAccount = AccountUtils.findNumber(receiverAddress);

        //更新DNF账号信息
        Long orderId = originalOrderDto.getId();
        OriginalOrder originalOrder = originalOrderService.findById(orderId);
        originalOrder.setCreateEmp(String.valueOf(userId));
        originalOrder.setChargeAccount(chargeAccount);
        originalOrder.setSupplier(PlatformEnum.JINGLAN.getName());
        originalOrderService.update(originalOrder);

        Blacklist blacklist = new Blacklist();
        blacklist.setAccount(chargeAccount);
        blacklist.setUserId(userRelate.getUserId());
        List<Blacklist> listByAccount = blacklistService.findListByAccount(blacklist);
        if(CollUtil.isNotEmpty(listByAccount)){
            Map resultOrderMap = new HashMap();
            resultOrderMap.put("fail", "订单号：" + tid+",黑名单账号："+chargeAccount);
            return resultOrderMap;
        }
        //面值
        Integer nominal = Integer.valueOf(goodsRelateFulu.getNominal());
        //数量
        Integer num = originalOrderDto.getNum().intValue();
        //QB购买数等于面值乘数量
        Integer buyNum = num * nominal;
        Map pushResultMap = jinglanOrderPushAndQueryResult(originalOrderDto,transaction, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum, null);
        return pushResultMap;
    }

    /**
     * 净蓝LOL下单
     *
     * @param transaction
     * @param transactionDto
     * @param userRelate
     * @param tid
     * @param originalOrderDto
     * @param goodsRelateFulu
     * @param platform
     * @return
     */
    private Map jinglanlolOrderPush(Transaction transaction, TransactionDto transactionDto, UserRelate userRelate,
                                    String tid, OriginalOrderDto originalOrderDto, GoodsRelateFulu goodsRelateFulu, Integer platform) {
        Integer type = goodsRelateFulu.getType();
        Long userId = userRelate.getUserId();
        String originalTid = tid;

        //根据类型查询所对应商品
        List<Goods> goods = goodsService.findListByTypeAndPlatform(platform, type);

        //充值账号 针对LOL 备注中可能有两个账号
        String receiverAddress = transactionDto.getReceiverAddress();
        if(receiverAddress.indexOf("账号")>-1){
            receiverAddress = receiverAddress.substring(receiverAddress.indexOf("账号"));
        }
        String chargeAccount = AccountUtils.findNumber(receiverAddress);

        //获取到LOL所有区信息
        List<GameServer> gameServers = gameServerService.findListByGoodType(GoodsRelateTypeEnum.LOL.getCode().longValue());
        //游戏区域
        String gameServerName = transactionDto.getReceiverAddress();
        //默认全国
        GameServer matchingGameServer = null;
        for (GameServer gameServer : gameServers) {
            //匹配游戏区域
            String areaName = gameServer.getAreaName();
            if (gameServerName.indexOf(areaName) > -1) {
                matchingGameServer = gameServer;
            }
        }
        if(matchingGameServer == null){
            Map resultOrderMap = new HashMap();
            resultOrderMap.put("fail", "订单号：" + tid+",区域信息未匹配："+gameServerName);
            return resultOrderMap;
        }

        //更新LOL账号信息
        Long orderId = originalOrderDto.getId();
        OriginalOrder originalOrder = originalOrderService.findById(orderId);
        originalOrder.setCreateEmp(String.valueOf(userId));
        originalOrder.setChargeAccount(chargeAccount);
        originalOrder.setGameAreaName(matchingGameServer.getAreaName());
        originalOrder.setSupplier(PlatformEnum.JINGLAN.getName());
        originalOrderService.update(originalOrder);

        Blacklist blacklist = new Blacklist();
        blacklist.setAccount(chargeAccount);
        blacklist.setUserId(userRelate.getUserId());
        List<Blacklist> listByAccount = blacklistService.findListByAccount(blacklist);
        if(CollUtil.isNotEmpty(listByAccount)){
            Map resultOrderMap = new HashMap();
            resultOrderMap.put("fail", "订单号：" + tid+",黑名单账号："+chargeAccount);
            return resultOrderMap;
        }
        //面值
        Integer nominal = Integer.valueOf(goodsRelateFulu.getNominal());
        //数量
        Integer num = originalOrderDto.getNum().intValue();
        //QB购买数等于面值乘数量
        Integer buyNum = num * nominal;
        Map pushResultMap = jinglanOrderPushAndQueryResult(originalOrderDto,transaction, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,matchingGameServer);
        return pushResultMap;
    }

    /**
     * 净蓝充值下单查询
     *
     *
     * @param originalOrderDto
     * @param transaction
     * @param tid
     * @param userRelate
     * @param goods
     * @param chargeAccount
     * @param buyNum
     * @param matchingGameServer
     * @return
     */
    private Map jinglanOrderPushAndQueryResult(OriginalOrderDto originalOrderDto, Transaction transaction, String tid, String originalTid, UserRelate userRelate, Goods goods,
                                               String chargeAccount, Integer buyNum, GameServer matchingGameServer) {
        String accessToken = userRelate.getAccessToken();
        if(matchingGameServer == null){
            log.info("净蓝DNF下单后查询返回，游戏区："+matchingGameServer.getAreaName());
        }else{
            log.info("净蓝LOL下单后查询返回，游戏区："+matchingGameServer.getAreaName());
        }

        Map dnfPushResultMap = jinglandnfPush(tid, buyNum, chargeAccount, goods,matchingGameServer);
        //下单失败后直接返回
        if(dnfPushResultMap.get("fail") != null){
            return dnfPushResultMap;
        }
        //更新状态
        OriginalOrder originalOrder = originalOrderService.findById(originalOrderDto.getId());

        //下单成功后去查询
        Map queryOrderResultMap = jinglanQueryOrderResult(tid);
        //查询充值失败后直接返回
        if(queryOrderResultMap.get("fail") != null){
            String fail = String.valueOf(queryOrderResultMap.get("fail"));
            log.error("净蓝下单查询后失败："+fail);
            originalOrder.setOrderStatus(TransactionStateEnum.FAIL.getCode());
            originalOrder.setFailReason(fail);
            originalOrderService.update(originalOrder);
            return queryOrderResultMap;
        }
        //福询充值成功后
        String orderDtoStr = String.valueOf(queryOrderResultMap.get("success"));

        log.error("净蓝下单查询后成功："+orderDtoStr);
        Map orderDtoStrResult = JSON.parseObject(orderDtoStr);
        //获取出data中的数据
        String orderDtoStrResultData = String.valueOf(orderDtoStrResult.get("data"));
        //data转map 获取详细数据
        Map resultDataMap = JSON.parseObject(orderDtoStrResultData);
        originalOrder.setOrderStatus(2);
        originalOrder.setRemark(orderDtoStr);
        originalOrder.setExternalOrderId(String.valueOf(resultDataMap.get("orderNo")));
        transactionService.update(transaction);
        //更新发货状态
        try {
            Boolean aBoolean = originalOrderService.changeTBOrderStatus(originalTid, accessToken);
            if (!aBoolean) {
                log.error("阿奇索自动发货失败");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("阿奇索自动发货失败："+ e);
        } catch (NoSuchAlgorithmException e) {
            log.error("阿奇索自动发货失败："+ e);
        }
        Map resultOrderMap = new HashMap();
        resultOrderMap.put("success", "订单号：" + tid);
        return resultOrderMap;
    }

    /**
     *净蓝DNF充值推送
     *
     * @param tid
     * @param buyNum
     * @param chargeAccount
     * @param good
     * @param matchingGameServer
     * @return
     */
    private Map jinglandnfPush(String tid, Integer buyNum, String chargeAccount, Goods good, GameServer matchingGameServer){
        Map dnfPushResultMap = new HashMap();
        Map resultMap = jinglandnfOrderPushApi(tid, buyNum, chargeAccount, good,matchingGameServer);
        if(matchingGameServer != null){
            log.info("净蓝下单LOL" + tid  + "-resultMap-" + resultMap);
        }else{
            log.info("净蓝下单DNF" + tid  + "-resultMap-" + resultMap);
        }
        //0 订单接收，其余拒收
        String resultCode = String.valueOf(resultMap.get("resultCode"));
        if("0".equals(resultCode)){
            dnfPushResultMap.put("success", "订单号：" + tid);
            return dnfPushResultMap;
        }else{
            String failStr = "平台下单失败。订单号：" + tid + "," + resultMap.get("resultMsg");
            dnfPushResultMap.put("fail", failStr);
            return dnfPushResultMap;
        }
    }

    /**
     * DNF 推送净蓝
     *
     * @param tid
     * @param buyNum
     * @param chargeAccount
     * @param good
     * @param matchingGameServer
     * @return
     */
    private Map jinglandnfOrderPushApi(String tid, Integer buyNum, String chargeAccount, Goods good, GameServer matchingGameServer) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("action","placeOrder");
        dataMap.put("requestTime", DateUtil.now());
        dataMap.put("merAccount", JinglanApiProperties.getMerAccount());
        dataMap.put("businessType", "13");
        dataMap.put("merOrderNo", tid);
        dataMap.put("rechargeAccount", chargeAccount);
        dataMap.put("productId", good.getProductId());
        dataMap.put("rechargeValue", buyNum);
        //LOL需要传
        if(matchingGameServer != null){
            dataMap.put("gameAreaName", matchingGameServer.getAreaNameOperator());
        }
        String shuShanSign = null;
        try {
            AccountUtils.getjinglanSign(dataMap, JinglanApiProperties.getAppSecret());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dataMap.put("sign",shuShanSign);

        System.out.println("净蓝DNF下单请求参数："+dataMap);

        //接口调用
        String result = HttpRequest.post("http://123.56.243.180:26000/mch/api/v2/form")
                .form(dataMap)
                .execute()
                .body();
        System.out.println(result);
        System.out.println("净蓝DNF下单接口返回："+result);
        Map resultMap = JSON.parseObject(result);
        return resultMap;
    }

    /**
     * 查询订单结果
     *
     * @param tid
     * @return
     */
    private Map jinglanQueryOrderResult(String tid) {
        Map resultOrderMap = new HashMap();
        //睡二秒后查询结果
        try {
            Thread.currentThread().sleep(2000);
        } catch (Exception e) {
            log.error("线程异常");
        }
        for (int i = 0; i < 500; i++) {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("action","queryOrder");
            dataMap.put("requestTime", DateUtil.now());
            dataMap.put("merAccount", JinglanApiProperties.getMerAccount());
            dataMap.put("merOrderNo", tid);
            String sign = null;
            try {
                sign =AccountUtils.getjinglanSign(dataMap, JinglanApiProperties.getAppSecret());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            dataMap.put("sign",sign);

            //接口调用
            String result = HttpRequest.post("http://123.56.243.180:26000/mch/api/v2/form")
                    .form(dataMap)
                    .execute()
                    .body();
            Map resultMap = JSON.parseObject(result);
            String data = String.valueOf(resultMap.get("data"));
            Map resultDataMap = JSON.parseObject(data);
            String orderState = String.valueOf(resultDataMap.get("orderState"));
            //24 充值全部成功
            if("24".equals(orderState)){
                resultOrderMap.put("success", result);
                return resultOrderMap;
            }else if("23".equals(orderState) || "-1".equals(orderState)){
                String orderStateDesc = String.valueOf(resultDataMap.get("orderStateDesc"));
                //23 充值全部失败,-1 订单已取消
                String failStr = "平台充值失败。订单号：" + tid + ",state-" + orderStateDesc;
                resultOrderMap.put("fail", failStr);
                return resultOrderMap;
            } else {
                //睡一秒后查询结果，因为查询下单有延迟
                try {
                    Thread.currentThread().sleep(1000);
                } catch (Exception e) {
                    log.error("jinglan线程异常");
                }
            }
        }
        String failStr = "平台查询充值失败。订单号：" + tid;
        resultOrderMap.put("fail", failStr);
        return resultOrderMap;
    }


}
