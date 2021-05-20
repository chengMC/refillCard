package com.mc.refillCard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.mc.refillCard.common.Enum.GoodsRelateTypeEnum;
import com.mc.refillCard.common.Enum.PlatformEnum;
import com.mc.refillCard.config.supplier.ShuShanApiProperties;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.*;
import com.mc.refillCard.service.*;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.util.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
public class OrderPushShuShanServiceImpl implements OrderPushShuShanService {

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

    /**
     *  蜀山下单
     * @param transactionDto
     * @param userRelate
     * @return
     */
    @Override
    public Map shushanPlaceOrder(TransactionDto transactionDto, UserRelate userRelate) {
        Map resultOrderMap = new HashMap();
        Long userId = userRelate.getUserId();

        //充值账号
        String receiverAddress = transactionDto.getReceiverAddress();
        String chargeAccount = AccountUtils.findNumber(receiverAddress);

        Integer platform = PlatformEnum.SHUSHAN.getCode();
        //订单保存添加人
        String tid = transactionDto.getTid();
        Transaction transaction = transactionService.findById(Long.valueOf(tid));
        //订单
        List<OriginalOrderDto> orders = transactionDto.getOrders();
        for (OriginalOrderDto orderDto : orders) {
            Long orderId = orderDto.getId();
            OriginalOrder originalOrder = originalOrderService.findById(orderId);
            originalOrder.setCreateEmp(String.valueOf(userId));
            originalOrder.setChargeAccount(chargeAccount);
            originalOrder.setSupplier(PlatformEnum.SHUSHAN.getName());
            originalOrderService.update(originalOrder);

            //订单的宝贝id
            Long numIid = orderDto.getNumIid();
            GoodsRelateFulu  goodsRelateFulu = goodsRelateFuluService.findByGoodId(numIid, userId);
            if(goodsRelateFulu == null){
                resultOrderMap.put("fail", "订单号：" + tid+",没有找到对应的宝贝Id："+numIid);
                return resultOrderMap;
            }

            Integer type = goodsRelateFulu.getType();
            //类型是QB 下单
            if (type.equals(GoodsRelateTypeEnum.QB.getCode()) || type.equals(GoodsRelateTypeEnum.MINI_QB.getCode())) {
                //面值
                Integer nominal = Integer.valueOf(goodsRelateFulu.getNominal());
                //数量
                Integer num = orderDto.getNum().intValue();
                //QB购买数等于面值乘数量
                Integer buyNum = num * nominal;
                //如果金额小于30，直接走去蜀山全国
                if(buyNum < 30) {
                    //走小额
                    type = 7;
                }

                //判断余额
                Map judgeMap = originalOrderService.judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                //QB下单
                Map qbOrderPushMap = shuShanQbOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(qbOrderPushMap.get("success") != null){
                    //更新余额
                    originalOrderService.updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return qbOrderPushMap;
            }
        }
        resultOrderMap.put("fail", "订单号：" + tid);
        return resultOrderMap;
    }

    /**
     * 蜀山QB下单
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
    private Map shuShanQbOrderPush(Transaction transaction, TransactionDto transactionDto, UserRelate userRelate,
                                   String tid, OriginalOrderDto originalOrderDto, GoodsRelateFulu goodsRelateFulu, Integer platform) {
        Integer type = goodsRelateFulu.getType();

        String originalTid = tid;
        //地区
        String buyerArea = transactionDto.getBuyerArea();
        //根据类型查询所对应商品
        List<Goods> goods = goodsService.findListByTypeAndPlatform(platform, type);

        //充值账号
        String receiverAddress = transactionDto.getReceiverAddress();
        String chargeAccount = AccountUtils.findNumber(receiverAddress);

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
        //如果金额小于30，直接走去全国
        if(buyNum < 30){
            //QB小额
            Map qbNationwidePushResultMap = shuShanQbOrderPushAndQueryResult(originalOrderDto,transaction, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
            return qbNationwidePushResultMap;
        }
        //默认全国
        Goods matchingGoods = null;
        if(StringUtils.isNotBlank(buyerArea)){
            for (Goods good : goods) {
                //地区匹配
                String area = good.getArea();
                if (buyerArea.indexOf(area) > -1) {
                    matchingGoods = good;
                }
            }
        }
        //地区匹配不成功后 走全国
        if(matchingGoods == null){
            Map qbNationwidePushResultMap = shuShanQbOrderPushAndQueryResult(originalOrderDto, transaction, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
            return qbNationwidePushResultMap;
        }else{
            //走地区
            Map qbWtoNationwidePushResultMap = shuShanQbOrderPushAndQueryResult(originalOrderDto, transaction, tid,originalTid,userRelate, matchingGoods, chargeAccount, buyNum,matchingGoods.getArea());
//            if(qbWtoNationwidePushResultMap.get("fail") != null){
//                tid = tid+"QB";
//                //失败后再次走全国
//                Map qbNationwidePushResultMap = shuShanQbOrderPushAndQueryResult(originalOrderDto, transaction, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
//                return qbNationwidePushResultMap;
//            }
            return qbWtoNationwidePushResultMap;
        }
    }


    /**
     * QB下单后查询结果并发返回
     *
     *
     * @param originalOrderDto
     * @param transaction
     * @param tid
     * @param userRelate
     * @param goods
     * @param chargeAccount
     * @param buyNum
     * @param area
     * @return
     */
    private Map shuShanQbOrderPushAndQueryResult(OriginalOrderDto originalOrderDto, Transaction transaction, String tid, String originalTid, UserRelate userRelate, Goods goods,
                                                 String chargeAccount, Integer buyNum, String area) {
        String accessToken = userRelate.getAccessToken();
        log.info("蜀山qb下单后查询返回，地区："+goods.getArea());

        Map qbNationwidePushResultMap = shushanQbNationwidePush(tid, buyNum, chargeAccount, goods,area);
        //下单失败后直接返回
        if(qbNationwidePushResultMap.get("fail") != null){
            return qbNationwidePushResultMap;
        }
        //更新状态
        OriginalOrder originalOrder = originalOrderService.findById(originalOrderDto.getId());

        //下单成功后去查询
        Map queryOrderResultMap = shushanQueryOrderResult(tid);
        //查询充值失败后直接返回
        if(queryOrderResultMap.get("fail") != null){
            String fail = String.valueOf(queryOrderResultMap.get("fail"));
            log.error("蜀山下单查询后失败："+fail);
//            originalOrder.setOrderStatus(TransactionStateEnum.FAIL.getCode());
            originalOrder.setFailReason(fail);
            originalOrderService.update(originalOrder);
            return queryOrderResultMap;
        }
        try{
            //福禄查询充值成功后 转化对象
            String orderDtoStr = String.valueOf(queryOrderResultMap.get("success"));
            log.error("蜀山下单查询后成功："+orderDtoStr);
            Map orderDtoStrResult = JSON.parseObject(orderDtoStr);
            originalOrder.setOrderStatus(2);
            originalOrder.setRemark(orderDtoStr);
            originalOrder.setExternalOrderId(String.valueOf(orderDtoStrResult.get("order-id")));
            originalOrderService.update(originalOrder);
        }catch (Exception e){
            log.error("净蓝下单查询后,保存订单信息失败");
        }
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
     * 直接推送蜀山
     *
     * @param tid
     * @param buyNum
     * @param chargeAccount
     * @param good
     * @return
     */
    private Map shushanQbNationwidePush(String tid, Integer buyNum, String chargeAccount, Goods good, String area){
        Map qbNationwidePushResultMap = new HashMap();
        Map resultMap = qbOrderPushShuShan(tid, buyNum, chargeAccount, good,area);
        System.out.println("蜀山下单"+area+"QB:" + tid  + "-resultMap-" + resultMap);
        //101交易成功  102充值中
        if ("102".equals(String.valueOf(resultMap.get("state")))
                ||"101".equals(String.valueOf(resultMap.get("state")))) {
            qbNationwidePushResultMap.put("success", "订单号：" + tid);
            return qbNationwidePushResultMap;
        }else{
            String failStr = "平台"+area+"QB下单失败。订单号：" + tid + "," + resultMap.get("state-info");
            qbNationwidePushResultMap.put("fail", failStr);
            return qbNationwidePushResultMap;
        }
    }

    /**
     * qb订单推送 蜀山
     *
     * @param tid
     * @param buyNum
     * @param chargeAccount
     * @param good
     * @param area
     * @return
     */
    private Map qbOrderPushShuShan(String tid,Integer buyNum, String chargeAccount, Goods good, String area) {

        HashMap<String, Object> dataMap = new HashMap<>();
        //商家编号
        dataMap.put("MerchantID", ShuShanApiProperties.getMerchantID());
        //商家订单编号（不允许重复）
        dataMap.put("MerchantOrderID", tid);
        //商品编号
        dataMap.put("ProductID", good.getProductId());
        //充值数量
        dataMap.put("BuyAmount", buyNum);
        //充值账户
        dataMap.put("TargetAccount", chargeAccount);
        String shuShanSign = null;
        try {
            String Sign = ShuShanApiProperties.getMerchantID()+tid+good.getProductId()+buyNum+chargeAccount+ShuShanApiProperties.getAppSecret();
            shuShanSign = AccountUtils.encryptMD5Str(Sign);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(StringUtils.isNotBlank(area)){
            dataMap.put("CustomerRegion", area);
        }
        dataMap.put("Sign",shuShanSign);

        System.out.println("蜀山QB下单请求参数："+dataMap);
        //接口调用
        String result = HttpRequest.post("http://api.shushanzx.shucard.com/Api/Pay")
                .form(dataMap)
                .execute()
                .body();
        String json = XmlUtils.xml2json(result);
        System.out.println("蜀山QB下单接口返回："+json);
        Map resultMap = JSON.parseObject(json);
        return resultMap;
    }

    /**
     * 查询订单结果
     *
     * @param tid
     * @return
     */
    private Map shushanQueryOrderResult(String tid) {
        Map resultOrderMap = new HashMap();
        //睡二秒后查询结果
        try {
            Thread.currentThread().sleep(2000);
        } catch (Exception e) {
            log.error("线程异常");
        }
        for (int i = 0; i < 500; i++) {
            HashMap<String, Object> dataMap = new HashMap<>();
            //商家编号
            dataMap.put("MerchantID",ShuShanApiProperties.getMerchantID());
            //商家订单编号（不允许重复）
            dataMap.put("MerchantOrderID", tid);
            String shuShanSign = null;
            try {
                String Sign = ShuShanApiProperties.getMerchantID()+tid;
                shuShanSign = AccountUtils.encryptMD5Str(Sign);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            dataMap.put("Sign",shuShanSign);

            //接口调用
            String json = HttpRequest.post("http://api.shushanzx.shucard.com/Api/QueryOrder")
                    .form(dataMap)
                    .execute()
                    .body();
            String result = XmlUtils.xml2json(json);

            Map resultMap = JSON.parseObject(result);
            String state = String.valueOf(resultMap.get("state"));
            //102	充值中
            if ("102".equals(state)) {
                //睡一秒后查询结果，因为查询下单有延迟
                try {
                    Thread.currentThread().sleep(1000);
                } catch (Exception e) {
                    log.error("线程异常");
                }
                continue;
                //101	交易成功
            }else if("101".equals(state)){
                resultOrderMap.put("success", result);
                return resultOrderMap;
            }else {
                String failStr = "平台充值失败。订单号：" + tid + ",state-" + state;
                resultOrderMap.put("fail", failStr);
                return resultOrderMap;
            }
        }
        String failStr = "平台查询充值失败。订单号：" + tid;
        resultOrderMap.put("fail", failStr);
        return resultOrderMap;
    }


}
