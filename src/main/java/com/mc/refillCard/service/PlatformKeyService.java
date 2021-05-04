package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.PlatformKeyDto;
import com.mc.refillCard.entity.PlatformKey;
import com.mc.refillCard.vo.PlatformKeyVo;

import java.util.List;

/****
 * @Author: MC
 * @Description:PlatformKey业务层接口
 * @Date 2021-5-4 22:30:48
 *****/
public interface PlatformKeyService {


    /***
     * PlatformKey多条件分页查询
     * @param platformKeyDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<PlatformKeyVo> findPage(PlatformKeyDto platformKeyDto, int page, int size);

    /***
     * PlatformKey多条件搜索方法
     * @param platformKey
     * @return
     */
    List<PlatformKey> findList(PlatformKey platformKey);

    /**
    * 根据ID查询PlatformKey
    * @param id
    * @return
    */
    PlatformKey findById(Long id);

    /**
     *  根据类型查询平台key
     *
     * @param type
     * @return
     */
    PlatformKey findByAppType(Integer type);

    /***
     * 删除PlatformKey
     * @param id
     */
    void delete(Long id);

    /***
     * 修改PlatformKey数据
     * @param platformKey
     */
    void update(PlatformKey platformKey);

    /***
     * 修改PlatformKey数据
     * @param platformKeyDto
     */
    void updateDto(PlatformKeyDto platformKeyDto);

    /***
     * 新增PlatformKey
     * @param platformKey
     */
    void add(PlatformKey platformKey);
    /***
     * 新增PlatformKey
     * @param platformKeyDto
     */
    void addDto(PlatformKeyDto platformKeyDto);

    /***
     * 查询所有PlatformKey
     * @return
     */
    List<PlatformKey> findAll();

    /**
     * 修改成启用状态
     *
     * @param id
     * @return
     */
    PlatformKey updatePlatformKey(String id);
}
