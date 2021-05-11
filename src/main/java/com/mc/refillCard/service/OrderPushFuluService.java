package com.mc.refillCard.service;

import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.UserRelate;

import java.util.Map;

/**
 *
 * 福禄订单推送
 */
public interface OrderPushFuluService {

    /**
     * 福禄下单
     * @param transactionDto
     * @return
     */
    Map fuliPlaceOrder(TransactionDto transactionDto, UserRelate userRelate);

}
