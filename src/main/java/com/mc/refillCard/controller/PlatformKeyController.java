package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Enum.PlatformEnum;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.PlatformKeyDto;
import com.mc.refillCard.entity.PlatformKey;
import com.mc.refillCard.service.PlatformKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-5-4 22:30:48
 *****/
@RestController
@RequestMapping("/platformKey")
@CrossOrigin
public class PlatformKeyController {

    @Autowired
    private PlatformKeyService platformKeyService;

    /***
     * 多条件搜索platformKey数据
     * @param platformKeyDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(PlatformKeyDto platformKeyDto,@PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = platformKeyService.findPage(platformKeyDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询PlatformKey全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用PlatformKeyService实现查询所有PlatformKey
        List<PlatformKey> list = platformKeyService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 查询PlatformKey全部数据
     * @return
     */
    @GetMapping(value = "/findByAppType" )
    public Result findByAppType(){
        PlatformKey platformKey = platformKeyService.findByAppType(PlatformEnum.FULU.getCode());
        return Result.success("查询成功",platformKey) ;
    }

    /***
     * 多条件搜索platformKey数据
     * @param platformKey
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(PlatformKey platformKey){
        //调用PlatformKeyService实现条件查询PlatformKey
        List<PlatformKey> list = platformKeyService.findList(platformKey);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询PlatformKey数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用PlatformKeyService实现根据主键查询PlatformKey
        PlatformKey platformKey = platformKeyService.findById(id);
        return Result.success("查询成功",platformKey);
    }

    /***
     * 根据ID删除platformKey数据
     * @param id
     * @return
     */
    @SystemControllerLog(description = "管理:删除")
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用PlatformKeyService实现根据主键删除
        platformKeyService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改PlatformKey数据
     * @param platformKeyDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "管理:编辑")
    @PostMapping(value="/update")
    public Result update(@RequestBody PlatformKeyDto platformKeyDto){
        //调用PlatformKeyService实现修改PlatformKey
       Long id = platformKeyDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        platformKeyService.updateDto(platformKeyDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增PlatformKey数据
     * @param platformKeyDto
     * @return
     */
    @SystemControllerLog(description = "管理:新增")
    @PostMapping(value="/add")
    public Result add(@RequestBody PlatformKeyDto platformKeyDto){
        //调用PlatformKeyService实现添加PlatformKey
        platformKeyService.addDto(platformKeyDto);
        return Result.success("新增成功");
    }


}
