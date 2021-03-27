package com.mc.refillCard.dao;

import com.mc.refillCard.entity.RoleUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author: MC
 * @Description:RoleUser的Dao
 * @Date 2020-9-29 17:00:55
 *****/
@Mapper
public interface RoleUserMapper {

    int deleteByPrimaryKey(Long id);

    int deleteByUserId(Long userId);

    int insert(RoleUser roleUser);

    int insertSelective(RoleUser roleUser);

    RoleUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleUser roleUser);

    int updateByPrimaryKey(RoleUser roleUser);

    List<RoleUser> selectByExample(RoleUser roleUser);

    List<RoleUser> findAll();

    List<RoleUser> findByUserId(Long userId);


}
