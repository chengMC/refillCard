package com.mc.refillCard.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Enum.GoodsRelateTypeEnum;
import com.mc.refillCard.common.Enum.TransactionStateEnum;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.config.supplier.FuliProperties;
import com.mc.refillCard.config.supplier.MiNiDianApiProperties;
import com.mc.refillCard.dto.GoodsDto;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.*;
import com.mc.refillCard.service.*;
import com.mc.refillCard.util.AccountUtils;
import com.mc.refillCard.util.XmlUtils;
import com.mc.refillCard.vo.TaobaoDoMemoUpdateVo;
import com.mc.refillCard.vo.TaobaoTransactionVo;
import fulu.sup.open.api.core.MethodConst;
import fulu.sup.open.api.model.InputProductTemplateDto;
import fulu.sup.open.api.sdk.DefaultOpenApiClient;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    @Lazy
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserRelateService userRelateService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsRelateFuluService goodsRelateFuluService;
    @Autowired
    private NationwideIpService nationwideIpService;
    @Autowired
    private OriginalOrderService originalOrderService;
    @Autowired
    private PlatformKeyService platformKeyService;
    @Autowired
    private GameServerService gameServerService;

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
        //json = "{\"Platform\":\"TAOBAO\",\"PlatformUserId\":\"1849101211\",\"ReceiverName\":null,\"ReceiverMobile\":null,\"ReceiverPhone\":null,\"ReceiverAddress\":null,\"BuyerArea\":\"移动\",\"ExtendedFields\":{\"ReceiverCity\":\"运城市\",\"ReceiverState\":\"山西省\"},\"Tid\":\"1829432917194238450\",\"TidStr\":\"1829432917194238450\",\"Status\":\"WAIT_SELLER_SEND_GOODS\",\"SellerNick\":\"阿小1554\",\"BuyerNick\":\"t_1510402241453_0796\",\"Type\":null,\"BuyerMessage\":\"910490431\",\"Price\":\"1.20\",\"Num\":1,\"TotalFee\":\"1.20\",\"Payment\":\"1.20\",\"PayTime\":null,\"PicPath\":\"https://img.alicdn.com/bao/uploaded/i2/1849101211/O1CN014vkdB41KogJfh9Crm_!!1849101211.jpg\",\"PostFee\":\"0.00\",\"Created\":\"2021-05-28 13:36:18\",\"TradeFrom\":\"WAP,WAP\",\"Orders\":[{\"Oid\":\"1829432917194238450\",\"OidStr\":\"1829432917194238450\",\"NumIid\":645045852023,\"OuterIid\":null,\"OuterSkuId\":null,\"Title\":\"游戏周边q币dnf地下城五一国庆套点券DNF点卷支持花坝收藏卡\",\"Price\":\"1.20\",\"Num\":1,\"TotalFee\":\"1.20\",\"Payment\":\"1.20\",\"PicPath\":\"https://img.alicdn.com/bao/uploaded/i2/1849101211/O1CN014vkdB41KogJfh9Crm_!!1849101211.jpg\",\"SkuId\":\"4820536282214\",\"SkuPropertiesName\":\"颜色分类:1Q币【每日特惠 备注号码】\",\"DivideOrderFee\":null,\"PartMjzDiscount\":null}],\"SellerMemo\":null,\"SellerFlag\":0,\"CreditCardFee\":null}";

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
        //处理非实体店铺订单备注问题
        if(transactionDto.getReceiverAddress() == null){
            transactionDto.setReceiverAddress(transactionDto.getBuyerMessage());
        }
        List<OriginalOrderDto> orders = transactionDto.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).setId(resultOrders.get(i).getId());
        }
        //判断账号
        String platformUserId = transactionDto.getPlatformUserId();
        String sellerNick = transactionDto.getSellerNick();
        //根据平台用户id查询对照关系
        UserRelate userRelate = userRelateService.findByPlatformUserId(platformUserId);
        //第一次先创建用户和对照关系
        if(userRelate == null){
            userRelate = userService.save(platformUserId, sellerNick);
        }
        String accessToken = userRelate.getAccessToken();
        if(userRelate == null || StringUtils.isEmpty(accessToken)){
            String fail = "订单推送失败，请先填写相关配置";
            //订单失败修改状态
            saveFailOrderStatus(orders, fail);
            return JSON.toJSONString(Result.fall(fail));
        }
        User user = userService.findPlatformUserId(platformUserId);
        if(user == null){
            String fail = "未找到用户或用户账号已被冻结，请联系管理员";
            //订单失败修改状态
            saveFailOrderStatus(orders, fail);
            return JSON.toJSONString(Result.fall(fail));
        }
        //推送订单
        System.out.println(transactionDto);
        Map resultMap = transactionService.placeOrder(transactionDto, userRelate);
        if(resultMap.get("fail") != null){

            //更新订单备注
            try {
                Boolean aBoolean = originalOrderService.failMemoUpdate(tid,accessToken);
                if (!aBoolean) {
                    log.error("更新订单备注失败");
                }
            } catch (UnsupportedEncodingException e) {
                log.error("更新订单备注失败："+ e);
            } catch (NoSuchAlgorithmException e) {
                log.error("更新订单备注失败："+ e);
            }

            String fail = String.valueOf(resultMap.get("fail"));
            //订单失败修改状态
            saveFailOrderStatus(orders, fail);
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

    private void saveFailOrderStatus(List<OriginalOrderDto> orders, String fail) {
        for (OriginalOrderDto order : orders) {
            OriginalOrder originalOrder = originalOrderService.findById(order.getId());
            originalOrder.setOrderStatus(TransactionStateEnum.FAIL.getCode());
            originalOrder.setFailReason(fail);
            originalOrder.setUpdateTime(new Date());
            originalOrderService.update(originalOrder);
        }
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
        String gameServerName="所在区/服:艾欧尼亚\n" +
        "游戏账号:569741817\n" +
        "备注:";
        //获取到LOL所有区信息
        List<GameServer> gameServers = gameServerService.findListByGoodType(GoodsRelateTypeEnum.LOL.getCode().longValue());
        //匹配区域
        GameServer matchingGameServer = null;
        log.info("gameServerName:"+gameServerName);
        for (GameServer gameServer : gameServers) {
            //匹配游戏区域
            String areaName = gameServer.getAreaName();
            log.info("areaName:"+areaName);
            if (gameServerName.indexOf(areaName) > -1) {
                matchingGameServer = gameServer;
            }
        }
        System.out.println(matchingGameServer.getAreaName());

        String json ="{\n" +
                "  \"code\" : 0,\n" +
                "  \"msg\" : \"success\",\n" +
                "  \"data\" : [ {\n" +
                "    \"value\" : \"100002\",\n" +
                "    \"name\" : \"艾欧尼亚 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100003\",\n" +
                "    \"name\" : \"比尔吉沃特 网通\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100004\",\n" +
                "    \"name\" : \"祖安 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100005\",\n" +
                "    \"name\" : \"诺克萨斯 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100006\",\n" +
                "    \"name\" : \"德玛西亚 网通\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100007\",\n" +
                "    \"name\" : \"班德尔城 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100008\",\n" +
                "    \"name\" : \"皮尔特沃夫 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100009\",\n" +
                "    \"name\" : \"战争学院 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100010\",\n" +
                "    \"name\" : \"弗雷尔卓德 网通\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100011\",\n" +
                "    \"name\" : \"巨神峰 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100012\",\n" +
                "    \"name\" : \"雷瑟守备 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100013\",\n" +
                "    \"name\" : \"无畏先锋 网通\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100014\",\n" +
                "    \"name\" : \"裁决之地 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100015\",\n" +
                "    \"name\" : \"黑色玫瑰 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100016\",\n" +
                "    \"name\" : \"暗影岛 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100017\",\n" +
                "    \"name\" : \"钢铁烈阳 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100018\",\n" +
                "    \"name\" : \"恕瑞玛 网通\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100019\",\n" +
                "    \"name\" : \"均衡教派 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100020\",\n" +
                "    \"name\" : \"水晶之痕 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100021\",\n" +
                "    \"name\" : \"教育网专区\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100022\",\n" +
                "    \"name\" : \"影流 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100023\",\n" +
                "    \"name\" : \"守望之海 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100024\",\n" +
                "    \"name\" : \"扭曲丛林 网通\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100025\",\n" +
                "    \"name\" : \"征服之海 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100026\",\n" +
                "    \"name\" : \"卡拉曼达 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100027\",\n" +
                "    \"name\" : \"皮城警备 电信\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100028\",\n" +
                "    \"name\" : \"巨龙之巢 网通\"\n" +
                "  }, {\n" +
                "    \"value\" : \"100029\",\n" +
                "    \"name\" : \"男爵领域 全网络\"\n" +
                "  } ]\n" +
                "}";
        Map params = JSON.parseObject(json);
        String data = String.valueOf(params.get("data"));
        List<Map> maps = JSON.parseArray(data,Map.class);
//        for (Map map : maps) {
//            String value =  String.valueOf(map.get("value"));
//            String name =  String.valueOf(map.get("name"));
//            name = name.replaceAll(" ","");
//            if(name.indexOf("电信")>-1){
//                name = name.replaceAll("电信","");
//            }
//            if(name.indexOf("网通")>-1){
//                name = name.replaceAll("网通","");
//            }
//            if(name.indexOf("全网络")>-1){
//                name = name.replaceAll("全网络","");
//            }
//            GameServer gameServer = new GameServer();
//            gameServer.setAreaValue(value);
//            gameServer.setAreaName(name);
//            gameServer.setAreaNameOperator(String.valueOf(map.get("name")));
//            gameServer.setGoodTypeId(6L);
//            gameServer.setGoodTypeName("DNF");
//            gameServerService.add(gameServer);
//        }

//        Map paramsdata = JSON.parseObject(data);
//        getSign(paramsdata);
    }


    private void getSign(Map<String, String> params){
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        for (String key : keys) {
            String value = params.get(key).replaceAll(" ","");
            if(value.indexOf("电信")>-1){
                value = value.replaceAll("电信","");
            }
            if(value.indexOf("网通")>-1){
                value = value.replaceAll("网通","");
            }
            if(value.indexOf("全网络")>-1){
                value = value.replaceAll("网通","");
            }
            GameServer gameServer = new GameServer();
            gameServer.setAreaValue(key);
            gameServer.setAreaName(value);
            gameServer.setAreaNameOperator(params.get(key));
            gameServer.setGoodTypeId(6L);
            gameServer.setGoodTypeName("DNF");
            gameServerService.add(gameServer);
        }
    }

    @GetMapping("/changeTBOrderStatus")
    public void changeTBOrderStatus() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String tid = "1678240369790554917";
        String token = "TbAldssngwbswy3kpt5tu6ykacpz4tu3xkaahzvgbcyp228vva";
        Boolean b = originalOrderService.changeTBOrderStatus(tid,token);

    }


    @GetMapping("/failMemoUpdate")
    public void failMemoUpdate(String tid) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        String tid = "1767584306739696261";
        String token = "TbAldssngwbswy3kpt5tu6ykacpz4tu3xkaahzvgbcyp228vva";
        //更新卖家备注
        try {
            Boolean aBoolean = originalOrderService.failMemoUpdate(tid,token);
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
        dataMap.put("MerchantID",MiNiDianApiProperties.getMerchantID());
        String shuShanSign = AccountUtils.getShuShanSign(dataMap, MiNiDianApiProperties.getAppSecret());
        dataMap.put("Sign",shuShanSign);

        //接口调用
        String result = HttpRequest.post("http://api.minidianwl.shucard.com/Api/QueryMerchant")
                .form(dataMap)
//                .addHeaders(headerMap)
                .execute()
                .body();
        System.out.println(result);
        String json = XmlUtils.xml2json(result);

        System.out.println(json);

    }


    /***
     * 查询PlatformKey全部数据
     * @return
     */
    @GetMapping(value = "/update/platformKey/{id}" )
    public Result updatePlatformKey(@PathVariable String id){
        PlatformKey platformKey = platformKeyService.updatePlatformKey(id);
        return Result.success("修改成功",platformKey) ;
    }

    @GetMapping("/from")
    public void getInfoFrom() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String fuliAppKey ="+gViu2wfFwlHcca4U4FR+ijRebmrPjLu9+9JLeKukYjh3aqGj5g1BLO+soJ7Npix";
        String fuluSercret = "77126992853e4fcabf04014ce75afbfa";

        //通过福禄API下单
        DefaultOpenApiClient client =
                new DefaultOpenApiClient(FuliProperties.getUrl(), fuliAppKey, fuluSercret, MethodConst.OPEN_API_GOODS_TEMPLATE_GET);
        InputProductTemplateDto dto = new InputProductTemplateDto();
        dto.setTemplateId("05a6216a-4da6-46d6-99c6-bd03f86ccbb4");

        client.setBizObject(dto);
        System.out.println("福禄"+"下单请求："+JSON.toJSONString(dto));
        String result = client.excute();
        Map resultMap = JSON.parseObject(result);
        System.out.println(resultMap);

