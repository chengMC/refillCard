package com.mc.refillCard.service;
import com.mc.refillCard.entity.GoodType;
import com.mc.refillCard.dto.GoodTypeDto;
import com.mc.refillCard.vo.GoodTypeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
/****
 * @Author: MC
 * @Description:GoodType业务层接口
 * @Date 2021-4-7 21:05:20
 *****/
public interface GoodTypeService {


    /***
     * GoodType多条件分页查询
     * @param goodTypeDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<GoodTypeVo> findPage(GoodTypeDto goodTypeDto, int page, int size);

    /***
     * GoodType多条件搜索方法
     * @param goodType
     * @return
     */
    List<GoodType> findList(GoodType goodType);

    /**
    * 根据ID查询GoodType
    * @param id
    * @return
    */
    GoodType findById(Long id);

    /***
     * 删除GoodType
     * @param id
     */
    void delete(Long id);

    /***
     * 修改GoodType数据
     * @param goodType
     */
    void update(GoodType goodType);

    /***
     * 修改GoodType数据
     * @param goodTypeDto
     */
    void updateDto(GoodTypeDto goodTypeDto);

    /***
     * 新增GoodType
     * @param goodType
     */
    void add(GoodType goodType);
    /***
     * 新增GoodType
     * @param goodTypeDto
     */
    void addDto(GoodTypeDto goodTypeDto);

    /***
     * 查询所有GoodType
     * @return
     */
    List<GoodType> findAll();
}
