package com.mc.refillCard.dao;

import com.mc.refillCard.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:Menu的Dao
 * @Date 2020-9-29 17:00:54
 *****/
@Mapper
public interface MenuMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Menu menu);

    int insertSelective(Menu menu);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu menu);

    int updateByPrimaryKey(Menu menu);

    List<Menu> selectByExample(Menu menu);

    List<Menu> findAll();

    List<Menu> findMenuByUserId(Long userId);

    Long[] findMenuIdByRoleId(Long id);

    List<Menu> findAllByParentId(@Param("menuIds") List<Long> menuIds);

    List<Menu> findTree();

}
