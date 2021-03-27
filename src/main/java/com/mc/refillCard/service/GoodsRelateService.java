package com.mc.refillCard.service;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.GoodsRelateDto;
import com.mc.refillCard.entity.GoodsRelate;
import com.mc.refillCard.vo.GoodsRelateVo;

import java.util.List;
/****
 * @Author: MC
 * @Description:GoodsRelate业务层接口
 * @Date 2021-3-21 16:36:30
 *****/
public interface GoodsRelateService {


    /***
     * GoodsRelate多条件分页查询
     * @param goodsRelateDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<GoodsRelateVo> findPage(GoodsRelateDto goodsRelateDto, int page, int size);

    /***
     * GoodsRelate多条件搜索方法
     * @param goodsRelate
     * @return
     */
    List<GoodsRelate> findList(GoodsRelate goodsRelate);

    /**
    * 根据ID查询GoodsRelate
    * @param id
    * @return
    */
    GoodsRelate findById(Long id);

    /***
     * 删除GoodsRelate
     * @param id
     */
    void delete(Long id);

    /***
     * 修改GoodsRelate数据
     * @param goodsRelate
     */
    void update(GoodsRelate goodsRelate);

    /***
     * 修改GoodsRelate数据
     * @param goodsRelateDto
     */
    void updateDto(GoodsRelateDto goodsRelateDto);

    /***
     * 新增GoodsRelate
     * @param goodsRelate
     */
    void add(GoodsRelate goodsRelate);
    /***
     * 新增GoodsRelate
     * @param goodsRelateDto
     */
    void addDto(GoodsRelateDto goodsRelateDto);

    /***
     * 查询所有GoodsRelate
     * @return
     */
    List<GoodsRelate> findAll();

    /**
     * 根据宝贝id查询商品对应关系
     *
     *
     * @param goodId
     * @param goodId
     * @return
     */
    List<GoodsRelate> findByGoodId(Long goodId, Long userId);
}
