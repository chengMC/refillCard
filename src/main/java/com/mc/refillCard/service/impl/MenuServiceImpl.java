package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.MenuMapper;
import com.mc.refillCard.entity.Menu;
import com.mc.refillCard.service.MenuService;
import com.mc.refillCard.vo.MenuIndexUserVo;
import com.mc.refillCard.vo.MenuIndexVo;
import com.mc.refillCard.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/****
 * @Author: MC
 * @Description: Menu业务层接口实现类
 * @Date 2020-9-29 17:00:54
 *****/
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     Menu条件+分页查询
     @param menu 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo<Menu> findPage(Menu menu, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //执行搜索
        return new PageInfo<Menu>(menuMapper.selectByExample(menu));
    }

    /**
     * Menu条件查询
     * @param menu
     * @return
     */
    @Override
    public List<Menu> findList(Menu menu){
        //根据构建的条件查询数据
        return menuMapper.selectByExample(menu);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        menuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Menu
     * @param menu
     */
    @Override
    public void update(Menu menu){
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 增加Menu
     * @param menu
     */
    @Override
    public void add(Menu menu){
        menuMapper.insertSelective(menu);
    }

    /**
     * 根据ID查询Menu
     * @param id
     * @return
     */
    @Override
    public Menu findById(Long id){
        return  menuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Menu全部数据
     * @return
     */
    @Override
    public List<Menu> findAll() {
        return menuMapper.findAll();
    }

    @Override
    public List<MenuVo> findMenuVoByUserId(Long userId) {
        List<Menu> menuList = menuMapper.findMenuByUserId(userId);
        List<MenuVo> menuVos = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuVo menuVo = BeanUtil.copyProperties(menu, MenuVo.class);
            menuVos.add(menuVo);
        }
        return builderMenuTree(menuVos);
    }

    @Override
    public List<Menu> findMenuByUserId(Long userId) {
        return menuMapper.findMenuByUserId(userId);
    }

    @Override
    public List<MenuVo> findTreeAll() {
        List<MenuVo> menuVos = new ArrayList<>();;
        List<Menu> all = menuMapper.findAll();
        for (Menu menu : all) {
            MenuVo menuVo = BeanUtil.copyProperties(menu, MenuVo.class);
            menuVos.add(menuVo);
        }
        return builderMenuTree(menuVos);
    }

    @Override
    public Long[] findMenuIdByRoleId(Long id) {
        return menuMapper.findMenuIdByRoleId(id);
    }

    @Override
    public List<Menu> findAllByParentId(List<Long> menuIds) {
        return menuMapper.findAllByParentId(menuIds);
    }

    @Override
    public List<MenuIndexVo> findMenuIndexVoByUserId(Long userId) {
        List<MenuIndexVo> menuIndexVoList = new ArrayList<>();
        //查询用户菜单
        List<Menu> menuList = menuMapper.findMenuByUserId(userId);
        //组合数据返回前端
        String checked = "checked/";String uncheck = "uncheck/";
        for (Menu menu : menuList) {
            MenuIndexVo menuIndexVo = new MenuIndexVo();
            menuIndexVo.setId(menu.getId());
            menuIndexVo.setParentId(menu.getParentId());
            menuIndexVo.setIcon(menu.getImgUrl());
            menuIndexVo.setChecked(checked+menu.getMark());
            menuIndexVo.setUncheck(uncheck+menu.getMark());
            menuIndexVo.setModuleName(menu.getDisplayName());
            menuIndexVo.setRouteName(menu.getUrl());
            menuIndexVo.setChildren(null);
            menuIndexVoList.add(menuIndexVo);
        }
        return builderMenuIndexVoTree(menuIndexVoList);
    }

    @Override
    public List<MenuIndexUserVo> findMenuIndexUserVoByUserId(Long userId) {
        List<MenuIndexUserVo> menuIndexVoList = new ArrayList<>();
        //查询用户菜单
        List<Menu> menuList = menuMapper.findMenuByUserId(userId);
        for (Menu menu : menuList) {
            MenuIndexUserVo menuIndexVo = new MenuIndexUserVo();
            menuIndexVo.setId(menu.getId());
            menuIndexVo.setParentId(menu.getParentId());
            menuIndexVo.setIcon(menu.getImgUrl());
            menuIndexVo.setIndex(menu.getUrl());
            menuIndexVo.setTitle(menu.getDisplayName());
            menuIndexVo.setSubs(null);
            menuIndexVoList.add(menuIndexVo);
        }
        return builderMenuIndexUserVoTree(menuIndexVoList);
    }

    private List<MenuIndexUserVo> builderMenuIndexUserVoTree(List<MenuIndexUserVo> all) {
        List<MenuIndexUserVo> resultList = new ArrayList();
        if (all.isEmpty()){
            return null;
        }
        for (MenuIndexUserVo m:all) {
            if (m.getParentId()==0){
                resultList.add(m);
            }
        }
        for (MenuIndexUserVo m:resultList) {
            List<MenuIndexUserVo> collect = all.stream().filter(menu -> menu.getParentId().equals(m.getId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect)){
                m.setSubs(collect);
            }
        }
        return resultList;
    }


    @Override
    public List<MenuVo> findTree() {
        List<MenuVo> menuVos = new ArrayList<>();;
        List<Menu> all = menuMapper.findTree();
        for (Menu menu : all) {
            MenuVo menuVo = BeanUtil.copyProperties(menu, MenuVo.class);
            menuVos.add(menuVo);
        }
        return menuVos;
    }

    public List<MenuIndexVo> builderMenuIndexVoTree(List<MenuIndexVo> all) {
        List<MenuIndexVo> resultList = new ArrayList();
        if (all.isEmpty()){
            return null;
        }
        for (MenuIndexVo m:all) {
            if (m.getParentId()==0){
                resultList.add(m);
            }
        }
        for (MenuIndexVo m:resultList) {
            List<MenuIndexVo> collect = all.stream().filter(menu -> menu.getParentId().equals(m.getId())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect)){
                m.setChildren(collect);
            }
        }
        return resultList;
    }

    public List<MenuVo> builderMenuTree(List<MenuVo> all) {
        List<MenuVo> resultList = new ArrayList();
        if (all.isEmpty()){
            return null;
        }
        for (MenuVo m:all) {
            if (m.getParentId()==0){
                resultList.add(m);
            }
        }
        for (MenuVo m:resultList) {
            List<MenuVo> collect = all.stream().filter(menu -> menu.getParentId().equals(m.getId())).collect(Collectors.toList());
            m.setChildren(collect);
        }
        return resultList;
    }


}
