package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.OriginalOrderQueryDto;
import com.mc.refillCard.entity.OriginalOrder;
import com.mc.refillCard.service.OriginalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-3-21 14:41:57
 *****/
@RestController
@RequestMapping("/originalOrder")
@CrossOrigin
public class OriginalOrderController {

    @Autowired
    private OriginalOrderService originalOrderService;

    /***
     * 多条件搜索originalOrder数据
     * @param originalOrderDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(OriginalOrderQueryDto originalOrderDto, @PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = originalOrderService.findPage(originalOrderDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询OriginalOrder全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用OriginalOrderService实现查询所有OriginalOrder
        List<OriginalOrder> list = originalOrderService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索originalOrder数据
     * @param originalOrder
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(OriginalOrder originalOrder){
        //调用OriginalOrderService实现条件查询OriginalOrder
        List<OriginalOrder> list = originalOrderService.findList(originalOrder);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询OriginalOrder数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用OriginalOrderService实现根据主键查询OriginalOrder
        OriginalOrder originalOrder = originalOrderService.findById(id);
        return Result.success("查询成功",originalOrder);
    }

    /***
     * 根据ID删除originalOrder数据
     * @param id
     * @return
     */
    @SystemControllerLog(description = "管理:删除")
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用OriginalOrderService实现根据主键删除
        originalOrderService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改OriginalOrder数据
     * @param originalOrderDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "管理:编辑")
    @PostMapping(value="/update")
    public Result update(@RequestBody OriginalOrderDto originalOrderDto){
        //调用OriginalOrderService实现修改OriginalOrder
       Long id = originalOrderDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        originalOrderService.updateDto(originalOrderDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增OriginalOrder数据
     * @param originalOrderDto
     * @return
     */
    @SystemControllerLog(description = "管理:新增")
    @PostMapping(value="/add")
    public Result add(@RequestBody OriginalOrderDto originalOrderDto){
        //调用OriginalOrderService实现添加OriginalOrder
        originalOrderService.addDto(originalOrderDto);
        return Result.success("新增成功");
    }


}
