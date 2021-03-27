package com.mc.refillCard.dao;

import com.mc.refillCard.entity.GoodsRelate;
import com.mc.refillCard.dto.GoodsRelateDto;
import com.mc.refillCard.vo.GoodsRelateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:GoodsRelate的Dao
 * @Date 2021-3-21 16:36:30
 *****/
@Mapper
public interface GoodsRelateMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GoodsRelate goodsRelate);

    int insertSelective(GoodsRelate goodsRelate);

    GoodsRelate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsRelate goodsRelate);

    int updateByPrimaryKey(GoodsRelate goodsRelate);

    List<GoodsRelate> selectByExample(GoodsRelate goodsRelate);

    List<GoodsRelate> findAll();

    List<GoodsRelateVo> findPageVoByExample(GoodsRelateDto goodsRelateDto);

    List<GoodsRelate> findByGoodId(@Param("goodId") Long goodId);
}
