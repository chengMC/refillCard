package com.mc.refillCard.dao;

import com.mc.refillCard.dto.PlatformKeyDto;
import com.mc.refillCard.entity.PlatformKey;
import com.mc.refillCard.vo.PlatformKeyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:PlatformKey的Dao
 * @Date 2021-5-4 22:30:48
 *****/
@Mapper
public interface PlatformKeyMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PlatformKey platformKey);

    int insertSelective(PlatformKey platformKey);

    PlatformKey selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlatformKey platformKey);

    int updateByPrimaryKey(PlatformKey platformKey);

    List<PlatformKey> selectByExample(PlatformKey platformKey);

    List<PlatformKey> findAll();

    List<PlatformKeyVo> findPageVoByExample(PlatformKeyDto platformKeyDto);

    PlatformKey findByAppType(@Param("appType") Integer appType);

    List<PlatformKey> findListByAppType(@Param("appType") Integer appType);
}
