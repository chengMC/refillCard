package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Enum.DictCodeEnum;
import com.mc.refillCard.common.Enum.GoodsRelateTypeEnum;
import com.mc.refillCard.common.Enum.PlatformEnum;
import com.mc.refillCard.common.Enum.TransactionStateEnum;
import com.mc.refillCard.common.UserConstants;
import com.mc.refillCard.dao.TransactionMapper;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.*;
import com.mc.refillCard.service.*;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.util.BaiDuMapApiUtil;
import com.mc.refillCard.vo.TransactionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
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

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private OrderPushFuluService orderPushFuluService;
    @Autowired
    private OrderPushShuShanService orderPushShuShanService;
    @Autowired
    private OrderPushJingLanService orderPushJingLanService;
    @Autowired
    private OriginalOrderService originalOrderService;
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
        transaction.setUpdateTime(DateUtil.date());

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
            order.setUpdateTime(DateUtil.date());
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
        OriginalOrder originalOrder = originalOrderService.findById(orderDto.getId());
        //类型是QB 下单
        if (type.equals(GoodsRelateTypeEnum.QB.getCode())) {
            //地区
            String buyerArea = transactionDto.getBuyerArea();
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
            //地区匹配不成功后，走全国商品
            if(matchingGoods == null){
                //查询全国下单平台
                SysDict nationwideCode = sysDictService.findByCode(DictCodeEnum.NATIONWIDE.getName());
                Integer nationwideValue = Integer.valueOf(nationwideCode.getDataValue());
               //下单平台 1 福禄 2 蜀山
                if(PlatformEnum.SHUSHAN.getCode().equals(nationwideValue)){
                    Map qbOrderPushMap = orderPushShuShanService.shushanPlaceOrder(transactionDto, userRelate);
                    //修改失败订单状态
                    updataOrderStatus(originalOrder, qbOrderPushMap);
                    return qbOrderPushMap;
                }else if(PlatformEnum.FULU.getCode().equals(nationwideValue)){
                    Map qbOrderPushMap =  orderPushFuluService.fuliPlaceOrder(transactionDto, userRelate);
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
                    Map qbOrderPushMap =  orderPushShuShanService.shushanPlaceOrder(transactionDto, userRelate);
                    //蜀山地区失败后，走福禄的全国
                    if(qbOrderPushMap.get("fail") != null){
                        transactionDto.setBuyerArea("未知");
                        Map qbOrderPushMapFulu = orderPushFuluService.fuliPlaceOrder(transactionDto, userRelate);
                        //修改失败订单状态
                        updataOrderStatus(originalOrder, qbOrderPushMapFulu);
                        return qbOrderPushMapFulu;
                    }
                    return qbOrderPushMap;
                }else if(PlatformEnum.FULU.getCode().equals(regionValue)){
                    Map qbOrderPushMap = orderPushFuluService.fuliPlaceOrder(transactionDto, userRelate);
                    //修改失败订单状态
                    updataOrderStatus(originalOrder, qbOrderPushMap);
                    return qbOrderPushMap;
                }
            }
        }else if (type.equals(GoodsRelateTypeEnum.DNF.getCode()) || type.equals(GoodsRelateTypeEnum.LOL.getCode())){
            //DNF或者LOL 净蓝下单
            Map orderPushMap = orderPushJingLanService.jinglanPlaceOrder(transactionDto, userRelate);
            //修改失败订单状态
            updataOrderStatus(originalOrder, orderPushMap);
            return orderPushMap;
        }else {

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
        paramMap.put("sign", AccountUtils.getAqusuoSign(paramMap, UserConstants.appSecret));

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
    public List<Transaction> findListByParam(Transaction transaction) {
       return transactionMapper.findListByParam(transaction);
    }



}
