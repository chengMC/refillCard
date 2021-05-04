package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Enum.*;
import com.mc.refillCard.config.fulu.FuliProperties;
import com.mc.refillCard.config.fulu.ShuShanApiProperties;
import com.mc.refillCard.dao.TransactionMapper;
import com.mc.refillCard.dto.FuluOrderDto;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.*;
import com.mc.refillCard.service.*;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.util.BaiDuMapApiUtil;
import com.mc.refillCard.util.IpUtil;
import com.mc.refillCard.util.XmlUtils;
import com.mc.refillCard.vo.TransactionVo;
import fulu.sup.open.api.core.MethodConst;
import fulu.sup.open.api.model.InputDirectOrderDto;
import fulu.sup.open.api.model.InputOrderGetDto;
import fulu.sup.open.api.sdk.DefaultOpenApiClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * @Author: MC
 * @Description: Transaction业务层接口实现类
 * @Date 2021-3-20 20:24:42
 *****/
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Value("${agiso.appSecret}")
    private String appSecret;

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private OriginalOrderService originalOrderService;
    @Lazy
    @Autowired
    private NationwideIpService nationwideIpService;
    @Autowired
    private GoodsRelateFuluService goodsRelateFuluService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private BlacklistService blacklistService;
    @Autowired
    private UserPricingService userPricingService;
    @Autowired
    private UserRelateService userRelateService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlatformKeyService platformKeyService;

    /**
     Transaction条件+分页查询
     @param transactionDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(TransactionDto transactionDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<TransactionVo> transactionVos = transactionMapper.findPageVoByExample(transactionDto);
        //执行搜索
        return new PageInfo(transactionVos);
    }

    /**
     * Transaction条件查询
     * @param transaction
     * @return
     */
    @Override
    public List<Transaction> findList(Transaction transaction){
        //根据构建的条件查询数据
        return transactionMapper.selectByExample(transaction);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        transactionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Transaction
     * @param transaction
     */
    @Override
    public void update(Transaction transaction){
        transactionMapper.updateByPrimaryKeySelective(transaction);
    }

    /**
     * 修改Transaction
     * @param transactionDto
     */
    @Override
    public void updateDto(TransactionDto transactionDto){
        Transaction transaction = BeanUtil.copyProperties(transactionDto, Transaction.class);
        transactionMapper.updateByPrimaryKeySelective(transaction);
    }

    /**
     * 增加Transaction
     * @param transaction
     */
    @Override
    public void add(Transaction transaction){
        transactionMapper.insertSelective(transaction);
    }

    /**
     * 增加Transaction
     * @param transactionDto
     * @return
     */
    @Transactional
    @Override
    public TransactionDto addDto(TransactionDto transactionDto){
        TransactionDto resultTransactions = new TransactionDto();
        List<OriginalOrderDto> orderDtos = transactionDto.getOrders();
        Transaction transaction = new Transaction();
        transaction.setPlatForm(transactionDto.getPlatform());
        transaction.setPlatformUserId(transactionDto.getPlatformUserId());
        transaction.setReceiverName(transactionDto.getReceiverName());
        transaction.setReceiverMobile(transactionDto.getReceiverMobile());
        transaction.setReceiverPhone(transactionDto.getReceiverPhone());
        transaction.setReceiverAddress(transactionDto.getReceiverAddress());
        transaction.setBuyerArea(transactionDto.getBuyerArea());
        transaction.setExtendedFields(transactionDto.getExtendedFields());
        transaction.setSellerFlag(transactionDto.getSellerFlag());
        transaction.setSellerMemo(transactionDto.getSellerMemo());
        transaction.setCreditCardFee(transactionDto.getCreditCardFee());
        
        transaction.setTid(transactionDto.getTid());
        transaction.setStatus(transactionDto.getStatus());
        transaction.setSellerNick(transactionDto.getSellerNick());
        transaction.setBuyerNick(transactionDto.getBuyerNick());
        transaction.setBuyerMessage(transactionDto.getBuyerMessage());
        transaction.setPrice(transactionDto.getPrice());
        transaction.setNum(transactionDto.getNum());
        transaction.setTotalFee(transactionDto.getTotalFee());
        transaction.setPayment(transactionDto.getPayment());
        transaction.setPayTime(transactionDto.getPayTime());
        transaction.setCreated(transactionDto.getCreated());
        transaction.setCreateTime(DateUtil.date());

        transactionMapper.insertSelective(transaction);
        //充值账号
        String receiverAddress = transactionDto.getReceiverAddress();
        String chargeAccount = AccountUtils.findNumber(receiverAddress);

        //保存订单id
        Long id = transaction.getId();
        resultTransactions.setId(id);
        List<OriginalOrderDto> resultOrders = new ArrayList<>();
        //保存订单详情
        for (OriginalOrderDto orderDto : orderDtos) {
            OriginalOrder order = new OriginalOrder();
            order.setTransactionId(id);
            order.setNum(orderDto.getNum());
            order.setNumId(orderDto.getNumIid());
            order.setOId(orderDto.getOid());
            order.setOuterId(orderDto.getOuterIid());
            order.setOuterSkuId(orderDto.getOuterSkuId());
            order.setPayment(orderDto.getPayment());
            order.setPrice(orderDto.getPrice());
            order.setSkuPropertiesName(orderDto.getSkuPropertiesName());
            order.setTitle(orderDto.getTitle());
            order.setTotalFee(orderDto.getTotalFee());
            order.setChargeAccount(chargeAccount);
            order.setCreateTime(DateUtil.date());
            originalOrderService.add(order);
            //保存订单详情id
            Long orderId = order.getId();
            OriginalOrderDto originalOrderDto = new OriginalOrderDto();
            originalOrderDto.setId(orderId);
            resultOrders.add(originalOrderDto);
        }
        resultTransactions.setOrders(resultOrders);
        return resultTransactions;
    }

    /**
     * 根据ID查询Transaction
     * @param id
     * @return
     */
    @Override
    public Transaction findById(Long id){
        return  transactionMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Transaction全部数据
     * @return
     */
    @Override
    public List<Transaction> findAll() {
        return transactionMapper.findAll();
    }

    @Override
    public Map placeOrder(TransactionDto transactionDto, UserRelate userRelate) {
        Map resultOrderMap = new HashMap();
        Long userId = userRelate.getUserId();
        String tid = transactionDto.getTid();
        //地区
        String buyerArea = transactionDto.getBuyerArea();
        //订单
        OriginalOrderDto orderDto = transactionDto.getOrders().get(0);
        //订单的宝贝id
        Long numIid = orderDto.getNumIid();
        GoodsRelateFulu  goodsRelateFulu = goodsRelateFuluService.findByGoodId(numIid, userId);
        if(goodsRelateFulu == null){
            resultOrderMap.put("fail", "订单号：" + tid+",没有找到对应的宝贝Id："+numIid);
            return resultOrderMap;
        }
        Integer type = goodsRelateFulu.getType();
        //类型是QB 下单
        if (type.equals(GoodsRelateTypeEnum.QB.getCode())) {
            //根据类型查询所对应福禄商品
            Integer platform = PlatformEnum.FULU.getCode();
            List<Goods> goods = goodsService.findListByTypeAndPlatform(platform, type);
            //默认全国
            Goods matchingGoods = null;
            for (Goods good : goods) {
                //地区匹配
                String area = good.getArea();
                if (buyerArea.indexOf(area) > -1) {
                    matchingGoods = good;
                }
            }
            OriginalOrder originalOrder = originalOrderService.findById(orderDto.getId());
            //地区匹配不成功后，走全国商品
            if(matchingGoods == null){
                //查询全国下单平台
                SysDict nationwideCode = sysDictService.findByCode(DictCodeEnum.NATIONWIDE.getName());
                Integer nationwideValue = Integer.valueOf(nationwideCode.getDataValue());
               //下单平台 1 福禄 2 蜀山
                if(PlatformEnum.SHUSHAN.getCode().equals(nationwideValue)){
                    Map qbOrderPushMap = shushanPlaceOrder(transactionDto, userRelate);
                    //修改失败订单状态
                    updataOrderStatus(originalOrder, qbOrderPushMap);
                    return qbOrderPushMap;
                }else if(PlatformEnum.FULU.getCode().equals(nationwideValue)){
                    Map qbOrderPushMap =  fuliPlaceOrder(transactionDto, userRelate);
                    //修改失败订单状态
                    updataOrderStatus(originalOrder, qbOrderPushMap);
                    return qbOrderPushMap;
                }
            }else{
                //查询区域下单平台
                SysDict regionCode = sysDictService.findByCode(DictCodeEnum.REGION.getName());
                Integer regionValue = Integer.valueOf(regionCode.getDataValue());
                //下单平台 1 福禄 2 蜀山
                if(PlatformEnum.SHUSHAN.getCode().equals(regionValue)){
                    Map qbOrderPushMap =  shushanPlaceOrder(transactionDto, userRelate);
                    //蜀山地区失败后，走福禄的全国
                    if(qbOrderPushMap.get("fail") != null){
                        transactionDto.setBuyerArea("未知");
                        Map qbOrderPushMapFulu = fuliPlaceOrder(transactionDto, userRelate);
                        //修改失败订单状态
                        updataOrderStatus(originalOrder, qbOrderPushMapFulu);
                        return qbOrderPushMapFulu;
                    }
                    return qbOrderPushMap;
                }else if(PlatformEnum.FULU.getCode().equals(regionValue)){
                    Map qbOrderPushMap =fuliPlaceOrder(transactionDto, userRelate);
                    //修改失败订单状态
                    updataOrderStatus(originalOrder, qbOrderPushMap);
                    return qbOrderPushMap;
                }
            }
        }

        return resultOrderMap;
    }

    private void updataOrderStatus(OriginalOrder originalOrder, Map qbOrderPushMap) {
        Object fail = qbOrderPushMap.get("fail");
        if( qbOrderPushMap.get("fail") != null){
            originalOrder.setOrderStatus(TransactionStateEnum.FAIL.getCode());
            originalOrder.setFailReason(String.valueOf(fail));
            originalOrder.setUpdateTime(DateUtil.date());
            originalOrderService.update(originalOrder);
        }
    }

    @Override
    public Map fuliPlaceOrder(TransactionDto transactionDto, UserRelate userRelate) {
        Map resultOrderMap = new HashMap();
        Long userId = userRelate.getUserId();
        Integer platform = PlatformEnum.FULU.getCode();
        //订单保存添加人
        String tid = transactionDto.getTid();
        Transaction transaction = transactionMapper.findByTid(tid);

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
            GoodsRelateFulu  goodsRelateFulu = goodsRelateFuluService.findByGoodId(numIid, userId);
              if(goodsRelateFulu == null){
                  resultOrderMap.put("fail", "订单号：" + tid+",没有找到对应的宝贝Id："+numIid);
                  return resultOrderMap;
              }
            Integer type = goodsRelateFulu.getType();
            //类型是QB 下单
            if (type.equals(GoodsRelateTypeEnum.QB.getCode())) {
                //判断余额
                Map judgeMap = judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                Map qbOrderPushMap = fuluQbOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(qbOrderPushMap.get("success") != null){
                    //更新余额
                    updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return qbOrderPushMap;
            }
            //下单是陌陌
            if (type.equals(GoodsRelateTypeEnum.MOMO.getCode())) {
                //判断余额
                Map judgeMap = judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                Map qbOrderPushMap = fuluCommonOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(qbOrderPushMap.get("success") != null){
                    //更新余额
                    updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return qbOrderPushMap;
            }
            //探探下单
            if (type.equals(GoodsRelateTypeEnum.TANTAN.getCode())) {
                //判断余额
                Map judgeMap = judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                Map qbOrderPushMap = fuluCommonOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(qbOrderPushMap.get("success") != null){
                    //更新余额
                    updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return qbOrderPushMap;
            }
        }
        return resultOrderMap;
    }

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
        Transaction transaction = transactionMapper.findByTid(tid);
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
            if (type.equals(GoodsRelateTypeEnum.QB.getCode())) {
                //判断余额
                Map judgeMap = judgeBalance(orderDto, goodsRelateFulu, userRelate, type);
                //账号余额 大于订单总价格
                if(judgeMap.get("fail") != null){
                    return judgeMap;
                }
                //QB下单
                Map qbOrderPushMap = shuShanQbOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu,platform);
                if(qbOrderPushMap.get("success") != null){
                    //更新余额
                    updateBalance(orderDto, goodsRelateFulu, userRelate, type);
                }
                return qbOrderPushMap;
            }
        }
        resultOrderMap.put("fail", "订单号：" + tid);
        return resultOrderMap;
    }

    /**
     * 更新价格
     *
     * @param orderDto
     * @param goodsRelateFulu
     * @param userRelate
     * @param type
     */
    private void updateBalance(OriginalOrderDto orderDto,GoodsRelateFulu goodsRelateFulu,UserRelate userRelate,Integer type) {
        Long userId = userRelate.getUserId();
        User user = userService.findById(userId);
        UserPricing userPricing = userPricingService.findByUserIdAndType(userId,type);
        //账号余额
        BigDecimal balance = user.getBalance();
        //计算商品价格
        BigDecimal totalPrice = calculatePrice(orderDto, goodsRelateFulu, userPricing);
        //成功后减少余额
        BigDecimal subtract = balance.subtract(totalPrice);
        //获取到最新余额
        user.setBalance(subtract);
        userService.update(user);
        //更新价格
        OriginalOrder originalOrder = originalOrderService.findById(orderDto.getId());
        //扣款前余额
        originalOrder.setBeforeBalance(balance);
        //扣款后余额
        originalOrder.setAfterBalance(subtract);
        //扣款金额
        originalOrder.setDeductPrice(totalPrice);
        originalOrder.setOrderStatus(TransactionStateEnum.SUCCEED.getCode());
        originalOrder.setUpdateTime(DateUtil.date());
        originalOrderService.update(originalOrder);
    }


    /**
     * 判断余额
     * @param orderDto
     * @param goodsRelateFulu
     * @param userRelate
     * @return
     */
    private Map judgeBalance(OriginalOrderDto orderDto,GoodsRelateFulu goodsRelateFulu,UserRelate userRelate,Integer type) {
        Map resultOrderMap = new HashMap();
        Long tid = orderDto.getOid();
        //根据用户和商品类型查询定价
        Long userId = userRelate.getUserId();
        UserPricing userPricing = userPricingService.findByUserIdAndType(userId,type);
        if(userPricing == null){
            resultOrderMap.put("fail", "订单号：" + tid+",未定价");
            return resultOrderMap;
        }
        //获取到最新余额
        User user = userService.findById(userId);
        //账号余额
        BigDecimal balance = user.getBalance();
        //计算商品价格
        BigDecimal totalPrice = calculatePrice(orderDto, goodsRelateFulu, userPricing);
        //账号余额 大于订单总价格
        if (!(balance.compareTo(totalPrice) == 1)) {
            resultOrderMap.put("fail", "订单号：" + tid + ",订单余额不足");
            return resultOrderMap;
        }
        resultOrderMap.put("success","余额充足");
        return resultOrderMap;
    }

    /**
     *  计算价格
     * @return
     */
    private BigDecimal calculatePrice(OriginalOrderDto orderDto,GoodsRelateFulu goodsRelateFulu,UserPricing userPricing){
        //订单中宝贝数量
        BigDecimal orderNumBig = new BigDecimal(orderDto.getNum());
        //宝贝金额
        BigDecimal nominalBig = new BigDecimal(goodsRelateFulu.getNominal());
        //统一定价
        BigDecimal unifyPrice = userPricing.getUnifyPrice();
        //总价格=统一定价*以宝贝金额*订单中宝贝数量
        BigDecimal totalPrice = unifyPrice.multiply(nominalBig).multiply(orderNumBig);
        return totalPrice;
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
        //查询IP段
//        List<NationwideIp> nationwideIpList = nationwideIpService.findAll();

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
        Map qbNationwidePushResultMap = commonOrderPushAndQueryResult(transaction, tid,originalTid,userRelate,matchingGood, chargeAccount, buyNum);
        return qbNationwidePushResultMap;
    }

    /**
     * QB订单推送
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
    private Map fuluQbOrderPush(Transaction transaction, TransactionDto transactionDto, UserRelate userRelate,
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
            Map qbNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto,transaction, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
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
                Map qbNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto, transaction, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
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
                Map qbWtoNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto, transaction, tid,originalTid,userRelate,matchingGoods, chargeAccount, buyNum,areaRandomIp);
                if(qbWtoNationwidePushResultMap.get("fail") != null){
                    tid = tid+"QB";
                    //失败后再次走全国
                    Map qbThreeNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto, transaction, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
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
            Map qbWtoNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto, transaction, tid,originalTid,userRelate,matchingGoods, chargeAccount, buyNum,areaRandomIp);
            if(qbWtoNationwidePushResultMap.get("fail") != null){
                tid = tid+"QB";
                //失败后再次走全国
                Map qbThreeNationwidePushResultMap = fuluQbOrderPushAndQueryResult(originalOrderDto, transaction, tid,originalTid,userRelate, goods.get(0), chargeAccount, buyNum,"");
                return qbThreeNationwidePushResultMap;
            }
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
     * @param ip
     * @return
     */
    private Map fuluQbOrderPushAndQueryResult(OriginalOrderDto originalOrderDto, Transaction transaction, String tid, String originalTid, UserRelate userRelate, Goods goods,
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
            Boolean aBoolean = changeTBOrderStatus(originalTid, accessToken);
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
            originalOrder.setOrderStatus(TransactionStateEnum.FAIL.getCode());
            originalOrder.setFailReason(fail);
            originalOrderService.update(originalOrder);
            return queryOrderResultMap;
        }
        //福禄查询充值成功后 转化对象
        String orderDtoStr = String.valueOf(queryOrderResultMap.get("success"));
        log.error("蜀山下单查询后成功："+orderDtoStr);
        Map orderDtoStrResult = JSON.parseObject(orderDtoStr);
        originalOrder.setOrderStatus(2);
        originalOrder.setRemark(orderDtoStr);
        originalOrder.setExternalOrderId(String.valueOf(orderDtoStrResult.get("order-id")));
        transactionMapper.updateByPrimaryKeySelective(transaction);
        //更新发货状态
        try {
            Boolean aBoolean = changeTBOrderStatus(originalTid, accessToken);
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
            transactionMapper.updateByPrimaryKeySelective(transaction);
            return queryOrderResultMap;
        }
        //福禄查询充值成功后 转化对象
        String fuluOrderDtoStr = String.valueOf(queryOrderResultMap.get("success"));
        FuluOrderDto fuluOrderDto = JSON.parseObject(fuluOrderDtoStr, FuluOrderDto.class);
        transaction.setState(TransactionStateEnum.SUCCEED.getCode());
        transaction.setRemark(fuluOrderDtoStr);
        transaction.setUpdateEmp(fuluOrderDto.getOrder_id());
        transactionMapper.updateByPrimaryKeySelective(transaction);
        //更新发货状态
        try {
            Boolean aBoolean = changeTBOrderStatus(originalTid, accessToken);
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
     * qb订单推送 福禄
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
        dataMap.put("MerchantID",ShuShanApiProperties.getMerchantID());
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

    /**
     * 根据订单获取ip
     *
     * @param tid 订单号
     * @param accessToken token
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public String getBuyerIp(String tid, String accessToken) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer "+ accessToken);
        headerMap.put("ApiVersion", "1");

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("timestamp", String.valueOf(DateUtil.currentSeconds()));
        paramMap.put("tid", tid);
        paramMap.put("selDataType", "tiqu");
        paramMap.put("sign", AccountUtils.getAqusuoSign(paramMap, appSecret));

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("timestamp",  paramMap.get("timestamp"));
        dataMap.put("tid", paramMap.get("tid"));
        dataMap.put("selDataType", paramMap.get("selDataType"));
        dataMap.put("sign",paramMap.get("sign"));

        //接口调用
        String result = HttpRequest.post("http://gw.api.agiso.com/alds/Log/GetTiqu")
                .form(dataMap)
                .addHeaders(headerMap)
                .execute()
                .body();
        System.out.println(result);
        //221.232.58.139
        String RealIp ="";
        Map resultMap = JSON.parseObject(result);
        if("true".equals(String.valueOf(resultMap.get("IsSuccess")))){
            List<Map> resultDataMap = JSON.parseArray(JSON.toJSONString(resultMap.get("data")),Map.class);
            if(CollUtil.isNotEmpty(resultDataMap)){
                RealIp = String.valueOf(resultDataMap.get(0).get("RealIp"));
            }
        }
        return RealIp;
    }

    /**
     *  百度地图根据IP获取地区
     * @param ip
     * @return
     */
    public String location(String ip){
        List<SysDict> listByCode = sysDictService.findListByCode(DictCodeEnum.BAIDUAK.getName());
        String location = BaiDuMapApiUtil.location(ip, listByCode);
        return location;
    }


    /**
     *
     *  下单失败修改卖家备注
     *
     * @param tid
     * @param accessToken
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public Boolean failMemoUpdate(String tid, String accessToken) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer "+ accessToken);
        headerMap.put("ApiVersion", "1");

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("timestamp", String.valueOf(DateUtil.currentSeconds()));
        paramMap.put("tids", tid);
        paramMap.put("memo", "充值失败");
        paramMap.put("flag", "1");
        paramMap.put("sign", AccountUtils.getAqusuoSign(paramMap, appSecret));

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("timestamp",  paramMap.get("timestamp"));
        dataMap.put("tids", paramMap.get("tids"));
        dataMap.put("memo", "充值失败");
        dataMap.put("flag", 1L);
        dataMap.put("sign",paramMap.get("sign"));

        //接口调用
        String result = HttpRequest.post("http://gw.api.agiso.com/alds/Trade/MemoUpdate")
                .form(dataMap)
                .addHeaders(headerMap)
                .execute()
                .body();
        System.out.println(result);
        log.info("");
        //221.232.58.139
        Boolean change = false;
        Map resultMap = JSON.parseObject(result);
        if("true".equals(String.valueOf(resultMap.get("IsSuccess")))){
            change= true;
        }
        return change;
    }

    /**
     *下单成功后调用阿奇索接口改变淘宝订单状态
     *
     * @param tid
     * @param accessToken
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public Boolean changeTBOrderStatus(String tid, String accessToken) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer "+ accessToken);
        headerMap.put("ApiVersion", "1");

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("timestamp", String.valueOf(DateUtil.currentSeconds()));
        paramMap.put("tids", tid);
        paramMap.put("sign", AccountUtils.getAqusuoSign(paramMap, appSecret));

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("timestamp",  paramMap.get("timestamp"));
        dataMap.put("tids", paramMap.get("tids"));
        dataMap.put("sign",paramMap.get("sign"));

        //接口调用
        String result = HttpRequest.post("http://gw.api.agiso.com/alds/Trade/LogisticsDummySend")
                .form(dataMap)
                .addHeaders(headerMap)
                .execute()
                .body();
        System.out.println(result);
        log.info("");
        //221.232.58.139
        Boolean change = false;
        Map resultMap = JSON.parseObject(result);
        if("true".equals(String.valueOf(resultMap.get("IsSuccess")))){
            change= true;
        }
        return change;
    }



    @Override
    public List<Transaction> findListByParam(Transaction transaction) {
       return transactionMapper.findListByParam(transaction);
    }



}
