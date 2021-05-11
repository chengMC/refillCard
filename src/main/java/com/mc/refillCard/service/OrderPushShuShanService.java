package com.mc.refillCard.service;

import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.UserRelate;

import java.util.Map;

/**
 *
 * 蜀山订单推送
 */
public interface OrderPushShuShanService {

    /**
     * 福禄下单
     * @param transactionDto
     * @return
     */
    Map shushanPlaceOrder(TransactionDto transactionDto, UserRelate userRelate);


}
