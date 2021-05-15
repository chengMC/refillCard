package com.mc.refillCard.service;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.UserBalanceDto;
import com.mc.refillCard.dto.UserRelateDto;
import com.mc.refillCard.entity.UserRelate;
import com.mc.refillCard.vo.UserRelateValueVo;
import com.mc.refillCard.vo.UserRelateVo;

import java.util.List;
/****
 * @Author: MC
 * @Description:UserRelate业务层接口
 * @Date 2021-3-21 16:20:43
 *****/
public interface UserRelateService {


    /***
     * UserRelate多条件分页查询
     * @param userRelateDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<UserRelateVo> findPage(UserRelateDto userRelateDto, int page, int size);

    /***
     * UserRelate多条件搜索方法
     * @param userRelate
     * @return
     */
    List<UserRelate> findList(UserRelate userRelate);

    /**
    * 根据ID查询UserRelate
    * @param id
    * @return
    */
    UserRelate findById(Long id);

    /***
     * 删除UserRelate
     * @param id
     */
    void delete(Long id);

    /***
     * 修改UserRelate数据
     * @param userRelate
     */
    void update(UserRelate userRelate);

    /***
     * 修改UserRelate数据
     * @param userRelateDto
     */
    void updateDto(UserRelateDto userRelateDto);

    /***
     * 新增UserRelate
     * @param userRelate
     */
    void add(UserRelate userRelate);
    /***
     * 新增UserRelate
     * @param userRelateDto
     */
    void addDto(UserRelateDto userRelateDto);

    /***
     * 查询所有UserRelate
     * @return
     */
    List<UserRelateVo> findAll();

    /**
     * 根据阿奇索用户查询用户
     *
     * @param platformUserId
     * @return
     */
    UserRelate findByPlatformUserId(String platformUserId);

    /**
     * 账号加款
     *
     * @param userBalanceDto
     */
    void updateBalance(UserBalanceDto userBalanceDto) throws Exception;

    /**
     * 账号加款
     *
     * @param userBalanceDto
     */
    void updateBalanceV1(UserBalanceDto userBalanceDto) throws Exception;

    /**
     * 账号扣款
     *
     * @param userBalanceDto
     */
    void deductBalance(UserBalanceDto userBalanceDto) throws Exception;

    List<UserRelateValueVo> find();

}
