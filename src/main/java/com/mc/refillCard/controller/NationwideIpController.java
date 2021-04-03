package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.NationwideIpDto;
import com.mc.refillCard.entity.NationwideIp;
import com.mc.refillCard.service.NationwideIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-4-3 16:33:59
 *****/
@RestController
@RequestMapping("/nationwideIp")
@CrossOrigin
public class NationwideIpController {

    @Autowired
    private NationwideIpService nationwideIpService;

    /***
     * 多条件搜索nationwideIp数据
     * @param nationwideIpDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(NationwideIpDto nationwideIpDto,@PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = nationwideIpService.findPage(nationwideIpDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询NationwideIp全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用NationwideIpService实现查询所有NationwideIp
        List<NationwideIp> list = nationwideIpService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索nationwideIp数据
     * @param nationwideIp
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(NationwideIp nationwideIp){
        //调用NationwideIpService实现条件查询NationwideIp
        List<NationwideIp> list = nationwideIpService.findList(nationwideIp);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询NationwideIp数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用NationwideIpService实现根据主键查询NationwideIp
        NationwideIp nationwideIp = nationwideIpService.findById(id);
        return Result.success("查询成功",nationwideIp);
    }

    /***
     * 根据ID删除nationwideIp数据
     * @param id
     * @return
     */
    @SystemControllerLog(description = "管理:删除")
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用NationwideIpService实现根据主键删除
        nationwideIpService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改NationwideIp数据
     * @param nationwideIpDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "管理:编辑")
    @PostMapping(value="/update")
    public Result update(@RequestBody NationwideIpDto nationwideIpDto){
        //调用NationwideIpService实现修改NationwideIp
       Long id = nationwideIpDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        nationwideIpService.updateDto(nationwideIpDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增NationwideIp数据
     * @param nationwideIpDto
     * @return
     */
    @SystemControllerLog(description = "管理:新增")
    @PostMapping(value="/add")
    public Result add(@RequestBody NationwideIpDto nationwideIpDto){
        //调用NationwideIpService实现添加NationwideIp
        nationwideIpService.addDto(nationwideIpDto);
        return Result.success("新增成功");
    }


}
