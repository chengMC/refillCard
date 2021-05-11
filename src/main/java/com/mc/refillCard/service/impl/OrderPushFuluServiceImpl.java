package com.mc.refillCard.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.mc.refillCard.common.Enum.*;
import com.mc.refillCard.config.supplier.FuliProperties;
import com.mc.refillCard.dto.FuluOrderDto;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.*;
import com.mc.refillCard.service.*;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.util.IpUtil;
import fulu.sup.open.api.core.MethodConst;
import fulu.sup.open.api.model.InputDirectOrderDto;
import fulu.sup.open.api.model.InputOrderGetDto;
import fulu.sup.open.api.sdk.DefaultOpenApiClient;
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
public class OrderPushFuluServiceImpl implements OrderPushFuluService {

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
    private SysDictService sysDictService;
    @Autowired
    private NationwideIpService nationwideIpService;
    @Autowired
    private BlacklistService blacklistService;
    @Autowired
    private PlatformKeyService platformKeyService;

    @Override
    public Map fuliPlaceOrder(TransactionDto transactionDto, UserRelate userRelate) {
        Map resultOrderMap = new HashMap();
        Long userId = userRelate.getUserId();
        Integer platform = PlatformEnum.FULU.getCode();
        //订单保存添加人
        String tid = transactionDto.getTid();
        Transaction transaction = transactionService.findById(Long.valueOf(tid));

        //充值账号
        String receiverAddress = transactionDto.getReceiverAddress();
        String chargeAccount = AccountUtils.findNumber(receiverAddress);

        //订单
        List<OriginalOrderDto> orders = transactionDto.getOrders();
        for (OriginalOrderDto orderDto : orders) {
            Long orderId = orderDto.getId();
            //保存订单记录
            OriginalOrder originalOrder = originalOrderService.findById(orderId);
            originalOrder.setCreateEmp(String.valueOf(userId));
            originalOrder.setChargeAccount(chargeAccount);
            originalOrder.setSupplier(PlatformEnum.FULU.getName());
            originalOrderService.update(originalOrder);

            //订单的宝贝id
            Long numIid = orderDto.getNumIid();
            GoodsRelateFulu goodsRelateFulu = goodsRelateFuluService.findByGoodId(numIid, userId);
            if(goodsRelateFulu == null){
                resultOrderMap.put("fail", "订单号：" + tid+",没有找到对应的宝贝Id："+numIid);
                return resultOrderMap;
            }
            Integer type = goodsRelateFulu.getType();
            //类型是QB 下单
            if (type.equals(GoodsRelateTypeEnum.QB.getCode())) {
                //判断余额
                Map judgeMap = originalOrderService.judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                Map qbOrderPushMap = fuluQbOrderPush(transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(qbOrderPushMap.get("success") != null){
                    //更新余额
                    originalOrderService.updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return qbOrderPushMap;
            }
            //下单是陌陌
            if (type.equals(GoodsRelateTypeEnum.MOMO.getCode())) {
                //判断余额
                Map judgeMap = originalOrderService.judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                Map qbOrderPushMap = fuluCommonOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(qbOrderPushMap.get("success") != null){
                    //更新余额
                    originalOrderService.updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return qbOrderPushMap;
            }
            //探探下单
            if (type.equals(GoodsRelateTypeEnum.TANTAN.getCode())) {
                //判断余额
                Map judgeMap = originalOrderService.judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                Map qbOrderPushMap = fuluCommonOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(qbOrderPushMap.get("success") != null){
                    //更新余额
                    originalOrderService.updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return qbOrderPushMap;
            }
        }
        return resultOrderMap;
    }


    /**
     * QB订单推送
     *
     * @param transactionDto
     * @param userRelate
     * @param tid
     * @param originalOrderDto
     * @param goodsRelateFulu
     * @param platform
     * @return
     */
    private Map fuluQbOrderPush(TransactionDto transactionDto, UserRelate userRelate,
                                String tid, OriginalOrderDto originalOrderDto, GoodsRelateFulu goodsRelateFulu, Integer platform) {
        Integer type = goodsRelateFulu.getType();

        String originalTid = tid;
        //地区
        String buyerArea = transactionDto.getBuyerArea();
        //根据类型查询所对应福禄商品
        List<Goods> goods = goodsService.findListByTypeAndPlatform(platform, type);
        //查询IP段
        List<NationwideIp> nationwideIpList = nationwideIpService.findAll();

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
            Map qbNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto,tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
            return qbNationwidePushResultMap;
        }

        //默认全国
        Goods matchingGoods = null;
        for (Goods good : goods) {
            //地区匹配
            String area = good.getArea();
            if (buyerArea.indexOf(area) > -1) {
                matchingGoods = good;
            }
        }
        //地区匹配不成功后
        if(matchingGoods == null){
            //模式一：走全国
            SysDict patternCode = sysDictService.findByCode(DictCodeEnum.PATTERN.getName());
            String patternValue = patternCode.getDataValue();
            if("一".equals(patternValue)){
                //走全国
                Map qbNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
                return qbNationwidePushResultMap;
            }else{
                //模式二：分配排行前6的地区拿到IP下单，失败走全国
                //查询排行靠前的地区
                SysDict rankingCode = sysDictService.findByCode(DictCodeEnum.RANKING.getName());
                String rankingValue = rankingCode.getDataValue();
                String[] rankingSplit = rankingValue.split(",");
                Integer random = IpUtil.createRandom(0, rankingSplit.length - 1);
                //靠前的地区来随机获取一个地区
                String randomArea = rankingSplit[random];
                nationwideIpList = nationwideIpService.findListByArea(randomArea);

                //获取到随机到的地区的真实IP
                Integer areaRandom = IpUtil.createRandom(0, nationwideIpList.size()-1);
                NationwideIp nationwideIp = nationwideIpList.get(areaRandom);
                log.info("无地区，随机匹配到的地区-"+randomArea+"，IP段-"+ JSON.toJSONString(nationwideIp));
                String startIp = nationwideIp.getStartIp();
                String endIp = nationwideIp.getEndIp();
                String area = nationwideIp.getArea();
                //排行靠前的地区的IP
                String areaRandomIp = IpUtil.getAreaRandomIp(startIp, endIp);
                matchingGoods = null;
                for (Goods good : goods) {
                    //地区匹配
                    if (area.indexOf(good.getArea()) > -1) {
                        matchingGoods = good;
                    }
                }
                //走河南或者山东的IP
                Map qbWtoNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto, tid,originalTid,userRelate,matchingGoods, chargeAccount, buyNum,areaRandomIp);
                if(qbWtoNationwidePushResultMap.get("fail") != null){
                    tid = tid+"QB";
                    //失败后再次走全国
                    Map qbThreeNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
                    return qbThreeNationwidePushResultMap;
                }
                return qbWtoNationwidePushResultMap;
            }
        }else{
            //匹配到的地区，拿到IP后下单
            String matchingGoodsArea = matchingGoods.getArea();
            //有问题的地区，匹配到问题地区的IP再下单，失败走全国
            nationwideIpList = nationwideIpService.findListByArea(matchingGoodsArea);
            //获取到问题地区的IP
            Integer areaRandom = IpUtil.createRandom(0, nationwideIpList.size()-1);
            NationwideIp nationwideIp = nationwideIpList.get(areaRandom);
            String startIp = nationwideIp.getStartIp();
            String endIp = nationwideIp.getEndIp();
            log.info("匹配到的地地区-"+matchingGoodsArea+"，IP段-"+ JSON.toJSONString(nationwideIp));
            String areaRandomIp = IpUtil.getAreaRandomIp(startIp, endIp);
            //有问题的地区加IP
            Map qbWtoNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto, tid,originalTid,userRelate,matchingGoods, chargeAccount, buyNum,areaRandomIp);
            if(qbWtoNationwidePushResultMap.get("fail") != null){
                tid = tid+"QB";
                //失败后再次走全国
                Map qbThreeNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto,  tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
                return qbThreeNationwidePushResultMap;
            }
            return qbWtoNationwidePushResultMap;
        }
    }

    /**
     * QB下单后查询结果并发返回
     *
     * @param originalOrderDto
     * @param tid
     * @param userRelate
     * @param goods
     * @param chargeAccount
     * @param buyNum
     * @param ip
     * @return
     */
    private Map fuluQbOrderPushAndQueryResult(OriginalOrderDto originalOrderDto, String tid, String originalTid, UserRelate userRelate, Goods goods,
                                              String chargeAccount, Integer buyNum, String ip) {
        PlatformKey platformKey = platformKeyService.findByAppType(PlatformEnum.FULU.getCode());
        String fuliAppKey = platformKey.getAppKey();
        String fuluSercret = platformKey.getAppSercret();
//        String fuluSercret = FuliProperties.getSysSecret();

        String accessToken = userRelate.getAccessToken();
        log.info("qb下单后查询返回，地区："+goods.getArea()+"-IP:"+ip);

        Map qbNationwidePushResultMap = fuliQbNationwidePush(fuliAppKey, fuluSercret, tid, buyNum, chargeAccount, goods,ip);
        //福禄下单失败后直接返回
        if(qbNationwidePushResultMap.get("fail") != null){
            return qbNationwidePushResultMap;
        }
        //更新状态
        OriginalOrder originalOrder = originalOrderService.findById(originalOrderDto.getId());
        //福禄下单成功后去查询
        Map queryOrderResultMap = fuliQueryOrderResult(fuliAppKey, fuluSercret, tid);
        //福禄查询充值失败后直接返回
        if(queryOrderResultMap.get("fail") != null){
            String fail = String.valueOf(queryOrderResultMap.get("fail"));
            log.error(fail);
            originalOrder.setOrderStatus(TransactionStateEnum.FAIL.getCode());
            originalOrder.setFailReason(fail);
            originalOrderService.update(originalOrder);
            return queryOrderResultMap;
        }
        //福禄查询充值成功后 转化对象
        String fuluOrderDtoStr = String.valueOf(queryOrderResultMap.get("success"));
        FuluOrderDto fuluOrderDto = JSON.parseObject(fuluOrderDtoStr, FuluOrderDto.class);
        originalOrder.setOrderStatus(TransactionStateEnum.SUCCEED.getCode());
        originalOrder.setRemark(fuluOrderDtoStr);
        originalOrder.setExternalOrderId(fuluOrderDto.getOrder_id());
        originalOrderService.update(originalOrder);
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
     * 直接推送福禄
     *
     * @param fuliAppKey
     * @param fuluSercret
     * @param tid
     * @param buyNum
     * @param chargeAccount
     * @param good
     * @return
     */
    private Map fuliQbNationwidePush(String fuliAppKey, String fuluSercret,
                                     String tid, Integer buyNum, String chargeAccount, Goods good, String ip){
        Map qbNationwidePushResultMap = new HashMap();
        String area = good.getArea();
        Map resultMap = qbOrderPushFulu(fuliAppKey, fuluSercret, tid, buyNum, chargeAccount, good,ip);
        System.out.println("福禄下单"+area+"QB:" + tid  + "-resultMap-" + resultMap);
        if (!"0".equals(String.valueOf(resultMap.get("code")))) {
            String failStr = "平台"+area+"QB下单失败。订单号：" + tid + "," + resultMap.get("message");
            qbNationwidePushResultMap.put("fail", failStr);
            return qbNationwidePushResultMap;
        }else{
            qbNationwidePushResultMap.put("success", "订单号：" + tid);
            return qbNationwidePushResultMap;
        }
    }

    /**
     * qb订单推送 福禄
     *
     * @param fuliAppKey
     * @param fuluSercret
     * @param tid
     * @param buyNum
     * @param chargeAccount
     * @param good
     * @param ip
     * @return
     */
    private Map qbOrderPushFulu(String fuliAppKey, String fuluSercret,
                                String tid,Integer buyNum, String chargeAccount, Goods good, String ip) {
        //通过福禄API下单
        DefaultOpenApiClient client =
                new DefaultOpenApiClient(FuliProperties.getUrl(), fuliAppKey, fuluSercret, MethodConst.OPEN_API_DIRECT_ORDER_ADD);
        InputDirectOrderDto dto = new InputDirectOrderDto();
        dto.setProductId(good.getProductId().intValue());
        dto.setCustomerOrderNo(tid);
        dto.setBuyNum(buyNum);
        dto.setChargeAccount(chargeAccount);
        dto.setChargeGameName(good.getProductName());
        if(!"".equals(ip)){
            dto.setChargeIp(ip);
        }
        client.setBizObject(dto);
        System.out.println("福禄QB下单请求："+JSON.toJSONString(dto));
        String result = client.excute();
        Map resultMap = JSON.parseObject(result);
        return resultMap;
    }

    /**
     * 查询订单结果
     *
     * @param fuliAppKey
     * @param fuluSercret
     * @param tid
     * @return
     */
    private Map fuliQueryOrderResult(String fuliAppKey, String fuluSercret, String tid) {
        Map resultOrderMap = new HashMap();
        //睡二秒后查询结果
        try {
            Thread.currentThread().sleep(2000);
        } catch (Exception e) {
            log.error("线程异常");
        }
        for (int i = 0; i < 500; i++) {
            DefaultOpenApiClient client =
                    new DefaultOpenApiClient(FuliProperties.getUrl(), fuliAppKey, fuluSercret, MethodConst.OPEN_API_ORDER_GET);
            InputOrderGetDto dto = new InputOrderGetDto();
            dto.setCustomerOrderNo(tid);
            client.setBizObject(dto);
            String result = client.excute();
            if (StringUtils.isBlank(result)) {
                return null;
            }
            Map resultMap = JSON.parseObject(result);
            if (!"0".equals(String.valueOf(resultMap.get("code")))) {
                log.info("福禄平台查询充值---"+result);
                String failStr = "平台查询充值失败,订单号：" + tid + "," + resultMap.get("message");
                log.info(failStr);
            }
            //解析结果
            String resultStr = String.valueOf(resultMap.get("result"));
            FuluOrderDto fuluOrderDto = JSON.parseObject(resultStr, FuluOrderDto.class);
            String order_state = fuluOrderDto.getOrder_state();
            if (order_state.equals(FuluOrderTypeEnum.SUCCESS.getCode())) {
                resultOrderMap.put("success", resultStr);
                return resultOrderMap;
            } else if (order_state.equals(FuluOrderTypeEnum.FAILED.getCode())) {
                String failStr = "平台充值失败。订单号：" + tid + "," + resultMap.get("message");
                resultOrderMap.put("fail", failStr);
                return resultOrderMap;
            }
            //睡一秒后查询结果，因为查询下单有延迟
            try {
                Thread.currentThread().sleep(1000);
            } catch (Exception e) {
                log.error("线程异常");
            }
        }
        String failStr = "平台查询充值失败。订单号：" + tid;
        resultOrderMap.put("fail", failStr);
        return resultOrderMap;
    }

    /**
     * 通用下单
     *
     * @param transaction
     * @param transactionDto
     * @param userRelate
     * @param tid
     * @param orderDto
     * @param goodsRelateFulu
     * @param platform
     * @return
     */
    private Map fuluCommonOrderPush(Transaction transaction, TransactionDto transactionDto, UserRelate userRelate,
                                    String tid, OriginalOrderDto orderDto, GoodsRelateFulu goodsRelateFulu, Integer platform) {
        Map resultOrderMap = new HashMap();
        String originalTid = tid;
        Integer type = goodsRelateFulu.getType();
        //金额
        String nominal = goodsRelateFulu.getNominal();
        //充值账号
        String receiverAddress = transactionDto.getReceiverAddress();
        String chargeAccount = AccountUtils.findNumber(receiverAddress);
        //数量
        Integer buyNum = orderDto.getNum().intValue();

        //根据类型查询所对应福禄商品
        Goods matchingGood = null;
        List<Goods> goods = goodsService.findListByTypeAndPlatform(platform,type);
        for (Goods good : goods) {
            //匹配值
            String area = good.getArea();
            if(nominal.equals(area)){
                matchingGood = good;
            }
        }
        if(matchingGood == null){
            resultOrderMap.put("fail", "订单号：" + tid+",未找到商品或者商品已下架");
            return resultOrderMap;
        }
        Map qbNationwidePushResultMap = commonOrderPushAndQueryResult(transaction, tid,originalTid,userRelate,matchingGood, chargeAccount, buyNum);
        return qbNationwidePushResultMap;
    }

    /**
     * 陌陌订单推送查询返回接口
     *
     * @param transaction
     * @param tid
     * @param originalTid
     * @param userRelate
     * @param goods
     * @param chargeAccount
     * @param buyNum
     * @return
     */
    private Map commonOrderPushAndQueryResult(Transaction transaction, String tid, String originalTid, UserRelate userRelate, Goods goods,
                                              String chargeAccount, Integer buyNum) {
        PlatformKey platformKey = platformKeyService.findByAppType(PlatformEnum.FULU.getCode());
        String fuliAppKey = platformKey.getAppKey();
        String fuluSercret = platformKey.getAppSercret();

        String accessToken = userRelate.getAccessToken();

        Map resultMap = momoOrderPushFulu(fuliAppKey, fuluSercret, tid, buyNum, chargeAccount, goods);
        if (!"0".equals(String.valueOf(resultMap.get("code")))) {
            String failStr = "平台下单失败。订单号：" + tid + "," + resultMap.get("message");
            resultMap.put("fail", failStr);
            return resultMap;
        }

        //福禄下单成功后去查询
        Map queryOrderResultMap = fuliQueryOrderResult(fuliAppKey, fuluSercret, tid);
        //福禄查询充值失败后直接返回
        if(queryOrderResultMap.get("fail") != null){
            String fail = String.valueOf(queryOrderResultMap.get("fail"));
            log.error(fail);
            transaction.setState(TransactionStateEnum.FAIL.getCode());
            transaction.setRemark(fail);
            transactionService.update(transaction);
            return queryOrderResultMap;
        }
        //福禄查询充值成功后 转化对象
        String fuluOrderDtoStr = String.valueOf(queryOrderResultMap.get("success"));
        FuluOrderDto fuluOrderDto = JSON.parseObject(fuluOrderDtoStr, FuluOrderDto.class);
        transaction.setState(TransactionStateEnum.SUCCEED.getCode());
        transaction.setRemark(fuluOrderDtoStr);
        transaction.setUpdateEmp(fuluOrderDto.getOrder_id());
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


    private Map momoOrderPushFulu(String fuliAppKey, String fuluSercret,
                                  String tid,Integer buyNum, String chargeAccount, Goods good) {
        //通过福禄API下单
        DefaultOpenApiClient client =
                new DefaultOpenApiClient(FuliProperties.getUrl(), fuliAppKey, fuluSercret, MethodConst.OPEN_API_DIRECT_ORDER_ADD);
        InputDirectOrderDto dto = new InputDirectOrderDto();
        dto.setProductId(good.getProductId().intValue());
        dto.setCustomerOrderNo(tid);
        dto.setBuyNum(buyNum);
        dto.setChargeAccount(chargeAccount);
        dto.setChargeGameName(good.getProductName());

        client.setBizObject(dto);
        System.out.println("福禄陌陌下单请求："+JSON.toJSONString(dto));
        String result = client.excute();
        Map resultMap = JSON.parseObject(result);
        return resultMap;
    }


}
