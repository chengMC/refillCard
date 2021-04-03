package com.mc.refillCard.service.impl;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.GoodsMapper;
import com.mc.refillCard.dto.GoodsDto;
import com.mc.refillCard.entity.Goods;
import com.mc.refillCard.service.GoodsService;
import com.mc.refillCard.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/****
 * @Author: MC
 * @Description: Goods业务层接口实现类
 * @Date 2021-3-28 20:28:52
 *****/
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     Goods条件+分页查询
     @param goodsDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(GoodsDto goodsDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<GoodsVo> goodsVos = goodsMapper.findPageVoByExample(goodsDto);
        //执行搜索
        return new PageInfo(goodsVos);
    }

    /**
     * Goods条件查询
     * @param goods
     * @return
     */
    @Override
    public List<Goods> findList(Goods goods){
        //根据构建的条件查询数据
        return goodsMapper.selectByExample(goods);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        goodsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Goods
     * @param goods
     */
    @Override
    public void update(Goods goods){
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    /**
     * 修改Goods
     * @param goodsDto
     */
    @Override
    public void updateDto(GoodsDto goodsDto){
        Goods goods = BeanUtil.copyProperties(goodsDto, Goods.class);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    /**
     * 增加Goods
     * @param goods
     */
    @Override
    public void add(Goods goods){
        goodsMapper.insertSelective(goods);
    }

    /**
     * 增加Goods
     * @param goodsDto
     */
    @Override
    public void addDto(GoodsDto goodsDto){
        Goods goods = BeanUtil.copyProperties(goodsDto, Goods.class);
        goodsMapper.insertSelective(goods);
    }

    /**
     * 根据ID查询Goods
     * @param id
     * @return
     */
    @Override
    public Goods findById(Long id){
        return  goodsMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Goods全部数据
     * @return
     */
    @Override
    public List<Goods> findAll() {
        return goodsMapper.findAll();
    }

    @Override
    public List<Goods> findListByType(Integer type) {
        return goodsMapper.findListByType(type);
    }

    @Override
    public void saveGood(GoodsDto goodsDto) {
        Goods goods = goodsMapper.findByArea(goodsDto.getArea());
        goods.setProductId(goodsDto.getProductId());
        goodsMapper.insertSelective(goods);
    }


}
