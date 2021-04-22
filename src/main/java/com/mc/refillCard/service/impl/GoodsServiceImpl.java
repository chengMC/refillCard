package com.mc.refillCard.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.refillCard.dao.GoodsMapper;
import com.mc.refillCard.dto.GoodsDto;
import com.mc.refillCard.entity.Goods;
import com.mc.refillCard.service.GoodsService;
import com.mc.refillCard.util.ExcelUtils;
import com.mc.refillCard.vo.GoodsVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/****
 * @Author: MC
 * @Description: Goods业务层接口实现类
 * @Date 2021-3-28 20:28:52
 *****/
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     Goods条件+分页查询
     @param goodsDto 查询条件
     @param page      页码
     @param size      页大小
     @return 分页结果
     */
    @Override
    public PageInfo findPage(GoodsDto goodsDto, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        List<GoodsVo> goodsVos = goodsMapper.findPageVoByExample(goodsDto);
        //执行搜索
        return new PageInfo(goodsVos);
    }

    /**
     * Goods条件查询
     * @param goods
     * @return
     */
    @Override
    public List<Goods> findList(Goods goods){
        //根据构建的条件查询数据
        return goodsMapper.selectByExample(goods);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        goodsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Goods
     * @param goods
     */
    @Override
    public void update(Goods goods){
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    /**
     * 修改Goods
     * @param goodsDto
     */
    @Override
    public void updateDto(GoodsDto goodsDto){
        Goods goods = BeanUtil.copyProperties(goodsDto, Goods.class);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    /**
     * 增加Goods
     * @param goods
     */
    @Override
    public void add(Goods goods){
        goodsMapper.insertSelective(goods);
    }

    /**
     * 增加Goods
     * @param goodsDto
     */
    @Override
    public void addDto(GoodsDto goodsDto){
        Goods goods = BeanUtil.copyProperties(goodsDto, Goods.class);
        goodsMapper.insertSelective(goods);
    }

    /**
     * 根据ID查询Goods
     * @param id
     * @return
     */
    @Override
    public Goods findById(Long id){
        return  goodsMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Goods全部数据
     * @return
     */
    @Override
    public List<Goods> findAll() {
        return goodsMapper.findAll();
    }

    @Override
    public List<Goods> findListByTypeAndPlatform(Integer platform, Integer type) {
        return goodsMapper.findListByType(platform,type);
    }

    @Override
    public void saveGood(GoodsDto goodsDto) {
        Goods goods = goodsMapper.findByArea(goodsDto.getArea());
        goods.setProductId(goodsDto.getProductId());
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public Goods updateGood(String goodId) {
        Goods goods = goodsMapper.findByArea("全国");
        goods.setProductId(Long.valueOf(goodId));
        goodsMapper.updateByPrimaryKeySelective(goods);
        return goodsMapper.findByArea("全国");
    }

    @Override
    public Integer goodsImportData(String platform, String type, MultipartFile file) throws IOException {

        List<Goods> goodsList = new ArrayList<>();

        //获得一个工作薄
        Workbook hssf = ExcelUtils.readExcel(file);

        //获得工作薄中第一个表单
        Sheet hssfSheet = hssf.getSheetAt(0);
        int rows = hssfSheet.getPhysicalNumberOfRows();
        //读取行
        for (int i = 1; i <= rows; i++) {
            Row hssfRow = hssfSheet.getRow(i);
            if (hssfRow == null) {
                continue;
            }
            //产品编号
            String productId = getCellValue(hssfRow.getCell(0));
            //省份
            String area = getCellValue(hssfRow.getCell(1));
            //价格
            String price =  getCellValue(hssfRow.getCell(2));
            //充值数量范围
            String scope =  getCellValue(hssfRow.getCell(3));

            if(StringUtils.isNotBlank(productId)){
                Goods goods = new Goods();
                goods.setProductId(Long.valueOf(productId));
                goods.setProductName("腾讯"+area+"Q币");
                goods.setArea(area);
                goods.setRemake(scope);
                goods.setCreateEmp(price);
                goods.setType(Integer.valueOf(type));
                goods.setPlatform(Integer.valueOf(platform));
                goodsList.add(goods);
            }
        }
        if(CollUtil.isNotEmpty(goodsList)){
            goodsMapper.batchAdd(goodsList);
        }
        return goodsList.size();
    }

    public String getCellValue(Cell cell) {
        if (cell == null) {
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
