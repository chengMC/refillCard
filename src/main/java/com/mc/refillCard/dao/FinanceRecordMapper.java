package com.mc.refillCard.dao;

import com.mc.refillCard.dto.FinanceRecordDto;
import com.mc.refillCard.entity.FinanceRecord;
import com.mc.refillCard.vo.FinanceRecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FinanceRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FinanceRecord record);

    int insertSelective(FinanceRecord record);

    FinanceRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinanceRecord record);

    int updateByPrimaryKey(FinanceRecord record);

    List<FinanceRecord> selectByExample(FinanceRecord financeRecord);

    List<FinanceRecord> findAll();

    List<FinanceRecordVo> findPageVoByExample(FinanceRecordDto financeRecordDto);
}