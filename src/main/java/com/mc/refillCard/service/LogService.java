package com.mc.refillCard.service;

import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dto.LogInformationDto;
import com.mc.refillCard.entity.LogInformation;
import com.mc.refillCard.vo.LogInformationVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/****
 * @Author: MC
 * @Description:Log业务层接口
 * @Date 2020-5-29 9:55:47
 *****/
public interface LogService {


    /***
     * Log多条件搜索方法
     * @param logInformation
     * @param page
     * @param size
     * @return
     */
    PageInfo<LogInformation> findList(LogInformation logInformation, int page, int size);

    /**
     *  操作日志分页列表
     *
     * @param logInformation
     * @param page
     * @param size
     * @return
     */
    PageInfo<LogInformationVo>  findPage(LogInformationDto logInformation, int page, int size);

    /**
     * 操作日志导出
     *
     * @param logInformation
     * @param response
     */
    void export(LogInformationDto logInformation, HttpServletResponse response) throws IOException;

    /**
    * 根据ID查询Log
    * @param id
    * @return
    */
    LogInformation findById(Long id);

    /***
     * 删除Log
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Log数据
     * @param logInformation
     */
    void update(LogInformation logInformation);

    /***
     * 新增Log
     * @param logInformation
     */
    void add(LogInformation logInformation);

    /***
     * 查询所有Log
     * @return
     */
    List<LogInformation> findAll(Long companyId);



}
