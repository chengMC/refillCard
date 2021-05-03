package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.FinanceRecordDto;
import com.mc.refillCard.entity.FinanceRecord;
import com.mc.refillCard.vo.FinanceRecordVo;

import java.util.List;

/****
 * @Author: MC
 * @Description:FinanceRecord业务层接口
 * @Date 2021-5-3 13:54:54
 *****/
public interface FinanceRecordService {


    /***
     * FinanceRecord多条件分页查询
     * @param financeRecordDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<FinanceRecordVo> findPage(FinanceRecordDto financeRecordDto, int page, int size);

    /***
     * FinanceRecord多条件搜索方法
     * @param financeRecord
     * @return
     */
    List<FinanceRecord> findList(FinanceRecord financeRecord);

    /**
    * 根据ID查询FinanceRecord
    * @param id
    * @return
    */
    FinanceRecord findById(Long id);

    /***
     * 删除FinanceRecord
     * @param id
     */
    void delete(Long id);

    /***
     * 修改FinanceRecord数据
     * @param financeRecord
     */
    void update(FinanceRecord financeRecord);

    /***
     * 修改FinanceRecord数据
     * @param financeRecordDto
     */
    void updateDto(FinanceRecordDto financeRecordDto);

    /***
     * 新增FinanceRecord
     * @param financeRecord
     */
    void add(FinanceRecord financeRecord);
    /***
     * 新增FinanceRecord
     * @param financeRecordDto
     */
    void addDto(FinanceRecordDto financeRecordDto);

    /***
     * 查询所有FinanceRecord
     * @return
     */
    List<FinanceRecord> findAll();
}
