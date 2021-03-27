package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.RoleDto;
import com.mc.refillCard.entity.Role;
import com.mc.refillCard.vo.RoleVo;

import java.util.List;

/****
 * @Author: MC
 * @Description:Role业务层接口
 * @Date 2020-9-29 17:00:54
 *****/
public interface RoleService {


    /***
     * Role多条件分页查询
     * @param role
     * @param page
     * @param size
     * @return
     */
    PageInfo<RoleVo> findPage(Role role, int page, int size);

    /***
     * Role多条件搜索方法
     * @param role
     * @return
     */
    List<RoleVo> findList(Role role);

    /**
    * 根据ID查询Role
    * @param id
    * @return
    */
    Role findById(Long id);

    /***
     * 删除Role
     * @param id
     */
    void delete(Long id) throws Exception;

    /***
     * 修改Role数据
     * @param role
     */
    void update(Role role);

    void updateDto(RoleDto roleDto) throws Exception;

    /***
     * 新增Role
     * @param role
     */
    Long add(Role role);

    void addDto(RoleDto roleDto) throws Exception;

    /***
     * 查询所有Role
     * @return
     */
    List<Role> findAll();

    Role findRoleByUserId(Long userId);

    /**
     *  判断是否为管理员 true：是
     * @param userId
     * @return
     */
    Boolean isAdminRole(Long userId);

    /**
     *  根据名称查询角色
     * @param name
     * @return
     */
    Role findByName(String name);
}
