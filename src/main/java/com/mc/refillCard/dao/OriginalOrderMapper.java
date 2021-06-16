package com.mc.refillCard.dao;

import com.mc.refillCard.dto.OriginalOrderQueryDto;
import com.mc.refillCard.entity.OriginalOrder;
import com.mc.refillCard.vo.OriginalOrderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author: MC
 * @Description:OriginalOrder的Dao
 * @Date 2021-3-21 14:41:57
 *****/
@Mapper
public interface OriginalOrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OriginalOrder originalOrder);

    int insertSelective(OriginalOrder originalOrder);

    OriginalOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OriginalOrder originalOrder);

    int updateByPrimaryKey(OriginalOrder originalOrder);

    List<OriginalOrder> selectByExample(OriginalOrder originalOrder);

    List<OriginalOrder> findAll();

    List<OriginalOrderVo> findPageVoByExample(OriginalOrderQueryDto originalOrderDto);

    void orderFail();

    List<OriginalOrder> findListByFail();

}
