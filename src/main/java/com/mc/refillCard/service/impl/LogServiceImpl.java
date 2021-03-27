package com.mc.refillCard.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.LogMapper;
import com.mc.refillCard.dto.LogInformationDto;
import com.mc.refillCard.entity.LogInformation;
import com.mc.refillCard.service.LogService;
import com.mc.refillCard.util.ExcelUtils;
import com.mc.refillCard.vo.LogInformationVo;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/****
 * @Author: MC
 * @Description: Log业务层接口实现类
 * @Date 2020-5-29 9:55:47
 *****/
@Service
public class LogServiceImpl implements LogService {

    @Autowired(required = false)
    private LogMapper logMapper;

    /**
     * Log条件查询
     * @param logInformation
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<LogInformation> findList(LogInformation logInformation, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //根据构建的条件查询数据
        return new PageInfo<LogInformation>(logMapper.selectByExample(logInformation));
    }

    @Override
    public PageInfo<LogInformationVo> findPage(LogInformationDto logInformation, int page, int size) {
        //分页
        PageHelper.startPage(page,size);
        return new PageInfo(logMapper.findPage(logInformation));
    }

    @Override
    public void export(LogInformationDto logInformation, HttpServletResponse response) throws IOException {
        List<LogInformationVo> logInformationVos = logMapper.findPage(logInformation);

        String[] headers = {"序号","操作时间","用户名","角色名","所属部门","操作模块","操作内容"};
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 19);
        //表头插入标题
        XSSFRow headerRow = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            XSSFCell headerCell = headerRow.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            headerCell.setCellValue(text);
            headerCell.setCellStyle(ExcelUtils.bigTitle(workbook));
        }
        if (CollectionUtil.isNotEmpty(logInformationVos)) {
            for (int i = 0; i < logInformationVos.size(); i++) {
                int number = 0;
                XSSFRow row = sheet.createRow(i + 1);
                LogInformationVo logInformationVo = logInformationVos.get(i);
                XSSFCell cell = row.createCell(number++);
                cell.setCellStyle(ExcelUtils.bigCenter(workbook));
                cell.setCellValue(i + 1);
                cell = row.createCell(number++);
                cell.setCellStyle(ExcelUtils.bigCenter(workbook));
                cell.setCellValue(DateUtil.format(logInformationVo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                cell = row.createCell(number++);
                cell.setCellStyle(ExcelUtils.bigCenter(workbook));
                cell.setCellValue(logInformationVo.getUserName());
                cell = row.createCell(number++);
                cell.setCellStyle(ExcelUtils.bigCenter(workbook));
                cell.setCellValue(logInformationVo.getRoleName());
                cell = row.createCell(number++);
                cell.setCellStyle(ExcelUtils.bigCenter(workbook));
                cell.setCellValue(logInformationVo.getDepartmentName());
                cell = row.createCell(number++);
                cell.setCellStyle(ExcelUtils.bigCenter(workbook));
                cell.setCellValue(logInformationVo.getAction());
                cell = row.createCell(number++);
                cell.setCellStyle(ExcelUtils.bigCenter(workbook));
                cell.setCellValue(logInformationVo.getResult());
            }
        }
        response.setContentType("application/octet-stream");
        //默认Excel名称
        response.setHeader("Content-Disposition", "attachment;filename="
                .concat(String.valueOf(URLEncoder.encode("操作日志导出.xlsx", "UTF-8"))));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        logMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Log
     * @param logInformation
     */
    @Override
    public void update(LogInformation logInformation){
        logMapper.updateByPrimaryKeySelective(logInformation);
    }

    /**
     * 增加Log
     * @param logInformation
     */
    @Override
    public void add(LogInformation logInformation){
        logMapper.insertSelective(logInformation);
    }

    /**
     * 根据ID查询Log
     * @param id
     * @return
     */
    @Override
    public LogInformation findById(Long id){
        return  logMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Log全部数据
     * @return
     */
    @Override
    public List<LogInformation> findAll(Long companyId) {
        return logMapper.findAll(companyId);
    }


}
