package com.mc.refillCard.dao;

import com.mc.refillCard.entity.UserRelate;
import com.mc.refillCard.dto.UserRelateDto;
import com.mc.refillCard.vo.UserRelateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:UserRelate的Dao
 * @Date 2021-3-21 16:20:43
 *****/
@Mapper
public interface UserRelateMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRelate userRelate);

    int insertSelective(UserRelate userRelate);

    UserRelate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRelate userRelate);

    int updateByPrimaryKey(UserRelate userRelate);

    List<UserRelate> selectByExample(UserRelate userRelate);

    List<UserRelateVo> findAll();

    List<UserRelateVo> findPageVoByExample(UserRelateDto userRelateDto);

    UserRelate findBySecret(@Param("secret") String secret);

    UserRelate findByPlatformUserId(String platformUserId);
}
