package com.mc.refillCard.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.GoodsRelateMapper;
import com.mc.refillCard.dto.GoodsRelateDto;
import com.mc.refillCard.entity.GoodsRelate;
import com.mc.refillCard.service.GoodsRelateService;
import com.mc.refillCard.vo.GoodsRelateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/****
 * @Author: MC
 * @Description: GoodsRelate业务层接口实现类
 * @Date 2021-3-21 16:36:30
 *****/
@Service
public class GoodsRelateServiceImpl implements GoodsRelateService {

    @Autowired
    private GoodsRelateMapper goodsRelateMapper;

    /**
     GoodsRelate条件+分页查询
     @param goodsRelateDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(GoodsRelateDto goodsRelateDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<GoodsRelateVo> goodsRelateVos = goodsRelateMapper.findPageVoByExample(goodsRelateDto);
        //执行搜索
        return new PageInfo(goodsRelateVos);
    }

    /**
     * GoodsRelate条件查询
     * @param goodsRelate
     * @return
     */
    @Override
    public List<GoodsRelate> findList(GoodsRelate goodsRelate){
        //根据构建的条件查询数据
        return goodsRelateMapper.selectByExample(goodsRelate);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        goodsRelateMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改GoodsRelate
     * @param goodsRelate
     */
    @Override
    public void update(GoodsRelate goodsRelate){
        goodsRelateMapper.updateByPrimaryKeySelective(goodsRelate);
    }

    /**
     * 修改GoodsRelate
     * @param goodsRelateDto
     */
    @Override
    public void updateDto(GoodsRelateDto goodsRelateDto){
        GoodsRelate goodsRelate = BeanUtil.copyProperties(goodsRelateDto, GoodsRelate.class);
        goodsRelateMapper.updateByPrimaryKeySelective(goodsRelate);
    }

    /**
     * 增加GoodsRelate
     * @param goodsRelate
     */
    @Override
    public void add(GoodsRelate goodsRelate){
        goodsRelateMapper.insertSelective(goodsRelate);
    }

    /**
     * 增加GoodsRelate
     * @param goodsRelateDto
     */
    @Override
    public void addDto(GoodsRelateDto goodsRelateDto){
        GoodsRelate goodsRelate = BeanUtil.copyProperties(goodsRelateDto, GoodsRelate.class);
        goodsRelateMapper.insertSelective(goodsRelate);
    }

    /**
     * 根据ID查询GoodsRelate
     * @param id
     * @return
     */
    @Override
    public GoodsRelate findById(Long id){
        return  goodsRelateMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询GoodsRelate全部数据
     * @return
     */
    @Override
    public List<GoodsRelate> findAll() {
        return goodsRelateMapper.findAll();
    }

    @Override
    public List<GoodsRelate> findByGoodId(Long goodId) {
        return goodsRelateMapper.findByGoodId(goodId);
    }


}
