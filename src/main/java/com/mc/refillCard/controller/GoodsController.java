package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.GoodsDto;
import com.mc.refillCard.entity.Goods;
import com.mc.refillCard.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-3-28 20:28:52
 *****/
@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /***
     * 多条件搜索goods数据
     * @param goodsDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(GoodsDto goodsDto,@PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = goodsService.findPage(goodsDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询Goods全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用GoodsService实现查询所有Goods
        List<Goods> list = goodsService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索goods数据
     * @param goods
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(Goods goods){
        //调用GoodsService实现条件查询Goods
        List<Goods> list = goodsService.findList(goods);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询Goods数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用GoodsService实现根据主键查询Goods
        Goods goods = goodsService.findById(id);
        return Result.success("查询成功",goods);
    }

    /***
     * 根据ID删除goods数据
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用GoodsService实现根据主键删除
        goodsService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改Goods数据
     * @param goodsDto
     * @param
     * @return
     */
    @PostMapping(value="/update")
    public Result update(@RequestBody GoodsDto goodsDto){
        //调用GoodsService实现修改Goods
       Long id = goodsDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        goodsService.updateDto(goodsDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增Goods数据
     * @param goodsDto
     * @return
     */
    @PostMapping(value="/add")
    public Result add(@RequestBody GoodsDto goodsDto){
        //调用GoodsService实现添加Goods
        goodsService.addDto(goodsDto);
        return Result.success("新增成功");
    }


}
