package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.entity.Menu;
import com.mc.refillCard.vo.MenuIndexVo;
import com.mc.refillCard.vo.MenuVo;

import java.util.List;

/****
 * @Author: MC
 * @Description:Menu业务层接口
 * @Date 2020-9-29 17:00:54
 *****/
public interface MenuService {


    /***
     * Menu多条件分页查询
     * @param menu
     * @param page
     * @param size
     * @return
     */
    PageInfo<Menu> findPage(Menu menu, int page, int size);

    /***
     * Menu多条件搜索方法
     * @param menu
     * @return
     */
    List<Menu> findList(Menu menu);

    /**
    * 根据ID查询Menu
    * @param id
    * @return
    */
    Menu findById(Long id);

    /***
     * 删除Menu
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Menu数据
     * @param menu
     */
    void update(Menu menu);

    /***
     * 新增Menu
     * @param menu
     */
    void add(Menu menu);

    /***
     * 查询所有Menu
     * @return
     */
    List<Menu> findAll();

    List<MenuVo> findMenuVoByUserId(Long userId);

    List<Menu> findMenuByUserId(Long userId);

    List<MenuVo> findTreeAll();

    Long[] findMenuIdByRoleId(Long id);

    List<Menu> findAllByParentId(List<Long> longs);

    List<MenuIndexVo> findMenuIndexVoByUserId(Long userId);

    List<MenuVo> findTree();

}
