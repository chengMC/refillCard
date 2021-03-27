package com.mc.refillCard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.SysDictMapper;
import com.mc.refillCard.entity.SysDict;
import com.mc.refillCard.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author: MC
 * @Description: SysDict业务层接口实现类
 * @Date 2021-1-26 17:11:37
 *****/
@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    /**
     SysDict条件+分页查询
     @param sysDict 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(SysDict sysDict, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //执行搜索
        return new PageInfo<SysDict>(sysDictMapper.selectByExample(sysDict));
    }

    /**
     * SysDict条件查询
     * @param sysDict
     * @return
     */
    @Override
    public List<SysDict> findList(SysDict sysDict){
        //根据构建的条件查询数据
        return sysDictMapper.selectByExample(sysDict);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        sysDictMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改SysDict
     * @param sysDict
     */
    @Override
    public void update(SysDict sysDict){
        sysDictMapper.updateByPrimaryKeySelective(sysDict);
    }

    /**
     * 增加SysDict
     * @param sysDict
     */
    @Override
    public void add(SysDict sysDict){
        sysDictMapper.insertSelective(sysDict);
    }

    /**
     * 根据ID查询SysDict
     * @param id
     * @return
     */
    @Override
    public SysDict findById(Long id){
        return  sysDictMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询SysDict全部数据
     * @return
     */
    @Override
    public List<SysDict> findAll() {
        return sysDictMapper.findAll();
    }

    @Override
    public List<SysDict> findListByCode(String code) {
        return sysDictMapper.findByCode(code);
    }

    @Override
    public SysDict findByCode(String code) {
        return sysDictMapper.findByCode(code).get(0);
    }


}
