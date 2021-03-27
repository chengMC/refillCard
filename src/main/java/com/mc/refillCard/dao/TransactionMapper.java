package com.mc.refillCard.dao;
import com.mc.refillCard.entity.Transaction;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.vo.TransactionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author: MC
 * @Description:Transaction的Dao
 * @Date 2021-3-20 20:24:42
 *****/
@Mapper
public interface TransactionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Transaction transaction);

    int insertSelective(Transaction transaction);

    Transaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Transaction transaction);

    int updateByPrimaryKey(Transaction transaction);

    List<Transaction> selectByExample(Transaction transaction);

    List<Transaction> findAll();

    List<TransactionVo> findPageVoByExample(TransactionDto transactionDto);

    Transaction findByTid(String tid);
}
