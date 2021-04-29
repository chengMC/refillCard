package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.entity.Menu;
import com.mc.refillCard.service.MenuService;
import com.mc.refillCard.vo.MenuIndexUserVo;
import com.mc.refillCard.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 *
 * 菜单控制器
 * @Author: MC
 * @Description:
 * @Date 2020-9-29 17:00:54
 *****/
@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class MenuController {

    @Autowired
    private MenuService menuService;

    /***
     * 多条件搜索menu数据
     * @param menu
     * @return
     */
    @GetMapping(value = "/page/{page}/{size}")
    public Result<PageInfo> findPage(Menu menu, @PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo<Menu> pageInfo = menuService.findPage(menu, page, size);
        return Result.success("查询成功", pageInfo);
    }


    /***
     * 查询Menu全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    public Result<List<Menu>> findAll(){
        //调用MenuService实现查询所有Menu
        List<Menu> list = menuService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索menu数据
     * @param menu
     * @return
     */
    @GetMapping(value = "/findList" )
    public Result<List<Menu>> findList(Menu menu){
        //调用MenuService实现条件查询Menu
        List<Menu> list = menuService.findList(menu);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询Menu数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result<Menu> findById(@PathVariable Long id){
        //调用MenuService实现根据主键查询Menu
        Menu menu = menuService.findById(id);
        return Result.success("查询成功",menu);
    }

    /***
     * 根据ID删除menu数据
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        //调用MenuService实现根据主键删除
        menuService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改Menu数据
     * @param menu
     * @param
     * @return
     */
    @PostMapping(value="/update")
    public Result update(@RequestBody Menu menu){
        //调用MenuService实现修改Menu
       Long id = menu.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        menuService.update(menu);
        return Result.success("编辑成功");
    }

    /***
     * 新增Menu数据
     * @param menu
     * @return
     */
    @PostMapping(value="/add")
    public Result add(@RequestBody Menu menu){
        //调用MenuService实现添加Menu
        menuService.add(menu);
        return Result.success("新增成功");
    }


    /***
     * 根据ID查询该用户对应的角色拥有的菜单列表
     * @param userId
     * @return
     */
    @GetMapping("/findRole/{userId}")
    public Result findRoleByUserId(@PathVariable Long userId){
        //调用RoleService实现根据主键查询Role
//        List<MenuVo> list =menuService.findMenuVoByUserId(userId);
        List<MenuIndexUserVo> list = menuService.findMenuIndexUserVoByUserId(userId);
        return Result.success("查询成功",list);
    }

    /***
     * 查询Menu全部数据--层级关系的
     * @return
     */
    @GetMapping(value="/findTreeAll")
    public Result findTreeAll(){
        List<MenuVo> list = menuService.findTreeAll();
        return Result.success("查询成功",list);
    }

    /***
     * 查询菜单数据，如果有二级菜单则返回二级菜单不返回相对于一级菜单，如果只有一级菜单直接返回
     *
     * @return
     */
    @GetMapping(value="/findTree")
    public Result findTree(){
        List<MenuVo> list = menuService.findTree();
        return Result.success("查询成功",list);
    }

}
