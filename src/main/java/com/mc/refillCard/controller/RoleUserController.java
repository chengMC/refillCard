package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.entity.RoleUser;
import com.mc.refillCard.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2020-9-29 17:00:55
 *****/
@RestController
@RequestMapping("/roleUser")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    /***
     * 多条件搜索roleUser数据
     * @param roleUser
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result<PageInfo> findPage(RoleUser roleUser, @PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo<RoleUser> pageInfo = roleUserService.findPage(roleUser, page, size);
        return Result.success("查询成功", pageInfo);
    }


    /***
     * 查询RoleUser全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result<List<RoleUser>> findAll(){
        //调用RoleUserService实现查询所有RoleUser
        List<RoleUser> list = roleUserService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索roleUser数据
     * @param roleUser
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result<List<RoleUser>> findList(RoleUser roleUser){
        //调用RoleUserService实现条件查询RoleUser
        List<RoleUser> list = roleUserService.findList(roleUser);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询RoleUser数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result<RoleUser> findById(@PathVariable Long id){
        //调用RoleUserService实现根据主键查询RoleUser
        RoleUser roleUser = roleUserService.findById(id);
        return Result.success("查询成功",roleUser);
    }

    /***
     * 根据ID删除roleUser数据
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用RoleUserService实现根据主键删除
        roleUserService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改RoleUser数据
     * @param roleUser
     * @param
     * @return
     */
    @PostMapping(value="/update")
    public Result update(@RequestBody RoleUser roleUser){
        //调用RoleUserService实现修改RoleUser
       Long id = roleUser.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        roleUserService.update(roleUser);
        return Result.success("编辑成功");
    }

    /***
     * 新增RoleUser数据
     * @param roleUser
     * @return
     */
    @PostMapping(value="/add")
    public Result add(@RequestBody RoleUser roleUser){
        //调用RoleUserService实现添加RoleUser
        roleUserService.add(roleUser);
        return Result.success("新增成功");
    }


}
