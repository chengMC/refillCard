package com.mc.refillCard.dao;

import com.mc.refillCard.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:User的Dao
 * @Date 2020-8-11 17:57:52
 *****/
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);

    List<User> selectByExample(User user);

    List<User> findAll();

    User findUser(User user);

    User findByUserName(@Param("userName") String userName);

    User findByPrimary(User user);

    /**
     * 查询手机号数量
     * @param userName
     * @return
     */
    Integer getCountByPhone(String userName);

}
