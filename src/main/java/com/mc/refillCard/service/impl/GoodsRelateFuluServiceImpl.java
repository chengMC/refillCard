package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.GoodsRelateFuluMapper;
import com.mc.refillCard.dto.GoodsRelateFuluDto;
import com.mc.refillCard.entity.GoodsRelateFulu;
import com.mc.refillCard.service.GoodsRelateFuluService;
import com.mc.refillCard.vo.GoodsRelateFuluVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/****
 * @Author: MC
 * @Description: GoodsRelateFulu业务层接口实现类
 * @Date 2021-3-28 20:16:38
 *****/
@Service
public class GoodsRelateFuluServiceImpl implements GoodsRelateFuluService {

    @Autowired
    private GoodsRelateFuluMapper goodsRelateFuluMapper;

    /**
     GoodsRelateFulu条件+分页查询
     @param goodsRelateFuluDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(GoodsRelateFuluDto goodsRelateFuluDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<GoodsRelateFuluVo> goodsRelateFuluVos = goodsRelateFuluMapper.findPageVoByExample(goodsRelateFuluDto);
        //执行搜索
        return new PageInfo(goodsRelateFuluVos);
    }

    /**
     * GoodsRelateFulu条件查询
     * @param goodsRelateFulu
     * @return
     */
    @Override
    public List<GoodsRelateFulu> findList(GoodsRelateFulu goodsRelateFulu){
        //根据构建的条件查询数据
        return goodsRelateFuluMapper.selectByExample(goodsRelateFulu);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        goodsRelateFuluMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改GoodsRelateFulu
     * @param goodsRelateFulu
     */
    @Override
    public void update(GoodsRelateFulu goodsRelateFulu){
        goodsRelateFuluMapper.updateByPrimaryKeySelective(goodsRelateFulu);
    }

    /**
     * 修改GoodsRelateFulu
     * @param goodsRelateFuluDto
     */
    @Override
    public void updateDto(GoodsRelateFuluDto goodsRelateFuluDto){
        GoodsRelateFulu goodsRelateFulu = BeanUtil.copyProperties(goodsRelateFuluDto, GoodsRelateFulu.class);
        goodsRelateFuluMapper.updateByPrimaryKeySelective(goodsRelateFulu);
    }

    /**
     * 增加GoodsRelateFulu
     * @param goodsRelateFulu
     */
    @Override
    public void add(GoodsRelateFulu goodsRelateFulu){
        goodsRelateFuluMapper.insertSelective(goodsRelateFulu);
    }

    /**
     * 增加GoodsRelateFulu
     * @param goodsRelateFuluDto
     */
    @Override
    public void addDto(GoodsRelateFuluDto goodsRelateFuluDto){
        GoodsRelateFulu goodsRelateFulu = BeanUtil.copyProperties(goodsRelateFuluDto, GoodsRelateFulu.class);
        goodsRelateFuluMapper.insertSelective(goodsRelateFulu);
    }

    /**
     * 根据ID查询GoodsRelateFulu
     * @param id
     * @return
     */
    @Override
    public GoodsRelateFulu findById(Long id){
        return  goodsRelateFuluMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询GoodsRelateFulu全部数据
     * @return
     */
    @Override
    public List<GoodsRelateFulu> findAll() {
        return goodsRelateFuluMapper.findAll();
    }

    @Override
    public GoodsRelateFulu findByGoodId(Long goodId, Long userId) {
        return goodsRelateFuluMapper.findByGoodId(goodId,userId);
    }

    @Override
    public Integer importExcel(String userId, String type, MultipartFile file) throws IOException {
        List<GoodsRelateFulu> goodsRelateFulus = new ArrayList<>();
        //获得一个工作薄
        HSSFWorkbook hssf = new HSSFWorkbook(file.getInputStream());
        //获得工作薄中第一个表单
        HSSFSheet hssfSheet = hssf.getSheetAt(0);
        int rows = hssfSheet.getPhysicalNumberOfRows();
        //读取行
        for (int i = 1; i <= rows; i++) {
            HSSFRow hssfRow = hssfSheet.getRow(i);
            if (hssfRow == null) {
                continue;
            }
           //数量
            String nominal = getCellValue(hssfRow.getCell(0));
            if(nominal.indexOf(" ")>-1){
                nominal = nominal.replace(" ","");
            }
            //宝贝ID
            String goodId = getCellValue(hssfRow.getCell(1));
            if(goodId.indexOf(" ")>-1){
                 goodId = goodId.replace(" ","");
            }
            if(StringUtils.isNotBlank(goodId)){
                GoodsRelateFulu goodsRelateFulu = new GoodsRelateFulu();
                goodsRelateFulu.setUserId(Long.valueOf(userId));
                goodsRelateFulu.setGoodId(Long.valueOf(goodId));
                goodsRelateFulu.setNominal(nominal);
                goodsRelateFulu.setType(Integer.valueOf(type));
                goodsRelateFulu.setStatus(1);
                goodsRelateFulus.add(goodsRelateFulu);
            }
        }
        if(CollUtil.isNotEmpty(goodsRelateFulus)){
            goodsRelateFuluMapper.batchAdd(goodsRelateFulus);
        }
        return goodsRelateFulus.size();
    }

    public String getCellValue(HSSFCell cell) {
        if(cell == null){
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_NUMERIC:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return "";
        }
    }


}
