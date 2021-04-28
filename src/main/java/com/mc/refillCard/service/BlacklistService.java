package com.mc.refillCard.service;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.BlacklistDto;
import com.mc.refillCard.entity.Blacklist;
import com.mc.refillCard.vo.BlacklistVo;

import java.util.List;
/****
 * @Author: MC
 * @Description:Blacklist业务层接口
 * @Date 2021-4-7 21:03:32
 *****/
public interface BlacklistService {


    /***
     * Blacklist多条件分页查询
     * @param blacklistDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<BlacklistVo> findPage(BlacklistDto blacklistDto, int page, int size);

    /***
     * Blacklist多条件搜索方法
     * @param blacklist
     * @return
     */
    List<Blacklist> findList(Blacklist blacklist);

    /**
    * 根据ID查询Blacklist
    * @param id
    * @return
    */
    Blacklist findById(Long id);

    /***
     * 删除Blacklist
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Blacklist数据
     * @param blacklist
     */
    void update(Blacklist blacklist);

    /***
     * 修改Blacklist数据
     * @param blacklistDto
     */
    void updateDto(BlacklistDto blacklistDto);

    /***
     * 新增Blacklist
     * @param blacklist
     */
    void add(Blacklist blacklist);
    /***
     * 新增Blacklist
     * @param blacklistDto
     */
    void addDto(BlacklistDto blacklistDto);

    /***
     * 查询所有Blacklist
     * @return
     */
    List<Blacklist> findAll();

    void batchAdd(String black);

    /**
     * 查询黑名单
     *
     * @param blacklist
     */
    List<Blacklist> findListByAccount(Blacklist blacklist);

    void updateStatus(BlacklistDto blacklistDto);
}
