package com.mc.refillCard.dao;

import com.mc.refillCard.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:Role的Dao
 * @Date 2020-9-29 17:00:54
 *****/
@Mapper
public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Role role);

    int insertSelective(Role role);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role role);

    int updateByPrimaryKey(Role role);

    List<Role> selectByExample(Role role);

    List<Role> findAll();

    Role findRoleByUserId(Long userId);

    Role selectByName(String roleName);

    int deleteRoleUserByRoleId(@Param("roleId") Long roleId);

    Long findUserByRoleId(@Param("id") Long id);

}
