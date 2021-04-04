package com.mc.refillCard.controller;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Enum.DictCodeEnum;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.GoodsDto;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.SysDict;
import com.mc.refillCard.entity.Transaction;
import com.mc.refillCard.entity.UserRelate;
import com.mc.refillCard.service.*;
import com.mc.refillCard.util.BaiDuMapApiUtil;
import com.mc.refillCard.util.IpUtil;
import com.mc.refillCard.vo.TaobaoDoMemoUpdateVo;
import com.mc.refillCard.vo.TaobaoTransactionVo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-3-20 20:24:42
 *****/
@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class TransactionController {

    private static Logger log = Logger.getLogger(TransactionController.class);

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
//          json = "{\"Platform\":\"TAOBAO\",\"PlatformUserId\":\"234234234\",\"ReceiverName\":null,\"ReceiverMobile\":null,\"ReceiverPhone\":null,\"ReceiverAddress\":\"QQ:569741817\\r\\n备注:\",\"BuyerArea\":null,\"ExtendedFields\":{},\"Tid\":1660873104587781269,\"TidStr\":\"1660873104587781269\",\"Status\":\"WAIT_SELLER_SEND_GOODS\",\"SellerNick\":\"劲舞团24小时充值\",\"BuyerNick\":\"启动蓝色\",\"Type\":null,\"BuyerMessage\":null,\"Price\":\"9.99\",\"Num\":1,\"TotalFee\":\"9.99\",\"Payment\":\"9.99\",\"PayTime\":null,\"PicPath\":\"https://img.alicdn.com/bao/uploaded/i1/T1aiVpXoBHXXb1upjX.jpg\",\"PostFee\":\"0.00\",\"Created\":\"2021-03-21 19:18:40\",\"TradeFrom\":\"WAP,WAP\",\"Orders\":[{\"Oid\":1660873104587781269,\"OidStr\":\"1660873104587781269\",\"NumIid\":640359390526,\"OuterIid\":\"10\",\"OuterSkuId\":null,\"Title\":\"【谨防诈骗】腾讯qq币10qbQ币10个q币10qb10个qb10QB 自动充值\",\"Price\":\"9.99\",\"Num\":1,\"TotalFee\":\"9.99\",\"Payment\":\"9.99\",\"PicPath\":\"https://img.alicdn.com/bao/uploaded/i1/T1aiVpXoBHXXb1upjX.jpg\",\"SkuId\":null,\"SkuPropertiesName\":null,\"DivideOrderFee\":null,\"PartMjzDiscount\":null}],\"SellerMemo\":null,\"SellerFlag\":0,\"CreditCardFee\":null}";
        Map<String, String> map = new HashMap<String, String>();
        map.put("json", json);
        map.put("timestamp", String.valueOf(timestamp));
        System.out.println("json---"+json);

        TransactionDto transactionDto = JSON.parseObject(json, TransactionDto.class);
        if(CollUtil.isEmpty(transactionDto.getOrders())){
            return JSON.toJSONString(Result.fall("没有订单信息"));
        }
        Transaction transaction = new Transaction();
        transaction.setStatus(transactionDto.getStatus());
        transaction.setTid(transactionDto.getTid());
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
                return JSON.toJSONString(Result.fall("订单ID："+transactionDto.getTid()+"已存在。待推送"));
            }else  if(state == 3){
                return JSON.toJSONString(Result.fall("订单ID："+transactionDto.getTid()+"已存在。推送失败"));
            }else{
                return JSON.toJSONString(Result.fall("订单ID："+transactionDto.getTid()+"已存在"));
            }
        }
        //保存订单
        Long transactionId = transactionService.addDto(transactionDto);
        transactionDto.setId(transactionId);
        String platformUserId = transactionDto.getPlatformUserId();
        String sellerNick = transactionDto.getSellerNick();
        //根据平台用户id查询对照关系
        UserRelate userRelate = userRelateService.findByPlatformUserId(platformUserId);
        //第一次先创建用户和对照关系
        if(userRelate == null){
            userRelate = userService.save(platformUserId, sellerNick);
        }
        if(userRelate == null || StringUtils.isEmpty(userRelate.getFuluSercret())){
            return JSON.toJSONString(Result.fall("订单推送失败，请先填写相关配置"));
        }

        //推送订单
        System.out.println(transactionDto);
        Map resultMap = transactionService.placeOrder(transactionDto, userRelate);
        if(resultMap.get("fail") != null){
            Object fail = resultMap.get("fail");
            return JSON.toJSONString(Result.fall("推送失败：",fail));
        }else if(resultMap.get("success") != null){
            TaobaoTransactionVo taobaoTransactionVo = new TaobaoTransactionVo();
            taobaoTransactionVo.setDoDummySend(true);
            taobaoTransactionVo.setAliwwMsg("充值成功");
            TaobaoDoMemoUpdateVo taobaoDoMemoUpdateVo = new TaobaoDoMemoUpdateVo();
            taobaoDoMemoUpdateVo.setFlag(-1);
            taobaoDoMemoUpdateVo.setMemo("");
            taobaoTransactionVo.setDoMemoUpdate(taobaoDoMemoUpdateVo);
            return JSON.toJSONString(Result.success("推送成功",taobaoTransactionVo));
        }
        return JSON.toJSONString(Result.fall("推送失败"));
    }


    @GetMapping("/location")
    public void location() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String ip = IpUtil.getRandomIp();
        List<SysDict> listByCode = sysDictService.findListByCode(DictCodeEnum.BAIDUAK.getName());
        String location = "";
//        String ip ="183.95.62.69";
        if(!"".equals(ip)){
             location = BaiDuMapApiUtil.location(ip, listByCode);
        }
        log.info("location1-"+location);
        log.error("location1112-"+location);
        System.out.println("location"+location);

    }

    @GetMapping("/changeTBOrderStatus")
    public void changeTBOrderStatus() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String tid = "1678240369790554917";
        String token = "TbAldssngwbswy3kpt5tu6ykacpz4tu3xkaahzvgbcyp228vva";
        Boolean b = transactionService.changeTBOrderStatus(tid,token);

        System.out.println(b);

    }

    @GetMapping("/save/good")
    public Result saveGood(GoodsDto goodsDto) {
         goodsService.saveGood(goodsDto);
        return Result.success("修改成功");
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

}
