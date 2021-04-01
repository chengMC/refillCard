package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Enum.DictCodeEnum;
import com.mc.refillCard.common.Enum.FuluOrderTypeEnum;
import com.mc.refillCard.common.Enum.GoodsRelateTypeEnum;
import com.mc.refillCard.common.Enum.TransactionStateEnum;
import com.mc.refillCard.config.fulu.FuliProperties;
import com.mc.refillCard.dao.TransactionMapper;
import com.mc.refillCard.dto.FuluOrderDto;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.*;
import com.mc.refillCard.service.*;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.util.BaiDuMapApiUtil;
import com.mc.refillCard.util.IpUtil;
import com.mc.refillCard.vo.TransactionVo;
import fulu.sup.open.api.core.MethodConst;
import fulu.sup.open.api.model.InputDirectOrderDto;
import fulu.sup.open.api.model.InputOrderGetDto;
import fulu.sup.open.api.sdk.DefaultOpenApiClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * @Author: MC
 * @Description: Transaction业务层接口实现类
 * @Date 2021-3-20 20:24:42
 *****/
@Service
public class TransactionServiceImpl implements TransactionService {

    @Value("${agiso.appSecret}")
    private String appSecret;

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private OriginalOrderService originalOrderService;
    @Autowired
    private GoodsRelateService goodsRelateService;
    @Autowired
    private GoodsRelateFuluService goodsRelateFuluService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SysDictService sysDictService;

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
    public Long addDto(TransactionDto transactionDto){
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

        transactionMapper.insertSelective(transaction);
        Long id = transaction.getId();
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
            originalOrderService.add(order);
        }
        return id;
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
        String fuliAppKey = userRelate.getFuliAppKey();
        String fuluSercret = userRelate.getFuluSercret();
        String accessToken = userRelate.getAccessToken();
        Long userId = userRelate.getUserId();
        //地区
        String buyerArea = transactionDto.getBuyerArea();
        //订单保存添加人
        String tid = transactionDto.getTid();
        String originaltid = transactionDto.getTid();
        Transaction transaction = transactionMapper.findByTid(tid);
        transaction.setCreateEmp(String.valueOf(userId));
        transactionMapper.updateByPrimaryKeySelective(transaction);
        OriginalOrderDto originalOrder = null;
        GoodsRelateFulu goodsRelateFulu = null;
        Goods defaultGood = null;
        //分配随机IP
        String ip = IpUtil.getRandomIp();
        Integer type = 0;
        //订单
        List<OriginalOrderDto> orders = transactionDto.getOrders();
        for (OriginalOrderDto order : orders) {
            Boolean matchingArea = false;
            //订单的宝贝id
            Long numIid = order.getNumIid();
             goodsRelateFulu = goodsRelateFuluService.findByGoodId(numIid, userId);
             type = goodsRelateFulu.getType();
            //类型是QB 下单
            if (type.equals(GoodsRelateTypeEnum.QB.getCode())) {
                List<Goods> goods = goodsService.findListByType(type);
                //默认全国
                Goods originalgoods = goods.get(0);
                defaultGood = goods.get(0);
                for (Goods good : goods) {
                    //地区匹配
                    String area = good.getArea();
                    if (buyerArea.indexOf(area) > -1) {
                        originalgoods = good;
                        matchingArea = true;
                    }
                }

                originalOrder = order;
                System.out.println("订单中的地区" + buyerArea);
                System.out.println("匹配到的地区:" + originalgoods.getArea());
                //面值
                Integer nominal = Integer.valueOf(goodsRelateFulu.getNominal());
                //数量
                Integer num = order.getNum().intValue();
                //QB购买数等于面值乘数量
                Integer buyNum = num * nominal;
                //如果金额小于20，直接走去全国
                if(buyNum < 20){
                    originalgoods = goods.get(0);
                }
                //如果地区无法匹配就根据IP获取到地区
                if(!matchingArea){
                    String location = location(ip);
                    for (Goods good : goods) {
                        //地区匹配
                        String area = good.getArea();
                        if (location.indexOf(area) > -1) {
                            originalgoods = good;
                        }
                    }
                    System.out.println("未匹配后的：" + ip+"-"+"地区："+location);
                    System.out.println("未匹配后的再次分配的地区:" + originalgoods.getArea());
                }

                Map resultMap = qbOrderPush(transactionDto, fuliAppKey, fuluSercret, originaltid, order, goodsRelateFulu, originalgoods,ip);
                System.out.println("第一次推送:" + tid + "-resultMap-" + resultMap);
                if (!"0".equals(String.valueOf(resultMap.get("code")))) {
                    //失败后默认到全国
                    tid = tid + "Q";
                    resultMap = qbOrderPush(transactionDto, fuliAppKey, fuluSercret, tid, order, goodsRelateFulu, defaultGood,ip);
                    System.out.println("第二次推送:" + tid + "Q1" + "-resultMap-" + resultMap);
                    if (!"0".equals(String.valueOf(resultMap.get("code")))) {
                        String failStr = "福禄平台下单充值失败。订单号：" + tid + "," + resultMap.get("message");
                        resultOrderMap.put("fail", failStr);
                        return resultOrderMap;
                    }
                }
            }
        }

