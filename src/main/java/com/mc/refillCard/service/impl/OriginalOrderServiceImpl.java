package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.OriginalOrderMapper;
import com.mc.refillCard.dto.OriginalOrderDto;
import com.mc.refillCard.entity.OriginalOrder;
import com.mc.refillCard.service.OriginalOrderService;
import com.mc.refillCard.vo.OriginalOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author: MC
 * @Description: OriginalOrder业务层接口实现类
 * @Date 2021-3-21 14:41:57
 *****/
@Service
public class OriginalOrderServiceImpl implements OriginalOrderService {

    @Autowired
    private OriginalOrderMapper originalOrderMapper;

    /**
     OriginalOrder条件+分页查询
     @param originalOrderDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(OriginalOrderDto originalOrderDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<OriginalOrderVo> originalOrderVos = originalOrderMapper.findPageVoByExample(originalOrderDto);
        //执行搜索
        return new PageInfo(originalOrderVos);
    }

    /**
     * OriginalOrder条件查询
     * @param originalOrder
     * @return
     */
    @Override
    public List<OriginalOrder> findList(OriginalOrder originalOrder){
        //根据构建的条件查询数据
        return originalOrderMapper.selectByExample(originalOrder);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        originalOrderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改OriginalOrder
     * @param originalOrder
     */
    @Override
    public void update(OriginalOrder originalOrder){
        originalOrderMapper.updateByPrimaryKeySelective(originalOrder);
    }

    /**
     * 修改OriginalOrder
     * @param originalOrderDto
     */
    @Override
    public void updateDto(OriginalOrderDto originalOrderDto){
        OriginalOrder originalOrder = BeanUtil.copyProperties(originalOrderDto, OriginalOrder.class);
        originalOrderMapper.updateByPrimaryKeySelective(originalOrder);
    }

    /**
     * 增加OriginalOrder
     * @param originalOrder
     */
    @Override
    public void add(OriginalOrder originalOrder){
        originalOrderMapper.insertSelective(originalOrder);
    }

    /**
     * 增加OriginalOrder
     * @param originalOrderDto
     */
    @Override
    public void addDto(OriginalOrderDto originalOrderDto){
        OriginalOrder originalOrder = BeanUtil.copyProperties(originalOrderDto, OriginalOrder.class);
        originalOrderMapper.insertSelective(originalOrder);
    }

    /**
     * 根据ID查询OriginalOrder
     * @param id
     * @return
     */
    @Override
    public OriginalOrder findById(Long id){
        return  originalOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询OriginalOrder全部数据
     * @return
     */
    @Override
    public List<OriginalOrder> findAll() {
        return originalOrderMapper.findAll();
    }


}
