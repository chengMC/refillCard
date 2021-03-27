package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.entity.RoleMenu;

import java.util.List;

/****
 * @Author: MC
 * @Description:RoleMenu业务层接口
 * @Date 2020-9-29 17:00:55
 *****/
public interface RoleMenuService {


    /***
     * RoleMenu多条件分页查询
     * @param roleMenu
     * @param page
     * @param size
     * @return
     */
    PageInfo<RoleMenu> findPage(RoleMenu roleMenu, int page, int size);

    /***
     * RoleMenu多条件搜索方法
     * @param roleMenu
     * @return
     */
    List<RoleMenu> findList(RoleMenu roleMenu);

    /**
    * 根据ID查询RoleMenu
    * @param id
    * @return
    */
    RoleMenu findById(Long id);

    /***
     * 删除RoleMenu
     * @param id
     */
    void delete(Long id);

    /***
     * 修改RoleMenu数据
     * @param roleMenu
     */
    void update(RoleMenu roleMenu);

    /***
     * 新增RoleMenu
     * @param roleMenu
     */
    void add(RoleMenu roleMenu);

    /***
     * 查询所有RoleMenu
     * @return
     */
    List<RoleMenu> findAll();

    void saveBatch(List<RoleMenu> roleMenus);

    void deleteRoleMenuByRoleId(Long roleId);

}
