package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.BlacklistDto;
import com.mc.refillCard.entity.Blacklist;
import com.mc.refillCard.service.BlacklistService;
import com.mc.refillCard.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2021-4-7 21:03:32
 *****/
@RestController
@RequestMapping("/blacklist")
@CrossOrigin
public class BlacklistController {

    @Autowired
    private BlacklistService blacklistService;

    /***
     * 多条件搜索blacklist数据
     * @param blacklistDto
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(BlacklistDto blacklistDto,@PathVariable int page, @PathVariable int size) {
        Long userId = blacklistDto.getUserId();
        if(userId==null){
            UserVo user = (UserVo) SecurityUtils.getSubject().getPrincipal();
            blacklistDto.setUserId(user.getId());
        }
        PageInfo pageInfo = blacklistService.findPage(blacklistDto, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询Blacklist全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用BlacklistService实现查询所有Blacklist
        List<Blacklist> list = blacklistService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索blacklist数据
     * @param blacklist
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result  findList(Blacklist blacklist){
        //调用BlacklistService实现条件查询Blacklist
        List<Blacklist> list = blacklistService.findList(blacklist);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询Blacklist数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result  findById(@PathVariable Long id){
        //调用BlacklistService实现根据主键查询Blacklist
        Blacklist blacklist = blacklistService.findById(id);
        return Result.success("查询成功",blacklist);
    }

    /***
     * 根据ID删除blacklist数据
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        blacklistService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改Blacklist数据
     * @param blacklistDto
     * @param
     * @return
     */
    @PostMapping(value="/update")
    public Result update(@RequestBody BlacklistDto blacklistDto){
        //调用BlacklistService实现修改Blacklist
       Long id = blacklistDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        blacklistService.updateDto(blacklistDto);
        return Result.success("编辑成功");
    }

    /***
     * 修改Blacklist数据
     * @param blacklistDto
     * @param
     * @return
     */
    @PostMapping(value="/update/status")
    public Result updateStatus(@RequestBody BlacklistDto blacklistDto){
        //调用BlacklistService实现修改Blacklist
        Long id = blacklistDto.getId();
        if(id == null){
            return Result.fall("无效ID");
        }
        blacklistService.updateStatus(blacklistDto);
        return Result.success("操作成功");
    }

    /***
     * 新增Blacklist数据
     * @param blacklistDto
     * @return
     */
    @SystemControllerLog(description = "管理:新增")
    @PostMapping(value="/add")
    public Result add(@RequestBody BlacklistDto blacklistDto){
        Long userId = blacklistDto.getUserId();
        if(userId==null){
            UserVo user = (UserVo) SecurityUtils.getSubject().getPrincipal();
            blacklistDto.setUserId(user.getId());
        }
        blacklistService.addDto(blacklistDto);
        return Result.success("新增成功");
    }


}