//        HashMap<String, Object> dataMap = new HashMap<>();
//        dataMap.put("action","placeOrder");
//        dataMap.put("requestTime", DateUtil.now());
//        dataMap.put("merAccount", "test");
//        dataMap.put("businessType", "13");
//        dataMap.put("merOrderNo", "1785427671079037869");
//        dataMap.put("rechargeAccount", "569741817");
//        dataMap.put("productId", "1078");
//        dataMap.put("rechargeValue", "1");
//        String shuShanSign = AccountUtils.getjinglanSign(dataMap, "0cbc6611f5540bd0809a388dc95a615b");
//        dataMap.put("sign",shuShanSign);
//
//        //接口调用
//        String result = HttpRequest.post("http://123.56.242.212:25000/mch/api/v2/form")
//                .form(dataMap)
////                .addHeaders(headerMap)
//                .execute()
//                .body();
//        System.out.println(result);
//        Map resultMap = JSON.parseObject(result);
//        String resultCode = String.valueOf(resultMap.get("resultCode"));
//        if(!"0".equals(resultCode)){
//            String resultMsg = String.valueOf(resultMap.get("resultMsg"));
//            System.out.println(resultMsg);
//        }
//
//
//        HashMap<String, Object> dataMap = new HashMap<>();
//        dataMap.put("action","queryOrder");
//        dataMap.put("requestTime", DateUtil.now());
//        dataMap.put("merAccount", JinglanApiProperties.getMerAccount());
//        dataMap.put("merOrderNo", "1785427671079037869");
//        String shuShanSign = AccountUtils.getjinglanSign(dataMap, JinglanApiProperties.getAppSecret());
//        dataMap.put("sign",shuShanSign);
//
//        //接口调用
//        String result = HttpRequest.post("http://123.56.242.212:25000/mch/api/v2/form")
//                .form(dataMap)
//                .execute()
//                .body();
//        System.out.println(result);
//        Map resultMap = JSON.parseObject(result);
//        String resultCode = String.valueOf(resultMap.get("resultCode"));
//
//        String data = String.valueOf(resultMap.get("data"));
//        Map resultDataMap = JSON.parseObject(data);
//        System.out.println(resultDataMap.get("orderNo"));
//        if(!"0".equals(resultCode)){
//            String resultMsg = String.valueOf(resultMap.get("resultMsg"));
//            System.out.println(resultMsg);
//        }


    }





}
