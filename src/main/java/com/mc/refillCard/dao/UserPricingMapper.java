package com.mc.refillCard.dao;

import com.mc.refillCard.dto.UserPricingDto;
import com.mc.refillCard.entity.UserPricing;
import com.mc.refillCard.vo.UserPricingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:UserPricing的Dao
 * @Date 2021-4-24 21:40:05
 *****/
@Mapper
public interface UserPricingMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserPricing userPricing);

    int insertSelective(UserPricing userPricing);

    UserPricing selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPricing userPricing);

    int updateByPrimaryKey(UserPricing userPricing);

    List<UserPricing> selectByExample(UserPricing userPricing);

    List<UserPricing> findAll();

    List<UserPricingVo> findPageVoByExample(UserPricingDto userPricingDto);

    UserPricing findByUserIdAndType(@Param("userId") Long userId, @Param("type") Integer type);
}
