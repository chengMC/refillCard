package com.mc.refillCard.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.mc.refillCard.entity.SysDict;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *  百度地图接口调用工具类
 */
@Log4j2
@Component
public class BaiDuMapApiUtil {


    private static String ak;
    @Value("${baiduApi.ak}")
    public void setAk(String ak) {
        BaiDuMapApiUtil.ak = ak;
    }

    /**
     * 百度地图API
     * http://lbsyun.baidu.com/index.php?title=webapi/ip-api
     * @param ip
     * @param sysDicts
     */
    public static String location(String ip,List<SysDict> sysDicts){
        String province = "";
        String url = " http://api.map.baidu.com/location/ip?ak="+ak+"&ip="+ip+"&coor=bd09ll" ;
        String resultStr = HttpUtil.get(url);
        Map resultMap = JSON.parseObject(resultStr);
        String status = String.valueOf(resultMap.get("status"));
        //状态
        if("0".equals(status)){
            String address = String.valueOf(resultMap.get("address"));
            String[] split = address.split("\\|");
            province = split[1];
        }else{
            Integer statusInt = Integer.valueOf(status);
            //状态码地址 http://lbsyun.baidu.com/index.php?title=webapi/appendix
            if (statusInt > 300) {
                //超额换ak
                for (SysDict dict : sysDicts) {
                    String baiDuAkStr = dict.getDataValue();
                    if (!ak.equals(baiDuAkStr)) {
                        url = " http://api.map.baidu.com/location/ip?ak=" + baiDuAkStr + "&ip=" + ip + "&coor=bd09ll";
                        resultStr = HttpUtil.get(url);
                        resultMap = JSON.parseObject(resultStr);
                        status = String.valueOf(resultMap.get("status"));
                        if ("0".equals(status)) {
                            String address = String.valueOf(resultMap.get("address"));
                            String[] split = address.split("|");
                            province = split[1];
                        }
                    }
                }
            }
        }
        return province;
    }

}
