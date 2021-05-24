package com.mc.refillCard.service;

import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.UserRelate;

import java.util.Map;

/**
 *
 * 迷你点订单推送
 */
public interface OrderPushMiNiDianService {

    /**
     * 福禄下单
     * @param transactionDto
     * @return
     */
    Map minidianPlaceOrder(TransactionDto transactionDto, UserRelate userRelate);


}
