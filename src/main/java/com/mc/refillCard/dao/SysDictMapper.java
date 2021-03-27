package com.mc.refillCard.dao;

import com.mc.refillCard.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author: MC
 * @Description:SysDict的Dao
 * @Date 2021-1-26 17:11:37
 *****/
@Mapper
public interface SysDictMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysDict sysDict);

    int insertSelective(SysDict sysDict);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict sysDict);

    int updateByPrimaryKey(SysDict sysDict);

    List<SysDict> selectByExample(SysDict sysDict);

    List<SysDict> findAll();

    List<SysDict> findByCode(String code);
}
