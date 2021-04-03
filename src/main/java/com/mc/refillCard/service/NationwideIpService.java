package com.mc.refillCard.service;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.NationwideIpDto;
import com.mc.refillCard.entity.NationwideIp;
import com.mc.refillCard.vo.NationwideIpVo;

import java.util.List;
/****
 * @Author: MC
 * @Description:NationwideIp业务层接口
 * @Date 2021-4-3 16:33:59
 *****/
public interface NationwideIpService {


    /***
     * NationwideIp多条件分页查询
     * @param nationwideIpDto
     * @param page
     * @param size
     * @return
     */
    PageInfo<NationwideIpVo> findPage(NationwideIpDto nationwideIpDto, int page, int size);

    /***
     * NationwideIp多条件搜索方法
     * @param nationwideIp
     * @return
     */
    List<NationwideIp> findList(NationwideIp nationwideIp);

    /**
    * 根据ID查询NationwideIp
    * @param id
    * @return
    */
    NationwideIp findById(Long id);

    /***
     * 删除NationwideIp
     * @param id
     */
    void delete(Long id);

    /***
     * 修改NationwideIp数据
     * @param nationwideIp
     */
    void update(NationwideIp nationwideIp);

    /***
     * 修改NationwideIp数据
     * @param nationwideIpDto
     */
    void updateDto(NationwideIpDto nationwideIpDto);

    /***
     * 新增NationwideIp
     * @param nationwideIp
     */
    void add(NationwideIp nationwideIp);
    /***
     * 新增NationwideIp
     * @param nationwideIpDto
     */
    void addDto(NationwideIpDto nationwideIpDto);

    /***
     * 查询所有NationwideIp
     * @return
     */
    List<NationwideIp> findAll();
}
