package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.RoleDto;
import com.mc.refillCard.entity.Role;
import com.mc.refillCard.service.RoleService;
import com.mc.refillCard.vo.RoleVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/****
 *
 *  角色控制器
 *
 * @Author: MC
 * @Description:
 * @Date 2020-9-29 17:00:54
 *****/
@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class RoleController {

    @Autowired
    private RoleService roleService;

    /***
     * 多条件搜索role数据
     * @param role
     * @return
     */
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @GetMapping(value = "/page/{page}/{size}")
    public Result findPage(Role role, @PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo<RoleVo> pageInfo = roleService.findPage(role, page, size);
        return Result.success("查询成功", pageInfo);
    }


    /***
     * 查询Role全部数据
     * @return
     */
    @GetMapping(value = "/findAll" )
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    public Result findAll(){
        //调用RoleService实现查询所有Role
        List<Role> list = roleService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 多条件搜索role数据
     * @param role
     * @return
     */
    @GetMapping(value = "/findList" )
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    public Result findList(Role role){
        //调用RoleService实现条件查询Role
        List<RoleVo> list = roleService.findList(role);
        return Result.success("查询成功",list);
    }

    /***
     * 根据ID查询Role数据
     * @param id
     * @return
     */
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @GetMapping("/findById/{id}")
    public Result<Role> findById(@PathVariable Long id){
        //调用RoleService实现根据主键查询Role
        Role role = roleService.findById(id);
        return Result.success("查询成功",role);
    }

    /***
     * 根据ID删除role数据
     * @param id
     * @return
     */
    @SystemControllerLog(description = "角色管理:删除")
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @PostMapping(value = "/deleteById/{id}" )
    public Result deleteById(@PathVariable Long id){
        if(id == null){
            return Result.fall("无效ID");
        }
        if(id.equals(1L)){
            return Result.fall("超级管理员无法删除");
        }
        try {
        //调用RoleService实现根据主键删除
            roleService.delete(id);
        } catch (Exception e) {
            return Result.fall(e.getMessage());
        }
        return Result.success("删除成功");
    }

    /***
     * 修改Role数据
     * @param roleDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "角色管理:编辑")
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @PostMapping(value="/update")
    public Result update(@RequestBody @Valid RoleDto roleDto){
        //调用RoleService实现修改Role
       Long id = roleDto.getId();
       if(id == null){
        return Result.fall("无效ID");
       }
        try {
            roleService.updateDto(roleDto);
        } catch (Exception e) {
            return Result.fall(e.getMessage());
        }
        return Result.success("编辑成功");
    }

    /***
     * 新增Role数据
     * @param roleDto
     * @return
     */
    @SystemControllerLog(description = "角色管理:新增")
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @PostMapping(value="/add")
    public Result add(@RequestBody RoleDto roleDto){
        //调用RoleService实现添加Role
        try {
            roleService.addDto(roleDto);
        } catch (Exception e) {
            return  Result.fall(e.getMessage());
        }
        return Result.success("新增成功");
    }


}
