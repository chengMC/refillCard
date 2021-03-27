package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.entity.RoleUser;

import java.util.List;

/****
 * @Author: MC
 * @Description:RoleUser业务层接口
 * @Date 2020-9-29 17:00:55
 *****/
public interface RoleUserService {


    /***
     * RoleUser多条件分页查询
     * @param roleUser
     * @param page
     * @param size
     * @return
     */
    PageInfo<RoleUser> findPage(RoleUser roleUser, int page, int size);

    /***
     * RoleUser多条件搜索方法
     * @param roleUser
     * @return
     */
    List<RoleUser> findList(RoleUser roleUser);

    /**
    * 根据ID查询RoleUser
    * @param id
    * @return
    */
    RoleUser findById(Long id);

    /***
     * 删除RoleUser
     * @param id
     */
    void delete(Long id);

    /***
     * 修改RoleUser数据
     * @param roleUser
     */
    void update(RoleUser roleUser);

    /***
     * 新增RoleUser
     * @param roleUser
     */
    void add(RoleUser roleUser);

    /***
     * 查询所有RoleUser
     * @return
     */
    List<RoleUser> findAll();

    List<RoleUser> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    /**
     * 绑定用户权限组
     *
     * @param userId
     */
    void saveRoleUser(Long userId, String roleName);


}
