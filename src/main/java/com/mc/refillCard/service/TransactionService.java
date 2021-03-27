package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.TransactionDto;
import com.mc.refillCard.entity.Transaction;
import com.mc.refillCard.entity.UserRelate;
import com.mc.refillCard.vo.TransactionVo;

import java.util.List;
import java.util.Map;

/****
 * @Author: MC
 * @Description:Transaction业务层接口
 * @Date 2021-3-20 20:24:42
 *****/
public interface TransactionService {


    /***
     * Transaction多条件分页查询
     * @param transactionDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<TransactionVo> findPage(TransactionDto transactionDto, int page, int size);

    /***
     * Transaction多条件搜索方法
     * @param transaction
     * @return
     */
    List<Transaction> findList(Transaction transaction);

    /**
    * 根据ID查询Transaction
    * @param id
    * @return
    */
    Transaction findById(Long id);

    /***
     * 删除Transaction
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Transaction数据
     * @param transaction
     */
    void update(Transaction transaction);

    /***
     * 修改Transaction数据
     * @param transactionDto
     */
    void updateDto(TransactionDto transactionDto);

    /***
     * 新增Transaction
     * @param transaction
     */
    void add(Transaction transaction);
    /***
     * 新增Transaction
     * @param transactionDto
     * @return
     */
    Long addDto(TransactionDto transactionDto);

    /***
     * 查询所有Transaction
     * @return
     */
    List<Transaction> findAll();

    /**
     * 下单
     * @param transactionDto
     * @return
     */
    Map placeOrder(TransactionDto transactionDto, UserRelate userRelate);
}
