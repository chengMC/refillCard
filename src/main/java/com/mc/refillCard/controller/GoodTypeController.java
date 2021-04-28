package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.GoodTypeDto;
import com.mc.refillCard.entity.GoodType;
import com.mc.refillCard.service.GoodTypeService;
import com.mc.refillCard.vo.GoodTypeEnumVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-4-7 21:05:20
 *****/
@RestController
@RequestMapping("/goodType")
@CrossOrigin
public class GoodTypeController {

    @Autowired
    private GoodTypeService goodTypeService;

    /***
     * 多条件搜索goodType数据
     * @param goodTypeDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(GoodTypeDto goodTypeDto,@PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = goodTypeService.findPage(goodTypeDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询GoodType全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用GoodTypeService实现查询所有GoodType
        List<GoodTypeEnumVo> list = goodTypeService.findVoAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索goodType数据
     * @param goodType
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(GoodType goodType){
        //调用GoodTypeService实现条件查询GoodType
        List<GoodType> list = goodTypeService.findList(goodType);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询GoodType数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用GoodTypeService实现根据主键查询GoodType
        GoodType goodType = goodTypeService.findById(id);
        return Result.success("查询成功",goodType);
    }

    /***
     * 根据ID删除goodType数据
     * @param id
     * @return
     */
    @SystemControllerLog(description = "管理:删除")
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用GoodTypeService实现根据主键删除
        goodTypeService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改GoodType数据
     * @param goodTypeDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "管理:编辑")
    @PostMapping(value="/update")
    public Result update(@RequestBody GoodTypeDto goodTypeDto){
        //调用GoodTypeService实现修改GoodType
       Long id = goodTypeDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        goodTypeService.updateDto(goodTypeDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增GoodType数据
     * @param goodTypeDto
     * @return
     */
    @SystemControllerLog(description = "管理:新增")
    @PostMapping(value="/add")
    public Result add(@RequestBody GoodTypeDto goodTypeDto){
        //调用GoodTypeService实现添加GoodType
        goodTypeService.addDto(goodTypeDto);
        return Result.success("新增成功");
    }


}
