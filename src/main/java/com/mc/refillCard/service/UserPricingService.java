package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.UserPricingDto;
import com.mc.refillCard.entity.UserPricing;
import com.mc.refillCard.vo.UserPricingVo;

import java.util.List;

/****
 * @Author: MC
 * @Description:UserPricing业务层接口
 * @Date 2021-4-24 21:40:05
 *****/
public interface UserPricingService {


    /***
     * UserPricing多条件分页查询
     * @param userPricingDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<UserPricingVo> findPage(UserPricingDto userPricingDto, int page, int size);

    /***
     * UserPricing多条件搜索方法
     * @param userPricing
     * @return
     */
    List<UserPricing> findList(UserPricing userPricing);

    /**
    * 根据ID查询UserPricing
    * @param id
    * @return
    */
    UserPricing findById(Long id);

    /***
     * 删除UserPricing
     * @param id
     */
    void delete(Long id);

    /***
     * 修改UserPricing数据
     * @param userPricing
     */
    void update(UserPricing userPricing);

    /***
     * 修改UserPricing数据
     * @param userPricingDto
     */
    void updateDto(UserPricingDto userPricingDto);

    /***
     * 新增UserPricing
     * @param userPricing
     */
    void add(UserPricing userPricing);
    /***
     * 新增UserPricing
     * @param userPricingDto
     */
    void addDto(UserPricingDto userPricingDto);

    /***
     * 查询所有UserPricing
     * @return
     */
    List<UserPricing> findAll();

    UserPricing findByUserIdAndType(Long userId, Integer type);
}
