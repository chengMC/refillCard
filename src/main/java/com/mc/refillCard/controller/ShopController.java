package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.UserBalanceDto;
import com.mc.refillCard.dto.UserRelateDto;
import com.mc.refillCard.entity.UserRelate;
import com.mc.refillCard.service.UserRelateService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-3-21 16:20:43
 *****/
@RestController
@RequestMapping("/shop")
@CrossOrigin
public class ShopController {

    @Autowired
    private UserRelateService userRelateService;

    /***
     * 多条件搜索userRelate数据
     * @param userRelateDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(UserRelateDto userRelateDto, @PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = userRelateService.findPage(userRelateDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询UserRelate全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用UserRelateService实现查询所有UserRelate
        List<UserRelate> list = userRelateService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索userRelate数据
     * @param userRelate
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(UserRelate userRelate){
        //调用UserRelateService实现条件查询UserRelate
        List<UserRelate> list = userRelateService.findList(userRelate);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询UserRelate数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用UserRelateService实现根据主键查询UserRelate
        UserRelate userRelate = userRelateService.findById(id);
        return Result.success("查询成功",userRelate);
    }

    /***
     * 根据ID删除userRelate数据
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用UserRelateService实现根据主键删除
        userRelateService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改UserRelate数据
     * @param
     * @param
     * @return
     */
    @SystemControllerLog(description = "店铺管理:加款")
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @PostMapping(value="/update/balance")
    public Result update(@RequestBody UserBalanceDto userBalanceDto){
        //调用UserRelateService实现修改UserRelate
        Long id = userBalanceDto.getId();
        if(id == null){
            return Result.fall("无效ID");
        }
        userRelateService.updateBalance(userBalanceDto);
        return Result.success("加款成功");
    }

    /***
     * 修改UserRelate数据
     * @param userRelateDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "店铺管理:编辑店铺信息")
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @PostMapping(value="/update")
    public Result update(@RequestBody UserRelateDto userRelateDto){
        //调用UserRelateService实现修改UserRelate
       Long id = userRelateDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        userRelateService.updateDto(userRelateDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增UserRelate数据
     * @param userRelateDto
     * @return
     */
    @PostMapping(value="/add")
    public Result add(@RequestBody UserRelateDto userRelateDto){
        //调用UserRelateService实现添加UserRelate
        userRelateService.addDto(userRelateDto);
        return Result.success("新增成功");
    }




}
