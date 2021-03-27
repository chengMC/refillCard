package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.entity.RoleMenu;
import com.mc.refillCard.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author: MC
 * @Description:
 * @Date 2020-9-29 17:00:55
 *****/
@RestController
@RequestMapping("/roleMenu")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    /***
     * 多条件搜索roleMenu数据
     * @param roleMenu
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result<PageInfo> findPage(RoleMenu roleMenu, @PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo<RoleMenu> pageInfo = roleMenuService.findPage(roleMenu, page, size);
        return Result.success("查询成功", pageInfo);
    }


    /***
     * 查询RoleMenu全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result<List<RoleMenu>> findAll(){
        //调用RoleMenuService实现查询所有RoleMenu
        List<RoleMenu> list = roleMenuService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索roleMenu数据
     * @param roleMenu
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result<List<RoleMenu>> findList(RoleMenu roleMenu){
        //调用RoleMenuService实现条件查询RoleMenu
        List<RoleMenu> list = roleMenuService.findList(roleMenu);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询RoleMenu数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result<RoleMenu> findById(@PathVariable Long id){
        //调用RoleMenuService实现根据主键查询RoleMenu
        RoleMenu roleMenu = roleMenuService.findById(id);
        return Result.success("查询成功",roleMenu);
    }

    /***
     * 根据ID删除roleMenu数据
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用RoleMenuService实现根据主键删除
        roleMenuService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改RoleMenu数据
     * @param roleMenu
     * @param
     * @return
     */
    @PostMapping(value="/update")
    public Result update(@RequestBody RoleMenu roleMenu){
        //调用RoleMenuService实现修改RoleMenu
       Long id = roleMenu.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        roleMenuService.update(roleMenu);
        return Result.success("编辑成功");
    }

    /***
     * 新增RoleMenu数据
     * @param roleMenu
     * @return
     */
    @PostMapping(value="/add")
    public Result add(@RequestBody RoleMenu roleMenu){
        //调用RoleMenuService实现添加RoleMenu
        roleMenuService.add(roleMenu);
        return Result.success("新增成功");
    }


}
