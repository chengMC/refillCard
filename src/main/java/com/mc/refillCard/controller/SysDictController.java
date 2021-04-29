package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.entity.SysDict;
import com.mc.refillCard.service.SysDictService;
import com.mc.refillCard.vo.SysDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-1-26 17:11:37
 *****/
@RestController
@RequestMapping("/sysDict")
@CrossOrigin
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    /***
     * 多条件搜索sysDict数据
     * @param sysDict
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(SysDict sysDict,@PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = sysDictService.findPage(sysDict, page, size);
        return Result.success("查询成功", pageInfo);
    }


    /***
     * 查询SysDict全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用SysDictService实现查询所有SysDict
        List<SysDict> list = sysDictService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索sysDict数据
     * @param sysDict
     * @return
     */
    @GetMapping(value = "/findListTypeByCode" )
    public Result  findListTypeByCode(SysDict sysDict){
        //调用SysDictService实现条件查询SysDict
        List<SysDict> list = sysDictService.findListTypeByCode(sysDict.getDataCode());
        return Result.success("查询成功",list);
    }

    /***
     * 多条件搜索sysDict数据
     * @param sysDict
     * @return
     */
    @GetMapping(value = "/findByCode" )
    public Result  findByCode(SysDict sysDict){
        //调用SysDictService实现条件查询SysDict
        SysDict dict = sysDictService.findByCode(sysDict.getDataCode());
        return Result.success("查询成功",dict);
    }

    /***
     * 多条件搜索sysDict数据
     * @param sysDict
     * @return
     */
    @GetMapping(value = "/findListByCode" )
    public Result findListByCode(SysDict sysDict){
        //调用SysDictService实现条件查询SysDict
        List<SysDictVo> list = sysDictService.findListVoByCode(sysDict.getDataCode());
        return Result.success("查询成功",list);
    }

    /***
     * 多条件搜索sysDict数据
     * @param sysDict
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(SysDict sysDict){
        //调用SysDictService实现条件查询SysDict
        List<SysDict> list = sysDictService.findList(sysDict);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询SysDict数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用SysDictService实现根据主键查询SysDict
        SysDict sysDict = sysDictService.findById(id);
        return Result.success("查询成功",sysDict);
    }

    /***
     * 根据ID删除sysDict数据
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用SysDictService实现根据主键删除
        sysDictService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改SysDict数据
     * @param sysDict
     * @param
     * @return
     */
    @PostMapping(value="/update")
    public Result update(@RequestBody SysDict sysDict){
        //调用SysDictService实现修改SysDict
       Long id = sysDict.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        sysDictService.update(sysDict);
        return Result.success("修改成功");
    }

    /***
     * 新增SysDict数据
     * @param sysDict
     * @return
     */
    @PostMapping(value="/add")
    public Result add(@RequestBody SysDict sysDict){
        //调用SysDictService实现添加SysDict
        sysDictService.add(sysDict);
        return Result.success("添加成功");
    }


}
