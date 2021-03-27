package com.mc.refillCard.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.RoleUserMapper;
import com.mc.refillCard.entity.Role;
import com.mc.refillCard.entity.RoleUser;
import com.mc.refillCard.service.RoleService;
import com.mc.refillCard.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/****
 * @Author: MC
 * @Description: RoleUser业务层接口实现类
 * @Date 2020-9-29 17:00:55
 *****/
@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    private RoleUserMapper roleUserMapper;
    @Autowired
    private RoleService roleService;


    /**
     RoleUser条件+分页查询
     @param roleUser 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo<RoleUser> findPage(RoleUser roleUser, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //执行搜索
        return new PageInfo<RoleUser>(roleUserMapper.selectByExample(roleUser));
    }

    /**
     * RoleUser条件查询
     * @param roleUser
     * @return
     */
    @Override
    public List<RoleUser> findList(RoleUser roleUser){
        //根据构建的条件查询数据
        return roleUserMapper.selectByExample(roleUser);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        roleUserMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改RoleUser
     * @param roleUser
     */
    @Override
    public void update(RoleUser roleUser){
        roleUserMapper.updateByPrimaryKeySelective(roleUser);
    }

    /**
     * 增加RoleUser
     * @param roleUser
     */
    @Override
    public void add(RoleUser roleUser){

        roleUserMapper.insertSelective(roleUser);
    }

    /**
     * 根据ID查询RoleUser
     * @param id
     * @return
     */
    @Override
    public RoleUser findById(Long id){
        return  roleUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询RoleUser全部数据
     * @return
     */
    @Override
    public List<RoleUser> findAll() {
        return roleUserMapper.findAll();
    }

    @Override
    public List<RoleUser> findByUserId(Long userId) {
        return roleUserMapper.findByUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
         roleUserMapper.deleteByUserId(userId);
    }


    @Override
    public void saveRoleUser(Long userId,String roleName) {
        Role role = roleService.findByName(roleName);
        Long roleId;
        if (role == null) {
            //查询不到则先新增用户组
            Role roleEntity = new Role();
            roleEntity.setRoleName(roleName);
            roleEntity.setDescription(roleName);
            roleEntity.setNickName(roleName);
            roleId = roleService.add(roleEntity);
        } else {
            roleId = role.getId();
        }
        //查询不到自身角色则创建
        List<RoleUser> roleUserList = roleUserMapper.findByUserId(userId);
        if (CollectionUtil.isEmpty(roleUserList)) {
            addRoleUser(roleId,userId);
        }else{
            //多条先删除后在添加
            roleUserMapper.deleteByUserId(userId);
            addRoleUser(roleId,userId);
        }
    }


    public void addRoleUser(Long roleId,Long userId){
        //添加用户为管理员
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(roleId);
        roleUser.setUserId(userId);
        roleUser.setStatus(0L);
        roleUser.setCreateTime(new Date());
        add(roleUser);
    }


}
