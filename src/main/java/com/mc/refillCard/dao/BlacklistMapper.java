package com.mc.refillCard.dao;

import com.mc.refillCard.dto.BlacklistDto;
import com.mc.refillCard.entity.Blacklist;
import com.mc.refillCard.vo.BlacklistVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author: MC
 * @Description:Blacklist的Dao
 * @Date 2021-4-7 21:03:32
 *****/
@Mapper
public interface BlacklistMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Blacklist blacklist);

    int insertSelective(Blacklist blacklist);

    Blacklist selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Blacklist blacklist);

    int updateByPrimaryKey(Blacklist blacklist);

    List<Blacklist> selectByExample(Blacklist blacklist);

    List<Blacklist> findAll();

    List<BlacklistVo> findPageVoByExample(BlacklistDto blacklistDto);

    List<Blacklist> findListByAccount(Blacklist blacklist);
}
