package com.mc.refillCard.dao;

import com.mc.refillCard.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:RoleMenu的Dao
 * @Date 2020-9-29 17:00:55
 *****/
@Mapper
public interface RoleMenuMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RoleMenu roleMenu);

    int insertSelective(RoleMenu roleMenu);

    RoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleMenu roleMenu);

    int updateByPrimaryKey(RoleMenu roleMenu);

    List<RoleMenu> selectByExample(RoleMenu roleMenu);

    List<RoleMenu> findAll();

    void saveBatch(@Param("roleMenus") List<RoleMenu> roleMenus);

    void deleteRoleMenuByRoleId(Long roleId);

}
