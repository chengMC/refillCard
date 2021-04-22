package com.mc.refillCard.dao;

import com.mc.refillCard.entity.Goods;
import com.mc.refillCard.dto.GoodsDto;
import com.mc.refillCard.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:Goods的Dao
 * @Date 2021-3-28 20:28:52
 *****/
@Mapper
public interface GoodsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Goods goods);

    int insertSelective(Goods goods);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods goods);

    int updateByPrimaryKey(Goods goods);

    List<Goods> selectByExample(Goods goods);

    List<Goods> findAll();

    List<GoodsVo> findPageVoByExample(GoodsDto goodsDto);

    List<Goods> findListByType(@Param("platform") Integer platform, @Param("type") Integer type);

    Goods findByArea(@Param("area") String area);

    void batchAdd(@Param("goodsList") List<Goods> goodsList);
}
