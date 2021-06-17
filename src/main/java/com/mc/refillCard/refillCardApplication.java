package com.mc.refillCard;


import com.mc.refillCard.util.AccountUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class refillCardApplication {

    public static void main(String[] args) {
        //充值账号
//        String receiverAddress = "所在区/服:373476573游戏账号:373476573 备注:";
//        String chargeAccount = AccountUtils.findNumber(receiverAddress);
//        System.out.println(chargeAccount);
//        String chargeAccount1="所在区/服:西北1区\n" +
//                "游戏账号:1510394449\n" +
//                "备注:";
//        String receiverAddress = chargeAccount1;
//        if(receiverAddress.indexOf("账号")>-1){
//            receiverAddress = receiverAddress.substring(receiverAddress.indexOf("账号"));
//        }
//        String chargeAccount = AccountUtils.findNumber(receiverAddress);
//        System.out.println(chargeAccount);

//        String json ="{\n" +
//                "  \"code\" : 0,\n" +
//                "  \"msg\" : \"success\",\n" +
//                "  \"data\" : [ {\n" +
//                "    \"value\" : \"100002\",\n" +
//                "    \"name\" : \"艾欧尼亚 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100003\",\n" +
//                "    \"name\" : \"比尔吉沃特 网通\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100004\",\n" +
//                "    \"name\" : \"祖安 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100005\",\n" +
//                "    \"name\" : \"诺克萨斯 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100006\",\n" +
//                "    \"name\" : \"德玛西亚 网通\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100007\",\n" +
//                "    \"name\" : \"班德尔城 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100008\",\n" +
//                "    \"name\" : \"皮尔特沃夫 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100009\",\n" +
//                "    \"name\" : \"战争学院 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100010\",\n" +
//                "    \"name\" : \"弗雷尔卓德 网通\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100011\",\n" +
//                "    \"name\" : \"巨神峰 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100012\",\n" +
//                "    \"name\" : \"雷瑟守备 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100013\",\n" +
//                "    \"name\" : \"无畏先锋 网通\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100014\",\n" +
//                "    \"name\" : \"裁决之地 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100015\",\n" +
//                "    \"name\" : \"黑色玫瑰 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100016\",\n" +
//                "    \"name\" : \"暗影岛 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100017\",\n" +
//                "    \"name\" : \"钢铁烈阳 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100018\",\n" +
//                "    \"name\" : \"恕瑞玛 网通\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100019\",\n" +
//                "    \"name\" : \"均衡教派 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100020\",\n" +
//                "    \"name\" : \"水晶之痕 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100021\",\n" +
//                "    \"name\" : \"教育网专区\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100022\",\n" +
//                "    \"name\" : \"影流 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100023\",\n" +
//                "    \"name\" : \"守望之海 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100024\",\n" +
//                "    \"name\" : \"扭曲丛林 网通\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100025\",\n" +
//                "    \"name\" : \"征服之海 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100026\",\n" +
//                "    \"name\" : \"卡拉曼达 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100027\",\n" +
//                "    \"name\" : \"皮城警备 电信\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100028\",\n" +
//                "    \"name\" : \"巨龙之巢 网通\"\n" +
//                "  }, {\n" +
//                "    \"value\" : \"100029\",\n" +
//                "    \"name\" : \"男爵领域 全网络\"\n" +
//                "  } ]\n" +
//                "}";
//        Map orderDtoStrResult = JSON.parseObject(json);



        SpringApplication.run(refillCardApplication.class, args);
    }



}
