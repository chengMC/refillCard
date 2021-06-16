package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Enum.TransactionStateEnum;
import com.mc.refillCard.common.UserConstants;
import com.mc.refillCard.dao.OriginalOrderMapper;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.OriginalOrderQueryDto;
import com.mc.refillCard.entity.*;
import com.mc.refillCard.service.OriginalOrderService;
import com.mc.refillCard.service.UserPricingService;
import com.mc.refillCard.service.UserService;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.vo.OriginalOrderVo;
import com.mc.refillCard.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * @Author: MC
 * @Description: OriginalOrder业务层接口实现类
 * @Date 2021-3-21 14:41:57
 *****/
@Service
public class OriginalOrderServiceImpl implements OriginalOrderService {

    @Autowired
    private OriginalOrderMapper originalOrderMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserPricingService userPricingService;
    @Autowired
    private OriginalOrderService originalOrderService;

    /**
     OriginalOrder条件+分页查询
     @param originalOrderDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(OriginalOrderQueryDto originalOrderDto, int page, int size) {
        UserVo user = (UserVo) SecurityUtils.getSubject().getPrincipal();
        //管理员
        if(!"4".equals(user.getRoleId())){
            originalOrderDto.setUserId(user.getId());
        }
        //分页
        PageHelper.startPage(page, size);
        List<OriginalOrderVo> originalOrderVos = originalOrderMapper.findPageVoByExample(originalOrderDto);
        //执行搜索
        return new PageInfo(originalOrderVos);
    }

    /**
     * OriginalOrder条件查询
     * @param originalOrder
     * @return
     */
    @Override
    public List<OriginalOrder> findList(OriginalOrder originalOrder){
        //根据构建的条件查询数据
        return originalOrderMapper.selectByExample(originalOrder);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        originalOrderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改OriginalOrder
     * @param originalOrder
     */
    @Override
    public void update(OriginalOrder originalOrder){
        originalOrderMapper.updateByPrimaryKeySelective(originalOrder);
    }

    /**
     * 修改OriginalOrder
     * @param originalOrderDto
     */
    @Override
    public void updateDto(OriginalOrderDto originalOrderDto){
        OriginalOrder originalOrder = BeanUtil.copyProperties(originalOrderDto, OriginalOrder.class);
        originalOrderMapper.updateByPrimaryKeySelective(originalOrder);
    }

    /**
     * 增加OriginalOrder
     * @param originalOrder
     */
    @Override
    public void add(OriginalOrder originalOrder){
        originalOrderMapper.insertSelective(originalOrder);
    }

    /**
     * 增加OriginalOrder
     * @param originalOrderDto
     */
    @Override
    public void addDto(OriginalOrderDto originalOrderDto){
        OriginalOrder originalOrder = BeanUtil.copyProperties(originalOrderDto, OriginalOrder.class);
        originalOrderMapper.insertSelective(originalOrder);
    }

    /**
     * 根据ID查询OriginalOrder
     * @param id
     * @return
     */
    @Override
    public OriginalOrder findById(Long id){
        return  originalOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询OriginalOrder全部数据
     * @return
     */
    @Override
    public List<OriginalOrder> findAll() {
        return originalOrderMapper.findAll();
    }

    /**
     * 判断余额
     * @param orderDto
     * @param goodsRelateFulu
     * @param userRelate
     * @return
     */
    @Override
    public Map judgeBalance(OriginalOrderDto orderDto,GoodsRelateFulu goodsRelateFulu,UserRelate userRelate,Integer type) {
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
     * 更新价格
     *
     * @param orderDto
     * @param goodsRelateFulu
     * @param userRelate
     * @param type
     */
    @Override
    public void updateBalance(OriginalOrderDto orderDto,GoodsRelateFulu goodsRelateFulu,UserRelate userRelate,Integer type) {
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
        paramMap.put("sign", AccountUtils.getAqusuoSign(paramMap, UserConstants.appSecret));

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
        Boolean change = false;
        Map resultMap = JSON.parseObject(result);
        if("true".equals(String.valueOf(resultMap.get("IsSuccess")))){
            change= true;
        }
        return change;
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
        paramMap.put("sign", AccountUtils.getAqusuoSign(paramMap, UserConstants.appSecret));

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
        Boolean change = false;
        Map resultMap = JSON.parseObject(result);
        if("true".equals(String.valueOf(resultMap.get("IsSuccess")))){
            change= true;
        }
        return change;
    }

    @Override
    public void orderFail() {
        originalOrderMapper.orderFail();
    }

    @Override
    public List<OriginalOrder> findListByFail() {
        return originalOrderMapper.findListByFail();
    }

}
