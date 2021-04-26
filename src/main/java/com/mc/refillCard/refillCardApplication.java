package com.mc.refillCard;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class refillCardApplication {

    public static void main(String[] args) {
//        //账号余额
//        BigDecimal balance = new BigDecimal("100.51");
//        BigDecimal orderNumBig = new BigDecimal("22");
//        //宝贝金额
//        BigDecimal nominalBig = new BigDecimal("10");
//        //统一定价
//        BigDecimal unifyPrice = new BigDecimal("0.995");
//        //总价格=统一定价*以宝贝金额*订单中宝贝数量
//        BigDecimal totalPrice = unifyPrice.multiply(nominalBig).multiply(orderNumBig);
//        //账号余额 大于订单总价格
//        if(!(balance.compareTo(totalPrice) == 1)){
//            System.out.println("余额不足");
//        }
        SpringApplication.run(refillCardApplication.class, args);
    }


}
