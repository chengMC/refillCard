package com.mc.refillCard.service;

import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.UserRelate;

import java.util.Map;

/**
 *
 * 行走订单推送
 */
public interface OrderXingZouService {

    /**
     * 福禄下单
     * @param transactionDto
     * @return
     */
    Map xingzouPlaceOrder(TransactionDto transactionDto, UserRelate userRelate);


}
