package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.dto.OriginalOrderQueryDto;
import com.mc.refillCard.entity.OriginalOrder;
import com.mc.refillCard.vo.OriginalOrderVo;

import java.util.List;

/****
 * @Author: MC
 * @Description:OriginalOrder业务层接口
 * @Date 2021-3-21 14:41:57
 *****/
public interface OriginalOrderService {


    /***
     * OriginalOrder多条件分页查询
     * @param originalOrderDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<OriginalOrderVo> findPage(OriginalOrderQueryDto originalOrderDto, int page, int size);

    /***
     * OriginalOrder多条件搜索方法
     * @param originalOrder
     * @return
     */
    List<OriginalOrder> findList(OriginalOrder originalOrder);

    /**
    * 根据ID查询OriginalOrder
    * @param id
    * @return
    */
    OriginalOrder findById(Long id);

    /***
     * 删除OriginalOrder
     * @param id
     */
    void delete(Long id);

    /***
     * 修改OriginalOrder数据
     * @param originalOrder
     */
    void update(OriginalOrder originalOrder);

    /***
     * 修改OriginalOrder数据
     * @param originalOrderDto
     */
    void updateDto(OriginalOrderDto originalOrderDto);

    /***
     * 新增OriginalOrder
     * @param originalOrder
     */
    void add(OriginalOrder originalOrder);
    /***
     * 新增OriginalOrder
     * @param originalOrderDto
     */
    void addDto(OriginalOrderDto originalOrderDto);

    /***
     * 查询所有OriginalOrder
     * @return
     */
    List<OriginalOrder> findAll();
}
