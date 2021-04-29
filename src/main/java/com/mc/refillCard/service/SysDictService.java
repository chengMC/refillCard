package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.entity.SysDict;
import com.mc.refillCard.vo.SysDictVo;

import java.util.List;

/****
 * @Author: MC
 * @Description:SysDict业务层接口
 * @Date 2021-1-26 17:11:37
 *****/
public interface SysDictService {


    /***
     * SysDict多条件分页查询
     * @param sysDict
     * @param page
     * @param size
     * @return
     */
    PageInfo<SysDict> findPage(SysDict sysDict, int page, int size);

    /***
     * SysDict多条件搜索方法
     * @param sysDict
     * @return
     */
    List<SysDict> findList(SysDict sysDict);

    /**
    * 根据ID查询SysDict
    * @param id
    * @return
    */
    SysDict findById(Long id);

    /***
     * 删除SysDict
     * @param id
     */
    void delete(Long id);

    /***
     * 修改SysDict数据
     * @param sysDict
     */
    void update(SysDict sysDict);

    /***
     * 新增SysDict
     * @param sysDict
     */
    void add(SysDict sysDict);

    /***
     * 查询所有SysDict
     * @return
     */
    List<SysDict> findAll();

    /***
     * 根据code获取对应值
     * @return
     */
    List<SysDict> findListByCode(String code);

    /***
     * 根据code获取对应值
     * @return
     */
    SysDict findByCode(String code);

    List<SysDict> findListTypeByCode(String dataCode);

    List<SysDictVo> findListVoByCode(String dataCode);
}
