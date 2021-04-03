package com.mc.refillCard.dao;

import com.mc.refillCard.entity.GoodsRelateFulu;
import com.mc.refillCard.dto.GoodsRelateFuluDto;
import com.mc.refillCard.vo.GoodsRelateFuluVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/****
 * @Author: MC
 * @Description:GoodsRelateFulu的Dao
 * @Date 2021-3-28 20:16:38
 *****/
@Mapper
public interface GoodsRelateFuluMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GoodsRelateFulu goodsRelateFulu);

    int insertSelective(GoodsRelateFulu goodsRelateFulu);

    GoodsRelateFulu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsRelateFulu goodsRelateFulu);

    int updateByPrimaryKey(GoodsRelateFulu goodsRelateFulu);

    List<GoodsRelateFulu> selectByExample(GoodsRelateFulu goodsRelateFulu);

    List<GoodsRelateFulu> findAll();

    List<GoodsRelateFuluVo> findPageVoByExample(GoodsRelateFuluDto goodsRelateFuluDto);

    GoodsRelateFulu findByGoodId(@Param("goodId") Long goodId, @Param("userId") Long userId);

    void batchAdd(@Param("goodsRelateFulus") List<GoodsRelateFulu> goodsRelateFulus);
}
