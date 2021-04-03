package com.mc.refillCard.service;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.GoodsDto;
import com.mc.refillCard.entity.Goods;
import com.mc.refillCard.vo.GoodsVo;

import java.util.List;
/****
 * @Author: MC
 * @Description:Goods业务层接口
 * @Date 2021-3-28 20:28:52
 *****/
public interface GoodsService {


    /***
     * Goods多条件分页查询
     * @param goodsDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<GoodsVo> findPage(GoodsDto goodsDto, int page, int size);

    /***
     * Goods多条件搜索方法
     * @param goods
     * @return
     */
    List<Goods> findList(Goods goods);

    /**
    * 根据ID查询Goods
    * @param id
    * @return
    */
    Goods findById(Long id);

    /***
     * 删除Goods
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Goods数据
     * @param goods
     */
    void update(Goods goods);

    /***
     * 修改Goods数据
     * @param goodsDto
     */
    void updateDto(GoodsDto goodsDto);

    /***
     * 新增Goods
     * @param goods
     */
    void add(Goods goods);
    /***
     * 新增Goods
     * @param goodsDto
     */
    void addDto(GoodsDto goodsDto);

    /***
     * 查询所有Goods
     * @return
     */
    List<Goods> findAll();

    /**
     * 根据类型查询商品
     *
     * @param type
     * @return
     */
    List<Goods> findListByType(Integer type);

    /**
     *
     * @param productId
     */
    void saveGood(GoodsDto goodsDto);

}
