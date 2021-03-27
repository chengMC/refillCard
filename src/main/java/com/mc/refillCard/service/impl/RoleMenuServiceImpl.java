package com.mc.refillCard.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.RoleMenuMapper;
import com.mc.refillCard.entity.RoleMenu;
import com.mc.refillCard.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author: MC
 * @Description: RoleMenu业务层接口实现类
 * @Date 2020-9-29 17:00:55
 *****/
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     RoleMenu条件+分页查询
     @param roleMenu 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo<RoleMenu> findPage(RoleMenu roleMenu, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //执行搜索
        return new PageInfo<RoleMenu>(roleMenuMapper.selectByExample(roleMenu));
    }

    /**
     * RoleMenu条件查询
     * @param roleMenu
     * @return
     */
    @Override
    public List<RoleMenu> findList(RoleMenu roleMenu){
        //根据构建的条件查询数据
        return roleMenuMapper.selectByExample(roleMenu);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        roleMenuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改RoleMenu
     * @param roleMenu
     */
    @Override
    public void update(RoleMenu roleMenu){
        roleMenuMapper.updateByPrimaryKeySelective(roleMenu);
    }

    /**
     * 增加RoleMenu
     * @param roleMenu
     */
    @Override
    public void add(RoleMenu roleMenu){
        roleMenuMapper.insertSelective(roleMenu);
    }

    /**
     * 根据ID查询RoleMenu
     * @param id
     * @return
     */
    @Override
    public RoleMenu findById(Long id){
        return  roleMenuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询RoleMenu全部数据
     * @return
     */
    @Override
    public List<RoleMenu> findAll() {
        return roleMenuMapper.findAll();
    }

    @Override
    public void saveBatch(List<RoleMenu> roleMenus) {
        if(CollectionUtil.isNotEmpty(roleMenus)){
            roleMenuMapper.saveBatch(roleMenus);
        }
    }

    @Override
    public void deleteRoleMenuByRoleId(Long roleId) {
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
    }

}
