package com.mc.refillCard.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.GoodsRelateFuluMapper;
import com.mc.refillCard.dto.GoodsRelateFuluDto;
import com.mc.refillCard.entity.GoodsRelateFulu;
import com.mc.refillCard.service.GoodsRelateFuluService;
import com.mc.refillCard.vo.GoodsRelateFuluVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/****
 * @Author: MC
 * @Description: GoodsRelateFulu业务层接口实现类
 * @Date 2021-3-28 20:16:38
 *****/
@Service
public class GoodsRelateFuluServiceImpl implements GoodsRelateFuluService {

    @Autowired
    private GoodsRelateFuluMapper goodsRelateFuluMapper;

    /**
     GoodsRelateFulu条件+分页查询
     @param goodsRelateFuluDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(GoodsRelateFuluDto goodsRelateFuluDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<GoodsRelateFuluVo> goodsRelateFuluVos = goodsRelateFuluMapper.findPageVoByExample(goodsRelateFuluDto);
        //执行搜索
        return new PageInfo(goodsRelateFuluVos);
    }

    /**
     * GoodsRelateFulu条件查询
     * @param goodsRelateFulu
     * @return
     */
    @Override
    public List<GoodsRelateFulu> findList(GoodsRelateFulu goodsRelateFulu){
        //根据构建的条件查询数据
        return goodsRelateFuluMapper.selectByExample(goodsRelateFulu);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        goodsRelateFuluMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改GoodsRelateFulu
     * @param goodsRelateFulu
     */
    @Override
    public void update(GoodsRelateFulu goodsRelateFulu){
        goodsRelateFuluMapper.updateByPrimaryKeySelective(goodsRelateFulu);
    }

    /**
     * 修改GoodsRelateFulu
     * @param goodsRelateFuluDto
     */
    @Override
    public void updateDto(GoodsRelateFuluDto goodsRelateFuluDto){
        GoodsRelateFulu goodsRelateFulu = BeanUtil.copyProperties(goodsRelateFuluDto, GoodsRelateFulu.class);
        goodsRelateFuluMapper.updateByPrimaryKeySelective(goodsRelateFulu);
    }

    /**
     * 增加GoodsRelateFulu
     * @param goodsRelateFulu
     */
    @Override
    public void add(GoodsRelateFulu goodsRelateFulu){
        goodsRelateFuluMapper.insertSelective(goodsRelateFulu);
    }

    /**
     * 增加GoodsRelateFulu
     * @param goodsRelateFuluDto
     */
    @Override
    public void addDto(GoodsRelateFuluDto goodsRelateFuluDto){
        GoodsRelateFulu goodsRelateFulu = BeanUtil.copyProperties(goodsRelateFuluDto, GoodsRelateFulu.class);
        goodsRelateFuluMapper.insertSelective(goodsRelateFulu);
    }

    /**
     * 根据ID查询GoodsRelateFulu
     * @param id
     * @return
     */
    @Override
    public GoodsRelateFulu findById(Long id){
        return  goodsRelateFuluMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询GoodsRelateFulu全部数据
     * @return
     */
    @Override
    public List<GoodsRelateFulu> findAll() {
        return goodsRelateFuluMapper.findAll();
    }

    @Override
    public GoodsRelateFulu findByGoodId(Long goodId, Long userId) {
        return goodsRelateFuluMapper.findByGoodId(goodId,userId);
    }


}
