package com.mc.refillCard.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Enum.TransactionStateEnum;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.config.fulu.ShuShanApiProperties;
import com.mc.refillCard.dto.GoodsDto;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.*;
import com.mc.refillCard.service.*;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.util.XmlUtils;
import com.mc.refillCard.vo.TaobaoDoMemoUpdateVo;
import com.mc.refillCard.vo.TaobaoTransactionVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-3-20 20:24:42
 *****/
@Log4j2
@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class TransactionController {

    private static String appSecret;
    @Value("${agiso.appSecret}")
    public static void setAppSecret(String appSecret) {
        TransactionController.appSecret = appSecret;
    }

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserRelateService userRelateService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsRelateFuluService goodsRelateFuluService;
    @Autowired
    private NationwideIpService nationwideIpService;
    @Autowired
    private OriginalOrderService originalOrderService;

    /***
     * 多条件搜索transaction数据
     * @param transactionDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(TransactionDto transactionDto,@PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = transactionService.findPage(transactionDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    @RequestMapping("/push/receiveMsg")
    public String receivedMsg(@RequestParam("timestamp") long timestamp, @RequestParam("json") String json,
                              @RequestParam("aopic") long aopic, @RequestParam("sign") String sign) {
//          json = "{\"Platform\":\"TAOBAO\",\"PlatformUserId\":\"234234234\",\"ReceiverName\":null,\"ReceiverMobile\":null,\"ReceiverPhone\":null,\"ReceiverAddress\":\"QQ:569741817\\r\\n备注:\",\"BuyerArea\":\"湖北\",\"ExtendedFields\":{},\"Tid\":16608731045877812697,\"TidStr\":\"1660873104587781269\",\"Status\":\"WAIT_SELLER_SEND_GOODS\",\"SellerNick\":\"劲舞团24小时充值\",\"BuyerNick\":\"启动蓝色\",\"Type\":null,\"BuyerMessage\":null,\"Price\":\"9.99\",\"Num\":3,\"TotalFee\":\"9.99\",\"Payment\":\"9.99\",\"PayTime\":null,\"PicPath\":\"https://img.alicdn.com/bao/uploaded/i1/T1aiVpXoBHXXb1upjX.jpg\",\"PostFee\":\"0.00\",\"Created\":\"2021-03-21 19:18:40\",\"TradeFrom\":\"WAP,WAP\",\"Orders\":[{\"Oid\":1660873104587781269,\"OidStr\":\"1660873104587781269\",\"NumIid\":640359390526,\"OuterIid\":\"10\",\"OuterSkuId\":null,\"Title\":\"【谨防诈骗】腾讯qq币10qbQ币10个q币10qb10个qb10QB 自动充值\",\"Price\":\"9.99\",\"Num\":3,\"TotalFee\":\"9.99\",\"Payment\":\"9.99\",\"PicPath\":\"https://img.alicdn.com/bao/uploaded/i1/T1aiVpXoBHXXb1upjX.jpg\",\"SkuId\":null,\"SkuPropertiesName\":null,\"DivideOrderFee\":null,\"PartMjzDiscount\":null}],\"SellerMemo\":null,\"SellerFlag\":0,\"CreditCardFee\":null}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("json", json);
        map.put("timestamp", String.valueOf(timestamp));
        System.out.println("json---"+json);

        TransactionDto transactionDto = JSON.parseObject(json, TransactionDto.class);
        if(CollUtil.isEmpty(transactionDto.getOrders())){
            return JSON.toJSONString(Result.fall("没有订单信息"));
        }
        String tid = transactionDto.getTid();
        Transaction transaction = new Transaction();
        transaction.setStatus(transactionDto.getStatus());
        transaction.setTid(tid);
        List<Transaction> list = transactionService.findList(transaction);
        if(CollUtil.isNotEmpty(list)){
            Transaction transactionEntity = list.get(0);
            Integer state = transactionEntity.getState();
            if(state == 2){
                TaobaoTransactionVo taobaoTransactionVo = new TaobaoTransactionVo();
                taobaoTransactionVo.setDoDummySend(true);
                taobaoTransactionVo.setAliwwMsg("充值成功");
                TaobaoDoMemoUpdateVo taobaoDoMemoUpdateVo = new TaobaoDoMemoUpdateVo();
                taobaoDoMemoUpdateVo.setFlag(-1);
                taobaoDoMemoUpdateVo.setMemo("");
                taobaoTransactionVo.setDoMemoUpdate(taobaoDoMemoUpdateVo);
                return JSON.toJSONString(taobaoTransactionVo);
            }else  if(state == 1){
                return JSON.toJSONString(Result.fall("订单ID："+ tid +"已存在。待推送"));
            }else  if(state == 3){
                return JSON.toJSONString(Result.fall("订单ID："+ tid +"已存在。推送失败"));
            }else{
                return JSON.toJSONString(Result.fall("订单ID："+ tid +"已存在"));
            }
        }
        //保存订单
        TransactionDto resultTransactions = transactionService.addDto(transactionDto);
        List<OriginalOrderDto> resultOrders = resultTransactions.getOrders();
        transactionDto.setId(resultTransactions.getId());
        List<OriginalOrderDto> orders = transactionDto.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).setId(resultOrders.get(i).getId());
        }
        //判断账号
        String platformUserId = transactionDto.getPlatformUserId();
        String sellerNick = transactionDto.getSellerNick();
        User user = userService.findPlatformUserId(platformUserId);
        if(user == null){
            return JSON.toJSONString(Result.fall("未找到用户或用户账号已被冻结，请联系管理员"));
        }
        //根据平台用户id查询对照关系
        UserRelate userRelate = userRelateService.findByPlatformUserId(platformUserId);
        //第一次先创建用户和对照关系
        if(userRelate == null){
            userRelate = userService.save(platformUserId, sellerNick);
        }
        String accessToken = userRelate.getAccessToken();
        if(userRelate == null || StringUtils.isEmpty(accessToken)){
            return JSON.toJSONString(Result.fall("订单推送失败，请先填写相关配置"));
        }
        //推送订单
        System.out.println(transactionDto);
        Map resultMap = transactionService.placeOrder(transactionDto, userRelate);
        if(resultMap.get("fail") != null){

            //更新订单备注
            try {
                Boolean aBoolean = transactionService.failMemoUpdate(tid,accessToken);
                if (!aBoolean) {
                    log.error("更新订单备注失败");
                }
            } catch (UnsupportedEncodingException e) {
                log.error("更新订单备注失败："+ e);
            } catch (NoSuchAlgorithmException e) {
                log.error("更新订单备注失败："+ e);
            }

            String fail = String.valueOf(resultMap.get("fail"));
            for (OriginalOrderDto order : orders) {
                OriginalOrder originalOrder = originalOrderService.findById(order.getId());
                originalOrder.setOrderStatus(TransactionStateEnum.FAIL.getCode());
                originalOrder.setFailReason(fail);
                originalOrder.setUpdateTime(new Date());
                originalOrderService.update(originalOrder);
            }
            return JSON.toJSONString(Result.fall("推送失败：",fail));
        }else if(resultMap.get("success") != null){
            TaobaoTransactionVo taobaoTransactionVo = new TaobaoTransactionVo();
            taobaoTransactionVo.setDoDummySend(true);
            taobaoTransactionVo.setAliwwMsg("充值成功");
            TaobaoDoMemoUpdateVo taobaoDoMemoUpdateVo = new TaobaoDoMemoUpdateVo();
            taobaoDoMemoUpdateVo.setFlag(1);
            taobaoDoMemoUpdateVo.setMemo("订单充值成功");
            taobaoTransactionVo.setDoMemoUpdate(taobaoDoMemoUpdateVo);
            return JSON.toJSONString(Result.success("推送成功",taobaoTransactionVo));
        }
        return JSON.toJSONString(Result.fall("推送失败"));
    }


    @GetMapping("/location")
    public void location() throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        String black = "tb91269863,汪彦辛,tb1149279783,t_1515504973110_0990,74111111gy,grp丶布勒斯特,tb883468160,国家认证顶级保镖,xiaochunmei1978,弥足珍贵的字,tb656361274,完整子豪,t_1487937049091_0196,tb718443754,tb773839588,slyans,我叫空白的美男子,不忘初心0075,金木丶研尼玛,心！剑天,卖游戏和代练,tb155256744，baozhong29279961,tb788015418,tb479479720,mxzsbd,宝专用号,ddddd390020350,t_1513062352824_0957,gyp5779310235,王东梅梅子95853921,t_1497365893182_0624,tb99848179,tb962157042,tb988374839,tb202927453,t_1513809298808_0761,tb927906176,tb34126745,tb5519712,tb507431253,tb19967196,tb33710731,寿光人筱泳,天喜运动系列,你旁边的小萝莉,tb3244858892,tb946612229,薛春芝魏建全,我们的天空59251739,qian19970903,燧宇玄芒柯,tb996193084,一笑倾诚,88888888wrx,t_1502098227232_0166,tb0809520_11,tb16188635,tb770952613,tb204151701,爱萝莉爱h漫,tb2036023637,tb883322078,tb492495132,支付一人666,tb670555494,挥洒小东哥丶,tb572116272,tb150107036,lkd15263686306,微暖0坏孩子,tb23920930,tb883322078,夜夜夜2596,tb06997122,t_1516435017623_0718,街道的寂寞90848980,cf1542551485,tb444157355,hf463949590,微笑的蓝天7807,花花呐波,楼下的李白哥哥,yilin程程,万志英,情战蓝狱杀手狐,tb442109275,tb50879474,tb100089835,n1u姣姣,tb88490969,取什么名字都不好1,南风iboy,找不到好的名字了20172014,tb022348138,玉面小达摩1982,dertuobcxs,梨花之殇34473948,花开花落175477020,张梓平、,t_1509779984346_0927,tb532370113,qq51071904,tb218465888,tb8135362717,jkluul,i黑猫大人,tb890169826,脚打后脑勺510,zyj99953668226,tb84564068,tb283543004,tb066853109,tb14186153,tb899521141,瑾瑜灬火,tb1027779483,zu13087915059,韩娇娇18,tb0192853915,一骑红尘傲九天,1114620960wb,tb917886335";
//        blacklistService.batchAdd(black);

//        String ip = IpUtil.getRandomIp();
//        List<SysDict> listByCode = sysDictService.findListByCode(DictCodeEnum.BAIDUAK.getName());
//        String location = "";
////        String ip ="183.95.62.69";
//        if(!"".equals(ip)){
//             location = BaiDuMapApiUtil.location(ip, listByCode);
//        }
        log.info("location1-");
//        log.error("location1112-"+location);
//        System.out.println("location"+location);

    }

    @GetMapping("/changeTBOrderStatus")
    public void changeTBOrderStatus() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String tid = "1678240369790554917";
        String token = "TbAldssngwbswy3kpt5tu6ykacpz4tu3xkaahzvgbcyp228vva";
        Boolean b = transactionService.changeTBOrderStatus(tid,token);

    }


    @GetMapping("/failMemoUpdate")
    public void failMemoUpdate(String tid) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        String tid = "1767584306739696261";
        String token = "TbAldssngwbswy3kpt5tu6ykacpz4tu3xkaahzvgbcyp228vva";
        //更新卖家备注
        try {
            Boolean aBoolean = transactionService.failMemoUpdate(tid,token);
            if (!aBoolean) {
                log.error("更新订单备注失败");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("更新订单备注失败："+ e);
        } catch (NoSuchAlgorithmException e) {
            log.error("更新订单备注失败："+ e);
        }

    }

    @GetMapping("/save/good")
    public Result saveGood(GoodsDto goodsDto) {
         goodsService.saveGood(goodsDto);
        return Result.success("修改成功");
    }

    @GetMapping("/statistics")
    public Result statistics() {
        LinkedHashMap map = nationwideIpService.statistics();
        return Result.success(map);
    }

    @GetMapping("/updateGood/{goodId}")
    public Result updateGood(@PathVariable String goodId) {
        Goods goods = goodsService.updateGood(goodId);
        return Result.success("修改成功",goods);
    }

    @PostMapping("/excel")
    public Result importData(@RequestParam("userId") String userId,@RequestParam("type") String type,
                             @RequestParam(value = "file") MultipartFile file) {
        try {
            Integer integer = goodsRelateFuluService.importExcel(userId, type, file);
            return Result.success("导入成功"+integer+"条");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fall("导入失败-"+e.getMessage());
        }
    }

    @PostMapping("/goods/excel")
    public Result goodsImportData(@RequestParam("platform") String platform,@RequestParam("type") String type,
                             @RequestParam(value = "file") MultipartFile file) {
        try {
            Integer integer = goodsService.goodsImportData(platform, type, file);
            return Result.success("导入成功"+integer+"条");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fall("导入失败-"+e.getMessage());
        }
    }


    @PostMapping("/ip/excel")
    public Result importDataIp( @RequestParam(value = "file") MultipartFile file) {
        try {
            Integer integer = nationwideIpService.importDataIp(file);
            return Result.success("导入成功"+integer+"条");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fall("导入失败-"+e.getMessage());
        }
    }


    @GetMapping("/getInfo")
    public void getInfo() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("MerchantID",ShuShanApiProperties.getMerchantID());
        String shuShanSign = AccountUtils.getShuShanSign(dataMap, ShuShanApiProperties.getAppSecret());
        dataMap.put("Sign",shuShanSign);

        //接口调用
        String result = HttpRequest.post("http://api.shushanzx.shucard.com/Api/QueryMerchant")
                .form(dataMap)
//                .addHeaders(headerMap)
                .execute()
                .body();
        System.out.println(result);
        String json = XmlUtils.xml2json(result);

        System.out.println(json);

    }




}
