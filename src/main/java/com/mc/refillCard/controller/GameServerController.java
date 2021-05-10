package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.GameServerDto;
import com.mc.refillCard.entity.GameServer;
import com.mc.refillCard.service.GameServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-5-10 23:11:50
 *****/
@RestController
@RequestMapping("/gameServer")
@CrossOrigin
public class GameServerController {

    @Autowired
    private GameServerService gameServerService;

    /***
     * 多条件搜索gameServer数据
     * @param gameServerDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(GameServerDto gameServerDto,@PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = gameServerService.findPage(gameServerDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询GameServer全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用GameServerService实现查询所有GameServer
        List<GameServer> list = gameServerService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索gameServer数据
     * @param gameServer
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(GameServer gameServer){
        //调用GameServerService实现条件查询GameServer
        List<GameServer> list = gameServerService.findList(gameServer);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询GameServer数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用GameServerService实现根据主键查询GameServer
        GameServer gameServer = gameServerService.findById(id);
        return Result.success("查询成功",gameServer);
    }

    /***
     * 根据ID删除gameServer数据
     * @param id
     * @return
     */
    @SystemControllerLog(description = "管理:删除")
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用GameServerService实现根据主键删除
        gameServerService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改GameServer数据
     * @param gameServerDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "管理:编辑")
    @PostMapping(value="/update")
    public Result update(@RequestBody GameServerDto gameServerDto){
        //调用GameServerService实现修改GameServer
       Long id = gameServerDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        gameServerService.updateDto(gameServerDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增GameServer数据
     * @param gameServerDto
     * @return
     */
    @SystemControllerLog(description = "管理:新增")
    @PostMapping(value="/add")
    public Result add(@RequestBody GameServerDto gameServerDto){
        //调用GameServerService实现添加GameServer
        gameServerService.addDto(gameServerDto);
        return Result.success("新增成功");
    }


}
