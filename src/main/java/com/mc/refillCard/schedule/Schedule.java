package com.mc.refillCard.schedule;

import com.mc.refillCard.service.OriginalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @Author: MC
 * @Date2021-03-22
 */

@Component
public class Schedule {

    private final OriginalOrderService originalOrderService;

    @Autowired
    public Schedule(OriginalOrderService originalOrderService) {
        this.originalOrderService = originalOrderService;
    }


    /**
     *  5分钟判断一次无账号订单，修改为失败
     */
    @Scheduled(fixedRate = 1000 * 60 * 5 )
    public void updateStatus() {
        originalOrderService.orderFail();
    }


}
