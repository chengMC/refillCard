package com.mc.refillCard.controller;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.annotation.SystemControllerLog;
import com.mc.refillCard.common.Result;
import com.mc.refillCard.dto.UserDto;
import com.mc.refillCard.dto.UserSaveDto;
import com.mc.refillCard.entity.User;
import com.mc.refillCard.service.UserService;
import com.mc.refillCard.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/****
 *  用户信息控制器
 *
 * @Author: MC
 * @Description:
 * @Date 2020-8-11 18:38:33
 *****/
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    /***
     * 多条件搜索user数据
     * @param user
     * @return
     */
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @GetMapping(value = "/page/{page}/{size}")
    public Result<PageInfo> findPage(User user, @PathVariable int page, @PathVariable int size) {
        //调用AccessoryService实现分页条件查询Accessory
        PageInfo pageInfo = userService.findPage(user, page, size);
        return Result.success("查询成功", pageInfo);
    }

    /***
     * 查询User全部数据
     * @return
     */
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @GetMapping(value = "/findAll" )
    public Result findAll(){
        //调用UserService实现查询所有User
        List<UserVo> list = userService.findAll();
        return Result.success("查询成功",list) ;
    }

    /***
     * 根据ID查询User数据
     * @return
     */
    @GetMapping("/find")
    public Result<UserVo> findById(){
        UserVo uservo = (UserVo) SecurityUtils.getSubject().getPrincipal();
        UserVo user = userService.findVoById(uservo.getId());
        return Result.success("查询成功",user);
    }

    /***
     * 根据ID删除user数据
     * @param id
     * @return
     */
    @SystemControllerLog(description = "用户管理:删除")
    @PostMapping(value = "/deleteById/{id}" )
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    public Result deleteById(@PathVariable Long id){
        if(id == 1){
            return Result.success("管理员不能删除");
        }
        //调用UserService实现根据主键删除
        userService.delete(id);
        return Result.success("删除成功");
    }

    /***
     * 修改User数据
     * @param userDto
     * @param
     * @return
     */
    @SystemControllerLog(description = "用户管理:编辑")
    @PostMapping(value="/update")
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @Transactional(rollbackFor=Exception.class)
    public Result update(@RequestBody @Valid UserSaveDto userDto) throws Exception {
        //调用UserService实现修改User
        Long id = userDto.getId();
        if(id == null){
            return Result.fall("无效ID");
        }
        userService.updateDto(userDto);
        return Result.success("编辑成功");
    }

    /***
     * 新增User数据
     * @param
     * @return
     */
    @SystemControllerLog(description = "用户管理:新增")
    @PostMapping(value="/add")
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @Transactional(rollbackFor=Exception.class)
    public Result add(@RequestBody @Valid UserSaveDto userDto) throws Exception {
        //调用UserService实现添加User
        userService.addDto(userDto);
        return Result.success("新增成功");
    }

    /***
     * 修改用户状态
     * @param
     * @return
     */
    @PostMapping(value = "/updateStatus" )
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    public Result updateStatus(@RequestBody UserDto userDto){
        Long id = userDto.getId();
        if(id == null){
            return Result.fall("无效ID");
        }
        userService.updateStatus(userDto);
        return Result.success("编辑成功");
    }

    /***
     * 超级管理员修改密码
     * @param
     * @return
     */
    @RequiresRoles(value={"0","1"},logical= Logical.OR)
    @PostMapping(value = "/super/updatePwd" )
    public Result superUpdatePwd(@RequestBody UserSaveDto userDto) throws Exception {
        Long id = userDto.getId();
        if(id == null){
            return Result.fall("无效ID");
        }
        userService.updatePwd(userDto);
        return Result.success("编辑成功");
    }

    /***
     * 修改密码
     * @param
     * @return
     */
    @PostMapping(value = "/updatePwd" )
    public Result updatePwd(@RequestBody UserSaveDto userDto) throws Exception {
        Long id = userDto.getId();
        if(id == null){
            return Result.fall("无效ID");
        }
        userService.updatePwd(userDto);
        return Result.success("修改成功");
    }

}
