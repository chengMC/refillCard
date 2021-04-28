package com.mc.refillCard.dao;
import com.mc.refillCard.entity.GoodType;
import com.mc.refillCard.dto.GoodTypeDto;
import com.mc.refillCard.vo.GoodTypeEnumVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author: MC
 * @Description:GoodType的Dao
 * @Date 2021-4-7 21:05:20
 *****/
@Mapper
public interface GoodTypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GoodType goodType);

    int insertSelective(GoodType goodType);

    GoodType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodType goodType);

    int updateByPrimaryKey(GoodType goodType);

    List<GoodType> selectByExample(GoodType goodType);

    List<GoodType> findAll();

    List<GoodTypeEnumVo> findPageVoByExample(GoodTypeDto goodTypeDto);

    List<GoodTypeEnumVo> findVoAll();

}
