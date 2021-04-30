package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.GoodsRelateDto;
import com.mc.refillCard.entity.GoodsRelate;
import com.mc.refillCard.service.GoodsRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-3-21 16:36:30
 *****/
@RestController
@RequestMapping("/goodsRelate1")
@CrossOrigin
public class GoodsRelateController {

    @Autowired
    private GoodsRelateService goodsRelateService;

    /***
     * 多条件搜索goodsRelate数据
     * @param goodsRelateDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(GoodsRelateDto goodsRelateDto,@PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = goodsRelateService.findPage(goodsRelateDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询GoodsRelate全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用GoodsRelateService实现查询所有GoodsRelate
        List<GoodsRelate> list = goodsRelateService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索goodsRelate数据
     * @param goodsRelate
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(GoodsRelate goodsRelate){
        //调用GoodsRelateService实现条件查询GoodsRelate
        List<GoodsRelate> list = goodsRelateService.findList(goodsRelate);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询GoodsRelate数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用GoodsRelateService实现根据主键查询GoodsRelate
        GoodsRelate goodsRelate = goodsRelateService.findById(id);
        return Result.success("查询成功",goodsRelate);
    }

    /***
     * 根据ID删除goodsRelate数据
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用GoodsRelateService实现根据主键删除
        goodsRelateService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改GoodsRelate数据
     * @param goodsRelateDto
     * @param
     * @return
     */
    @PostMapping(value="/update")
    public Result update(@RequestBody GoodsRelateDto goodsRelateDto){
        //调用GoodsRelateService实现修改GoodsRelate
       Long id = goodsRelateDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        goodsRelateService.updateDto(goodsRelateDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增GoodsRelate数据
     * @param goodsRelateDto
     * @return
     */
    @PostMapping(value="/add")
    public Result add(@RequestBody GoodsRelateDto goodsRelateDto){
        goodsRelateService.addDto(goodsRelateDto);
        return Result.success("新增成功");
    }


}