        //类型QB 查询结果后二次下单
        if (type.equals(GoodsRelateTypeEnum.QB.getCode())) {
            Integer pushNum = 1;
            //睡一秒后查询结果
            try {
                Thread.currentThread().sleep(2000);
            } catch (Exception e) {
                System.out.println("线程异常");
            }
            for (int i = 0; i < 100; i++) {
                DefaultOpenApiClient client =
                        new DefaultOpenApiClient(FuliProperties.getUrl(), fuliAppKey, fuluSercret, MethodConst.OPEN_API_ORDER_GET);
                InputOrderGetDto dto = new InputOrderGetDto();
                dto.setCustomerOrderNo(tid);
                client.setBizObject(dto);
                String result = client.excute();
                if (StringUtils.isBlank(result)) {
                    continue;
                }
                Map resultMap = JSON.parseObject(result);
                if (!"0".equals(String.valueOf(resultMap.get("code")))) {
                    String failStr = "福禄平台查询充值失败,订单号：" + tid + "," + resultMap.get("message");
                    resultOrderMap.put("fail", failStr);
                    transaction.setState(TransactionStateEnum.FAIL.getCode());
                    transactionMapper.updateByPrimaryKeySelective(transaction);
                } else {
                    //解析结果
                    String resultStr = String.valueOf(resultMap.get("result"));
                    FuluOrderDto fuluOrderDto = JSON.parseObject(resultStr, FuluOrderDto.class);
                    String order_state = fuluOrderDto.getOrder_state();
                    if (order_state.equals(FuluOrderTypeEnum.SUCCESS.getCode())) {
                        resultOrderMap.put("success", "订单号：" + tid);
                        transaction.setState(TransactionStateEnum.SUCCEED.getCode());
                        transaction.setRemark(resultStr);
                        transaction.setUpdateEmp(fuluOrderDto.getOrder_id());
                        transactionMapper.updateByPrimaryKeySelective(transaction);
                        //更新发货状态
                        try {
                            Boolean aBoolean = changeTBOrderStatus(originaltid, accessToken);
                            if (!aBoolean) {
                                System.out.println("阿奇索自动发货失败");
                            }
                        } catch (UnsupportedEncodingException e) {
                            System.out.println("阿奇索自动发货失败1:" + e);
                        } catch (NoSuchAlgorithmException e) {
                            System.out.println("阿奇索自动发货失败2:" + e);
                        }
                        break;
                    } else if (order_state.equals(FuluOrderTypeEnum.FAILED.getCode())) {
                        if (pushNum < 2) {
                            pushNum++;
                            tid = tid + "QB";
                            //失败后默认到全国
                            resultMap = qbOrderPush(transactionDto, fuliAppKey, fuluSercret, tid, originalOrder, goodsRelateFulu, defaultGood,ip);
                            System.out.println("查询失败后第二次推送:" + tid + "-resultMap-" + resultMap);
                            if (!"0".equals(String.valueOf(resultMap.get("code")))) {
                                String failStr = "福禄平台下单充值失败2。订单号：" + tid + "," + resultMap.get("message");
                                resultOrderMap.put("fail", failStr);
                                return resultOrderMap;
                            } else {
                                //第二次解析结果
                                resultStr = String.valueOf(resultMap.get("result"));
                                System.out.println("查询失败后第二次推送后解析:" + resultStr);
                                fuluOrderDto = JSON.parseObject(resultStr, FuluOrderDto.class);
                                order_state = fuluOrderDto.getOrder_state();
                                if (order_state.equals(FuluOrderTypeEnum.SUCCESS.getCode())) {
                                    resultOrderMap.put("success", "订单号：" + tid);
                                    transaction.setState(TransactionStateEnum.SUCCEED.getCode());
                                    transaction.setRemark(resultStr);
                                    transaction.setUpdateEmp(fuluOrderDto.getOrder_id());
                                    transactionMapper.updateByPrimaryKeySelective(transaction);
                                    //更新发货状态
                                    try {
                                        Boolean aBoolean = changeTBOrderStatus(originaltid, accessToken);
                                        if (!aBoolean) {
                                            System.out.println("阿奇索自动发货失败22");
                                        }
                                    } catch (UnsupportedEncodingException e) {
                                        System.out.println("阿奇索自动发货失败11:" + e);
                                    } catch (NoSuchAlgorithmException e) {
                                        System.out.println("阿奇索自动发货失败22:" + e);
                                    }
                                    break;
                                } else if (order_state.equals(FuluOrderTypeEnum.FAILED.getCode())) {
                                    String failStr = "福禄平台充值失败,订单号：" + tid + "," + resultMap.get("message");
                                    resultOrderMap.put("fail", failStr);
                                    transaction.setState(TransactionStateEnum.FAIL.getCode());
                                    transaction.setRemark(resultStr);
                                    transactionMapper.updateByPrimaryKeySelective(transaction);
                                    break;
                                }
                            }
                        } else {
                            String failStr = "福禄平台充值失败,订单号：" + tid + "," + resultMap.get("message");
                            resultOrderMap.put("fail", failStr);
                            transaction.setState(TransactionStateEnum.FAIL.getCode());
                            transaction.setRemark(resultStr);
                            transactionMapper.updateByPrimaryKeySelective(transaction);
                            break;
                        }
                    }
                }
                //睡一秒后查询结果
                try {
                    Thread.currentThread().sleep(1000);
                } catch (Exception e) {
                    System.out.println("线程异常");
                }
            }
        }
        return resultOrderMap;
    }

    /**
     *  qb订单推送
     *
     * @param transactionDto
     * @param fuliAppKey
     * @param fuluSercret
     * @param tid
     * @param order
     * @param goodsRelate
     * @return
     */
    private Map qbOrderPush(TransactionDto transactionDto, String fuliAppKey, String fuluSercret,
                            String tid, OriginalOrderDto order,  GoodsRelateFulu goodsRelate, Goods good,String ip) {
        String receiverAddress = transactionDto.getReceiverAddress();
        String ChargeAccount = AccountUtils.findNumber(receiverAddress);
        //面值
        Integer nominal = Integer.valueOf(goodsRelate.getNominal());
        //数量
        Integer num = order.getNum().intValue();
        //QB购买数等于面值乘数量
        Integer buyNum = num * nominal;
        //通过福禄API下单
        DefaultOpenApiClient client =
                new DefaultOpenApiClient(FuliProperties.getUrl(), fuliAppKey, fuluSercret, MethodConst.OPEN_API_DIRECT_ORDER_ADD);
        InputDirectOrderDto dto = new InputDirectOrderDto();
        dto.setProductId(good.getProductId().intValue());
        dto.setCustomerOrderNo(tid);
        dto.setBuyNum(buyNum);
        dto.setChargeAccount(ChargeAccount);
        dto.setContactQq(ChargeAccount);
        dto.setChargeGameName(good.getProductName());
        dto.setChargeIp(ip);
        client.setBizObject(dto);
        System.out.println("第一次请求："+JSON.toJSONString(dto));
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
        paramMap.put("sign", AccountUtils.getSign(paramMap, appSecret));

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


    @Override
    public Boolean changeTBOrderStatus(String tid, String accessToken) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer "+ accessToken);
        headerMap.put("ApiVersion", "1");

        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("timestamp", String.valueOf(DateUtil.currentSeconds()));
        paramMap.put("tids", tid);
        paramMap.put("sign", AccountUtils.getSign(paramMap, appSecret));

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
        //221.232.58.139
        Boolean change = false;
        Map resultMap = JSON.parseObject(result);
        if("true".equals(String.valueOf(resultMap.get("IsSuccess")))){
            change= true;
        }
        return change;
    }




}
