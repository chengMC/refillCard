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
import org.apache.log4j.Logger;
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

    private static Logger log = Logger.getLogger(TransactionServiceImpl.class);

    @Value("${agiso.appSecret}")
    private String appSecret;

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private OriginalOrderService originalOrderService;
    @Autowired
    private NationwideIpService nationwideIpService;
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
        Long userId = userRelate.getUserId();
        //订单保存添加人
        String tid = transactionDto.getTid();
        Transaction transaction = transactionMapper.findByTid(tid);
        transaction.setCreateEmp(String.valueOf(userId));
        transactionMapper.updateByPrimaryKeySelective(transaction);

        //订单
        List<OriginalOrderDto> orders = transactionDto.getOrders();
        for (OriginalOrderDto orderDto : orders) {
            //订单的宝贝id
            Long numIid = orderDto.getNumIid();
            GoodsRelateFulu  goodsRelateFulu = goodsRelateFuluService.findByGoodId(numIid, userId);
            Integer type = goodsRelateFulu.getType();
            //类型是QB 下单
            if (type.equals(GoodsRelateTypeEnum.QB.getCode())) {
                Map qbOrderPushMap = qbOrderPush(transaction, transactionDto, userRelate, tid, orderDto, goodsRelateFulu);
                return qbOrderPushMap;
            }
        }
        return resultOrderMap;
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
     * @return
     */
    private Map qbOrderPush(Transaction transaction,TransactionDto transactionDto,UserRelate userRelate,
                            String tid, OriginalOrderDto originalOrderDto,GoodsRelateFulu goodsRelateFulu) {
        Integer type = goodsRelateFulu.getType();

        //地区
//        String buyerArea = transactionDto.getBuyerArea();
        String buyerArea = "湖北移动";
        //根据类型查询所对应福禄商品
        List<Goods> goods = goodsService.findListByType(type);
        //查询IP段
        List<NationwideIp> nationwideIpList = nationwideIpService.findAll();

        //充值账号
        String receiverAddress = transactionDto.getReceiverAddress();
        String chargeAccount = AccountUtils.findNumber(receiverAddress);

        //面值
        Integer nominal = Integer.valueOf(goodsRelateFulu.getNominal());
        //数量
        Integer num = originalOrderDto.getNum().intValue();
        //QB购买数等于面值乘数量
        Integer buyNum = num * nominal;
        //如果金额小于20，直接走去全国
        if(buyNum < 20){
            Map qbNationwidePushResultMap = QbOrderPushAndQueryResult(transaction, tid,userRelate, goods.get(0), chargeAccount, buyNum,"");
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
            //获取到真实的随机地区
            Integer areaRandom = IpUtil.createAreaRandomIp(0, nationwideIpList.size());
            NationwideIp nationwideIp = nationwideIpList.get(areaRandom);
            String startIp = nationwideIp.getStartIp();
            String endIp = nationwideIp.getEndIp();
            String area = nationwideIp.getArea();
            //地区商品下单失败后，拿到河南或者山东IP后下单
            String areaRandomIp = IpUtil.getAreaRandomIp(startIp, endIp);
            matchingGoods = null;
            for (Goods good : goods) {
                //地区匹配
                if (area.indexOf(good.getArea()) > -1) {
                    matchingGoods = good;
                }
            }
            //走河南或者山东的IP
            Map qbWtoNationwidePushResultMap = QbOrderPushAndQueryResult(transaction, tid,userRelate,matchingGoods, chargeAccount, buyNum,areaRandomIp);
            if(qbWtoNationwidePushResultMap.get("fail") != null){
                //失败后再次走全国
                Map qbThreeNationwidePushResultMap = QbOrderPushAndQueryResult(transaction, tid,userRelate, goods.get(0), chargeAccount, buyNum,"");
                return qbThreeNationwidePushResultMap;
            }
            return qbWtoNationwidePushResultMap;
        }else{
            //地区匹配成功后直接根据地区商品下单
            Map qbNationwidePushResultMap = QbOrderPushAndQueryResult(transaction, tid,userRelate,matchingGoods, chargeAccount, buyNum,"");
            if(qbNationwidePushResultMap.get("fail") != null){
                //获取到真实的随机地区
                Integer areaRandom = IpUtil.createAreaRandomIp(0, nationwideIpList.size());
                NationwideIp nationwideIp = nationwideIpList.get(areaRandom);
                String startIp = nationwideIp.getStartIp();
                String endIp = nationwideIp.getEndIp();
                String area = nationwideIp.getArea();
                //地区商品下单失败后，拿到河南或者山东IP后下单
                String areaRandomIp = IpUtil.getAreaRandomIp(startIp, endIp);
                matchingGoods = null;
                for (Goods good : goods) {
                    //地区匹配
                    if (area.indexOf(good.getArea()) > -1) {
                        matchingGoods = good;
                    }
                }
                //走河南或者山东的IP
                Map qbWtoNationwidePushResultMap = QbOrderPushAndQueryResult(transaction, tid,userRelate,matchingGoods, chargeAccount, buyNum,areaRandomIp);
                if(qbWtoNationwidePushResultMap.get("fail") != null){
                    //失败后再次走全国
                    Map qbThreeNationwidePushResultMap = QbOrderPushAndQueryResult(transaction, tid,userRelate, goods.get(0), chargeAccount, buyNum,"");
                    return qbThreeNationwidePushResultMap;
                }
                return qbWtoNationwidePushResultMap;
            }
            return qbNationwidePushResultMap;
        }
    }

    /**
     * QB下单后查询结果并发返回
     *
     * @param transaction
     * @param tid
     * @param userRelate
     * @param goods
     * @param chargeAccount
     * @param buyNum
     * @param ip
     * @return
     */
    private Map QbOrderPushAndQueryResult(Transaction transaction, String tid,UserRelate userRelate, Goods goods,
                                          String chargeAccount, Integer buyNum,String ip) {
        String fuliAppKey = userRelate.getFuliAppKey();
        String fuluSercret = userRelate.getFuluSercret();
        String accessToken = userRelate.getAccessToken();
         log.info("qb下单后查询返回，地区："+goods.getArea()+"-IP:"+ip);

        Map qbNationwidePushResultMap = qbNationwidePushFulu(fuliAppKey, fuluSercret, tid, buyNum, chargeAccount, goods,ip);
        //福禄下单失败后直接返回
        if(qbNationwidePushResultMap.get("fail") != null){
            return qbNationwidePushResultMap;
        }
        //福禄下单成功后去查询
        Map queryOrderResultMap = queryOrderResult(fuliAppKey, fuluSercret, tid);
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
            Boolean aBoolean = changeTBOrderStatus(tid, accessToken);
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
    private Map qbNationwidePushFulu( String fuliAppKey, String fuluSercret,
                                 String tid,Integer buyNum, String chargeAccount, Goods good,String ip){
        Map qbNationwidePushResultMap = new HashMap();
        String area = good.getArea();
        Map resultMap = qbOrderPushFulu(fuliAppKey, fuluSercret, tid, buyNum, chargeAccount, good,ip);
        System.out.println("下单"+area+"QB:" + tid  + "-resultMap-" + resultMap);
        if (!"0".equals(String.valueOf(resultMap.get("code")))) {
            String failStr = "福禄平台"+area+"QB下单失败。订单号：" + tid + "," + resultMap.get("message");
            qbNationwidePushResultMap.put("fail", failStr);
            return qbNationwidePushResultMap;
        }else{
            qbNationwidePushResultMap.put("success", "订单号：" + tid);
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
    private Map queryOrderResult(String fuliAppKey, String fuluSercret, String tid) {
        Map resultOrderMap = new HashMap();
        //睡二秒后查询结果
        try {
            Thread.currentThread().sleep(2000);
        } catch (Exception e) {
            log.error("线程异常");
        }
        for (int i = 0; i < 100; i++) {
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
                String failStr = "福禄平台查询充值失败,订单号：" + tid + "," + resultMap.get("message");
                log.info(failStr);
            }
            //解析结果
            String resultStr = String.valueOf(resultMap.get("result"));
            FuluOrderDto fuluOrderDto = JSON.parseObject(resultStr, FuluOrderDto.class);
            String order_state = fuluOrderDto.getOrder_state();
            if (order_state.equals(FuluOrderTypeEnum.SUCCESS.getCode())) {
                resultOrderMap.put("success", fuluOrderDto);
                return resultOrderMap;
            } else if (order_state.equals(FuluOrderTypeEnum.FAILED.getCode())) {
                String failStr = "福禄平台全国QB充值失败。订单号：" + tid + "," + resultMap.get("message");
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
        String failStr = "福禄平台全国QB充值失败。订单号：" + tid;
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
        System.out.println("福禄下单请求："+JSON.toJSONString(dto));
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


    /**
     * 福禄下单成功后调用阿奇索接口改变淘宝订单状态
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
        log.info("");
        //221.232.58.139
        Boolean change = false;
        Map resultMap = JSON.parseObject(result);
        if("true".equals(String.valueOf(resultMap.get("IsSuccess")))){
            change= true;
        }
        return change;
    }




}
