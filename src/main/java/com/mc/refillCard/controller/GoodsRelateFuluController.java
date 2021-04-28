package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.GoodsRelateFuluDto;
import com.mc.refillCard.entity.GoodsRelateFulu;
import com.mc.refillCard.service.GoodsRelateFuluService;
import com.mc.refillCard.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-3-28 20:16:38
 *****/
@RestController
@RequestMapping("/goodsRelate")
@CrossOrigin
public class GoodsRelateFuluController {

    @Autowired
    private GoodsRelateFuluService goodsRelateFuluService;

    /***
     * 多条件搜索goodsRelateFulu数据
     * @param goodsRelateFuluDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(GoodsRelateFuluDto goodsRelateFuluDto,@PathVariable int page, @PathVariable int size) {
        Long userId = goodsRelateFuluDto.getUserId();
        if(userId==null){
            UserVo user = (UserVo) SecurityUtils.getSubject().getPrincipal();
            goodsRelateFuluDto.setUserId(user.getId());
        }

        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = goodsRelateFuluService.findPage(goodsRelateFuluDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询GoodsRelateFulu全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用GoodsRelateFuluService实现查询所有GoodsRelateFulu
        List<GoodsRelateFulu> list = goodsRelateFuluService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索goodsRelateFulu数据
     * @param goodsRelateFulu
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(GoodsRelateFulu goodsRelateFulu){
        //调用GoodsRelateFuluService实现条件查询GoodsRelateFulu
        List<GoodsRelateFulu> list = goodsRelateFuluService.findList(goodsRelateFulu);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询GoodsRelateFulu数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用GoodsRelateFuluService实现根据主键查询GoodsRelateFulu
        GoodsRelateFulu goodsRelateFulu = goodsRelateFuluService.findById(id);
        return Result.success("查询成功",goodsRelateFulu);
    }

    /***
     * 根据ID删除goodsRelateFulu数据
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用GoodsRelateFuluService实现根据主键删除
        goodsRelateFuluService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改GoodsRelateFulu数据
     * @param goodsRelateFuluDto
     * @param
     * @return
     */
    @PostMapping(value="/update")
    public Result update(@RequestBody GoodsRelateFuluDto goodsRelateFuluDto){
        //调用GoodsRelateFuluService实现修改GoodsRelateFulu
       Long id = goodsRelateFuluDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        goodsRelateFuluService.updateDto(goodsRelateFuluDto);
        return Result.success("编辑成功");
    }

    /***
     * 修改GoodsRelateFulu数据
     * @param goodsRelateFuluDto
     * @param
     * @return
     */
    @PostMapping(value="/update/status")
    public Result updateStatus(@RequestBody GoodsRelateFuluDto goodsRelateFuluDto){
        //调用GoodsRelateFuluService实现修改GoodsRelateFulu
        Long id = goodsRelateFuluDto.getId();
        if(id == null){
            return Result.fall("无效ID");
        }
        goodsRelateFuluService.updateStatus(goodsRelateFuluDto);
        return Result.success("操作成功");
    }

    /***
     * 新增GoodsRelateFulu数据
     * @param goodsRelateFuluDto
     * @return
     */
    @PostMapping(value="/add")
    public Result add(@RequestBody GoodsRelateFuluDto goodsRelateFuluDto){
        goodsRelateFuluService.addDto(goodsRelateFuluDto);
        return Result.success("新增成功");
    }


}
