package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.UserPricingDto;
import com.mc.refillCard.entity.UserPricing;
import com.mc.refillCard.service.UserPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-4-29 9:27:10
 *****/
@RestController
@RequestMapping("/userPricing")
@CrossOrigin
public class UserPricingController {

    @Autowired
    private UserPricingService userPricingService;

    /***
     * 多条件搜索userPricing数据
     * @param userPricingDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(UserPricingDto userPricingDto,@PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = userPricingService.findPage(userPricingDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询UserPricing全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用UserPricingService实现查询所有UserPricing
        List<UserPricing> list = userPricingService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索userPricing数据
     * @param userPricing
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(UserPricing userPricing){
        //调用UserPricingService实现条件查询UserPricing
        List<UserPricing> list = userPricingService.findList(userPricing);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询UserPricing数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用UserPricingService实现根据主键查询UserPricing
        UserPricing userPricing = userPricingService.findById(id);
        return Result.success("查询成功",userPricing);
    }

    /***
     * 根据ID删除userPricing数据
     * @param id
     * @return
     */
    @SystemControllerLog(description = "定价管理:删除")
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用UserPricingService实现根据主键删除
        userPricingService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改UserPricing数据
     * @param userPricingDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "定价管理:编辑")
    @PostMapping(value="/update")
    public Result update(@RequestBody UserPricingDto userPricingDto){
        //调用UserPricingService实现修改UserPricing
       Long id = userPricingDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        userPricingService.updateDto(userPricingDto);
        return Result.success("编辑成功");
    }

    /***
     * 修改UserPricing数据
     * @param userPricingDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "定价管理:修改定价")
    @PostMapping(value="/update/pricing")
    public Result updatePricing(@RequestBody UserPricingDto userPricingDto){
        //调用UserPricingService实现修改UserPricing
        Long id = userPricingDto.getId();
        if(id == null){
            return Result.fall("无效ID");
        }
        userPricingService.updatePricing(userPricingDto);
        return Result.success("操作成功");
    }

    /***
     * 新增UserPricing数据
     * @param userPricingDto
     * @return
     */
    @SystemControllerLog(description = "定价管理:新增")
    @PostMapping(value="/add")
    public Result add(@RequestBody UserPricingDto userPricingDto){
        //调用UserPricingService实现添加UserPricing
        userPricingService.addDto(userPricingDto);
        return Result.success("新增成功");
    }


}
