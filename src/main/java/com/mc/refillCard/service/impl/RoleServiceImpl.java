package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.RoleMapper;
import com.mc.refillCard.dto.RoleDto;
import com.mc.refillCard.entity.Role;
import com.mc.refillCard.entity.RoleMenu;
import com.mc.refillCard.service.MenuService;
import com.mc.refillCard.service.RoleMenuService;
import com.mc.refillCard.service.RoleService;
import com.mc.refillCard.vo.RoleVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/****
 * @Author: MC
 * @Description: Role业务层接口实现类
 * @Date 2020-9-29 17:00:54
 *****/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private MenuService menuService;


    /**
     Role条件+分页查询
     @param role 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo<RoleVo> findPage(Role role, int page, int size) {
        List<RoleVo> roleVos = new ArrayList<>();
        //分页
        PageHelper.startPage(page, size);
        List<Role> roles = roleMapper.selectByExample(role);
        for (Role roleEntity : roles) {
            RoleVo roleVo = BeanUtil.copyProperties(roleEntity, RoleVo.class);
            Long[] menuIds= menuService.findMenuIdByRoleId(roleEntity.getId());
            roleVo.setMenuId(menuIds);
            roleVos.add(roleVo);
        }
        //针对转换vo后丢失pageInfo分页信息处理
        PageInfo pageInfo = new PageInfo (roles);
        pageInfo.setList(roleVos);
        return pageInfo;
    }

    /**
     * Role条件查询
     * @param role
     * @return
     */
    @Override
    public List<RoleVo> findList(Role role){
        List<RoleVo> roleVos = new ArrayList<>();
        List<Role> roles = roleMapper.selectByExample(role);
        for (Role roleEntity : roles) {
            RoleVo roleVo = BeanUtil.copyProperties(roleEntity, RoleVo.class);
            Long[] menuIds= menuService.findMenuIdByRoleId(roleEntity.getId());
            roleVo.setMenuId(menuIds);
            roleVos.add(roleVo);
        }
        return roleVos;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id) throws Exception {
        Long idFind = roleMapper.findUserByRoleId(id);
        if (idFind !=0){
            throw new Exception("角色已分配用户，请先解除");
        }
        //删除角色
        roleMapper.deleteByPrimaryKey(id);
        //删除角色绑定的用户
        roleMapper.deleteRoleUserByRoleId(id);
        //删除角色绑定的菜单
        roleMenuService.deleteRoleMenuByRoleId(id);
    }

    /**
     * 修改Role
     * @param role
     */
    @Override
    public void update(Role role){
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void updateDto(RoleDto roleDto) throws Exception {
        Role role = BeanUtil.copyProperties(roleDto, Role.class);
        String nickName = role.getNickName();
        Role roleByName = roleMapper.selectByName(nickName);
        Long roleId = role.getId();
        if(null != roleByName && StringUtils.isNotEmpty(roleByName.getNickName())){
            if(!roleId.equals(roleByName.getId())){
                throw new Exception("角色名称已存在，请重新添加");
            }
        }

        role.setRoleName(nickName);
        //保存角色
        roleMapper.updateByPrimaryKeySelective(role);
        roleMenuService.deleteRoleMenuByRoleId(roleId);
        //保存角色菜单
        Long[] menuIds = roleDto.getMenuId();
        if (menuIds != null && menuIds.length > 0) {
            List<RoleMenu> roleMenus = new ArrayList<>();
            //父级
            for (Long menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenus.add(roleMenu);
            }
            roleMenuService.saveBatch(roleMenus);
        }
    }

    /**
     * 增加Role
     * @param role
     */
    @Override
    public Long add(Role role){
        roleMapper.insertSelective(role);
        return null;
    }

    @Override
    public void addDto(RoleDto roleDto) throws Exception {
        Role role = BeanUtil.copyProperties(roleDto, Role.class);
        Role roleByName = roleMapper.selectByName(role.getNickName());
        if (null != roleByName && StringUtils.isNotEmpty(roleByName.getNickName())) {
            throw new Exception("角色名称已存在，请重新添加");
        }
        role.setRoleName(role.getNickName());
        Long[] menuIds = roleDto.getMenuId();
        //保存角色
        roleMapper.insertSelective(role);
         roleByName = roleMapper.selectByName(role.getNickName());
        //保存角色菜单
        if (menuIds != null && menuIds.length > 0) {
            List<RoleMenu> roleMenus = new ArrayList<>();
            for (Long menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleByName.getId());
                roleMenu.setMenuId(menuId);
                roleMenus.add(roleMenu);
            }
            roleMenuService.saveBatch(roleMenus);
        }
    }

    /**
     * 根据ID查询Role
     * @param id
     * @return
     */
    @Override
    public Role findById(Long id){
        return  roleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Role全部数据
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleMapper.selectByExample(new Role());
    }

    @Override
    public Role findRoleByUserId(Long userId) {
        return roleMapper.findRoleByUserId(userId);
    }

    @Override
    public Boolean isAdminRole(Long userId) {
        Role role = roleMapper.findRoleByUserId(userId);
        if(role != null && role.getId()!=null){
            //管理员组等级为0
            if(role.getGrade()== 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public Role findByName(String name) {
        return roleMapper.selectByName(name);
    }


}
