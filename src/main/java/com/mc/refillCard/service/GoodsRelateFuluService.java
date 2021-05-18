package com.mc.refillCard.service;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.GoodsRelateFuluDto;
import com.mc.refillCard.entity.GoodsRelateFulu;
import com.mc.refillCard.vo.GoodsRelateFuluVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
/****
 * @Author: MC
 * @Description:GoodsRelateFulu业务层接口
 * @Date 2021-3-28 20:16:38
 *****/
public interface GoodsRelateFuluService {


    /***
     * GoodsRelateFulu多条件分页查询
     * @param goodsRelateFuluDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<GoodsRelateFuluVo> findPage(GoodsRelateFuluDto goodsRelateFuluDto, int page, int size);

    /***
     * GoodsRelateFulu多条件搜索方法
     * @param goodsRelateFulu
     * @return
     */
    List<GoodsRelateFulu> findList(GoodsRelateFulu goodsRelateFulu);

    /**
    * 根据ID查询GoodsRelateFulu
    * @param id
    * @return
    */
    GoodsRelateFulu findById(Long id);

    /***
     * 删除GoodsRelateFulu
     * @param id
     */
    void delete(Long id);

    /***
     * 修改GoodsRelateFulu数据
     * @param goodsRelateFulu
     */
    void update(GoodsRelateFulu goodsRelateFulu);

    /***
     * 修改GoodsRelateFulu数据
     * @param goodsRelateFuluDto
     */
    void updateDto(GoodsRelateFuluDto goodsRelateFuluDto) throws Exception;

    /***
     * 新增GoodsRelateFulu
     * @param goodsRelateFulu
     */
    void add(GoodsRelateFulu goodsRelateFulu);
    /***
     * 新增GoodsRelateFulu
     * @param goodsRelateFuluDto
     */
    void addDto(GoodsRelateFuluDto goodsRelateFuluDto) throws Exception;

    /***
     * 查询所有GoodsRelateFulu
     * @return
     */
    List<GoodsRelateFulu> findAll();

    /**
     * 根据商品ID和用户ID查询福禄商品对照
     *
     * @param goodId
     * @param userId
     * @return
     */
   GoodsRelateFulu findByGoodId(Long goodId, Long userId);

    Integer importExcel(String userId, String type, MultipartFile file) throws IOException;

    void updateStatus(GoodsRelateFuluDto goodsRelateFuluDto);
}
