package com.mc.refillCard.service;

import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.UserRelate;

import java.util.Map;

/**
 *
 * 净蓝订单推送
 */
public interface OrderPushJingLanService {

    /**
     * 福禄下单
     * @param transactionDto
     * @return
     */
    Map jinglanPlaceOrder(TransactionDto transactionDto, UserRelate userRelate);


}
